package com.example.demo.web;

import com.example.demo.model.LoanRecord;
import com.example.demo.model.Participant;
import com.example.demo.model.SupportingDocument;
import com.example.demo.repository.LoanRecordRepository;
import com.example.demo.repository.ParticipantRepository;
import com.example.demo.repository.SupportingDocumentRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Controller
public class WebController {

    private final ParticipantRepository participantRepo;
    private final LoanRecordRepository loanRecordRepo;
    private final SupportingDocumentRepository docRepo;

    @Value("${file.upload-dir}")
    private String uploadDir;

    public WebController(ParticipantRepository participantRepo,
                         LoanRecordRepository loanRecordRepo,
                         SupportingDocumentRepository docRepo) {
        this.participantRepo = participantRepo;
        this.loanRecordRepo = loanRecordRepo;
        this.docRepo = docRepo;
    }

    @GetMapping("/participant-form")
    public String participantForm(Model model) {
        model.addAttribute("participant", new Participant());
        return "participant";
    }

    @PostMapping("/participants")
    public String submitParticipant(@ModelAttribute Participant participant, Model model) {
        Participant saved = participantRepo.save(participant);
        // redirect to loan form with participant id
        return "redirect:/loan-form?participantId=" + saved.getId();
    }

    @GetMapping("/loan-form")
    public String loanForm(@RequestParam(value = "participantId", required = false) Long participantId, Model model) {
        LoanRecord loanRecord = new LoanRecord();
        if (participantId != null) {
            Participant p = new Participant();
            p.setId(participantId);
            loanRecord.setParticipant(p);
        }
        model.addAttribute("loanRecord", loanRecord);
        return "loan";
    }

    @PostMapping("/loan-records")
    public String submitLoan(@ModelAttribute LoanRecord loanRecord, @RequestParam(value = "files", required = false) MultipartFile[] files, Model model) throws IOException {
        // Ensure participant exists if id provided
        if (loanRecord.getParticipant() != null && loanRecord.getParticipant().getId() != null) {
            Long pid = loanRecord.getParticipant().getId();
            Participant p = participantRepo.findById(pid).orElse(null);
            if (p == null) {
                model.addAttribute("error", "Participant not found");
                return "loan";
            }
            loanRecord.setParticipant(p);
        }
        LoanRecord saved = loanRecordRepo.save(loanRecord);

        // handle files
        if (files != null && files.length > 0) {
            Path dir = Paths.get(uploadDir).toAbsolutePath().normalize();
            Files.createDirectories(dir);
            for (MultipartFile file : files) {
                if (file.isEmpty()) continue;
                String filename = StringUtils.cleanPath(file.getOriginalFilename());
                Path target = dir.resolve(System.currentTimeMillis() + "_" + filename);
                Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);

                SupportingDocument doc = new SupportingDocument();
                doc.setFilename(filename);
                doc.setContentType(file.getContentType());
                doc.setSize(file.getSize());
                doc.setPath(target.toString());
                doc.setLoanRecord(saved);
                docRepo.save(doc);
            }
        }

        return "redirect:/participant-form";
    }
}

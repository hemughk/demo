package com.example.demo.controller;

import com.example.demo.model.LoanRecord;
import com.example.demo.model.Participant;
import com.example.demo.model.SupportingDocument;
import com.example.demo.repository.LoanRecordRepository;
import com.example.demo.repository.ParticipantRepository;
import com.example.demo.repository.SupportingDocumentRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;

/**
 * REST API Controller for Loan Records
 * For form-based submission, use WebController instead
 */
@RestController
@RequestMapping("/api/loan-records")
public class LoanRecordController {
    private final LoanRecordRepository loanRepo;
    private final ParticipantRepository participantRepo;
    private final SupportingDocumentRepository docRepo;

    @Value("${file.upload-dir}")
    private String uploadDir;

    public LoanRecordController(LoanRecordRepository loanRepo,
                                ParticipantRepository participantRepo,
                                SupportingDocumentRepository docRepo) {
        this.loanRepo = loanRepo;
        this.participantRepo = participantRepo;
        this.docRepo = docRepo;
    }

    @GetMapping
    public List<LoanRecord> list() {
        return loanRepo.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<LoanRecord> get(@PathVariable Long id) {
        return loanRepo.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody LoanRecord record) {
        // validate participant reference if provided
        if (record.getParticipant() != null && record.getParticipant().getId() != null) {
            Participant p = participantRepo.findById(record.getParticipant().getId()).orElse(null);
            if (p == null) return ResponseEntity.badRequest().body("Participant not found");
            record.setParticipant(p);
        }
        LoanRecord saved = loanRepo.save(record);
        return ResponseEntity.ok(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody LoanRecord record) {
        return loanRepo.findById(id).map(existing -> {
            record.setId(existing.getId());
            // ensure participant exists if provided
            if (record.getParticipant() != null && record.getParticipant().getId() != null) {
                Participant p = participantRepo.findById(record.getParticipant().getId()).orElse(null);
                if (p == null) return ResponseEntity.badRequest().body("Participant not found");
                record.setParticipant(p);
            }
            LoanRecord saved = loanRepo.save(record);
            return ResponseEntity.ok(saved);
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!loanRepo.existsById(id)) return ResponseEntity.notFound().build();
        loanRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // File upload endpoint - saves file to disk and metadata to SupportingDocument
    @PostMapping("/{loanId}/files")
    public ResponseEntity<?> uploadFiles(@PathVariable Long loanId, @RequestParam("files") MultipartFile[] files) throws IOException {
        LoanRecord loan = loanRepo.findById(loanId).orElse(null);
        if (loan == null) return ResponseEntity.notFound().build();

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
            doc.setLoanRecord(loan);
            docRepo.save(doc);
        }
        return ResponseEntity.ok().build();
    }
}

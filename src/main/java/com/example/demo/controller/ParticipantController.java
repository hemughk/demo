package com.example.demo.controller;

import com.example.demo.model.Participant;
import com.example.demo.repository.ParticipantRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/participants")
public class ParticipantController {
    private final ParticipantRepository repo;

    public ParticipantController(ParticipantRepository repo) { this.repo = repo; }

    @GetMapping
    public List<Participant> list() { return repo.findAll(); }

    @GetMapping("/{id}")
    public ResponseEntity<Participant> get(@PathVariable Long id) {
        return repo.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Participant create(@RequestBody Participant p) { return repo.save(p); }

    @PutMapping("/{id}")
    public ResponseEntity<Participant> update(@PathVariable Long id, @RequestBody Participant p) {
        return repo.findById(id).map(existing -> {
            p.setId(existing.getId());
            return ResponseEntity.ok(repo.save(p));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!repo.existsById(id)) return ResponseEntity.notFound().build();
        repo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

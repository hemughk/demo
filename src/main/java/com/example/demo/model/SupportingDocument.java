package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data @NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name = "supporting_documents")
public class SupportingDocument {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String filename;
    private String contentType;
    private Long size;
    private String path;

    @ManyToOne
    @JoinColumn(name = "loan_record_id")
    private LoanRecord loanRecord;
}

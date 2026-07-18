package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data @NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name = "loan_records")
public class LoanRecord {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // link to participant (form1)
    @ManyToOne
    @JoinColumn(name = "participant_id")
    private Participant participant;

    // one-to-one sub-sections
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "loan_details_id")
    private LoanDetails loanDetails;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "security_details_id")
    private SecurityDetails securityDetails;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "entity_details_id")
    private EntityDetails entityDetails;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "estamp_details_id")
    private EStampDetails eStampDetails;

    @OneToMany(mappedBy = "loanRecord", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SupportingDocument> supportingDocuments;
}

package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data @NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name = "security_details")
public class SecurityDetails {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String scrty_loanno;
    private String dtofvltn;
    private String vlofscrty;
    private String assetid;
    private String scrtyidcersai;
    private String asstyp;
    private String scrtyidroc;
    private String dscofscrty;
    private String typofchrg;
    private String dtofcrtn;
}

package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data @NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name = "loan_details")
public class LoanDetails {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String loanno;
    private String snctnno;
    private String chgamt;
    private String crdtsubtyp;
    private String currofsanc;
    private String dtofsnctn;
    private String fcltynm;
    private String fundtyp;
    private String isacctclosed;
    private String ntrofcrdt;
    private String emiamt;
    private String rtofint;
    private String amtovrdue;
    private String dtofdbrs;
    private String intamt;
    private String oldaccno;
    private String priamt;
    private String dpd;
    private String dp;
    private String loc;
    private String rpyfrq;
    private String lndngarrg;
    private String snctnamt;
    private String tenure;
    private String toutstndamt;
    private String remarkparta;
    private String bu;
}

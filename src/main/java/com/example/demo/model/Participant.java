package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data @NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name = "participants")
public class Participant {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String prtcptenttyId;
    private String fulnm;
    private String partytyp;
    private String cntrprtycntnm;
    private String cntrprtycntdsgn;
    private String cntrprtycntmobno;
    private String altmobno;
    private String emlid;
    private String altemlid;
    private String panno;
    private String signatoryAadhar;
    private String signatoryGender;
    private String cntrprtyaddr;
    private String comaddr;
    private String pin;
    private String regoffpin;
    private String doi; // string for now
    private String lglcnstn;
    private String reltocntrct;
    private String ovdtype;
    private String ovdid;
    private String cin;
    private String kin;
    private Integer seqno;
    private String documentIds; // comma separated if needed
}

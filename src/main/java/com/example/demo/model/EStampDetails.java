package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data @NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name = "estamp_details")
public class EStampDetails {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstparty;
    private String considerationPrice;
    private String stampDutyAmount;
    private String stampdutyPaidby;
    private String secondparty;
    private String documentID;
    private String articleCode;
    private String descriptionofDocument;
    private String firstPartyOVDType;
    private String firstPartyOVDValue;
    private String secondPartyOVDType;
    private String secondPartyOVDValue;
    private String firstPartyPin;
    private String secondPartyPin;
}

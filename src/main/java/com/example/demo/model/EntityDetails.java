package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data @NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name = "entity_details")
public class EntityDetails {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String entty_pan;
    private String entty_name;
    private String lgl_cnstn;
    private String doi;
    private String eml_id;
    private String cntct_no;
    private String reg_addr;
    private String reg_pin;
    private String comm_addr;
    private String comm_pin;
}

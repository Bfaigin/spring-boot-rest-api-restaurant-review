package com.springbootproject.restfulapp.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Restaurant {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String line1;
    private String city;
    private String state;
    private String zipCode;

    private String phoneNumber;
    private String website;


    private String overallScore;
    private String peanutScore;
    private String eggScore;
    private String dairyScore;
}

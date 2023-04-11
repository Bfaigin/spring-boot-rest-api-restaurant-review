package com.springbootproject.restfulapp.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.Data;

@Entity
@Data
public class User {

    @Id
    @GeneratedValue
    private Long id;

    private String displayName;

    private String city;
    private String state;
    private String zipCode;

    private Boolean peanutWatch;
    private Boolean dairyWatch;
    private Boolean eggWatch;
}

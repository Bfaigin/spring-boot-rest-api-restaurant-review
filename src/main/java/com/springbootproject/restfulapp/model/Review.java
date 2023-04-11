package com.springbootproject.restfulapp.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Review {

    @Id
    @GeneratedValue
    private Long id;

    private String submittedBy;
    private Long restaurantId;
    private String review;

    private Integer peanutScore;
    private Integer eggScore;
    private Integer dairyScore;

    private ReviewStatus status;
}

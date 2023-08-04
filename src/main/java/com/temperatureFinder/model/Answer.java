package com.temperatureFinder.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class Answer {

    @Id
    @GeneratedValue(strategy= GenerationType.TABLE)

    private int id;
    private String answername;
    private String postedBy;
}

package com.temperatureFinder.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Question {
    @Id
    @GeneratedValue(strategy=GenerationType.TABLE)
    private int id;
    private String qname;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="qid")
    @OrderColumn(name="type")
    private List<Answer> answers;
}

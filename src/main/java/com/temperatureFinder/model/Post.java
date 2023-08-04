package com.temperatureFinder.model;


import lombok.Data;

import javax.persistence.*;

@Data

@Entity
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name ="title" )
    private String title;
    @Column(name = "description")
    private String description;
    @Column(name = "content")
    private String content;
}

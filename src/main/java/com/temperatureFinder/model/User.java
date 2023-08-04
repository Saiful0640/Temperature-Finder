package com.temperatureFinder.model;

import com.sun.istack.NotNull;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Data
@Entity
@Table(name = "user")
public class User {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private long id;

        @NotNull
        @NotEmpty
        @Column(name = "firstname")
        private String firstName;

        @NotNull
        @NotEmpty
        @Column(name = "lastname")
        private String lastName;

        @NotNull
        @NotEmpty
        @Column(name = "password")
        private String password;
        @Column(name = "matchingpassword")
        private String matchingPassword;

        @NotNull
        @NotEmpty
        @Column(name = "email")
        private String email;


}

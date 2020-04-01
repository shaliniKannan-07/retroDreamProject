package com.cognizant.loginservice.entity;

import lombok.*;

import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
@Table(name= "user")
@Data
@NoArgsConstructor
@Entity
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private long id;
    @NotNull
    @Column(name = "username")
    private String username;
    @NotNull
    @Column(name = "firstname")
    private String firstname;
    @NotNull
    @Column(name = "lastname")
    private String lastname;
    @NotNull
    @Column(name = "contactnumber")
    private String contactnumber;
    @NotNull
    @Column(name = "password")
    private String password;
    @Column(name = "dateofbirth")
    private Date dateofbirth;
    @NotNull
    @Column(name = "gender")
    private String gender;
    @NotNull
    @Column(name = "role")
    private String role;

}




package com.microservices.user.UserService.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Users")
@Getter
@Setter
public class User {

    @Id
    private String id;

    @Column(length = 20)
    private String name;

    private String email;
    private String about;

    @Transient
    private List<Rating> ratings = new ArrayList<>();

}

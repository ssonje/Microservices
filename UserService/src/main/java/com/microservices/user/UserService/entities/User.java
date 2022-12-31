package com.microservices.user.UserService.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "Users")
public class User {

    @Id
    private String id;

    @Column(length = 50)
    private String name;

    private String email;
    private String about;

    @Transient
    private List<Rating> ratings = new ArrayList<>();

}

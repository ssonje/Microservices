package com.microservices.hotel.entities;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "hotel")
public class Hotel {

    @Id
    private String id;
    private String name;
    private String location;
    private String about;

}

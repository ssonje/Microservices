package com.microservices.rating.RatingService.entities;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "Rating")
public class Rating {

    @Id
    private String id;

    @Column(nullable = false)
    private String userID;

    private String hotelID;
    private Integer rating;

    @Column(length = 1000)
    private String feedback;

}

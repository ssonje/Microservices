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
    private String userID;
    private String hotelID;
    private Integer rating;
    private String feedback;

}

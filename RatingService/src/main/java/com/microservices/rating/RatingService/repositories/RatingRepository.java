package com.microservices.rating.RatingService.repositories;

import com.microservices.rating.RatingService.entities.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RatingRepository extends JpaRepository<Rating, Integer> {

    List<Rating> findByUserID(Integer userID);
    List<Rating> findByHotelID(Integer hotelID);

}

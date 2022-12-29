package com.microservices.rating.RatingService.payloads;

import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class APIResponse {

    private String message;
    private Boolean responseStatus;
    private HttpStatus httpStatus;

}

package com.microservices.user.service.payloads;

import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class APIResponse {

    private String message;
    private Boolean status;
    private HttpStatus httpStatus;

}

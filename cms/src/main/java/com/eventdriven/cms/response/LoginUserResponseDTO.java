package com.eventdriven.cms.response;

import lombok.Data;

@Data
public class LoginUserResponseDTO {
    
    private String token;
    private String message;
}

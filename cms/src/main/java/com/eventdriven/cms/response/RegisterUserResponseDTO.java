package com.eventdriven.cms.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class RegisterUserResponseDTO {

    private Long id;
    private String name;
    private String email;
    private String role;
}

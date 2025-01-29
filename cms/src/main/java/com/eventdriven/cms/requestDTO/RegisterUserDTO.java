package com.eventdriven.cms.requestDTO;

import lombok.Data;

@Data
public class RegisterUserDTO {
    private String name;
    private String email;
    private String password;
}

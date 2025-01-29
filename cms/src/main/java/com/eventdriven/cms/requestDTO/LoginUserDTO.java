package com.eventdriven.cms.requestDTO;

import lombok.Data;

@Data
public class LoginUserDTO {
    
    private String email;
    private String password;
}

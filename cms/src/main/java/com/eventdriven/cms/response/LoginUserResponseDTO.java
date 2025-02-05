package com.eventdriven.cms.response;

import lombok.Data;

@Data
public class LoginUserResponseDTO {
    
    private String token;
    private String message;
    private Userlogin user;


    @Data
    public static class Userlogin{
        private Long id;
        private String name;
        private String email;
    }
}

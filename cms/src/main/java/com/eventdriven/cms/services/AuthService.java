package com.eventdriven.cms.services;

import java.util.HashMap;

import com.eventdriven.cms.domain.AppUser;
import com.eventdriven.cms.requestDTO.LoginUserDTO;
import com.eventdriven.cms.requestDTO.RegisterUserDTO;
import com.eventdriven.cms.response.LoginUserResponseDTO;

public interface AuthService {

    public AppUser registerUser(RegisterUserDTO data);

    public HashMap<String, LoginUserResponseDTO.Userlogin> loginUser(LoginUserDTO data);
}

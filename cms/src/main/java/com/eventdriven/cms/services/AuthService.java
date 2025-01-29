package com.eventdriven.cms.services;

import com.eventdriven.cms.domain.AppUser;
import com.eventdriven.cms.requestDTO.LoginUserDTO;
import com.eventdriven.cms.requestDTO.RegisterUserDTO;

public interface AuthService {

    public AppUser registerUser(RegisterUserDTO data);

    public String loginUser(LoginUserDTO data);
}

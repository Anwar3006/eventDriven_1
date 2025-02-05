package com.eventdriven.cms.services;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.eventdriven.cms.domain.AppUser;
import com.eventdriven.cms.domain.USER_ROLES;
import com.eventdriven.cms.repository.AppUserRepository;
import com.eventdriven.cms.requestDTO.LoginUserDTO;
import com.eventdriven.cms.requestDTO.RegisterUserDTO;
import com.eventdriven.cms.response.LoginUserResponseDTO;
import com.eventdriven.cms.security.PasswordEncoder;
import com.eventdriven.cms.security.session.JwtGenerator;
import com.eventdriven.cms.util.CreateAuth;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService{

    private final AppUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtGenerator jwtGenerator;
    private final CreateAuth createAuth;

    @Override
    public AppUser registerUser(RegisterUserDTO register) {
        Optional<AppUser> userFound = userRepository.findByEmail(register.getEmail());
        if(userFound.isPresent()){
            return userFound.get();
        }

        AppUser newUser = new AppUser();
        newUser.setName(register.getName());
        newUser.setEmail(register.getEmail());
        newUser.setPassword(passwordEncoder.bCryptPasswordEncoder().encode(register.getPassword()));
        newUser.setRole(USER_ROLES.ROLE_USER);
        userRepository.save(newUser);
        return newUser;
    }

    @Override
    public HashMap<String, LoginUserResponseDTO.Userlogin> loginUser(LoginUserDTO data) {
        Optional<AppUser> userFound = userRepository.findByEmail(data.getEmail());
        if(userFound.isEmpty()){
            // throw UsernameNotFoundException
        }

        AppUser user = userFound.get();

        if (!passwordEncoder.bCryptPasswordEncoder().matches(data.getPassword(), user.getPassword())){
            //throw BadCredentialsException
        }

        //create the auth object and set the jwt
        Authentication session = createAuth.createAuthObj(user);

        HashMap<String, LoginUserResponseDTO.Userlogin> loginObj = new HashMap<>();
        String jwt = jwtGenerator.generateToken(session);

        
        LoginUserResponseDTO.Userlogin obj = new LoginUserResponseDTO.Userlogin();
        obj.setId(user.getId());
        obj.setName(user.getName());
        obj.setEmail(user.getEmail());

        loginObj.put(jwt, obj);

        return loginObj;
    }

}

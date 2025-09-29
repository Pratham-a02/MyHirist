package com.example.linkedinProject.userService.service;

import com.example.linkedinProject.userService.dto.LoginRequestDto;
import com.example.linkedinProject.userService.dto.SignupRequestDto;
import com.example.linkedinProject.userService.dto.UserDto;
import com.example.linkedinProject.userService.entity.User;
import com.example.linkedinProject.userService.exception.BadRequestException;
import com.example.linkedinProject.userService.exception.ResourceNotFoundException;
import com.example.linkedinProject.userService.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import com.example.linkedinProject.userService.utils.BCrypt;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final JwtService jwtService;

    public UserDto signup(SignupRequestDto signupRequestDto){
        log.info("Signing up a user with mail: {}",signupRequestDto.getEmail());

        boolean exists = userRepository.existsByEmail(signupRequestDto.getEmail());
        if(exists) throw new BadRequestException("User already exists");

        User user = modelMapper.map(signupRequestDto, User.class);
        user.setPassword(BCrypt.hash(signupRequestDto.getPassword()));
        user = userRepository.save(user);
        return modelMapper.map(user, UserDto.class);
    }

    public String login(LoginRequestDto loginRequestDto){
        log.info("Login request for user with email: {}",loginRequestDto.getEmail());
        User user = userRepository.findByEmail(loginRequestDto.getEmail())
                .orElseThrow(() -> new BadRequestException("Incorrect email or password"));

        boolean isPasswordMatch = BCrypt.match(loginRequestDto.getPassword(),user.getPassword());

        if(!isPasswordMatch) throw new BadRequestException("Incorrect email or password");
        return jwtService.generateToken(user);
    }
}

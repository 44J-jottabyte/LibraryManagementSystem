package org.example.librarymanagementsystem.service;

import lombok.RequiredArgsConstructor;
import org.example.librarymanagementsystem.model.dto.request.LoginDto;
import org.example.librarymanagementsystem.model.dto.request.UserRequestDto;
import org.example.librarymanagementsystem.model.entity.User;
import org.example.librarymanagementsystem.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final MyUserDetailsService myUserDetailsService;


    public String register(UserRequestDto userRequestDto) {
        if (userRepository.existsByUsername(userRequestDto.getUsername())) {
            throw new RuntimeException("Username already exists");


        }

        User user = new User();
        user.setUsername(userRequestDto.getUsername());
        user.setEmail(userRequestDto.getEmail());
        user.setPassword(passwordEncoder.encode(userRequestDto.getPassword()));
        user.setRole(userRequestDto.getRole());
        userRepository.save(user);
        return "User registered successfully!";
    }

    public String login(LoginDto loginDTO) {
        User user = userRepository.findByUsername(loginDTO.getUsername())
                .orElseThrow(() -> new RuntimeException("Invalid username or password"));

        if (!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid username or password");
        }

        UserDetails userDetails = myUserDetailsService.loadUserByUsername(user.getUsername());

        return jwtService.generateToken(userDetails.getUsername());
    }
}


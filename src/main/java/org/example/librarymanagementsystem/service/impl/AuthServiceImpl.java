//package org.example.librarymanagementsystem.service.impl;
//
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//import org.example.librarymanagementsystem.repository.UserRepository;
//import org.example.librarymanagementsystem.model.entity.User;
//import org.example.librarymanagementsystem.model.dto.request.UserRequestDto;
//import org.example.librarymanagementsystem.model.dto.response.UserResponseDto;
//import org.example.librarymanagementsystem.mapper.UserMapper;
//import org.example.librarymanagementsystem.service.AuthService;
//
//@Service
//public class AuthServiceImpl implements AuthService {
//
//    public AuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, UserMapper userMapper) {
//        this.userRepository = userRepository;
//        this.passwordEncoder = passwordEncoder;
//        this.userMapper = userMapper;
//    }
//
//    private final UserRepository userRepository;
//
//
//    private final PasswordEncoder passwordEncoder;
//
//    private final UserMapper userMapper;
//
//    @Override
//    public UserResponseDto register(UserRequestDto userRequestDto) {
//        User user = userMapper.toEntity(userRequestDto);
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
//        User savedUser = userRepository.save(user);
//        return userMapper.toResponseDto(savedUser);
//    }
//
//    @Override
//    public UserResponseDto login(String email, String password) {
//        User user = userRepository.findByEmail(email);
//        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
//            return userMapper.toResponseDto(user);
//        }
//        return null;
//    }
//}


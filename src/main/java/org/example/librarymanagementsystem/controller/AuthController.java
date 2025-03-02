package org.example.librarymanagementsystem.controller;

import org.example.librarymanagementsystem.model.dto.request.LoginDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.example.librarymanagementsystem.model.dto.request.UserRequestDto;
import org.example.librarymanagementsystem.service.AuthService;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/user-register")
    public ResponseEntity<?> register(@RequestBody UserRequestDto userRequestDto) {
        return ResponseEntity.ok(authService.register(userRequestDto));
    }

    @PostMapping("/user-login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto) {
        String token = authService.login(loginDto);
        return ResponseEntity.ok(Map.of("token", token));
    }

//    @PostMapping("/register")
//    public ResponseEntity<UserResponseDto> register(@RequestBody UserRequestDto userRequestDto) {
//        UserResponseDto responseDto = authService.register(userRequestDto);
//        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
//    }
//
//    @PostMapping("/login")
//    public ResponseEntity<Map<String, String>> login(@RequestBody UserRequestDto userRequestDto) {
//        UserResponseDto responseDto = authService.login(userRequestDto.getEmail(), userRequestDto.getPassword());
//        String token = jwtService.generateToken(userRequestDto.getEmail());
//        return ResponseEntity.status(HttpStatus.OK).body(Map.of("token", token));
//    }
//
//    @GetMapping("/validate")
//    public ResponseEntity<String> validateToken(@RequestHeader("Authorization") String token) {
//        String validateToken = token.replace("Bearer ", "");
//
//        boolean valid = jwtService.validateToken(validateToken);
//
//        if (valid) {
//            return ResponseEntity.status(HttpStatus.OK).body("Token is valid");
//        } else {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token is invalid");
//        }
//    }
}

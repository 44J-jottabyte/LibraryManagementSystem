package org.example.librarymanagementsystem.model.dto.request;

import lombok.Data;

@Data
public class UserRequestDto {
    private String username;
    private String email;
    private String password;
    private String role;
}


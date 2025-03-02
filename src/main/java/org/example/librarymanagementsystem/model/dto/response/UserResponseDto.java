package org.example.librarymanagementsystem.model.dto.response;

import org.example.librarymanagementsystem.model.enums.Role;
import lombok.Data;

@Data
public class UserResponseDto {
    private Long id;
    private String username;
    private String email;
    private Role role;
}

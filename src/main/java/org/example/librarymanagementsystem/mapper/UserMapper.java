package org.example.librarymanagementsystem.mapper;

import org.example.librarymanagementsystem.model.dto.request.UserRequestDto;
import org.example.librarymanagementsystem.model.dto.response.UserResponseDto;
import org.example.librarymanagementsystem.model.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toEntity(UserRequestDto requestDto);

    UserResponseDto toResponseDto(User user);
}

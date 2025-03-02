package org.example.librarymanagementsystem.mapper;

import org.example.librarymanagementsystem.model.dto.request.BookRequestDto;
import org.example.librarymanagementsystem.model.dto.response.BookResponseDto;
import org.example.librarymanagementsystem.model.entity.Book;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface BookMapper {
    Book toEntity(BookRequestDto requestDto);

    BookResponseDto toResponseDto(Book book);

    void updateFromDto(BookRequestDto bookRequestDto,@MappingTarget Book book);
}


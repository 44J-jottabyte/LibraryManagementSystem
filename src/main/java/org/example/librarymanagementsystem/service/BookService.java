package org.example.librarymanagementsystem.service;

import org.example.librarymanagementsystem.model.dto.request.BookRequestDto;
import org.example.librarymanagementsystem.model.dto.response.BookResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookService {
    BookResponseDto save(BookRequestDto bookRequestDto);
    BookResponseDto findById(Long id);
    Page<BookResponseDto> getAllBooks(Pageable pageable);
    BookResponseDto update(Long id, BookRequestDto bookRequestDto);
    void deleteBook(Long id);
}


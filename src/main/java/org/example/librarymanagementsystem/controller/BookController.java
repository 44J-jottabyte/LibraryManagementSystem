package org.example.librarymanagementsystem.controller;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.example.librarymanagementsystem.model.dto.request.BookRequestDto;
import org.example.librarymanagementsystem.model.dto.response.BookResponseDto;
import org.example.librarymanagementsystem.service.BookService;

@RestController
@RequestMapping("/api/books")
@AllArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping("/all")
    public ResponseEntity<Page<BookResponseDto>> getAllBooks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<BookResponseDto> bookPage = bookService.getAllBooks(pageable);
        return ResponseEntity.ok(bookPage);
    }

    @PostMapping("/add")
    public ResponseEntity<BookResponseDto> addBook(@RequestBody BookRequestDto bookRequestDto) {
        BookResponseDto responseDto = bookService.save(bookRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookResponseDto> getBookById(@PathVariable Long id) {
        BookResponseDto responseDto = bookService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<BookResponseDto> updateBook(@PathVariable Long id, @RequestBody BookRequestDto bookRequestDto) {
        BookResponseDto responseDto = bookService.update(id, bookRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }
}


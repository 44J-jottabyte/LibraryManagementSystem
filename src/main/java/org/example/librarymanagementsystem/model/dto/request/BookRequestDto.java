package org.example.librarymanagementsystem.model.dto.request;

import lombok.Data;

@Data
public class BookRequestDto {
    private String title;
    private String author;
    private String isbn;
    private String publisher;
    private int pages;
    private int quantity;
    private boolean deleted;
    private String genre;
}
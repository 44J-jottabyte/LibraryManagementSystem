package org.example.librarymanagementsystem.service.impl;

import org.example.librarymanagementsystem.exception.BookNotFoundException;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.example.librarymanagementsystem.repository.BookRepository;
import org.example.librarymanagementsystem.model.entity.Book;
import org.example.librarymanagementsystem.model.dto.request.BookRequestDto;
import org.example.librarymanagementsystem.model.dto.response.BookResponseDto;
import org.example.librarymanagementsystem.service.BookService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private ModelMapper modelMapper;


    @Override
    @Transactional
    public BookResponseDto save(BookRequestDto createBookDto) {
        Optional<Book> existingBookOptional = bookRepository.findByTitleIgnoreCase(createBookDto.getTitle());
        if (existingBookOptional.isPresent()) {
            Book existingBook = existingBookOptional.get();

            if (existingBook.getAuthor().equalsIgnoreCase(createBookDto.getAuthor()) &&
                    existingBook.getIsbn().equals(createBookDto.getIsbn()) &&
                    existingBook.getPublisher().equalsIgnoreCase(createBookDto.getPublisher()) &&
                    existingBook.getPages() == createBookDto.getPages() &&
                    existingBook.getGenre().equalsIgnoreCase(createBookDto.getGenre())) {

                existingBook.setQuantity(existingBook.getQuantity() + createBookDto.getQuantity());
                bookRepository.save(existingBook);
                return modelMapper.map(existingBook, BookResponseDto.class);
            } else {
                throw new IllegalArgumentException("A book with the same title but different in other areas is already available!");
            }
        }

        Book newBook = modelMapper.map(createBookDto, Book.class);
        newBook.setDeleted(false);// 0 ve 1 seklimde 0-false 1- true
        bookRepository.save(newBook);
        return modelMapper.map(newBook, BookResponseDto.class);
    }

    @Override
    @Transactional
    public BookResponseDto findById(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException("Book not found with id : " + id));
        BookResponseDto bookResponseDto = modelMapper.map(book, BookResponseDto.class);
        return bookResponseDto;

    }

    @Override
    @Transactional
    public Page<BookResponseDto> getAllBooks(Pageable pageable) {
        Page<Book> booksPage = bookRepository.findAllByDeletedFalse(pageable);
        List<BookResponseDto> bookDtos = modelMapper.map(booksPage.getContent(), new TypeToken<List<BookResponseDto>>() {
        }.getType());
        return new PageImpl<>(bookDtos, pageable, booksPage.getTotalElements());
    }


    @Override
    @Transactional
    public BookResponseDto update(Long id, BookRequestDto createBookDto) {

        Book existingBook = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book not found with id : " + id));
        modelMapper.map(createBookDto, existingBook);
        Book updatedBook = bookRepository.save(existingBook);
        return modelMapper.map(updatedBook, BookResponseDto.class);
    }


    @Override
    @Transactional
    public void deleteBook(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book not found with id: " + id));

        book.setDeleted(true);// 0-->1
        bookRepository.save(book);
    }
}

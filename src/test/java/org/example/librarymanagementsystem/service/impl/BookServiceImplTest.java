package org.example.librarymanagementsystem.service.impl;

import org.example.librarymanagementsystem.exception.BookNotFoundException;
import org.example.librarymanagementsystem.model.dto.request.BookRequestDto;
import org.example.librarymanagementsystem.model.dto.response.BookResponseDto;
import org.example.librarymanagementsystem.model.entity.Book;
import org.example.librarymanagementsystem.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {

    @InjectMocks
    private BookServiceImpl bookService;

    private Book book;
    private BookRequestDto bookRequestDto;
    private BookResponseDto bookResponseDto;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        book = new Book();
        book.setId(1L);
        book.setTitle("Test Book");
        book.setAuthor("Test Author");
        book.setIsbn("1234567890");
        book.setPublisher("Test Publisher");
        book.setPages(100);
        book.setGenre("Test Genre");
        book.setQuantity(10);
        book.setDeleted(false);

        bookRequestDto = new BookRequestDto();
        bookRequestDto.setTitle("Test Book");
        bookRequestDto.setAuthor("Test Author");
        bookRequestDto.setIsbn("1234567890");
        bookRequestDto.setPublisher("Test Publisher");
        bookRequestDto.setPages(100);
        bookRequestDto.setGenre("Test Genre");
        bookRequestDto.setQuantity(10);

        bookResponseDto = new BookResponseDto();
        bookResponseDto.setId(1L);
        bookResponseDto.setTitle("Test Book");
        bookResponseDto.setAuthor("Test Author");
        bookResponseDto.setIsbn("1234567890");
        bookResponseDto.setPublisher("Test Publisher");
        bookResponseDto.setPages(100);
        bookResponseDto.setGenre("Test Genre");
        bookResponseDto.setQuantity(10);
    }

    @Test
    void testSave() {
        when(bookRepository.findByTitleIgnoreCase(any(String.class))).thenReturn(Optional.empty());
        when(modelMapper.map(any(BookRequestDto.class), eq(Book.class))).thenReturn(book);
        when(modelMapper.map(any(Book.class), eq(BookResponseDto.class))).thenReturn(bookResponseDto);
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        BookResponseDto savedBook = bookService.save(bookRequestDto);

        assertNotNull(savedBook);
        assertEquals(bookResponseDto, savedBook);
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    void testExistingBook() {
        when(bookRepository.findByTitleIgnoreCase(any(String.class))).thenReturn(Optional.of(book));
        when(modelMapper.map(any(Book.class), eq(BookResponseDto.class))).thenReturn(bookResponseDto);
        book.setQuantity(book.getQuantity() + bookRequestDto.getQuantity());

        BookResponseDto savedBook = bookService.save(bookRequestDto);

        assertNotNull(savedBook);
        assertEquals(bookResponseDto, savedBook);
        verify(bookRepository, times(1)).save(book);
    }

    @Test
    void testFindById() {
        when(bookRepository.findById(any(Long.class))).thenReturn(Optional.of(book));
        when(modelMapper.map(any(Book.class), eq(BookResponseDto.class))).thenReturn(bookResponseDto);

        BookResponseDto foundBook = bookService.findById(1L);

        assertNotNull(foundBook);
        assertEquals(bookResponseDto, foundBook);
    }

    @Test
    void testFindById_NotFound() {
        when(bookRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        assertThrows(BookNotFoundException.class, () -> bookService.findById(1L));
    }

    @Test
    void testGetAllBooks() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Book> booksPage = new PageImpl<>(Collections.singletonList(book), pageable, 1);
        when(bookRepository.findAllByDeletedFalse(pageable)).thenReturn(booksPage);
        when(modelMapper.map(anyList(), eq(new TypeToken<List<BookResponseDto>>() {}.getType())))
                .thenReturn(Collections.singletonList(bookResponseDto));

        Page<BookResponseDto> bookPage = bookService.getAllBooks(pageable);

        assertNotNull(bookPage);
        assertEquals(1, bookPage.getTotalElements());
        assertEquals(bookResponseDto, bookPage.getContent().get(0));
        verify(bookRepository, times(1)).findAllByDeletedFalse(pageable);
        verify(modelMapper, times(1)).map(anyList(), eq(new TypeToken<List<BookResponseDto>>() {}.getType()));
    }

//    @Test
//    void testUpdate() {
//        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(book));
//        when(modelMapper.map(eq(bookRequestDto), eq(Book.class))).thenReturn(book);
//        when(bookRepository.save(any(Book.class))).thenReturn(book);
//        when(modelMapper.map(eq(book), eq(BookResponseDto.class))).thenReturn(bookResponseDto);
//
//        BookResponseDto updatedBook = bookService.update(1L, bookRequestDto);
//
//        assertNotNull(updatedBook);
//        assertEquals(bookResponseDto, updatedBook);
//
//        ArgumentCaptor<Book> bookCaptor = ArgumentCaptor.forClass(Book.class);
//        verify(bookRepository).save(bookCaptor.capture());
//        Book savedBook = bookCaptor.getValue();
//
//        assertEquals(bookRequestDto.getTitle(), savedBook.getTitle());
//        assertEquals(bookRequestDto.getAuthor(), savedBook.getAuthor());
//        assertEquals(bookRequestDto.getIsbn(), savedBook.getIsbn());
//        assertEquals(bookRequestDto.getPublisher(), savedBook.getPublisher());
//        assertEquals(bookRequestDto.getPages(), savedBook.getPages());
//        assertEquals(bookRequestDto.getGenre(), savedBook.getGenre());
//        assertEquals(bookRequestDto.getQuantity(), savedBook.getQuantity());
//
//        verify(bookRepository).findById(anyLong());
//        verify(bookRepository).save(any(Book.class));
//        verify(modelMapper).map(eq(bookRequestDto), eq(Book.class));
//        verify(modelMapper).map(eq(book), eq(BookResponseDto.class));
//    }

    @Test
    void testDeleteBook() {
        when(bookRepository.findById(any(Long.class))).thenReturn(Optional.of(book));
        doAnswer(invocation -> {
            book.setDeleted(true);
            return null;
        }).when(bookRepository).save(any(Book.class));

        bookService.deleteBook(1L);

        assertTrue(book.isDeleted());
    }

    @Test
    void testDeleteBook_NotFound() {
            when(bookRepository.findById(any(Long.class))).thenReturn(Optional.empty());

            assertThrows(BookNotFoundException.class, () -> bookService.deleteBook(1L));
    }
}
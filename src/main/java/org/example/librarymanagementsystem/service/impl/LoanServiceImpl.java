package org.example.librarymanagementsystem.service.impl;

import org.example.librarymanagementsystem.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.example.librarymanagementsystem.repository.LoanRepository;
import org.example.librarymanagementsystem.repository.UserRepository;
import org.example.librarymanagementsystem.repository.BookRepository;
import org.example.librarymanagementsystem.model.entity.Loan;
import org.example.librarymanagementsystem.model.entity.User;
import org.example.librarymanagementsystem.model.entity.Book;
import org.example.librarymanagementsystem.model.dto.request.LoanRequestDto;
import org.example.librarymanagementsystem.model.dto.response.LoanResponseDto;
import org.example.librarymanagementsystem.mapper.LoanMapper;
import org.example.librarymanagementsystem.service.LoanService;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LoanServiceImpl implements LoanService {

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private LoanMapper loanMapper;

    @Override
    public List<LoanResponseDto> getUserLoans(Long userId) {
        return loanRepository.findAll()
                .stream()
                .map(loanMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public LoanResponseDto createLoan(LoanRequestDto requestDto) {

        User user = userRepository.findByUsername(requestDto.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Book book = (Book) bookRepository.findByTitle(requestDto.getBookTitle())
                .orElseThrow(() -> new RuntimeException("Book not found"));

        if (loanRepository.existsByUserAndBooksContains(user, book)) {
            throw new RuntimeException("User has already borrowed this book");
        }

        long loanedBookCount = loanRepository.countByBooksContains(book);
        if (loanedBookCount > book.getQuantity()) {
            throw new RuntimeException("No more copies available for loan");
        }

        Loan loan = new Loan();
        loan.setLoanDate(LocalDate.now());
        loan.setReturnDate(LocalDate.now().plusDays(14));
        loan.setUser(user);
        loan.setBooks(Collections.singletonList(book));

        Loan savedLoan = loanRepository.save(loan);

        book.setQuantity(book.getQuantity() - 1);
        bookRepository.save(book);

        LoanResponseDto responseDto = new LoanResponseDto();
        responseDto.setLoanId(savedLoan.getId());
        responseDto.setUsername(user.getUsername());
        responseDto.setBookTitle(book.getTitle());
        responseDto.setLoanDate(savedLoan.getLoanDate().toString());

        return responseDto;
    }

    @Transactional
    @Override
    public LoanResponseDto returnBook(Long loanId) {
        Loan loan = (Loan) loanRepository.findById(loanId)
                .orElseThrow(() -> new ResourceNotFoundException("Loan not found"));

        Book book = loan.getBooks().get(0);

        loan.setReturnDate(LocalDate.now());
        loanRepository.save(loan);

        book.setQuantity(book.getQuantity() + 1);
        bookRepository.save(book);

        LoanResponseDto responseDto = new LoanResponseDto();
        responseDto.setLoanId(loan.getId());
        responseDto.setUsername(loan.getUser().getUsername());
        responseDto.setBookTitle(book.getTitle());
        responseDto.setLoanDate(loan.getLoanDate().toString());
        responseDto.setLoanDate(loan.getReturnDate().toString());

        return responseDto;
    }

//    @Override
//    public LoanResponseDto borrowBook(LoanRequestDto loanRequestDto) {
//        User user = userRepository.findById(loanRequestDto.getUserId())
//                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + loanRequestDto.getUserId()));
//
//        Book book = bookRepository.findById(loanRequestDto.getBookId())
//                .orElseThrow(() -> new IllegalArgumentException("Book not found with ID: " + loanRequestDto.getBookId()));
//
//        if (book.getQuantity() <= 0) {
//            throw new IllegalStateException("Book is out of stock.");
//        }
//
//        Loan loan = loanMapper.toEntity(loanRequestDto);
//        loan.setUser(user);
//        loan.setBook(book);
//        loan.setStatus(LoanStatus.BORROWED);
//
//        book.setQuantity(book.getQuantity() - 1);
//        bookRepository.save(book);
//
//        Loan savedLoan = loanRepository.save(loan);
//
//        return loanMapper.toResponseDto(savedLoan);
//    }
//
//    @Override
//    public LoanResponseDto returnBook(Long id) {
//        Loan loan = loanRepository.findById(id)
//                .orElseThrow(() -> new IllegalArgumentException("Loan not found with ID: " + id));
//
//        if (loan.getStatus() == LoanStatus.RETURNED) {
//            throw new IllegalStateException("Loan has already been returned.");
//        }
//
//        loan.setStatus(LoanStatus.RETURNED);
//
//        Book book = loan.getBook();
//        book.setQuantity(book.getQuantity() + 1);
//
//        bookRepository.save(book);
//        Loan updatedLoan = loanRepository.save(loan);
//
//        return loanMapper.toResponseDto(updatedLoan);
//    }
}


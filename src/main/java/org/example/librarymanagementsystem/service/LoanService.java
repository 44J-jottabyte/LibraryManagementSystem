package org.example.librarymanagementsystem.service;

import org.example.librarymanagementsystem.model.dto.request.LoanRequestDto;
import org.example.librarymanagementsystem.model.dto.response.LoanResponseDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface LoanService {
    List<LoanResponseDto> getUserLoans(Long userId);
    LoanResponseDto createLoan(LoanRequestDto requestDto);

    @Transactional
    LoanResponseDto returnBook(Long loanId);
}

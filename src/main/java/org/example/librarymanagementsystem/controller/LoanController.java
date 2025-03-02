package org.example.librarymanagementsystem.controller;

import lombok.AllArgsConstructor;
import org.example.librarymanagementsystem.service.impl.LoanServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.example.librarymanagementsystem.model.dto.request.LoanRequestDto;
import org.example.librarymanagementsystem.model.dto.response.LoanResponseDto;
import org.example.librarymanagementsystem.service.LoanService;

@RestController
@RequestMapping("/api/loans")
@AllArgsConstructor
public class LoanController {

    @Autowired
    LoanService loanService;
    @Autowired
    private LoanServiceImpl loanServiceImpl;

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<LoanResponseDto>> getUserLoans(@PathVariable Long userId) {
        List<LoanResponseDto> loans = loanService.getUserLoans(userId);
        return ResponseEntity.status(HttpStatus.OK).body(loans);
    }

    @PostMapping("/create")
    public ResponseEntity<LoanResponseDto> createLoan(@RequestBody LoanRequestDto requestDto) {
        LoanResponseDto response = loanService.createLoan(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/return/{id}")
    public ResponseEntity<String> returnLoan(@PathVariable Long id) {
        loanServiceImpl.returnBook(id);
        return ResponseEntity.ok("Book successfully returned");
    }
}

package org.example.librarymanagementsystem.model.dto.response;

import lombok.Data;

@Data
public class LoanResponseDto {
    private Long loanId;
    private String username;
    private String bookTitle;
    private String loanDate;
}

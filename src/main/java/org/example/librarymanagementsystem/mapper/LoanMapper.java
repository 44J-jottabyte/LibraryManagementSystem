package org.example.librarymanagementsystem.mapper;

import org.mapstruct.Mapper;
import org.example.librarymanagementsystem.model.dto.request.LoanRequestDto;
import org.example.librarymanagementsystem.model.dto.response.LoanResponseDto;
import org.example.librarymanagementsystem.model.entity.Loan;

@Mapper(componentModel = "spring")
public interface LoanMapper {
    Loan toEntity(LoanRequestDto requestDto);
    LoanResponseDto toResponseDto(Loan loan);
}

package org.example.librarymanagementsystem.repository;

import org.example.librarymanagementsystem.model.entity.Book;
import org.example.librarymanagementsystem.model.entity.Loan;
import org.example.librarymanagementsystem.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Loan> {

    boolean existsByUserAndBooksContains(User user, Book book);

    long countByBooksContains(Book book);

    Optional<Object> findById(Long loanId);

}

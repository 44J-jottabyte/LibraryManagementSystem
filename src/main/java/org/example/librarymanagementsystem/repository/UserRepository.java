package org.example.librarymanagementsystem.repository;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.example.librarymanagementsystem.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUsername(@NotNull(message = "Username must not be null")
                             @Size(min = 6, max = 50, message = "Username must be between 3 and 50 characters")
                             String username);

    Optional<User> findByUsername(String username);

    User findByEmail(String email);

}

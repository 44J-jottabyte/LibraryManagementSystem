package org.example.librarymanagementsystem.model.entity;

import org.example.librarymanagementsystem.model.enums.Role;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String username;

    @Column(nullable = false)
    private String password;

    private String phone;

    @Column(unique = true, nullable = false)
    private String email;

    private String address;

    public String role;

//    @Enumerated(EnumType.STRING)
//    private Role role;

    @OneToMany(mappedBy = "user")
    private List<Loan> loans;
}

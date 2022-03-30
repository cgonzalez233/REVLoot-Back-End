package com.revature.com.loginservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(
    name = "user",
    uniqueConstraints = @UniqueConstraint(
        name = "email_unique",
        columnNames = "email"
    )
)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;
    @Column(
        name = "email",
        nullable = false
    )
    private String email;
    private String password;
    private String firstName;
    private String lastName;
}

package com.bookUniverse.BookUniverse.model.login;

import com.bookUniverse.BookUniverse.enums.RoleName;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(generator = "users_seq_gen",strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "users_seq_gen",sequenceName = "user_seq",allocationSize = 1)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private RoleName roles ;
}

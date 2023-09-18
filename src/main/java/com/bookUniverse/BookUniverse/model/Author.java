package com.bookUniverse.BookUniverse.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "author",
        uniqueConstraints = {@UniqueConstraint(name = "uk_author_phonenumber",columnNames = "phone_number"),
                @UniqueConstraint(name = "uk_author_email",columnNames = "email")})
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "author_seq_gen")
    @SequenceGenerator(name = "author_seq_gen",sequenceName = "author_seq",allocationSize = 1)
    private Short id;
    @Column(name = "author_name",nullable = false)
    private String name;

    @Column(name = "email",length = 100,nullable = false)
    private String email;

    @Column(name = "phone_number",length = 10)
    private String number;

    @Column(name = "is_active",columnDefinition = "boolean default true")
    private Boolean isActive;
}

package com.bookUniverse.BookUniverse.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "category",
        uniqueConstraints = {@UniqueConstraint(name = "uk_category_name",columnNames = "name")})
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "category_seq_gen")
    @SequenceGenerator(name = "category_seq_gen",sequenceName = "category_seq",allocationSize = 1)
    private Short id;

    @Column(name = "name",nullable = false)
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "is_active",columnDefinition = "boolean default true")
    private Boolean isActive;
}

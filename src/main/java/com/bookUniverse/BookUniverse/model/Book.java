package com.bookUniverse.BookUniverse.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "book",uniqueConstraints = {
        @UniqueConstraint(name = "uk_book_name",columnNames = "book_name"),
        @UniqueConstraint(name = "uk_book_isbn",columnNames = "isbn_number")
})
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    @Id
    @GeneratedValue(generator = "book_seq_gen",strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "book_seq_gen",sequenceName = "book_seq",allocationSize = 1)
    private Integer id;

    @Column(name = "book_name", length = 100, nullable = false)
    private String name;

    @Column(name = "page_number")
    private Integer page;

    @Column(name = "isbn_number", length = 30, nullable = false)
    private String isbn;

    @Column(name = "book_rating")
    private Double rating;

    @Column(name = "book_stock", nullable = false)
    private Integer stock;

    @Column(name = "published_date", nullable = false)
    private LocalDate published_date;

    @Column(name = "image_path", nullable = false, length = 150)
    private String image_path;

    private Boolean deleted ;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", foreignKey = @ForeignKey(name = "fk_book_categoryId"))
    private Category category;

    @ManyToMany(targetEntity = Author.class,fetch = FetchType.EAGER)
    @JoinTable(name = "book_author_table",
            foreignKey = @ForeignKey(name = "fk_book_authorId"),
            inverseForeignKey = @ForeignKey(name = "fk_author_bookId")
    )
    private List<Author> authors;
}

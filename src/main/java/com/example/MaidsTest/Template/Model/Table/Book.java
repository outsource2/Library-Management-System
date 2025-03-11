package com.example.MaidsTest.Template.Model.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
@Table(name = "Book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Title is required")
    @Column(name = "title")
    private String title;

    @Size(max = 10, message = "Author must be less than 10 characters")
    @Column(name = "author")
    private String author;

    @Min(value = 100, message = "Pages must be at least 100")
    @Column(name = "pages")
    private Integer pages;

    @Positive(message = "Price must be greater than 0")
    @Column(name = "price")
    private Double price;

//    @Pattern(regexp = "\\d{13}", message = "ISBN must be a 13-digit number")
//    @Column(name = "isbn")
//    private String isbn;

    @Min(value = 1900, message = "Publication year must be a valid year")
    @Max(value = 2025, message = "Publication year cannot be in the future")
    @Column(name = "publication_year")
    private Integer publicationYear;

    @Column(name = "available")
    private Boolean available;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @Column(name = "insert_date")
    private Date insertDate;

    @Column(name = "insert_by")
    private Integer insertBy;

    @PrePersist
    void createdAt() {
        this.insertDate = new Date();
    }

    public Book(Long id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
    }
}

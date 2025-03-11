package com.example.MaidsTest.Template.Model.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Setter(AccessLevel.PUBLIC)
@Getter(AccessLevel.PUBLIC)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
@Table(name = "Patron")
public class Patron {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is required")
    @Column(name = "name")
    private String name;

    @Pattern(regexp = "^\\+?\\d{10,15}$", message = "Phone number must be between 10 and 15 digits")  // التحقق من رقم الهاتف
    @NotBlank(message = "phoneNumber is required")
    @Column(name = "phone_number")
    private String phoneNumber;

    @Size(max = 255, message = "Address must be less than 255 characters")  // التحقق من أن العنوان لا يتجاوز 255 حرفًا
    @NotBlank(message = "address is required")
    @Column(name = "address")
    private String address;

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
}

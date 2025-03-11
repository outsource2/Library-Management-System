package com.example.MaidsTest.Template.API.Response.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CBookDTO {

    private String title;

    private String author;

    private Integer pages;

    private Double price;

    private Integer publicationYear;

    private Boolean available;

}

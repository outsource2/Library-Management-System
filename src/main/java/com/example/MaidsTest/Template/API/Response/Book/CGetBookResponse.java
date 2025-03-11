package com.example.MaidsTest.Template.API.Response.Book;

import com.example.MaidsTest.Base.API.Response.CAPIResponse;
import com.example.MaidsTest.Template.API.Response.DTO.CBookDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CGetBookResponse extends CAPIResponse {
    CBookDTO book;

    List<CBookDTO> bookList;
}

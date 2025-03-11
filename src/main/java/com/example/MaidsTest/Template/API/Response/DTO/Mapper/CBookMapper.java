package com.example.MaidsTest.Template.API.Response.DTO.Mapper;

import com.example.MaidsTest.Template.API.Response.DTO.CBookDTO;
import com.example.MaidsTest.Template.Model.Table.Book;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CBookMapper {
    public static CBookDTO parse(Book book)
    {
        CBookDTO bookDTO = null;

        do
        {
            if(book == null) break;

            bookDTO = new CBookDTO();

            bookDTO.setAuthor(book.getAuthor());
            bookDTO.setPages(book.getPages());
            bookDTO.setTitle(book.getTitle());
            bookDTO.setPrice(book.getPrice());
            bookDTO.setAvailable(book.getAvailable());
            bookDTO.setPublicationYear(book.getPublicationYear());

        }
        while (false);

        return bookDTO;
    }

    public static List<CBookDTO> parse(List<Book> bookList)
    {
        CBookDTO bookDTO = null;

        List<CBookDTO> bookDTOList = null;

        do
        {
            if(bookList == null) break;

            bookDTOList = new ArrayList<>();

            for(Book book : bookList){

                bookDTO = new CBookDTO();

                bookDTO.setAuthor(book.getAuthor());
                bookDTO.setPages(book.getPages());
                bookDTO.setTitle(book.getTitle());
                bookDTO.setPrice(book.getPrice());
                bookDTO.setAvailable(book.getAvailable());

                bookDTOList.add(bookDTO);
            }

        }
        while (false);

        return bookDTOList;
    }
}

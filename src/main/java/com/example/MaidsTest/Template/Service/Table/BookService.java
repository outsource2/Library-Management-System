package com.example.MaidsTest.Template.Service.Table;

import com.example.MaidsTest.Base.Enum.EError;
import com.example.MaidsTest.Template.Aspect.Loggable;
import com.example.MaidsTest.Template.Exception.BookNotFoundException;
import com.example.MaidsTest.Template.Model.Table.Book;
import com.example.MaidsTest.Template.Repository.Table.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    @Loggable
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Loggable
    @Cacheable("book")
    public Book getBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(EError.BOOK_NOT_FOUND.name()));
        return book;
    }

    @Loggable
    public Book addBook(Book book) {
        book.setAvailable(true);
        return bookRepository.save(book);
    }

    @Loggable
    @CacheEvict(value = "book", key = "#id")
    public Book updateBook(Long id, Book bookDetails) {
        Book book = getBookById(id);
        book.setTitle(bookDetails.getTitle());
        book.setAuthor(bookDetails.getAuthor());
        book.setPublicationYear(bookDetails.getPublicationYear());
        return bookRepository.save(book);
    }

    @Loggable
    public void deleteBook(Long id) {
        Optional<Book> patron = bookRepository.findById(id);

        if (patron.isEmpty()) {
            throw new BookNotFoundException(EError.BOOK_NOT_FOUND.name());
        }

        bookRepository.deleteById(id);
    }

}

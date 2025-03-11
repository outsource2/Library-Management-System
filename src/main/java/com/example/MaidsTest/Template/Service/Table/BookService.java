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

/**
 * Service layer for managing books.
 * Contains business logic for handling book operations such as retrieving, adding, updating, and deleting books.
 * Implements caching for book retrieval.
 */
@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    /**
     * Retrieves all books from the repository.
     *
     * @return List of books
     */
    @Loggable
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    /**
     * Retrieves a book by its ID. If not found, throws a BookNotFoundException.
     *
     * @param id The ID of the book to retrieve
     * @return The book object
     * @throws BookNotFoundException If the book is not found
     */
    @Loggable
    @Cacheable("book")
    public Book getBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(EError.BOOK_NOT_FOUND.name()));
        return book;
    }

    /**
     * Adds a new book to the repository and marks it as available.
     *
     * @param book The book to add
     * @return The saved book object
     */
    @Loggable
    public Book addBook(Book book) {
        book.setAvailable(true);  // Ensure the book is available when added
        return bookRepository.save(book);
    }

    /**
     * Updates an existing book. The book's title, author, and publication year will be updated.
     * This method uses cache eviction to clear the cache after updating a book.
     *
     * @param id The ID of the book to update
     * @param bookDetails The new details of the book
     * @return The updated book object
     * @throws BookNotFoundException If the book is not found
     */
    @Loggable
    @CacheEvict(value = "book", key = "#id")
    public Book updateBook(Long id, Book bookDetails) {
        Book book = getBookById(id);  // Retrieve the existing book by ID
        book.setTitle(bookDetails.getTitle());
        book.setAuthor(bookDetails.getAuthor());
        book.setPublicationYear(bookDetails.getPublicationYear());
        return bookRepository.save(book);  // Save the updated book
    }

    /**
     * Deletes a book by its ID. If the book is not found, throws a BookNotFoundException.
     *
     * @param id The ID of the book to delete
     * @throws BookNotFoundException If the book is not found
     */
    @Loggable
    public void deleteBook(Long id) {
        Optional<Book> book = bookRepository.findById(id);

        if (book.isEmpty()) {
            throw new BookNotFoundException(EError.BOOK_NOT_FOUND.name());
        }

        bookRepository.deleteById(id);  // Delete the book from the repository
    }
}

package com.example.MaidsTest.Template.RestController.Table;

import com.example.MaidsTest.Base.Enum.ESuccess;
import com.example.MaidsTest.Template.API.Response.Book.CGetBookResponse;
import com.example.MaidsTest.Template.API.Response.DTO.Mapper.CBookMapper;
import com.example.MaidsTest.Template.Model.Table.Book;
import com.example.MaidsTest.Template.Service.Table.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing books.
 * <p>
 * This class exposes REST API endpoints for performing CRUD (Create, Read, Update, Delete)
 * operations on books. It interacts with the {@link BookService} to retrieve, add, update,
 * and delete books in the database. The response is formatted using {@link CGetBookResponse}.
 * </p>
 */
@RestController
@RequestMapping("/api/books")  // Endpoint mapping for book-related API
public class BookRestController {

    /** Service for handling book-related business logic. */
    @Autowired
    private BookService bookService;

    /**
     * Endpoint for retrieving all books.
     * <p>
     * This method handles HTTP GET requests to {@code /api/books}. It retrieves all books
     * from the database through the {@link BookService} and returns them in a response.
     * </p>
     *
     * @return A {@link CGetBookResponse} containing a list of books and success information.
     */
    @GetMapping
    public CGetBookResponse getAllBooks() {

        CGetBookResponse apiResponse = new CGetBookResponse();

        // Fetching all books using the service
        List<Book> books = bookService.getAllBooks();

        // Mapping the list of books to the response format
        apiResponse.setBookList(CBookMapper.parse(books));
        apiResponse.setStatus(HttpStatus.OK);  // HTTP status code for success
        apiResponse.setSuccessMessage(ESuccess.GET_ALL_BOOK_SUCCESSFULLY.toString());  // Success message

        return apiResponse;
    }

    /**
     * Endpoint for retrieving a specific book by its ID.
     * <p>
     * This method handles HTTP GET requests to {@code /api/books/{id}}. It fetches a book
     * from the database by its ID, using the {@link BookService}.
     * </p>
     *
     * @param id The ID of the book to retrieve.
     * @return A {@link CGetBookResponse} containing the book data and success information.
     */
    @GetMapping("/{id}")
    public CGetBookResponse getBookById(@PathVariable Long id) {

        CGetBookResponse apiResponse = new CGetBookResponse();

        // Fetching the book by ID from the service
        Book getBook = bookService.getBookById(id);

        // Mapping the fetched book to the response format
        apiResponse.setBook(CBookMapper.parse(getBook));
        apiResponse.setStatus(HttpStatus.OK);  // HTTP status code for success
        apiResponse.setSuccessMessage(ESuccess.GET_BOOK_SUCCESSFULLY.toString());  // Success message

        return apiResponse;
    }

    /**
     * Endpoint for adding a new book.
     * <p>
     * This method handles HTTP POST requests to {@code /api/books}. It creates a new book
     * by passing the received book data to the {@link BookService}.
     * </p>
     *
     * @param book The book to be added, provided in the request body.
     * @return A {@link CGetBookResponse} containing the newly added book and success information.
     */
    @PostMapping
    public CGetBookResponse addBook(@RequestBody Book book) {

        CGetBookResponse apiResponse = new CGetBookResponse();

        // Adding the new book via the service
        Book addedBook = bookService.addBook(book);

        // Mapping the added book to the response format
        apiResponse.setBook(CBookMapper.parse(addedBook));
        apiResponse.setStatus(HttpStatus.OK);  // HTTP status code for success
        apiResponse.setSuccessMessage(ESuccess.BOOK_ADDED_SUCCESSFULLY.toString());  // Success message

        return apiResponse;
    }

    /**
     * Endpoint for updating an existing book.
     * <p>
     * This method handles HTTP PUT requests to {@code /api/books/{id}}. It updates the
     * existing book by its ID, using the provided book data.
     * </p>
     *
     * @param id   The ID of the book to update.
     * @param book The new book data to update the existing book.
     * @return A {@link CGetBookResponse} containing the updated book and success information.
     */
    @PutMapping("/{id}")
    public CGetBookResponse updateBook(@PathVariable Long id, @RequestBody Book book) {

        CGetBookResponse apiResponse = new CGetBookResponse();

        // Updating the book via the service
        Book updatedBook = bookService.updateBook(id, book);

        // Mapping the updated book to the response format
        apiResponse.setBook(CBookMapper.parse(updatedBook));
        apiResponse.setStatus(HttpStatus.OK);  // HTTP status code for success
        apiResponse.setSuccessMessage(ESuccess.BOOK_UPDATED_SUCCESSFULLY.toString());  // Success message

        return apiResponse;
    }

    /**
     * Endpoint for deleting a book.
     * <p>
     * This method handles HTTP DELETE requests to {@code /api/books/{id}}. It deletes a
     * specific book by its ID using the {@link BookService}.
     * </p>
     *
     * @param id The ID of the book to delete.
     * @return A {@link CGetBookResponse} containing success information.
     */
    @DeleteMapping("/{id}")
    public CGetBookResponse deleteBook(@PathVariable Long id) {

        CGetBookResponse apiResponse = new CGetBookResponse();

        // Deleting the book by ID using the service
        bookService.deleteBook(id);

        // Setting the success message and HTTP status code
        apiResponse.setStatus(HttpStatus.OK);  // HTTP status code for success
        apiResponse.setSuccessMessage(ESuccess.BOOK_DELETED_SUCCESSFULLY.toString());  // Success message

        return apiResponse;
    }
}

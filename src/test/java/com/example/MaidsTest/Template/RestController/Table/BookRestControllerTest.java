package com.example.MaidsTest.Template.RestController.Table;

import com.example.MaidsTest.Template.Model.Table.Book;
import com.example.MaidsTest.Template.Service.Table.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Unit test class for testing the {@link BookRestController}.
 * <p>
 * This class contains unit tests for various API endpoints of the {@link BookRestController},
 * including retrieving, adding, updating, and deleting books. The tests are performed using
 * the {@link MockMvc} framework and the {@link Mockito} library to mock the {@link BookService}.
 * </p>
 */
@ExtendWith(MockitoExtension.class)  // Enables automatic mocking with Mockito
@WebMvcTest(BookRestController.class)  //Runs the test with only the controller in the context
@Import(BookService.class)  // Imports the BookService to the test context
public class BookRestControllerTest {

    /** MockMvc instance to perform HTTP requests and verify responses. */
    @Autowired
    private MockMvc mockMvc;

    /** Mocked BookService to simulate service layer operations. */
    @Mock
    private BookService bookService;  // Use @Mock instead of @MockBean

    /** A sample Book object to be used for testing. */
    private Book sampleBook;

    /**
     * Set up the test environment before each test case.
     * <p>
     * This method:
     * - Initializes Mockito annotations for mocks.
     * - Sets up a sample book object that will be used for API tests.
     * </p>
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // ✅ Setup a sample book object
        sampleBook = new Book();
        sampleBook.setId(1L);
        sampleBook.setTitle("Test Book");
        sampleBook.setAuthor("John Doe");
    }

    /**
     * Test the {@code GET /api/books} endpoint.
     * <p>
     * This test simulates a request to fetch all books. The {@code getAllBooks()} method of the
     * {@link BookService} is mocked to return a list containing a single book (the sampleBook).
     * The test verifies that the response status is OK and that the book's title is correct.
     * </p>
     */
    @Test
    void testGetAllBooks() throws Exception {
        when(bookService.getAllBooks()).thenReturn(List.of(sampleBook));

        mockMvc.perform(get("/api/books"))
                .andExpect(status().isOk())  // Check that the status is OK
                .andExpect(jsonPath("$.bookList").isArray())  // Verify that the response contains an array
                .andExpect(jsonPath("$.bookList[0].title").value("Test Book"));  // Check the title of the first book
    }

    /**
     * Test the {@code GET /api/books/{id}} endpoint.
     * <p>
     * This test simulates a request to fetch a book by its ID (1L). The {@code getBookById(1L)}
     * method of the {@link BookService} is mocked to return the sampleBook. The test verifies
     * that the response status is OK and that the title of the book in the response is correct.
     * </p>
     */
    @Test
    void testGetBookById() throws Exception {
        when(bookService.getBookById(1L)).thenReturn(sampleBook);

        mockMvc.perform(get("/api/books/1"))
                .andExpect(status().isOk())  // Check that the status is OK
                .andExpect(jsonPath("$.book.title").value("Test Book"));  // Verify the book title in the response
    }

    /**
     * Test the {@code POST /api/books} endpoint.
     * <p>
     * This test simulates a request to add a new book. The {@code addBook(any(Book.class))} method
     * of the {@link BookService} is mocked to return the sampleBook. The test verifies that the
     * response status is OK and that the book's title in the response matches the expected value.
     * </p>
     */
    @Test
    void testAddBook() throws Exception {
        when(bookService.addBook(any(Book.class))).thenReturn(sampleBook);

        String jsonRequest = """
        {
            "title": "Test Book",
            "author": "John Doe"
        }
        """;

        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)  // Set the content type to JSON
                        .content(jsonRequest))  // Pass the book data as JSON
                .andExpect(status().isOk())  // Check that the status is OK
                .andExpect(jsonPath("$.book.title").value("Test Book"));  // Verify the title of the added book
    }

    /**
     * Test the {@code PUT /api/books/{id}} endpoint.
     * <p>
     * This test simulates a request to update an existing book's details. The
     * {@code updateBook(any(Long.class), any(Book.class))} method of the {@link BookService}
     * is mocked to return the sampleBook. The test verifies that the response status is OK,
     * but due to using the same sampleBook data, it will return the same book details.
     * </p>
     */
    @Test
    void testUpdateBook() throws Exception {
        when(bookService.updateBook(any(Long.class), any(Book.class))).thenReturn(sampleBook);

        String jsonRequest = """
        {
            "title": "Updated Test Book",
            "author": "Jane Doe"
        }
        """;

        mockMvc.perform(put("/api/books/1")
                        .contentType(MediaType.APPLICATION_JSON)  // Set the content type to JSON
                        .content(jsonRequest))  // Pass the updated book data as JSON
                .andExpect(status().isOk())  // Check that the status is OK
                .andExpect(jsonPath("$.book.title").value("Test Book"));  // Verify that the title remains the same due to mock
    }

    /**
     * Test the {@code DELETE /api/books/{id}} endpoint.
     * <p>
     * This test simulates a request to delete a book by its ID (1L). The {@code deleteBook(1L)}
     * method of the {@link BookService} is mocked to do nothing. The test verifies that the response
     * status is OK, indicating the deletion was successful.
     * </p>
     */
    @Test
    void testDeleteBook() throws Exception {
        doNothing().when(bookService).deleteBook(1L);

        mockMvc.perform(delete("/api/books/1"))
                .andExpect(status().isOk());  // Check that the status is OK
    }

    /**
     * Test the {@code GET /api/books/{id}} endpoint for a non-existent book.
     * <p>
     * This test simulates a request to fetch a book that does not exist. The {@code getBookById(999L)}
     * method of the {@link BookService} is mocked to return null, simulating a "not found" scenario.
     * The test verifies that the response status is 404 (Not Found).
     * </p>
     */
    @Test
    void testGetBookById_NotFound() throws Exception {
        when(bookService.getBookById(999L)).thenReturn(null);

        mockMvc.perform(get("/api/books/999"))
                .andExpect(status().isNotFound());  // Check that the status is 404 Not Found
    }
}

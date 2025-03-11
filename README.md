
### **1. Running the Application**
- **Required Environment**:
  - **Java** (preferably Java 17)
  - **Spring Boot** (using Maven as the build tool)
  - **Database** (MySQL)

- **Setting up the Application**:
  1. Make sure **Java** is installed on your system.
  2. Clone the project from Git repository or download it.
  3. Open the project using an **IDE** (e.g., IntelliJ IDEA or Eclipse).
  4. Ensure that the database settings are configured in the `application.properties`  file.
     For example:
     ```properties
     spring.datasource.url=jdbc:mysql://localhost:3306/maids_test
     spring.datasource.username=root
     spring.datasource.password=
     spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
     spring.jpa.database-platform=org.hibernate.dialect.MySQL8Dialect
     ```

  After running the application, the server will typically run on `http://localhost:8080`.

### **2. Interacting with API Endpoints**

You can use tools like **Postman** to test and interact with the endpoints. Here are a few examples using BokkRestController:

#### **A. Example for Getting All Books**
- **Description**: This endpoint retrieves all the books in the database.
- **Method**: `GET`
- **URL**: `http://localhost:8080/MaidsTest/api/books`
- **Response**:
  ```json
  {
    "statusCode": 200,
    "statusDescription": "OK",
    "successMessage": "GET_ALL_BOOK_SUCCESSFULLY",
    "book": null,
    "bookList": [
        {
            "title": "The Great Gatsby",
            "author": "F. Scott Fitzgerald",
            "pages": 180,
            "price": 10.99,
            "publicationYear": 1925,
            "available": true
        },
        {
            "title": "1984",
            "author": "George Orwell",
            "pages": 328,
            "price": 14.99,
            "publicationYear": 1949,
            "available": true
        },
        {
            "title": "To Kill a Mockingbird",
            "author": "Harper Lee",
            "pages": 281,
            "price": 12.99,
            "publicationYear": 1960,
            "available": true
        }
    ]
}

  ```

  ```

### **B. Example for Adding a New Book**
- **Description**: This endpoint allows you to add a new book.
- **Method**: `POST`
- **URL**: `http://localhost:8080/MaidsTest/api/books`
- **Request Body**:
  ```json
  {
    "title": "let's guess who checks",
    "author": "chee hwan",
    "publicationYear": 2020,
    "pages": 105,
    "publisher": "che & hwan",
    "price": 150
  }
  ```

#### **C. Example for Updating a Book**
- **Description**: This endpoint allows you to update a book’s details using the book ID.
- **Method**: `PUT`
- **URL**: `http://localhost:8080/MaidsTest/api/patrons/{id}`
- **Request Body**:
  ```json
  {
    "title": "Updated Book Title",
    "author": "Updated Author",
    "publicationYear": 2024
  }
  ```

#### **D. Example for Deleting a Book**
- **Description**: This endpoint allows you to delete a book based on its ID.
- **Method**: `DELETE`
- **URL**: `http://localhost:8080/MaidsTest/api/books/{id}`

---

### **3. Validation:**

Here’s a breakdown of the `Book` model with validation and error handling:

### **Book Class with Validations**:

```java
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

    @NotBlank(message = "Title is required")  // Title cannot be blank
    @Column(name = "title")
    private String title;

    @Size(max = 10, message = "Author must be less than 10 characters")  // Author's name should be less than 10 characters
    @Column(name = "author")
    private String author;

    @Min(value = 100, message = "Pages must be at least 100")  // Page count must be at least 100
    @Column(name = "pages")
    private Integer pages;

    @Positive(message = "Price must be greater than 0")  // Price must be greater than 0
    @Column(name = "price")
    private Double price;

    @Min(value = 1900, message = "Publication year must be a valid year")  // Publication year should not be before 1900
    @Max(value = 2025, message = "Publication year cannot be in the future")  // Publication year cannot be in the future
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

    // Automatically set the insertDate to the current time before inserting into DB
    @PrePersist
    void createdAt() {
        this.insertDate = new Date();
    }

    // Constructor with minimal fields for easy instantiation
    public Book(Long id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
    }
}
```

### **Validation Annotations Used:**

- **`@NotBlank`**: Ensures that the `title` field is not null or empty. If it is, a custom error message is returned: `"Title is required"`.
  
- **`@Size(max = 10)`**: Restricts the length of the `author` field to be no more than 10 characters. If the length exceeds 10, it throws an error message `"Author must be less than 10 characters"`.
  
- **`@Min(value = 100)`**: Ensures that the `pages` field has a minimum value of 100. If the value is less than 100, an error message `"Pages must be at least 100"` is triggered.
  
- **`@Positive`**: Ensures the `price` field is a positive value greater than 0. If the value is negative or zero, the error message `"Price must be greater than 0"` will be returned.
  
- **`@Min(value = 1900)`** and **`@Max(value = 2025)`**: These annotations ensure that the `publicationYear` is between 1900 and 2025. Any year outside this range will result in an error message stating that the publication year must be valid or not in the future.
  
- **`@DateTimeFormat`** and **`@JsonFormat`**: These annotations format the `insertDate` field, ensuring that it is serialized and parsed correctly in the format `yyyy-MM-dd HH:mm`.

### **Error Handling in Controller:**

To handle validation errors effectively, Spring provides an automatic binding of validation annotations to the fields. If any validation fails, the `@Valid` annotation can be used to trigger validation in the controller layer.


### **Conclusion:**

- **Validation annotations** ensure that data conforms to the expected format and rules before being persisted or processed.
- **Error handling** in the controller leverages `BindingResult` to handle validation failures gracefully and return meaningful error messages to the client.

This combination of validation and error handling improves data integrity and ensures that users get clear feedback about why their requests were rejected.

### **4. Handling Errors**

create GlobalExceptionHandler class to handle all the errors. 

For example:

- If the book is not found, you’ll receive a response with a 400 status code:
  ```json
  {
    "ERROR_MESSAGE": "BOOK_NOT_FOUND",
    "STATUS_CODE": 400,
    "ERROR": "BOOK_NOT_FOUND"
  }
  ```

---

### **5. Aspects**

#### **Overview:**
The `Aspects` module in this project is used to handle logging functionality for methods annotated with the `@Loggable` annotation. 
This helps in tracking method execution, logging arguments, return values, and execution time for various methods within the application.
The aspect is implemented using **Aspect-Oriented Programming (AOP)** in Spring, which allows you to separate the logging logic from the business logic, keeping the code clean and maintainable.

#### **Key Features:**
- **Method Logging**: Logs method execution, parameters, and return values.
- **Execution Time Tracking**: Logs how long a method takes to execute.
- **Customizable Annotation**: The logging behavior is triggered by a custom annotation `@Loggable` that can be added to methods where logging is required.

#### **Steps to Use:**

1. **Add the @Loggable Annotation to Methods:**
   Any method in the application where you want to log execution, arguments, and the result should be annotated with the `@Loggable` annotation.

   Example:
   ```java
   @Loggable
   public void borrowBook(Long bookId, Long patronId) {
       // business logic
   }
   ```

2. **Enable AOP in Spring:**
   The logging aspect is activated through Spring AOP. Ensure that you have the necessary configuration to enable Aspect-Oriented Programming in your Spring application.

   Add the following annotation to the main application class or any configuration class:
   ```java
   @EnableAspectJAutoProxy
   @SpringBootApplication
   public class Application {
       public static void main(String[] args) {
           SpringApplication.run(Application.class, args);
       }
   }
   ```

3. **Method Execution Logs:**
   When a method annotated with `@Loggable` is invoked, the `LoggingAspect` will log the following:
   - **Method name**: The name of the method being executed.
   - **Arguments**: The arguments passed to the method.
   - **Execution time**: How long the method took to execute (in milliseconds).
   - **Return value**: The value returned by the method.

#### **Custom Annotation (`@Loggable`) Code:**

```java
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Loggable {
}
```

#### **Logs Example:**
When a method annotated with `@Loggable` is called, the following log messages will be generated:

```
2025-03-11 12:00:00 INFO  - Executing method: borrowBook(Long, Long) with arguments: [1, 101]
2025-03-11 12:00:02 INFO  - Method borrowBook executed successfully in 2000 ms - Return Value: Book [id=1, title="The Great Gatsby"]
```

#### **Benefits of Using Aspects:**
- **Separation of Concerns**: The logging logic is separated from the business logic, making the codebase cleaner and more maintainable.
- **Reusable**: You can reuse the logging functionality across multiple methods without duplicating code.
- **Centralized Management**: All logging configurations and behaviors are centralized, making it easier to modify or update the logging logic in one place.
---

### **6. Cach:**

#### **Key Features:**
- **Cache Management**: Frequently accessed data is cached to reduce database load.
- **Automatic Cache Eviction**: Cache is automatically cleared when you updated to ensure that stale data is not used.
- **Improved Performance**: Caching improves the system's response time by serving data from the cache instead of querying the database repeatedly.
---

### **For eample: Service Class - `BookService`**

#### **1. `getBookById(Long id)` Method:**
This method retrieves a specific book by its ID. The result is cached to reduce database queries for frequently requested books.

```java
@Loggable
@Cacheable("book")
public Book getBookById(Long id) {
    Book book = bookRepository.findById(id)
            .orElseThrow(() -> new BookNotFoundException(EError.BOOK_NOT_FOUND.name()));
    return book;
}
```

- **Description**: Fetches the book by its ID. If not found, throws a `BookNotFoundException`.
- **Caching**: The book object is cached with the key `book`. This means that if the same book is requested again, it will be fetched from the cache instead of querying the database.

#### **2. `updateBook(Long id, Book bookDetails)` Method:**
This method updates an existing book's details (such as title, author, and publication year). 
It uses **cache eviction** to clear the cached book after it's updated, ensuring that the cache contains the most up-to-date data.

```java
@Loggable
@CacheEvict(value = "book", key = "#id")
public Book updateBook(Long id, Book bookDetails) {
    Book book = getBookById(id);  // Retrieve the existing book by ID
    book.setTitle(bookDetails.getTitle());
    book.setAuthor(bookDetails.getAuthor());
    book.setPublicationYear(bookDetails.getPublicationYear());
    return bookRepository.save(book);  // Save the updated book
}
```

- **Description**: Updates the book’s details and saves the updated information to the repository.
- **Cache Eviction**: The cache for the updated book is evicted by using `@CacheEvict(value = "book", key = "#id")`.
-  This ensures that the next time the book is requested, it will be fetched from the database and cached again.
---

### **Caching Behavior Overview:**
- **`@Cacheable("book")`**: This annotation is used in the `getBookById()` method to store the book in the cache when it is retrieved for the first time.
-  If the same book is requested later, the cached value will be returned, improving performance by avoiding database hits.
  
- **`@CacheEvict(value = "book", key = "#id")`**: This annotation is used in the `updateBook()` method to evict (remove) the cached book entry whenever a book is updated.
-  This ensures that the cache stays fresh and reflects the latest data.
---

### **8. Transaction Management:**

The `Transaction Management` ensures the atomicity of operations like borrowing and returning books. 
These operations require multiple changes to be made in the database, and the `@Transactional` annotation ensures that 
if one part of the process fails, the whole transaction will be rolled back, preventing partial updates to the database.

### **Transaction Management with `@Transactional` Annotation:**

#### **Borrowing a Book (`borrowBook` method)**:
```java
@Transactional
@Loggable
public BorrowingRecord borrowBook(Long bookId, Long patronId) {

    // Retrieve book by ID and patron by ID, throw exceptions if not found
    Book book = bookRepository.findById(bookId)
            .orElseThrow(() -> new BookNotFoundException(EError.BOOK_NOT_FOUND.name()));

    Patron patron = patronRepository.findById(patronId)
            .orElseThrow(() -> new PatronNotFoundException(EError.PATRON_NOT_FOUND.name()));

    // Check if the book is already borrowed
    boolean isBookAlreadyBorrowed = borrowingRecordRepository
            .findByBookIdAndReturnDateIsNull(bookId)
            .isPresent();

    if (isBookAlreadyBorrowed) {
        throw new BookAlreadyBorrowedException(EError.BOOK_IS_ALREADY_BORROWED.name());
    }

    // Create and save the borrowing record
    BorrowingRecord record = new BorrowingRecord();
    record.setBookId(book);
    record.setPatronId(patron);
    record.setBorrowDate(new Date());

    // Mark the book as unavailable and save
    book.setAvailable(false);
    bookRepository.save(book);

    // Save the borrowing record and return
    return borrowingRecordRepository.save(record);
}
```

- **Transactional Behavior**: Ensures that either all operations succeed or all operations fail (rollback).
- If any exception is thrown, the transaction is rolled back, and no changes are made to the database.
  
---

#### **Returning a Book (`returnBook` method)**:
```java
@Transactional
@Loggable
public BorrowingRecord returnBook(Long bookId, Long patronId) {

    Optional<BorrowingRecord> recordOpt = borrowingRecordRepository.findByBookIdAndPatronId(bookId, patronId);

    if (recordOpt.isEmpty()) {
        throw new BorrowingRecordNotFoundException(EError.BORROWING_RECORD_NOT_FOUND.name());
    }

    BorrowingRecord record = recordOpt.get();

    if (record.getReturnDate() != null) {
        throw new BorrowingRecordNotFoundException(EError.BOOK_IS_ALREADY_RETURNED.name());
    }

    // Set the return date
    record.setReturnDate(new Date());

    // Retrieve the book and mark it as available again
    Book book = record.getBookId();
    book.setAvailable(true);

    // Save the updated borrowing record and book
    borrowingRecordRepository.save(record);
    bookRepository.save(book);

    return borrowingRecordRepository.save(record);
}
```

- **Transactional Behavior**: Ensures both the borrowing record and the book availability are updated in the same transaction. If one operation fails, both operations are rolled back.

  ### **Conclusion:**

- **Transaction Management** ensures that borrowing and returning books are performed atomically.

---

### **9. Testing:**

Testing is performed to verify that each API endpoint works as expected.
We use **JUnit**, **Mockito**, and **MockMvc** to test the **BookRestController** methods for different actions such as retrieving, adding, updating, and deleting books.

### **Unit Testing with JUnit & Mockito**

#### **BookRestControllerTest Class:**
```java
@ExtendWith(MockitoExtension.class)  // Enables automatic mocking with Mockito
@WebMvcTest(BookRestController.class)  // Runs the test with only the controller in the context
@Import(BookService.class)  // Imports the BookService to the test context
public class BookRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private BookService bookService;  // Use @Mock instead of @MockBean

    private Book sampleBook;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Setup a sample book object for testing
        sampleBook = new Book();
        sampleBook.setId(1L);
        sampleBook.setTitle("Test Book");
        sampleBook.setAuthor("John Doe");
    }

    // Test for retrieving all books
    @Test
    void testGetAllBooks() throws Exception {
        when(bookService.getAllBooks()).thenReturn(List.of(sampleBook));

        mockMvc.perform(get("/api/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bookList").isArray())
                .andExpect(jsonPath("$.bookList[0].title").value("Test Book"));
    }

    // Test for retrieving a book by ID
    @Test
    void testGetBookById() throws Exception {
        when(bookService.getBookById(1L)).thenReturn(sampleBook);

        mockMvc.perform(get("/api/books/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.book.title").value("Test Book"));
    }

    // Test for adding a new book
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
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.book.title").value("Test Book"));
    }

    // Test for updating an existing book
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
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.book.title").value("Test Book"));
    }

    // Test for deleting a book
    @Test
    void testDeleteBook() throws Exception {
        doNothing().when(bookService).deleteBook(1L);

        mockMvc.perform(delete("/api/books/1"))
                .andExpect(status().isOk());
    }

    // Test for a non-existent book (not found)
    @Test
    void testGetBookById_NotFound() throws Exception {
        when(bookService.getBookById(999L)).thenReturn(null);

        mockMvc.perform(get("/api/books/999"))
                .andExpect(status().isNotFound());
    }
}
```

---

### **Explanation of Tests**:

1. **Test for retrieving all books (`testGetAllBooks`)**: This test simulates a `GET` request to fetch all books.
   It mocks the `getAllBooks()` method in `BookService` to return a list containing `sampleBook` and checks the response status and the content.

2. **Test for retrieving a book by ID (`testGetBookById`)**: This test simulates a `GET` request to fetch a book by its ID.
   It mocks the `getBookById(1L)` method to return `sampleBook` and checks the response status and the book's title.

3. **Test for adding a book (`testAddBook`)**: This test simulates a `POST` request to add a new book. It mocks the `addBook(any(Book.class))` method
    in `BookService` and verifies the response status and the content.

4. **Test for updating a book (`testUpdateBook`)**: This test simulates a `PUT` request to update an existing book's details.
   It mocks the `updateBook(any(Long.class), any(Book.class))` method and verifies the response status and content.

5. **Test for deleting a book (`testDeleteBook`)**: This test simulates a `DELETE` request to delete a book by its ID.
   It mocks the `deleteBook(1L)` method and verifies that the status is OK, indicating the deletion was successful.

6. **Test for a non-existent book (`testGetBookById_NotFound`)**: This test simulates a request to fetch a non-existent book. It mocks the `getBookById(999L)` method
    to return `null` and verifies that the status is 404 (Not Found).

---

### **Conclusion:**
- **Unit Tests** ensure the correct behavior of each API endpoint using JUnit, Mockito, and MockMvc.

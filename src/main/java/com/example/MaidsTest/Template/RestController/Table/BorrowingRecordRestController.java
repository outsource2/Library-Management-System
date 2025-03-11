package com.example.MaidsTest.Template.RestController.Table;

import com.example.MaidsTest.Base.Enum.ESuccess;
import com.example.MaidsTest.Template.API.Response.BorrowingRecord.CGetBorrowingRecordResponse;
import com.example.MaidsTest.Template.API.Response.DTO.Mapper.CBorrowingRecordMapper;
import com.example.MaidsTest.Template.Model.Table.BorrowingRecord;
import com.example.MaidsTest.Template.Service.Table.BorrowingRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for managing borrowing records.
 * <p>
 * This class exposes REST API endpoints for handling borrowing and returning of books.
 * It interacts with the {@link BorrowingRecordService} to perform business logic related
 * to borrowing and returning books. The response is formatted using {@link CGetBorrowingRecordResponse}.
 * </p>
 */
@RestController
@RequestMapping("/api/borrowings")  // Endpoint mapping for borrowing-related operations
public class BorrowingRecordRestController {

    /** Service for handling borrowing record-related business logic. */
    @Autowired
    private BorrowingRecordService borrowingRecordService;

    /**
     * Endpoint for borrowing a book.
     * <p>
     * This method handles HTTP POST requests to {@code /api/borrowings/borrow/{bookId}/patron/{patronId}}.
     * It allows a patron to borrow a book by providing the book ID and patron ID.
     * </p>
     *
     * @param bookId   The ID of the book to borrow.
     * @param patronId The ID of the patron borrowing the book.
     * @return A {@link CGetBorrowingRecordResponse} containing the borrowing record and success information.
     */
    @PostMapping("/borrow/{bookId}/patron/{patronId}")
    public CGetBorrowingRecordResponse borrowBook(@PathVariable Long bookId, @PathVariable Long patronId) {

        CGetBorrowingRecordResponse apiResponse = new CGetBorrowingRecordResponse();

        // Borrowing the book using the service
        BorrowingRecord borrowingRecord = borrowingRecordService.borrowBook(bookId, patronId);

        // Mapping the borrowing record to the response format
        apiResponse.setBorrowingRecord(CBorrowingRecordMapper.parse(borrowingRecord));
        apiResponse.setStatus(HttpStatus.OK);  // HTTP status code for success
        apiResponse.setSuccessMessage(ESuccess.BOOK_BORROWED_SUCCESSFULLY.name());  // Success message

        return apiResponse;
    }

    /**
     * Endpoint for returning a borrowed book.
     * <p>
     * This method handles HTTP PUT requests to {@code /api/borrowings/return/{bookId}/patron/{patronId}}.
     * It allows a patron to return a borrowed book by providing the book ID and patron ID.
     * </p>
     *
     * @param bookId   The ID of the book to return.
     * @param patronId The ID of the patron returning the book.
     * @return A {@link CGetBorrowingRecordResponse} containing the updated borrowing record and success information.
     */
    @PutMapping("/return/{bookId}/patron/{patronId}")
    public CGetBorrowingRecordResponse returnBook(@PathVariable Long bookId, @PathVariable Long patronId) {

        CGetBorrowingRecordResponse apiResponse = new CGetBorrowingRecordResponse();

        // Returning the borrowed book using the service
        BorrowingRecord borrowingRecord = borrowingRecordService.returnBook(bookId, patronId);

        // Mapping the updated borrowing record to the response format
        apiResponse.setBorrowingRecord(CBorrowingRecordMapper.parse(borrowingRecord));
        apiResponse.setStatus(HttpStatus.OK);  // HTTP status code for success
        apiResponse.setSuccessMessage(ESuccess.BOOK_RETURNED_SUCCESSFULLY.name());  // Success message

        return apiResponse;
    }
}

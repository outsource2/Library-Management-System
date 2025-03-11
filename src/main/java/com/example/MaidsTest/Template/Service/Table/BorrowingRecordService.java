package com.example.MaidsTest.Template.Service.Table;

import com.example.MaidsTest.Base.Enum.EError;
import com.example.MaidsTest.Template.Aspect.Loggable;
import com.example.MaidsTest.Template.Exception.BookAlreadyBorrowedException;
import com.example.MaidsTest.Template.Exception.BookNotFoundException;
import com.example.MaidsTest.Template.Exception.BorrowingRecordNotFoundException;
import com.example.MaidsTest.Template.Exception.PatronNotFoundException;
import com.example.MaidsTest.Template.Model.Table.Book;
import com.example.MaidsTest.Template.Model.Table.BorrowingRecord;
import com.example.MaidsTest.Template.Model.Table.Patron;
import com.example.MaidsTest.Template.Repository.Table.BookRepository;
import com.example.MaidsTest.Template.Repository.Table.BorrowingRecordRepository;
import com.example.MaidsTest.Template.Repository.Table.PatronRepository;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

@Service
public class BorrowingRecordService {
    @Autowired
    private BorrowingRecordRepository borrowingRecordRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private PatronRepository patronRepository;

    @Transactional
    @Loggable
    public BorrowingRecord borrowBook(Long bookId, Long patronId) {

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException(EError.BOOK_NOT_FOUND.name()));

        Patron patron = patronRepository.findById(patronId)
                .orElseThrow(() -> new PatronNotFoundException(EError.PATRON_NOT_FOUND.name()));

        boolean isBookAlreadyBorrowed = borrowingRecordRepository
                .findByBookIdAndReturnDateIsNull(bookId)
                .isPresent();

        if (isBookAlreadyBorrowed) {
            throw new BookAlreadyBorrowedException(EError.BOOK_IS_ALREADY_BORROWED.name());
        }

        BorrowingRecord record = new BorrowingRecord();
        record.setBookId(book);
        record.setPatronId(patron);
        record.setBorrowDate(new Date());

        book.setAvailable(false);
        bookRepository.save(book);

        return borrowingRecordRepository.save(record);
    }

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

        record.setReturnDate(new Date());

        Book book = record.getBookId();
        book.setAvailable(true);
        borrowingRecordRepository.save(record);
        bookRepository.save(book);

        return borrowingRecordRepository.save(record);
    }

}

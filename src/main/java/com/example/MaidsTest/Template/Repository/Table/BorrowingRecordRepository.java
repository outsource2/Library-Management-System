package com.example.MaidsTest.Template.Repository.Table;

import com.example.MaidsTest.Template.Model.Table.BorrowingRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BorrowingRecordRepository extends JpaRepository<BorrowingRecord, Long> {
    @Query("SELECT A FROM BorrowingRecord A WHERE A.bookId.id = :bookId AND A.patronId.id = :patronId AND A.bookId.available = FALSE AND A.returnDate IS NULL")
    Optional<BorrowingRecord> findByBookIdAndPatronId(@Param("bookId") Long bookId, @Param("patronId") Long patronId);

    @Query("SELECT b FROM BorrowingRecord b WHERE b.bookId.id = :bookId AND b.returnDate IS NULL")
    Optional<BorrowingRecord> findByBookIdAndReturnDateIsNull(@Param("bookId") Long bookId);
}

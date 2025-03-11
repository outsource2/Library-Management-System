package com.example.MaidsTest.Template.API.Response.DTO.Mapper;

import com.example.MaidsTest.Template.API.Response.DTO.CBookDTO;
import com.example.MaidsTest.Template.API.Response.DTO.CBorrowingRecordDTO;
import com.example.MaidsTest.Template.Model.Table.Book;
import com.example.MaidsTest.Template.Model.Table.BorrowingRecord;
import com.example.MaidsTest.Template.Model.Table.Patron;

public class CBorrowingRecordMapper {

    public static CBorrowingRecordDTO parse(BorrowingRecord borrowingRecord)
    {
        CBorrowingRecordDTO borrowingRecordDTO = null;

        do
        {
            if(borrowingRecord == null) break;

            borrowingRecordDTO = new CBorrowingRecordDTO();

            borrowingRecordDTO.setAuthor(borrowingRecord.getBookId().getAuthor());
            borrowingRecordDTO.setName(borrowingRecord.getPatronId().getName());
            borrowingRecordDTO.setTitle(borrowingRecord.getBookId().getTitle());
            borrowingRecordDTO.setPhoneNumber(borrowingRecord.getPatronId().getPhoneNumber());
        }
        while (false);

        return borrowingRecordDTO;
    }
}

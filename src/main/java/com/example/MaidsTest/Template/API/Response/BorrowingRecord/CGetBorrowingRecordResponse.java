package com.example.MaidsTest.Template.API.Response.BorrowingRecord;

import com.example.MaidsTest.Base.API.Response.CAPIResponse;
import com.example.MaidsTest.Template.API.Response.DTO.CBorrowingRecordDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CGetBorrowingRecordResponse extends CAPIResponse {

    CBorrowingRecordDTO borrowingRecord;
}

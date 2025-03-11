package com.example.MaidsTest.Template.API.Response.Patron;

import com.example.MaidsTest.Base.API.Response.CAPIResponse;
import com.example.MaidsTest.Template.API.Response.DTO.CPatronDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CGetPatronResponse extends CAPIResponse {

    CPatronDTO patron;

    List<CPatronDTO> patronList;
}

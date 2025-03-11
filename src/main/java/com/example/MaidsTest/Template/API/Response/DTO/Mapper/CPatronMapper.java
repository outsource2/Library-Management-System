package com.example.MaidsTest.Template.API.Response.DTO.Mapper;

import com.example.MaidsTest.Template.API.Response.DTO.CPatronDTO;
import com.example.MaidsTest.Template.Model.Table.Patron;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CPatronMapper {
    public static CPatronDTO parse(Patron patron)
    {
        CPatronDTO patronDTO = null;

        do
        {
            if(patron == null) break;

            patronDTO = new CPatronDTO();

            patronDTO.setName(patron.getName());
            patronDTO.setAddress(patron.getAddress());
            patronDTO.setPhoneNumber(patron.getPhoneNumber());

        }
        while (false);

        return patronDTO;
    }

    public static List<CPatronDTO> parse(List<Patron> patronList)
    {
        CPatronDTO patronDTO = null;

        List<CPatronDTO> patronDTOList = null;

        do
        {
            if(patronList == null) break;

            patronDTOList = new ArrayList<>();

            for(Patron patron : patronList){

                patronDTO = new CPatronDTO();

                patronDTO.setName(patron.getName());
                patronDTO.setAddress(patron.getAddress());
                patronDTO.setPhoneNumber(patron.getPhoneNumber());

                patronDTOList.add(patronDTO);
            }

        }
        while (false);

        return patronDTOList;
    }
}

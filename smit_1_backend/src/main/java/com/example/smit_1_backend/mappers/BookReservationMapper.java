package com.example.smit_1_backend.mappers;

import com.example.smit_1_backend.dtos.ReservedBookDto;
import com.example.smit_1_backend.entities.BookReservation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BookReservationMapper {

    @Mapping(target = "uuid", source = "uuid")
    @Mapping(target = "startDate", source = "startDate", dateFormat = "yyyy-MM-dd")
    @Mapping(target = "endDate", source = "endDate", dateFormat = "yyyy-MM-dd")
    BookReservation dtoToBookReservation(ReservedBookDto reservedBookDto);
}

package org.hotfilm.backend.Mapper;

import org.hotfilm.backend.Model.Seat;
import org.hotfilm.backend.ModelDTO.Request.SeatRequest;
import org.hotfilm.backend.ModelDTO.Response.SeatResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SeatMapper {
    Seat toSeat(SeatRequest seatRequest);

    SeatResponse toSeatResponse(Seat seat);
}

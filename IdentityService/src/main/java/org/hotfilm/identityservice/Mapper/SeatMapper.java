package org.hotfilm.identityservice.Mapper;

import org.hotfilm.identityservice.Model.Seat;
import org.hotfilm.identityservice.ModelDTO.Request.SeatRequest;
import org.hotfilm.identityservice.ModelDTO.Response.SeatResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SeatMapper {
    Seat toSeat(SeatRequest seatRequest);

    SeatResponse toSeatResponse(Seat seat);
}

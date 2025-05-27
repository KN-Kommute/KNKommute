package kn.kommute.app.mapper;

import kn.kommute.app.dto.RideDTO;
import kn.kommute.app.model.Ride;
import kn.kommute.app.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RideMapper {
    Ride toRide(RideDTO dto, User user);
    RideDTO toRideDTO(Ride ride);
}

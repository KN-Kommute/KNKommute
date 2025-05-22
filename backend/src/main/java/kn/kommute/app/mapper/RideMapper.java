package kn.kommute.app.mapper;

import kn.kommute.app.dto.RideDTO;
import kn.kommute.app.model.Ride;
import kn.kommute.app.model.User;
import org.springframework.stereotype.Component;

@Component
public class RideMapper {

    public Ride toRide(RideDTO dto, User user) {
        return Ride.builder()
                .origin(dto.getOrigin())
                .destination(dto.getDestination())
                .time(dto.getTime())
                .totalValue(dto.getTotalValue())
                .maxUsers(dto.getMaxUsers())
                .totalCarpoolers(1)
                .owner(user)
                .build();
    }

    public RideDTO toSummaryDTO(Ride ride) {
        RideDTO dto = new RideDTO();
        dto.setOrigin(ride.getOrigin());
        dto.setDestination(ride.getDestination());
        dto.setTotalValue(ride.getTotalValue());
        dto.setDate(ride.getTime().toLocalDate());
        dto.setDepartureTime(ride.getTime().toLocalTime());
        return dto;
    }

    public RideDTO toParticipationDTO(Ride ride) {
        RideDTO dto = toSummaryDTO(ride);
        dto.setOwnerName(ride.getOwner().getName());
        dto.setContact(ride.getOwner().getPhoneNumber());
        dto.setParticipants(ride.getTotalCarpoolers() + "/" + ride.getMaxUsers());
        return dto;
    }
}

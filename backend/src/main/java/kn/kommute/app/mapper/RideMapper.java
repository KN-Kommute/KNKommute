
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
}

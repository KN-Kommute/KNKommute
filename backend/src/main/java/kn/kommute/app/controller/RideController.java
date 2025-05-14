package kn.kommute.app.controller;

import kn.kommute.app.model.Ride;
import kn.kommute.app.model.User;
import kn.kommute.app.repository.RideRepository;
import kn.kommute.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/rides")
public class RideController {

    @Autowired
    private RideRepository rideRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/create")
    public ResponseEntity<String> createRide(@AuthenticationPrincipal User user, @RequestParam String origin, @RequestParam String destination, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime time, @RequestParam Float totalValue, @RequestParam int maxUsers) {

        Ride ride = Ride.builder()
                .origin(origin)
                .destination(destination)
                .time(time)
                .totalValue(totalValue)
                .maxUsers(maxUsers)
                .totalCarpoolers(1)
                .owner(user)
                .build();

        rideRepository.save(ride);
        return ResponseEntity.status(HttpStatus.CREATED).body("Ride created successfully");
    }

    @GetMapping
    public ResponseEntity<List<Map<String, Object>>> listRides() {
        List<Ride> rides = rideRepository.findAll();
        List<Map<String, Object>> response = new ArrayList<>();

        for (Ride ride : rides) {
            Map<String, Object> rideData = new LinkedHashMap<>();
            rideData.put("data", ride.getTime().toLocalDate());
            rideData.put("origem", ride.getOrigin());
            rideData.put("destino", ride.getDestination());
            rideData.put("hora_partida", ride.getTime().toLocalTime());
            rideData.put("preco", ride.getTotalValue());
            response.add(rideData);
        }

        return ResponseEntity.ok(response);
    }

    @PostMapping("/{rideId}/participate")
    public ResponseEntity<Map<String, Object>> participate(@AuthenticationPrincipal User user, @PathVariable Long rideId) {

        Optional<Ride> optionalRide = rideRepository.findById(rideId);
        if (optionalRide.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        Ride ride = optionalRide.get();

        if (ride.getTotalCarpoolers() >= ride.getMaxUsers()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        ride.setTotalCarpoolers(ride.getTotalCarpoolers() + 1);
        rideRepository.save(ride);

        Map<String, Object> participationInfo = new LinkedHashMap<>();
        participationInfo.put("nome", ride.getOwner().getName());
        participationInfo.put("contacto", ride.getOwner().getPhoneNumber());
        participationInfo.put("data", ride.getTime().toLocalDate());
        participationInfo.put("origem", ride.getOrigin());
        participationInfo.put("destino", ride.getDestination());
        participationInfo.put("hora_partida", ride.getTime().toLocalTime());
        participationInfo.put("preco", ride.getTotalValue());
        participationInfo.put("participantes", ride.getTotalCarpoolers() + "/" + ride.getMaxUsers());

        return ResponseEntity.ok(participationInfo);
    }
}

package kn.kommute.app.service;

import kn.kommute.app.dto.RideDTO;
import kn.kommute.app.dto.RideDTO;
import kn.kommute.app.mapper.RideMapper;
import kn.kommute.app.model.Ride;
import kn.kommute.app.model.User;
import kn.kommute.app.repository.RideRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class RideService {

    private final RideRepository rideRepository;
    private final RideMapper rideMapper;

    @Autowired
    public RideService(RideRepository rideRepository, RideMapper rideMapper) {
        this.rideRepository = rideRepository;
        this.rideMapper = rideMapper;
    }

    public Ride createRide(User user, RideDTO dto) {
        Ride ride = rideMapper.toRide(dto, user);
        return rideRepository.save(ride);
    }

    public List<Map<String, Object>> listRides() {
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

        return response;
    }

    public Map<String, Object> participate(User user, Long rideId)
    {
        Optional<Ride> optionalRide = rideRepository.findById(rideId);
        if (optionalRide.isEmpty()) {
            return null;
        }

        Ride ride = optionalRide.get();

        if (ride.getTotalCarpoolers() >= ride.getMaxUsers()) {
            return null;
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

        return participationInfo;
    }

    public Optional<Ride> findRideById(Long id) {
        return rideRepository.findById(id);
    }

    public Optional<Ride> findRideByOrigin(String origin) {
        return rideRepository.findByOrigin(origin);
    }

    public Ride updateRide(Long id, Ride updatedRide) {
        Optional<Ride> existingRide = rideRepository.findById(id);
        if (existingRide.isPresent()) {
            Ride ride = existingRide.get();
            ride.setOrigin(updatedRide.getOrigin());
            ride.setDestination(updatedRide.getDestination());
            ride.setTime(updatedRide.getTime());
            ride.setTotalValue(updatedRide.getTotalValue());
            ride.setMaxUsers(updatedRide.getMaxUsers());
            return rideRepository.save(ride);
        }
        return null;
    }

    public void deleteRide(Long id) {
        rideRepository.deleteById(id);
    }
}

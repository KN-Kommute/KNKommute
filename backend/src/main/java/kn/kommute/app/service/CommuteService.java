package kn.kommute.app.service;
import kn.kommute.app.dto.CommuteDTO;
import kn.kommute.app.mapper.CommuteMapper;
import kn.kommute.app.model.Commute;
import kn.kommute.app.repository.CommuteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static kn.kommute.app.util.CommuteUtil.isVehicleNumberOfSeatsValid;

@Service
public class CommuteService {

    @Autowired
    private CommuteRepository commuteRepository;

    @Autowired
    private CommuteMapper commuteMapper;

    // Add a new commute
    public CommuteDTO addCommute(CommuteDTO commuteDTO) {

        commuteDTO.setActive(true);
        commuteDTO.setCreationDate(LocalDateTime.now());

        // Validate the input data
        if (commuteDTO.getOrigin() == null || commuteDTO.getDestination() == null || !isVehicleNumberOfSeatsValid(commuteDTO.getNumberOfSeats())) {
            throw new IllegalArgumentException("Invalid commute data");
        }

        Commute commute = commuteMapper.toEntity(commuteDTO);

        return commuteMapper.toDTO(commuteRepository.save(commute));
    }

    // Get all commutes
    public List<CommuteDTO> getAllCommutes() {
        List<Commute> commutes = commuteRepository.findAll();
        return commuteMapper.toDTOs(commutes);
    }
}


package kn.kommute.app.mapper;

import kn.kommute.app.dto.ParticipationDTO;
import kn.kommute.app.model.Participation;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ParticipationMapper {

    ParticipationDTO toDTO(Participation participation);

    Participation toEntity(ParticipationDTO dto);
}
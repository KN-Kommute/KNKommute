package kn.kommute.app.mapper;

import kn.kommute.app.dto.CommuteDTO;
import kn.kommute.app.model.Commute;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CommuteMapper {

    Commute toEntity(CommuteDTO commuteDTO);

    List<CommuteDTO> toDTOs(List<Commute> commutes);

    CommuteDTO toDTO(Commute commute);

}

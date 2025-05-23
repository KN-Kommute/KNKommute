package kn.kommute.app.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import kn.kommute.app.dto.CommuteDTO;
import kn.kommute.app.model.Commute;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-05-22T14:47:33+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.7 (Eclipse Adoptium)"
)
@Component
public class CommuteMapperImpl implements CommuteMapper {

    @Override
    public Commute toEntity(CommuteDTO commuteDTO) {
        if ( commuteDTO == null ) {
            return null;
        }

        Commute commute = new Commute();

        return commute;
    }

    @Override
    public List<CommuteDTO> toDTOs(List<Commute> commutes) {
        if ( commutes == null ) {
            return null;
        }

        List<CommuteDTO> list = new ArrayList<CommuteDTO>( commutes.size() );
        for ( Commute commute : commutes ) {
            list.add( toDTO( commute ) );
        }

        return list;
    }

    @Override
    public CommuteDTO toDTO(Commute commute) {
        if ( commute == null ) {
            return null;
        }

        CommuteDTO commuteDTO = new CommuteDTO();

        return commuteDTO;
    }
}

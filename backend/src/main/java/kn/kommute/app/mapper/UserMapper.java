package kn.kommute.app.mapper;

import kn.kommute.app.dto.UserDTO;
import kn.kommute.app.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDTO toDTO(User user);

    User toEntity(UserDTO userDTO);
}

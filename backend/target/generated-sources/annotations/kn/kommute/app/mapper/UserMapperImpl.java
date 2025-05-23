package kn.kommute.app.mapper;

import javax.annotation.processing.Generated;
import kn.kommute.app.dto.UserDTO;
import kn.kommute.app.model.User;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-05-22T14:47:33+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.7 (Eclipse Adoptium)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserDTO toDTO(User user) {
        if ( user == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        return userDTO;
    }

    @Override
    public User toEntity(UserDTO userDTO) {
        if ( userDTO == null ) {
            return null;
        }

        String name = null;
        String email = null;
        String password = null;
        String phoneNumber = null;

        User user = new User( name, email, password, phoneNumber );

        return user;
    }
}

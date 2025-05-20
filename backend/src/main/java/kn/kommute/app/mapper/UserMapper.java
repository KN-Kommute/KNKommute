package kn.kommute.app.mapper;


import kn.kommute.app.dto.UserDTO;
import kn.kommute.app.model.User;

public class UserMapper {

    public static UserDTO toDTO(User user) {
        return new UserDTO(
                user.getName(),
                user.getEmail(),
                user.getPhoneNumber()
        );
    }

    public static User toEntity(UserDTO dto) {
        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPhoneNumber(dto.getPhoneNumber());
        return user;
    }
}


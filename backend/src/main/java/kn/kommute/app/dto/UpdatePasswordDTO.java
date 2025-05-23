package kn.kommute.app.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UpdatePasswordDTO {

    private String oldPassword;
    private String newPassword;

    public UpdatePasswordDTO() {
    }

    public UpdatePasswordDTO(String oldPassword, String newPassword) {
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }

}

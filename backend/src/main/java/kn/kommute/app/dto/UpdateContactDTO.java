package kn.kommute.app.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UpdateContactDTO {

    private String phoneNumber;

    public UpdateContactDTO() {
    }

    public UpdateContactDTO(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

}

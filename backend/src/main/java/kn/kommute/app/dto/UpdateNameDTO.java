package kn.kommute.app.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UpdateNameDTO {

    private String name;

    public UpdateNameDTO() {
    }

    public UpdateNameDTO(String name) {
        this.name = name;
    }

}

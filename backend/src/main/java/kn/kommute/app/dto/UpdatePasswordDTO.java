package kn.kommute.app.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePasswordDTO {
    @NotBlank
    private String oldPassword;

    @NotBlank
    @Size(min = 6)
    private String newPassword;
}

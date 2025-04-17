package kn.kommute.app.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class CommuteDTO {

    public CommuteDTO() {}

    private Long id;
    private String origin;
    private String destination;
    private int numberOfSeats;
    private boolean active;
    private LocalDateTime creationDate;
    private LocalDateTime commuteDate;


}

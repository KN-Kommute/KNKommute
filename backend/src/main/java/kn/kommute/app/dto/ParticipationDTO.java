package kn.kommute.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParticipationDTO {
    private Long id;
    private Long rideId;
    private Long participantId;
    private String participantName;
    private String participantPhoneNumber;
    private String pickupLocation;
    private LocalDateTime pickupTime;
}

package kn.kommute.app.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class RideDTO {
    private String origin;
    private String destination;
    private LocalDateTime time;
    private Float totalValue;
    private int maxUsers;
}

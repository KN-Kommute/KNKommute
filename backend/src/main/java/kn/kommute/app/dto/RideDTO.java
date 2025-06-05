package kn.kommute.app.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class RideDTO {
    private String origin;
    private String destination;
    private LocalDateTime time;
    private Float totalValue;
    private int maxUsers;
    private int totalCarpoolers;
    private String ownerName;
    private String phoneNumber;
}

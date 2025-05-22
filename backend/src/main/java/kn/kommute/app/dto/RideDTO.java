package kn.kommute.app.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class RideDTO {
    // Usado na criação
    private String origin;
    private String destination;
    private LocalDateTime time;
    private Float totalValue;
    private int maxUsers;

    // Usado na listagem e visualização (não preenchido na criação)
    private LocalDate date;
    private LocalTime departureTime;
    private String ownerName;
    private String contact;
    private String participants;
}

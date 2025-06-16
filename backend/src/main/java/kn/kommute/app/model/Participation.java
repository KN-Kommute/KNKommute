package kn.kommute.app.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "participations")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Participation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ride_id", nullable = false)
    private Ride ride;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "joined_location",nullable = false, length = 255)
    private String pickupLocation;

    @Column(name = "joined_at", nullable = false)
    private LocalDateTime pickupTime;

    @Column(name = "status", nullable = false)
    private String status = "TEMP";

}

package kn.kommute.app.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ride {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String origin;

    @Column(nullable = false, length = 255)
    private String destination;

    @Column(nullable = false )
    private LocalDateTime time;

    @ManyToOne
    @JoinColumn(name = "user_owner_id", nullable = false) // Chave estrangeira para User.id
    private User owner;

    private Float totalValue;

    private int maxUsers;

    @Column(name = "total_carpoolers")
    private int totalCarpoolers;
}
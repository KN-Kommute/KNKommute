package kn.kommute.app.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@AllArgsConstructor
@Table(name = "commutes")
public class Commute {

    public Commute() {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String origin;
    private String destination;
    private int numberOfSeats;
    private boolean active;
    private LocalDateTime creationDate;
    private LocalDateTime commuteDate;

}

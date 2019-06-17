package game.database.leaderBoard.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Min;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Player {

    @GeneratedValue
    @Id
    private Long id;

    @Column(nullable = false)
    @Getter
    private String name;

    @Min(0)
    @Column(nullable = false)
    @Getter
    private int steps;
}

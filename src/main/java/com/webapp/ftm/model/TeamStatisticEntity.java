package com.webapp.ftm.model;
import com.webapp.ftm.model.pk.TeamStatisticPk;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name="teamstatistic")
public class TeamStatisticEntity {
    @EmbeddedId
    private TeamStatisticPk teamStatisticPk;
    private Long played;
    private Long won;
    private Long drawn;
    private Long lost;
    private Long scored;
    private Long conceded;
    private Long point;

    public Long getGd() {
        return scored - conceded;
    }
}

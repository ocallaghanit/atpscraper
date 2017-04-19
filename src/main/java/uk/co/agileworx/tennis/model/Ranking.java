package uk.co.agileworx.tennis.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

/**
 * Created by conor on 15/04/2017.
 */
@Entity
public class Ranking {
    @Id
    private long id;
    @ManyToOne
    private Player player;

    private Integer position;
    private Integer points;
    private LocalDate rankingWeek;

    public Ranking(Integer position, Integer points, LocalDate rankingWeek) {
        this.position = position;
        this.points = points;
        this.rankingWeek = rankingWeek;
    }

    public Ranking() {
    }

    public Integer getPosition() {
        return position;
    }

    public Integer getPoints() {
        return points;
    }

    public LocalDate getRankingWeek() {
        return rankingWeek;
    }
}

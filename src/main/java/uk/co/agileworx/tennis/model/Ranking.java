package uk.co.agileworx.tennis.model;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * Created by conor on 15/04/2017.
 */
@Entity
@Table(name = "rankings")
public class Ranking {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private Integer position;
    private Integer points;
    private LocalDate rankingWeek;
    @ManyToOne
    private Player player;

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

    public long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Ranking ranking = (Ranking) o;

        if (position != null ? !position.equals(ranking.position) : ranking.position != null) return false;
        if (points != null ? !points.equals(ranking.points) : ranking.points != null) return false;
        if (rankingWeek != null ? !rankingWeek.equals(ranking.rankingWeek) : ranking.rankingWeek != null) return false;
        return player != null ? player.equals(ranking.player) : ranking.player == null;

    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public void setRankingWeek(LocalDate rankingWeek) {
        this.rankingWeek = rankingWeek;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    @Override
    public int hashCode() {
        int result = position != null ? position.hashCode() : 0;
        result = 31 * result + (points != null ? points.hashCode() : 0);
        result = 31 * result + (rankingWeek != null ? rankingWeek.hashCode() : 0);
        result = 31 * result + (player != null ? player.hashCode() : 0);
        return result;
    }
}

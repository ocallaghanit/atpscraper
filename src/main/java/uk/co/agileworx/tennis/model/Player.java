package uk.co.agileworx.tennis.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by conor on 06/04/2017.
 */
@Entity
@Table(name = "players")
public class Player {
    @Id
    private String id;
    private String name;

    @OneToMany
    private List<Ranking> rankingHistory = new ArrayList<>();

    public Player(String id, String name) {
        this.id = id;
        this.name = name;


    }

    public Player() {
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Ranking> getRankingHistory() {
        return rankingHistory;
    }

    public void addRanking(Ranking ranking) {
        rankingHistory.add(ranking);
    }
}

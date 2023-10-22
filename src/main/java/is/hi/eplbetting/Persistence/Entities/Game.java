package is.hi.eplbetting.Persistence.Entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "games")
public class Game{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String homeTeam;
    private String awayTeam;
    private boolean hasElapsed;

    public Game() {}

    public Game(String homeTeam, String awayTeam, boolean hasElapsed) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.hasElapsed = hasElapsed;
    }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getHomeTeam() {
        return homeTeam;
    }
    public void setHomeTeam(String homeTeam) {
        this.homeTeam = homeTeam;
    }
    public String getAwayTeam() {
        return awayTeam;
    }
    public void setAwayTeam(String awayTeam) {
        this.awayTeam = awayTeam;
    }
    public boolean isHasElapsed() {
        return hasElapsed;
    }
    public void setHasElapsed(boolean hasElapsed) {
        this.hasElapsed = hasElapsed;
    }

}
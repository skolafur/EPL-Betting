package is.hi.eplbetting.Persistence.Entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "bets")
public class Bet{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long gameId;
    private long userId;
    private double amount;
    private String selectedTeam;

    public Bet() {}

    public Bet(long gameId, long userId, double amount, String selectedTeam) {
        this.gameId = gameId;
        this.userId = userId;
        this.amount = amount;
        this.selectedTeam = selectedTeam;
    }
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public long getGame() {
        return gameId;
    }

    public void setGame(long gameId) {
        this.gameId = gameId;
    }

    public long getUser() {
        return userId;
    }

    public void setUser(long userId) {
        this.userId = userId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getSelectedTeam() {
        return selectedTeam;
    }

    public void setSelectedTeam(String selectedTeam) {
        this.selectedTeam = selectedTeam;
    }
   

}
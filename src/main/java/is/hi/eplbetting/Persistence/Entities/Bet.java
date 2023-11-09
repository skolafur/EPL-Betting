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
    private double multiplier1;
    private double multiplierX;
    private double multiplier2;
    private String selectedTeam;

    public Bet() {}

    public Bet(long gameId, long userId, double amount, double multiplier1, double multiplierX, double multiplier2, String selectedTeam) {
        this.gameId = gameId;
        this.userId = userId;
        this.amount = amount;
        this.multiplier1 = multiplier1;
        this.multiplierX = multiplierX;
        this.multiplier2 = multiplier2;
        this.selectedTeam = selectedTeam;
    }
    public double getMultiplierX() {
        return multiplierX;
    }

    public void setMultiplierX(double multiplierX) {
        this.multiplierX = multiplierX;
    }

    public double getMultiplier2() {
        return multiplier2;
    }

    public void setMultiplier2(double multiplier2) {
        this.multiplier2 = multiplier2;
    }

    public long getId() {
        return id;
    }
    public double getMultiplier1() {
        return multiplier1;
    }

    public void setMultiplier1(double multiplier1) {
        this.multiplier1 = multiplier1;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getGameId() {
        return gameId;
    }

    public void setGameId(long gameId) {
        this.gameId = gameId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
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
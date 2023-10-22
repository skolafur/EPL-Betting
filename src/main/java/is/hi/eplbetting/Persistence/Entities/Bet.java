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
    private Game game;
    private User user;
    private double amount;
    private String selectedTeam;

    public Bet() {}

    public Bet(Game game, User user, double amount, String selectedTeam) {
        this.game = game;
        this.user = user;
        this.amount = amount;
        this.selectedTeam = selectedTeam;
    }
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
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
    private int homeTeamScore;
    private int awayTeamScore;
    private boolean hasStarted;
    private boolean hasFinished;
    private String dateStr;
    private String timeStr;
    private boolean userHasBetted;
    private long betId;
    private double multiplier1;
    private double multiplierX;
    private double multiplier2;
    private boolean paidOut;

    public String getDateStr() {
        return dateStr;
    }

    public void setDateStr(String dateStr) {
        this.dateStr = dateStr;
    }

    public String getTimeStr() {
        return timeStr;
    }

    public void setTimeStr(String timeStr) {
        this.timeStr = timeStr;
    }

    public Game() {}

    public Game(String homeTeam, String awayTeam, int homeTeamScore, int awayTeamScore, boolean hasStarted, boolean hasFinished, String dateStr, String timeStr, boolean userHasBetted, long betId, double multiplier1, double multiplierX, double multiplier2, boolean paidOut) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.homeTeamScore = homeTeamScore;
        this.awayTeamScore = awayTeamScore;
        this.hasStarted = hasStarted;
        this.hasFinished = hasFinished;
        this.dateStr = dateStr;
        this.timeStr = timeStr;
        this.userHasBetted = userHasBetted;
        this.betId = betId;
        this.multiplier1 = multiplier1;
        this.multiplierX = multiplierX;
        this.multiplier2 = multiplier2;
        this.paidOut = paidOut;
    }

    public double getMultiplier1() {
        return multiplier1;
    }

    public void setMultiplier1(double multiplier1) {
        this.multiplier1 = multiplier1;
    }

    public double getMultiplierX() {
        return multiplierX;
    }

    public boolean isPaidOut() {
        return paidOut;
    }

    public void setPaidOut(boolean paidOut) {
        this.paidOut = paidOut;
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
    public long getBetId() {
        return betId;
    }

    public void setBetId(long betId) {
        this.betId = betId;
    }

    public boolean isUserHasBetted() {
        return userHasBetted;
    }

    public void setUserHasBetted(boolean userHasBetted) {
        this.userHasBetted = userHasBetted;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isHasFinished() {
        return hasFinished;
    }

    public void setHasFinished(boolean hasFinished) {
        this.hasFinished = hasFinished;
    }

    public String getHomeTeam() {
        return homeTeam;
    }
    public int getHomeTeamScore() {
        return homeTeamScore;
    }

    public void setHomeTeamScore(int homeTeamScore) {
        this.homeTeamScore = homeTeamScore;
    }

    public int getAwayTeamScore() {
        return awayTeamScore;
    }

    public void setAwayTeamScore(int awayTeamScore) {
        this.awayTeamScore = awayTeamScore;
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
    public boolean isHasStarted() {
        return hasStarted;
    }
    public void setHasStarted(boolean hasStarted) {
        this.hasStarted = hasStarted;
    }

}
package is.hi.eplbetting.Models;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

public class BetResult {
    @Min(1)
    @Max(3)
    private int generatedNumber;

    private boolean win;
    private double winAmount;

    public BetResult() {}

    public BetResult(int generatedNumber, boolean win, double winAmount) {
        this.generatedNumber = generatedNumber;
        this.win = win;
        this.winAmount = winAmount;
    }

    public int getGeneratedNumber() {
        return generatedNumber;
    }

    public void setGeneratedNumber(int generatedNumber) {
        this.generatedNumber = generatedNumber;
    }

    public boolean isWin() {
        return win;
    }

    public void setWin(boolean win) {
        this.win = win;
    }

    public double getWinAmount() {
        return winAmount;
    }

    public void setWinAmount(double winAmount) {
        this.winAmount = winAmount;
    }
}


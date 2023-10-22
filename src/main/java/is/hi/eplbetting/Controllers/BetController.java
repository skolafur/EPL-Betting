package is.hi.eplbetting.Controllers;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.concurrent.ThreadLocalRandom;


import is.hi.eplbetting.Models.Bet;
import is.hi.eplbetting.Models.BetResult;

@RestController
@RequestMapping("/bet")
public class BetController {

 @PostMapping(value = "/bet", produces = MediaType.APPLICATION_JSON_VALUE)
    public BetResult getBet(@RequestBody Bet bet) {
            double winAmount = 0;
            double betIndex = 1.3;

           
        boolean win = false;
        int generatedNumber = ThreadLocalRandom.current().nextInt(1, 4);

        if ((bet.getNumber() >= 1 && bet.getNumber() <= 3)
                && (bet.getBetAmount() >= 0.01 && bet.getBetAmount() <= Double.MAX_VALUE)) {
            win = bet.getNumber() == generatedNumber;
            if (win) {
                if(bet.getNumber() == 100){
                    BetResult betResult = new BetResult();
                    betResult.setGeneratedNumber(generatedNumber);
                    betResult.setWin(true);
                    betResult.setWinAmount(0);
                    return betResult;
                }
                winAmount = bet.getBetAmount() * (betIndex);
            } else {
                winAmount = 0;
            }
        }

        double roundNumberToTwoDecimals = Math.round(winAmount * 100) / 100.0D;

        BetResult betResult = new BetResult();
        betResult.setGeneratedNumber(generatedNumber);
        betResult.setWin(win);
        betResult.setWinAmount(roundNumberToTwoDecimals);

        return betResult;
 }
}

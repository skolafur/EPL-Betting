package is.hi.eplbetting.Services;

import java.util.List;

import is.hi.eplbetting.Persistence.Entities.Bet;

public interface BetService {

    Bet createBet(Bet bet);
    String getBetInfo(long id);
    Bet getBet(long id);
    List<Bet> getBetsList();
    void deleteBet(Bet bet);
    
}

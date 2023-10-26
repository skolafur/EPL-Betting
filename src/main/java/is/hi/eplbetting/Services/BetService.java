package is.hi.eplbetting.Services;

import java.util.List;

import is.hi.eplbetting.Persistence.Entities.Bet;

public interface BetService {

    Bet createBet(Bet bet);
    Bet getBet(long betId);
    List<Bet> getBetsByUserId(long userId);
    Bet getBet(long gameId, long userId);
    List<Bet> getBetsList();
    void deleteBet(Bet bet);
    
}

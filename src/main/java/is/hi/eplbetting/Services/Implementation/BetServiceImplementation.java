package is.hi.eplbetting.Services.Implementation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import is.hi.eplbetting.Persistence.Entities.Bet;
import is.hi.eplbetting.Persistence.Entities.Game;
import is.hi.eplbetting.Persistence.Entities.User;
import is.hi.eplbetting.Persistence.Repositories.BetRepository;
import is.hi.eplbetting.Services.BetService;

@Service
public class BetServiceImplementation implements BetService{
    private BetRepository betRepository;

    public BetServiceImplementation(BetRepository betRepository) {
        this.betRepository = betRepository;
    }

    @Override
    public Bet createBet(Bet bet) {
        return betRepository.save(bet);
    }

    @Override
    public void deleteBet(Bet bet) {
       betRepository.delete(bet);
        
    }

    @Override
    public List<Bet> getBetsList() {
        return betRepository.findAll();
    }

    @Override
    public List<Bet> getBetsByUserId(long userId) {
        List<Bet> allBets = betRepository.findAll();
        List<Bet> bets = new ArrayList<Bet>();
        for (Bet bet: allBets) {
            if (bet.getUserId() == userId) {
                bets.add(bet);
            }
        }
        return bets;
    }

    @Override
    public Bet getBet(long gameId, long userId) {
        List<Bet> bets = getBetsByUserId(userId);
        for (Bet bet: bets) {
            if (bet.getGameId() == gameId) {
                return bet;
            }
        }
        return null;
    }

    @Override
    public Bet getBet(long betId) {
        return betRepository.getById(betId);
    }
    
}

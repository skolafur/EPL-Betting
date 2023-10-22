package is.hi.eplbetting.Services.Implementation;

import java.util.List;

import org.springframework.stereotype.Service;

import is.hi.eplbetting.Persistence.Entities.Bet;
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
        // TODO Auto-generated method stub
        
    }

    @Override
    public Bet getBet(long id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getBetInfo(long id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Bet> getBetsList() {
        // TODO Auto-generated method stub
        return null;
    }
    
}

package is.hi.eplbetting.Persistence.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import is.hi.eplbetting.Persistence.Entities.Bet;

public interface BetRepository extends JpaRepository<Bet, Long>{

    <S extends Bet> S save(Bet bet);
    void delete(Bet bet);
    List<Bet> findAll();
    Bet findById(long id);
    
}

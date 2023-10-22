package is.hi.eplbetting.Persistence.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import is.hi.eplbetting.Persistence.Entities.User;

public interface UserRepository extends JpaRepository<User, Long>{

    <S extends User> S save(User user);
    void delete(User user);
    List<User> findAll();
    User findById(long id);
    User findByUsername(String username); 
    
}

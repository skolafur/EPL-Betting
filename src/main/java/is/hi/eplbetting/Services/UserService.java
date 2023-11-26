package is.hi.eplbetting.Services;

import java.util.List;

import is.hi.eplbetting.Persistence.Entities.User;

public interface UserService {

    User createUser(User user);
    User login(User user);
    List<User> getUsers();
    User getByUsername(String username);
    User getById(long id);
    void deleteUser(User user);
    
}
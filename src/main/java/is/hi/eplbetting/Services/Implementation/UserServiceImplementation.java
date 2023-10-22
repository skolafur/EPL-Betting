package is.hi.eplbetting.Services.Implementation;

import java.util.List;

import org.springframework.stereotype.Service;

import is.hi.eplbetting.Persistence.Entities.User;
import is.hi.eplbetting.Persistence.Repositories.UserRepository;
import is.hi.eplbetting.Services.UserService;

@Service
public class UserServiceImplementation implements UserService{

    private UserRepository userRepository;

    public UserServiceImplementation(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(User user) {
        userRepository.delete(user);
    }

    @Override
    public User getByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public User login(User user) {
        User doesExist = getByUsername(user.getUsername());
        if (doesExist != null) {
            if (doesExist.getPassword().equals(user.getPassword())){
                return doesExist;
            }
        }
        return null;
    }

    @Override
    public void logout(User user) {
        // TODO Auto-generated method stub
        
    }
    
}

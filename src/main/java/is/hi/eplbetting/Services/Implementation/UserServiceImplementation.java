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

        User admin = userRepository.findById(1);
        if (admin == null) {
            admin = new User();
            admin.setUsername("admin");
            admin.setPassword("admin");
            admin.setAdmin(true);
            userRepository.save(admin);
        }
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
    public User getById(long id) {
        return userRepository.findById(id);
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

    
}

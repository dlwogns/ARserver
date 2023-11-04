package ARserver.ARserver.domain.service;

import ARserver.ARserver.domain.user.User;
import ARserver.ARserver.domain.user.UserRepository;

public class UserService {
    UserRepository userRepository = UserRepository.getInstance();
    public Long login(String email, String password){
        User user = userRepository.findByEmail(email);
        if(user.getPassword().equals(password))
            return user.getId();
        return null;
    }
}

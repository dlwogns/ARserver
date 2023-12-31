package ARserver.ARserver.domain.service;

import ARserver.ARserver.domain.user.User;
import ARserver.ARserver.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    UserRepository userRepository = new UserRepository();
    public User login(String email, String password){
        User user = userRepository.findByEmail(email);
        if(user == null)
            return null;
        if(user.getPassword().equals(password))
            return user;
        return null;
    }
    public Long join(User user){
        userRepository.save(user);
        return user.getId();
    }
    public boolean checkLoginEmailDuplicated(User user){
        if(userRepository.findByEmail(user.getEmail()) != null)
            return true;
        return false;
    }

    public User getUserById(Long id){
        return userRepository.findById(id);
    }
}

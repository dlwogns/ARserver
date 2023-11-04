package ARserver.ARserver.domain.user;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class UserRepository {
    private static UserRepository instance;
    private static final Map<String, User> store = new ConcurrentHashMap<>();
    private UserRepository(){}

    private Long sequence = 1L;

    public static UserRepository getInstance(){
        if(instance == null){
            instance = new UserRepository();
        }
        return instance;
    }

    public User save(User user){
        user.setId(sequence++);
        store.put(user.getEmail(), user);
        return user;
    }

    public User findByEmail(String email){
        return store.get(email);
    }


}

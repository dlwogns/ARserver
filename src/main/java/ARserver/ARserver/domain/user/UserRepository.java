package ARserver.ARserver.domain.user;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class UserRepository {
    private static final Map<String, User> store = new ConcurrentHashMap<>();

    public User save(User user){
        store.put(user.getEmail(), user);
        return user;
    }

    public User findByEmail(String email){
        return store.get(email);
    }


}

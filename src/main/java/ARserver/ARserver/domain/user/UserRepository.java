package ARserver.ARserver.domain.user;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class UserRepository {
    private static final Map<Long, User> store = new ConcurrentHashMap<>();
    private static final Map<String, User> storebyemail = new ConcurrentHashMap<>();
    private Long sequence = 1L;

    public User save(User user){
        user.setId(sequence++);
        store.put(user.getId(), user);
        storebyemail.put(user.getEmail(), user);
        return user;
    }

    public User findByEmail(String email){
        return storebyemail.get(email);
    }
    public User findById(Long id){
        return store.get(id);
    }


}

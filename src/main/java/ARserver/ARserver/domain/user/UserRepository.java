package ARserver.ARserver.domain.user;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class UserRepository {
    private static final Map<Long, User> store = new ConcurrentHashMap<>();
    private static long sequence = 0L;

    public User save(User user){
        user.setId(++sequence);
        store.put(user.getId(), user);
        return user;
    }

    public User findById(Long id){
        return store.get(id);
    }

}

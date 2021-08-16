package com.example.restfulwebservice.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class UserDaoService {

    private static List<User> users = new ArrayList<>();

    private static int userCount = 3;

    static {
        users.add(new User(1, "Min", new Date(), "pass1", "101111"));
        users.add(new User(2, "Rem", new Date(), "pass2", "102222"));
        users.add(new User(3, "Alice", new Date(), "pass3", "103333"));
    }

    public List<User> findAll() {
        return users;
    }

    public User save(User user) {
        if (user.getId() == null) {
            user.setId(++userCount);
        }

        users.add(user);
        return user;
    }

    public User findOne(int id) {
        return users.stream().filter(user -> user.getId().equals(id)).findFirst().orElse(null);
    }

    public User deleteById(int id) {
        // TODO: 2021-08-15 Iterator말고 다른 방법으로 없을까 확인
        Iterator<User> iterator = users.iterator();

        while (iterator.hasNext()) {
            User user = iterator.next();

            if (user.getId() == id) {
                iterator.remove();
                return user;
            }
        }

        return null;
    }

}

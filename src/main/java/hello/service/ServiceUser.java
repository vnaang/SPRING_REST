package hello.service;

import hello.model.User;
import java.util.List;


public interface ServiceUser {

    void addUser(User user);

    User getUserById(long id);

    void delete(User user);

    void saveUser(User user);

    List<User> getAllUsers();

    void updateUser(User user);

    User getUserByName(String name);

    void dropPass(User user);
}

package hello.service;

import hello.model.Role;
import hello.model.User;
import hello.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Service
public class ServiceUserImpl implements ServiceUser {

    private final UserRepository userRepository;

    public ServiceUserImpl( BCryptPasswordEncoder encoder, UserRepository userRepository){
        this.encoder = encoder;
        this.userRepository = userRepository;
    }

    private final BCryptPasswordEncoder encoder;

    @Transactional
    @Override
    public void addUser(User user) { userRepository.save(user); }

    @Transactional
    @Override
    public User getUserById(long id) {
        return userRepository.findById(id).get();
    }

    @Transactional
    @Override
    public void saveUser(User user) throws UsernameNotFoundException {
        user.setRoles(Collections.singleton(new Role(2L,"ROLE_USER")));
        user.setPassword(encoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Transactional
    @Override
    public void delete(User user) {
        userRepository.delete(user);
    }

    @Transactional
    @Override
    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        userRepository.findAll().forEach(list::add);
        return list;
    }

    @Transactional
    @Override
    public void updateUser(User user) {
        user.setRoles(Collections.singleton(new Role(2L,"ROLE_USER")));
        user.setPassword(encoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Transactional
    @Override
    public User getUserByName(String username) {
        return userRepository.findByUsername(username);
    }

    @Transactional
    @Override
    public void dropPass(User user){
        user.setPassword(null);
    }
}


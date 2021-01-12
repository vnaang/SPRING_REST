package hello.service;

import hello.model.Role;
import hello.model.User;
import hello.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


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
        if(user.getRole().contains("ROLE_ADMIN")){
            user.setRoles(Collections.singleton(new Role(1l,"ROLE_ADMIN")));
        }else{
            user.setRoles(Collections.singleton(new Role(2l,"ROLE_USER")));
        }
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
    public User getUserByName(String username) {
        return userRepository.findByUsername(username);
    }

    @Transactional
    @Override
    public void dropPass(User user){
        user.setPassword(null);
    }

}


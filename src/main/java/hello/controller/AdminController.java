package hello.controller;

import hello.model.User;
import hello.service.ServiceUser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/api")
public class AdminController {

    private final ServiceUser serviceUser;

    public AdminController(ServiceUser serviceUser) {
        this.serviceUser = serviceUser;
    }


    @RequestMapping(value = "/admin/users", method = RequestMethod.POST)
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<>(serviceUser.getAllUsers(), HttpStatus.OK);
    }

    @PostMapping(value = "/admin/update")
    public void update(User user) {
        serviceUser.saveUser(user);
    }

    @PostMapping("/admin/remove")
    public void deleteUser(User user){
        serviceUser.delete(user);

    }

    @PostMapping("/admin/addUser")
    public void add( User user) {
        serviceUser.addUser(user);
    }

    @PostMapping("/user/getUser")
    public ResponseEntity<List<User>> getUser(HttpSession session) {
        List<User> userList = new ArrayList<>();
        User user = (User) session.getAttribute("user");
        userList.add(user);
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }
}


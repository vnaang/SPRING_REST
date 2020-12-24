package hello.controller;

import hello.model.User;
import hello.service.ServiceUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import hello.repository.UserRepository;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class AdminController {

    private final UserRepository userRepository;
    private final ServiceUser serviceUser;

    public AdminController(UserRepository userRepository, ServiceUser serviceUser) {
        this.userRepository = userRepository;
        this.serviceUser = serviceUser;
    }

    @GetMapping("/admin")
    public String homePage(Model model){
        model.addAttribute("users", serviceUser.getAllUsers());
        return "table";
    }

    @GetMapping("/delete")
    public String deleteUser(@ModelAttribute("user") User user){
        serviceUser.delete(user);
        return "redirect:/admin";
    }

    @GetMapping("/new")
    public String add(@ModelAttribute("user") User user) {
        return "edit";
    }

    @PostMapping("/save")
    public String saveUser(@ModelAttribute("user") User user){
        if (user.getId()==0){
            serviceUser.saveUser(user);
        }else {
            serviceUser.updateUser(user);
        }
        return "redirect:/admin";
    }

    @GetMapping("/edit")
    public String edit(Model model,@RequestParam Long id ){
        User user = serviceUser.getUserById(id);
        serviceUser.dropPass(user);
        model.addAttribute("user", user);
        return "edit";
    }
}


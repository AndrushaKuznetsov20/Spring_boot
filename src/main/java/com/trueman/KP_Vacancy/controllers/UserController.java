package com.trueman.KP_Vacancy.controllers;

import com.trueman.KP_Vacancy.models.Announcement;
import com.trueman.KP_Vacancy.models.User;
import com.trueman.KP_Vacancy.models.enums.Role;
import com.trueman.KP_Vacancy.repositories.UserRepository;
import com.trueman.KP_Vacancy.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@CrossOrigin(origins = "http://localhost:8092")
@RestController
@RequestMapping("/api_users")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers()
    {
        try
        {
            List<User> users = new ArrayList<User>();
            users = userService.getAllUsers();

            if (users.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(users,HttpStatus.OK);
        }
        catch (Exception exception)
        {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/block/{id}")
    public ResponseEntity<String> userBlock(@PathVariable Long id) {
        User user = userRepository.findById(id).orElse(null);
        user.setActive(false);
        userRepository.save(user);
        return ResponseEntity.ok("Пользователь успешно заблокирован !");
    }

    @PutMapping("/inBlock/{id}")
    public ResponseEntity<String> userInBlock(@PathVariable Long id) {
        User user = userRepository.findById(id).orElse(null);
        user.setActive(true);
        userRepository.save(user);
        return ResponseEntity.ok("Пользователь успешно разблокирован !");
    }

    @PutMapping("/changeRole/{id}/{role}")
    public ResponseEntity<String> userInBlock(@PathVariable Long id, @PathVariable String role) {

        User user = userRepository.findById(id).orElse(null);
        user.getRoles().clear();

        if (role.equals("ROLE_USER")) {
            user.getRoles().add(Role.ROLE_USER);
            userRepository.save(user);
            return ResponseEntity.ok("Роль назначена!");
        } else if (role.equals("ROLE_MODER")) {
            user.getRoles().add(Role.ROLE_MODER);
            userRepository.save(user);
            return ResponseEntity.ok("Роль назначена!");
        } else if (role.equals("ROLE_ADMIN")) {
            user.getRoles().add(Role.ROLE_ADMIN);
            userRepository.save(user);
            return ResponseEntity.ok("Роль назначена!");
        } else {
            return ResponseEntity.badRequest().body("Ошибка!");
        }
    }
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user)
    {
        User existsUser = userRepository.findByName(user.getName());

        userService.createUser(user);

        return ResponseEntity.ok("Вы успешно зарегистрировались !");

    }

    @PostMapping("/login")
    public ResponseEntity<User> loginUser(@RequestBody User user) {

        User existingUser = userRepository.findByName(user.getName());

        String checkPassword = existingUser.getPassword();
        if(existingUser.isActive())
        {
            if (existingUser != null)
            {
                if (user.getPassword().equals(checkPassword))
                {
                    if (existingUser.isUser()) {
                        return ResponseEntity.ok(existingUser);
                    }
                    if (existingUser.isAdmin()) {
                        return ResponseEntity.ok(existingUser);
                    }
                    if (existingUser.isModer()) {
                        return ResponseEntity.ok(existingUser);
                    }
                }
                else
                {
                    return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
        }
        else
        {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateUser(@PathVariable("id") Long id, @RequestBody User user) {
        User updatedUser = userService.updateUser(id, user);
        if (updatedUser != null) {
            return ResponseEntity.ok("Информация успешно обновлена");
        } else {
            return ResponseEntity.ok("Пользователь не найден");
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("Пользователь успешно удалён");
    }
}
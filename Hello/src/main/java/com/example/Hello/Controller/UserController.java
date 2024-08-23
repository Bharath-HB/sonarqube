package com.example.Hello.Controller;

import com.example.Hello.Entity.User;
import com.example.Hello.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping
    public ResponseEntity<User> addUser(@RequestBody User user){
        return ResponseEntity.ok().body(userRepository.save(user));
    }

    @GetMapping
    public ResponseEntity<List<User>> getUsers(){
        return ResponseEntity.ok().body(userRepository.findAll());
    }

    @PutMapping
    public ResponseEntity<User> editUser(@RequestParam Long userId,User user){
        Optional<User> userOptional = userRepository.findById(userId);
        if(userOptional.isPresent()){
            User user1 = userOptional.get();
            user1.setUsername(user.getUsername());
            return ResponseEntity.ok().body(userRepository.save(user1));
        }
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Boolean> deleteUser(@RequestParam Long userId){
        Optional<User> userOptional = userRepository.findById(userId);
        if(userOptional.isPresent()){
            userRepository.deleteById(userId);
            return ResponseEntity.ok().body(Boolean.TRUE);
        }
        return ResponseEntity.noContent().build();
    }
}

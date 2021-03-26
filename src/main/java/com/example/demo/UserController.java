package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/redis")
public class UserController {

    @Autowired
    private UserRepository _userRepository;
    public UserController(UserRepository userRepository){
        _userRepository = userRepository;
    }

    @GetMapping("/all")
    public Map<String, User> getAllUsers(){
        return _userRepository.findAll();
    }

    @Cacheable(value = "get-single", key = "#id")
    @GetMapping("/all/{id}")
    public User getUserById(@PathVariable("id") final String id){
        return _userRepository.findById(id);
    }
    @PostMapping("/add")
    public void addUser(@RequestBody User user){
        _userRepository.save(new User(user.getId(),user.getName(),80000L));
         _userRepository.findById(user.getId());
    }
    @PostMapping("/update")
    public void updateUser(@RequestBody User user){
        _userRepository.update(new User(user.getId(),user.getName(),1000L));
         _userRepository.findById(user.getId());
    }

}

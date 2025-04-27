package net.engineeringdigest.journalApp.service;

import java.util.*;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Component;

import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.UserRepository;

@Component
public class UserService {
    @Autowired
    private UserRepository userRepository;


    public void saveUser(User user){
         userRepository.save(user);
    } 

    public List<User> getAll(){
        return userRepository.findAll();
    }

    public Optional<User> findById(ObjectId id){
        return userRepository.findById(id);
    }

    public void deleteById(ObjectId id){
        userRepository.deleteById(id);
    }

    public User findByUserName(String userName){
        return userRepository.findByUserName(userName);
    }
}

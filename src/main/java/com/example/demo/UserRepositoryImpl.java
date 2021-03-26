package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.Map;

@Repository
public class UserRepositoryImpl implements  UserRepository{

    private RedisTemplate<String, User> redisTemplate;
    private HashOperations hashOperations; //to access Redis cache

    @Autowired
    public UserRepositoryImpl(RedisTemplate<String, User> redisTemplate) {
        this.redisTemplate = redisTemplate;

    }

    @PostConstruct
    private void init(){
        hashOperations = redisTemplate.opsForHash();
    }
    @Override
    public void save(User user) {
        hashOperations.put("USER",user.getId(),user);
    }
    @Override
    public Map<String,User> findAll() {
        return hashOperations.entries("USER");
    }
    @Override
    public User findById(String id) {
        return (User)hashOperations.get("USER",id);
    }
    @Override
    public void update(User user) {
        save(user);
    }
    @Override
    public void delete(String id) {
        hashOperations.delete("USER",id);
    }

}

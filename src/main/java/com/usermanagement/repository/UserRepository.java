package com.usermanagement.repository;

import com.usermanagement.model.User;
import java.util.Map;

public interface UserRepository {

    void save(User user);
    
    User findById(Long id);

    Map<Long, User> findAll();

    User findByEmail(String email);

    void deleteById(Long id);

}

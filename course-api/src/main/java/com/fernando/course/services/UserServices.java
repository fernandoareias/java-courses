package com.fernando.course.services;


import com.fernando.course.entities.User;
import com.fernando.course.repositories.UserRepository;
import com.fernando.course.services.exceptions.DatabaseException;
import com.fernando.course.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserServices {

    @Autowired
    private UserRepository userRepository;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Long id)
    {

        var user = userRepository.findById(id);

        return user.orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public User insert(User user){
        return userRepository.save(user);
    }

    public void delete(Long userId)
    {
        try {
            userRepository.deleteById(userId);
        }catch (EmptyResultDataAccessException e)
        {
            throw new ResourceNotFoundException(userId);
        }catch (RuntimeException e){
            throw new DatabaseException(e.getMessage());
        }
    }

    public User update(Long id, User user)
    {
        try {
            User entity = userRepository.findById(id).get();
            updateData(entity, user);
            return userRepository.save(user);
        }catch (EntityNotFoundException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    private void updateData(User entity, User user) {
        entity.setName(user.getName());
        entity.setEmail(user.getEmail());
        entity.setPhone(user.getPhone());
    }


}

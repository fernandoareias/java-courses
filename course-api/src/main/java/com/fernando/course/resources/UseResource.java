package com.fernando.course.resources;

import com.fernando.course.entities.User;
import com.fernando.course.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping(value = "/users")
public class UseResource {

    @Autowired
    private UserServices userServices;

    @GetMapping
    public ResponseEntity<List<User>> findAll() {
        List<User> users = userServices.findAll();

        return ResponseEntity.ok().body(users);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<User> findById(@PathVariable Long id) {
        var user = userServices.findById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(user);
    }


    @PostMapping
    public ResponseEntity<User> create(@RequestBody User user)
    {
        var response = userServices.insert(user);

        if(response == null)
            return ResponseEntity.badRequest().build();

        var uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(response.getId()).toUri();
        return ResponseEntity.created(uri).body(user);
    }


    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        userServices.delete(id);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping(value = "/{id}")
    public ResponseEntity<User> update(@PathVariable Long id, @RequestBody User user)
    {
        var response = userServices.update(id, user);

        if(response == null)
            return ResponseEntity.badRequest().build();

        return ResponseEntity.ok().body(response);
    }

}

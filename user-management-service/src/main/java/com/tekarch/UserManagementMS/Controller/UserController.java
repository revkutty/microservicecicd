package com.tekarch.UserManagementMS.Controller;

import com.tekarch.UserManagementMS.Models.Users;
import com.tekarch.UserManagementMS.Services.UserServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private final UserServiceImpl  userService;

/*
    @GetMapping("/{id}")
    public UserDTO getUserById(@PathVariable Long id) {
        Optional<Users> user = userService.getUserById(id);
        if (user.isPresent()) {
            User u = user.get();
            return new UserDTO(u.getUserId(), u.getUsername(), u.getEmail(), u.getPhoneNumber());
        } else {
            throw new ResourceNotFoundException("User not found");
        }
    }     */

    //GET /api/user – Retrieve all users.
    @GetMapping
    public List<Users> getAllUsers(){
        return userService.getAllUsers();
    }

  /*  //POST /api/users – Create a new user.
    @PostMapping
    public ResponseEntity<Users> addCustomer(@RequestBody Users users) {
        return new ResponseEntity<>(userService.addUser(users), HttpStatus.CREATED);
    }   */


    @PostMapping
    public ResponseEntity<Users> createUser(@RequestBody Users user) {
        try {
            Users savedUser = userService.createUser(user);
            return ResponseEntity.ok(savedUser);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Users> getUserById(@PathVariable Long id) {
        return userService.getUserById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Users> updateUser(@PathVariable Long id, @RequestBody Users updatedUser) {
        return userService.updateUser(id, updatedUser).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        if (userService.deleteUser(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler
    public ResponseEntity<?> respondWithError(Exception e){
        return new ResponseEntity<>("Exception Occurred:" + e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}



   /* //GET /api/users/{id} – Retrieve users by ID.
    @GetMapping("/{id}")
    public ResponseEntity<Users> getUserById(@PathVariable Long id) {
        return userService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }



    //PUT /api/users/{id} – Update an user.
    @PutMapping("/{id}")
    public ResponseEntity<Users> updateUser(@PathVariable Long userId,@RequestBody Users updatedUser){
        try {
            return new ResponseEntity<>(userService.updateUser(userId, updatedUser),HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    //DELETE /api/users/{id} – Delete an user by ID.
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }



}


    */
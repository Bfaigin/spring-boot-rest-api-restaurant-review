package com.springbootproject.restfulapp.controller;

import com.springbootproject.restfulapp.model.User;
import com.springbootproject.restfulapp.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RequestMapping("/users")
@RestController
public class UserController {
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addUser(@RequestBody User user) {
        validateUser(user);

        userRepository.save(user);
    }

    @GetMapping("/{displayName}")
    public User getUser(@PathVariable String displayName) {
        validateDisplayName(displayName);

        Optional<User> optionalExistingUser = userRepository.findUserByDisplayName(displayName);
        if (!optionalExistingUser.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        User existingUser = optionalExistingUser.get();
        existingUser.setId(null);

        return existingUser;
    }

    @PutMapping("/{displayName}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateUserInfo(@PathVariable String displayName, @RequestBody User updatedUser) {
        validateDisplayName(displayName);

        Optional<User> optionalExistingUser = userRepository.findUserByDisplayName(displayName);
        if (optionalExistingUser.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        User existingUser = optionalExistingUser.get();

        copyUserInfoFrom(updatedUser, existingUser);
        userRepository.save(existingUser);
    }

    private void copyUserInfoFrom(User updatedUser, User existingUser) {
        if (ObjectUtils.isEmpty(updatedUser.getDisplayName())) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        }

        if (!ObjectUtils.isEmpty(updatedUser.getCity())) {
            existingUser.setCity(updatedUser.getCity());
        }

        if (!ObjectUtils.isEmpty(updatedUser.getState())) {
            existingUser.setState(updatedUser.getState());
        }

        if (!ObjectUtils.isEmpty(updatedUser.getZipCode())) {
            existingUser.setZipCode(updatedUser.getZipCode());
        }

        if (!ObjectUtils.isEmpty(updatedUser.getPeanutWatch())) {
            existingUser.setPeanutWatch(updatedUser.getPeanutWatch());
        }

        if (!ObjectUtils.isEmpty(updatedUser.getDairyWatch())) {
            existingUser.setDairyWatch(updatedUser.getDairyWatch());
        }

        if (!ObjectUtils.isEmpty(updatedUser.getEggWatch())) {
            existingUser.setEggWatch(updatedUser.getEggWatch());
        }
    }

    private void validateUser(User user) {
        validateDisplayName(user.getDisplayName());

        Optional<User> existingUser = userRepository.findUserByDisplayName(user.getDisplayName());
        if (existingUser.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }

    private void validateDisplayName(String displayName) {
        if (ObjectUtils.isEmpty(displayName)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
}

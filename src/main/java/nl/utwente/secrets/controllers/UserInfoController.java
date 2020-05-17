package nl.utwente.secrets.controllers;


import nl.utwente.secrets.Logger;
import nl.utwente.secrets.entities.User;
import nl.utwente.secrets.representations.user.UserAddDto;
import nl.utwente.secrets.representations.user.UserDto;
import nl.utwente.secrets.representations.user.UserUpdateDto;
import nl.utwente.secrets.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path="/users")
@Transactional
public class UserInfoController {

    private Logger logger = new Logger();
    private final UserService userService;

    public UserInfoController(@Autowired UserService userService) {
        this.userService = userService;
    }

    @GetMapping(path = "/")
    public List<UserDto> getAllUsers() {
        long startTime = System.currentTimeMillis();
        try {
            return userService.getAllUsers().stream().map(UserDto::new).collect(Collectors.toList());
        } finally {
            logger.cntrlLog("getAllUsers", System.currentTimeMillis() - startTime);
        }
    }

    @PostMapping(path = "/")
    public UserDto addUser(@RequestBody UserAddDto dto) {
        long startTime = System.currentTimeMillis();
        try {
            User user = userService.addUser(dto.getName(), dto.getEmail());
            return new UserDto(user);
        } finally {
            logger.cntrlLog("addUser", System.currentTimeMillis() - startTime);
        }
    }

    @PutMapping(path = "/{id}")
    public UserDto updateUser(@PathVariable long id, @RequestBody UserUpdateDto dto) {
        long startTime = System.currentTimeMillis();
        try {
            return new UserDto(userService.updateUser(id, dto.getName(), dto.getEmail(), dto.getSecrets()));
        } finally {
            logger.cntrlLog("updateUser", System.currentTimeMillis() - startTime);
        }
    }



}

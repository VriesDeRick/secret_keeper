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
        List<UserDto> result = userService.getAllUsers().stream().map(UserDto::new).collect(Collectors.toList());
        logger.cntrlLog("getAllUsers", System.currentTimeMillis() - startTime);
        return result;
    }

    @PostMapping(path = "/")
    public UserDto addUser(@RequestBody UserAddDto dto) {
        long startTime = System.currentTimeMillis();
        User user = userService.addUser(dto.getName(), dto.getEmail());
        UserDto result = new UserDto(user);
        logger.cntrlLog("addUser", System.currentTimeMillis() - startTime);
        return result;
    }

    @PutMapping(path = "/{id}")
    public UserDto updateUser(@PathVariable long id, @RequestBody UserUpdateDto dto) {
        long startTime = System.currentTimeMillis();
        UserDto result = new UserDto(userService.updateUser(id, dto.getName(), dto.getEmail(), dto.getSecrets()));
        logger.cntrlLog("updateUser", System.currentTimeMillis() - startTime);
        return result;
    }



}

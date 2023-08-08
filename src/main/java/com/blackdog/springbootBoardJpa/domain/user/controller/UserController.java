package com.blackdog.springbootBoardJpa.domain.user.controller;

import com.blackdog.springbootBoardJpa.domain.user.controller.converter.UserControllerConverter;
import com.blackdog.springbootBoardJpa.domain.user.controller.dto.UserCreateDto;
import com.blackdog.springbootBoardJpa.domain.user.service.UserService;
import com.blackdog.springbootBoardJpa.domain.user.service.dto.UserResponse;
import com.blackdog.springbootBoardJpa.domain.user.service.dto.UserResponses;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService service;
    private final UserControllerConverter converter;

    public UserController(
            final UserService service,
            final UserControllerConverter converter
    ) {
        this.service = service;
        this.converter = converter;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResponse> saveUser(@Valid @RequestBody UserCreateDto createDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.saveUser(converter.toRequest(createDto)));
    }

    @DeleteMapping(value = "/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable long userId) {
        service.deleteUserById(userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResponses> getAllUsers(Pageable pageable) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.findAllUsers(pageable));
    }

    @GetMapping(value = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResponse> getUser(@PathVariable Long userId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.findUserById(userId));
    }

}

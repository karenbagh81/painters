package com.test.controller;

import com.test.exceptions.NotFoundException;
import com.test.model.User;
import com.test.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PutMapping("/save")
    public ResponseEntity save(@Valid @RequestBody User user) {
        userService.saveUser(user);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable(value = "id") int id) throws NotFoundException {
        User user = userService.getById(id);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable(value = "id") int id) {
        userService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/email")
    public ResponseEntity getByEmail(@RequestParam(value = "email") String email) throws NotFoundException {
        User user = userService.getByEmail(email);
        return ResponseEntity.ok(user);
    }

    @GetMapping
    public ResponseEntity getAll() {
        Sort sort = Sort.by("name");
        Pageable pageable = PageRequest.of(0, 2, sort);
        Page<User> page = userService.getAll(pageable);
        return ResponseEntity.ok(page);
    }

    @PutMapping("/register")
    public ResponseEntity register(@Valid @RequestBody User user) throws NotFoundException {
        userService.register(user);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/verify")
    public ResponseEntity verify(@RequestParam("email") String email,
                                 @RequestParam("verification") String verification) {
        userService.verify(email, verification);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestParam("email") String email,
                                @RequestParam("password") String password) {
        userService.login(email, password);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/authority")
    public ResponseEntity saveAuthority(@Valid @RequestBody User user) {
        userService.saveAuthority(user);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/reset-password")
    public ResponseEntity resetPassword(@RequestParam(value = "email") String email,
                                        @RequestParam(value = "resetPasswordCode") String resetPasswordCode,
                                        @RequestParam(value = "newPassword") String newPassword) {
        userService.resetPassword(email, resetPasswordCode, newPassword);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/end-point")
    public ResponseEntity endPoint(@RequestParam(value = "email") String email) {
        userService.endPoint(email);
        return ResponseEntity.ok().build();
    }
}

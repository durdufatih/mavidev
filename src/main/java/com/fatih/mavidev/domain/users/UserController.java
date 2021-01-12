package com.fatih.mavidev.domain.users;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public Page<UserResponse> list(@PageableDefault(size = 50) Pageable pageable) {
        return userService.list(pageable);
    }

    @GetMapping("/{id}")
    public UserResponse findById(@PathVariable Integer id) {
        return userService.findById(id);
    }

    @PostMapping
    public UserResponse create(@RequestBody CreateUserRequest createUserRequest) {
        return userService.create(createUserRequest.toEntity());

    }

    @PutMapping("/{id}")
    public UserResponse update(@PathVariable Integer id, @RequestBody CreateUserRequest createUserRequest) {
        return userService.update(id, createUserRequest.toEntity());

    }

    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Integer id) {
        try {
            userService.delete(id);
        } catch (Exception ex) {
            return false;
        }
        return true;
    }
}

package com.arun.financebackend.service;

import com.arun.financebackend.dto.UserCreateRequest;
import com.arun.financebackend.dto.UserRoleUpdateRequest;
import com.arun.financebackend.dto.UserStatusUpdateRequest;
import com.arun.financebackend.exception.ResourceNotFoundException;
import com.arun.financebackend.model.AppUser;
import com.arun.financebackend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    // Constructor injection (preferred over @Autowired on field)
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Create a new user
    public AppUser createUser(UserCreateRequest request) {
        AppUser user = new AppUser();
        user.setName(request.getName());
        user.setRole(request.getRole());
        user.setActive(true); // default active
        return userRepository.save(user);
    }

    // Get all users
    public List<AppUser> getAllUsers() {
        return userRepository.findAll();
    }

    // Get a single user by ID
    public AppUser getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    }

    // Update a user's role
    public AppUser updateUserRole(Long id, UserRoleUpdateRequest request) {
        AppUser user = getUserById(id);
        user.setRole(request.getRole());
        return userRepository.save(user);
    }

    // Activate or deactivate a user
    public AppUser updateUserStatus(Long id, UserStatusUpdateRequest request) {
        AppUser user = getUserById(id);
        user.setActive(request.getActive());
        return userRepository.save(user);
    }
}

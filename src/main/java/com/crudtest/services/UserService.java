package com.crudtest.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.crudtest.models.entities.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.crudtest.models.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  public List<User> getAllUsers() {
    return userRepository.findAll();
  }

  public Optional<User> getUserById(Long id) {
    return userRepository.findById(id);
  }

  public User createUser(User user) {
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    return userRepository.save(user);
  }

  public User updateUser(Long id, User userDetails) {
    User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));

    if (userDetails.getName() != null) {
      user.setName(userDetails.getName());
    }
    if (userDetails.getEmail() != null) {
      user.setEmail(userDetails.getEmail());
    }
    if (userDetails.getPassword() != null && !userDetails.getPassword().isEmpty()) {
      user.setPassword(passwordEncoder.encode(userDetails.getPassword()));
    }

    return userRepository.save(user);
  }

  public void deleteUser(Long id) {
    userRepository.deleteById(id);
  }
}
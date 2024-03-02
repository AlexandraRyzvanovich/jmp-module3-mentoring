package com.epam.module3.service;

import java.util.List;import java.util.Optional;
import com.epam.module3.User;
import com.epam.module3.UserRepository;
import com.epam.module3.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
  @Autowired private final UserRepository userRepository;

  public UserServiceImpl(UserRepository userRepository) {
    super();
    this.userRepository = userRepository;
  }

  @Override
  public List<User> getAllUsers() {
    return userRepository.findAll();
  }

  @Override
  public User createUser(User user) {
    return userRepository.save(user);
  }

  @Override
  public User updateUser(Long id, User userRequest) {
    User user = userRepository.findById(id).orElseThrow(ResourceNotFoundException::new);

    user.setFirst_name(userRequest.getFirst_name());
    user.setLast_name(userRequest.getLast_name());
    user.setBirthday(userRequest.getBirthday());
    return userRepository.save(user);
  }

  @Override
  public void deleteUser(Long id) {
    User user = userRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
    userRepository.delete(user);
  }

  @Override
  public User getUser(Long id) {
    return userRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
  }
}

package com.epam.module3;

import java.util.List;

public interface UserService {
  List<User> getAllUsers();
  User createUser(User user);
  User updateUser(Long id, User user);
  void deleteUser(Long id);
  User getUser(Long id);


}

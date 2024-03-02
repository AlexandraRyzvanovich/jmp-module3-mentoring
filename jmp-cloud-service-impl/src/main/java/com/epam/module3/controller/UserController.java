package com.epam.module3.controller;

import java.util.List;
import java.util.stream.Collectors;
import com.epam.module3.User;
import com.epam.module3.UserRequestDto;
import com.epam.module3.UserResponseDto;
import com.epam.module3.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import static org.springframework.hateoas.server.core.DummyInvocationUtils.methodOn;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping("/api/users")
public class UserController {

  @Autowired private ModelMapper modelMapper;

  private UserService userService;

  public UserController(UserService userService) {
    super();
    this.userService = userService;
  }

  @PostMapping
  public ResponseEntity<UserResponseDto> createUser(@RequestBody UserRequestDto userRequestDto) {
    User user = modelMapper.map(userRequestDto, User.class);
    User userCreated = userService.createUser(user);
    UserResponseDto userResponseDto = modelMapper.map(userCreated, UserResponseDto.class);
    userResponseDto = addHateoas(userResponseDto);
    return ResponseEntity.ok().body(userResponseDto);
  }

  @PutMapping("/{id}")
  public ResponseEntity<UserResponseDto> updateUser(
      @PathVariable long id, @RequestBody UserRequestDto userRequestDto) {
    User user = modelMapper.map(userRequestDto, User.class);
    User userCreated = userService.updateUser(id, user);
    UserResponseDto userResponseDto = modelMapper.map(userCreated, UserResponseDto.class);
    userResponseDto = addHateoas(userResponseDto);
    return ResponseEntity.ok().body(userResponseDto);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity deleteUser(@PathVariable(name = "id") Long id) {
    userService.deleteUser(id);

    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @GetMapping("/{id}")
  public ResponseEntity<UserResponseDto> getUser(@PathVariable(name = "id") Long id) {
    User user = userService.getUser(id);
    UserResponseDto userResponseDto = modelMapper.map(user, UserResponseDto.class);
    userResponseDto = addHateoas(userResponseDto);
    return ResponseEntity.ok().body(userResponseDto);
  }

  @GetMapping
  public List<UserResponseDto> getAllUsers() {
    return userService.getAllUsers().stream()
        .map(user -> modelMapper.map(user, UserResponseDto.class))
        .map(this::addHateoas)
        .collect(Collectors.toList());
  }

  private UserResponseDto addHateoas(UserResponseDto userResponseDto) {
    return userResponseDto
        .add(linkTo(methodOn(UserController.class).getUser(userResponseDto.getId())).withSelfRel())
        .add(
            linkTo(methodOn(UserController.class).createUser(new UserRequestDto()))
                .withRel("create"))
        .add(
            linkTo(
                    methodOn(UserController.class)
                        .updateUser(userResponseDto.getId(), new UserRequestDto()))
                .withRel("update"))
        .add(
            linkTo(methodOn(UserController.class).deleteUser(userResponseDto.getId()))
                .withRel("delete"))
        .add(linkTo(methodOn(UserController.class).getAllUsers()).withRel("all"));
  }
}

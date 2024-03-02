package com.epam.module3.controller;

import java.util.List;
import java.util.stream.Collectors;
import com.epam.module3.Subscription;
import com.epam.module3.SubscriptionRequestDto;
import com.epam.module3.SubscriptionResponseDto;
import com.epam.module3.SubscriptionService;
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
@RequestMapping("/api/subscriptions")
public class SubscriptionController {
  private ModelMapper modelMapper;
  private SubscriptionService subscriptionService;

  @Autowired
  public SubscriptionController(
      SubscriptionService subscriptionService, UserService userService, ModelMapper modelMapper) {
    this.subscriptionService = subscriptionService;
    this.modelMapper = modelMapper;
  }

  @PostMapping
  public ResponseEntity<SubscriptionResponseDto> createSubscription(
      @RequestBody SubscriptionRequestDto subscriptionRequestDto) {
    Subscription subscription = modelMapper.map(subscriptionRequestDto, Subscription.class);
    Subscription subscriptionRes = subscriptionService.createSubscription(subscription);
    SubscriptionResponseDto subscriptionResponseDto =
        modelMapper.map(subscriptionRes, SubscriptionResponseDto.class);
    subscriptionResponseDto = addHateoas(subscriptionResponseDto);
    return ResponseEntity.ok().body(subscriptionResponseDto);
  }

  @PutMapping("/{id}")
  public ResponseEntity<SubscriptionResponseDto> updateSubscription(
      @PathVariable long id, @RequestBody SubscriptionRequestDto subscriptionRequestDto) {
    Subscription subscription = modelMapper.map(subscriptionRequestDto, Subscription.class);
    Subscription subscriptionRes = subscriptionService.updateSubscription(id, subscription);
    SubscriptionResponseDto subscriptionResponseDto =
        modelMapper.map(subscriptionRes, SubscriptionResponseDto.class);
    subscriptionResponseDto = addHateoas(subscriptionResponseDto);

    return ResponseEntity.ok().body(subscriptionResponseDto);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity deleteSubscription(@PathVariable Long id) {
    subscriptionService.deleteSubscription(id);

    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @GetMapping("/{id}")
  public ResponseEntity<SubscriptionResponseDto> getSubscription(@PathVariable Long id) {
    Subscription subscription = subscriptionService.getSubscription(id);
    SubscriptionResponseDto subscriptionResponseDto =
        modelMapper.map(subscription, SubscriptionResponseDto.class);
    subscriptionResponseDto = addHateoas(subscriptionResponseDto);

    return ResponseEntity.ok().body(subscriptionResponseDto);
  }

  @GetMapping
  public List<SubscriptionResponseDto> getAllSubscription() {
    return subscriptionService.getAllSubscriptions().stream()
        .map(subscription -> modelMapper.map(subscription, SubscriptionResponseDto.class))
            .map(this::addHateoas)
        .collect(Collectors.toList());
  }

  private SubscriptionResponseDto addHateoas(SubscriptionResponseDto subscriptionResponseDto) {
    return subscriptionResponseDto
        .add(
            linkTo(
                    methodOn(SubscriptionController.class)
                        .getSubscription(subscriptionResponseDto.getId()))
                .withSelfRel())
        .add(
            linkTo(
                    methodOn(SubscriptionController.class)
                        .createSubscription(new SubscriptionRequestDto()))
                .withRel("create"))
        .add(
            linkTo(
                    methodOn(SubscriptionController.class)
                        .updateSubscription(
                            subscriptionResponseDto.getId(), new SubscriptionRequestDto()))
                .withRel("update"))
        .add(
            linkTo(
                    methodOn(SubscriptionController.class)
                        .deleteSubscription(subscriptionResponseDto.getId()))
                .withRel("delete"))
        .add(linkTo(methodOn(SubscriptionController.class).getAllSubscription()).withRel("all"));
  }
}

package com.epam.module3;

import java.util.List;

public interface SubscriptionService {
  List<Subscription> getAllSubscriptions();

  Subscription createSubscription(Subscription user);

  Subscription updateSubscription(Long id, Subscription user);

  void deleteSubscription(Long id);

  Subscription getSubscription(Long id);
}

package sk.kasv.infinity.data;

import sk.kasv.infinity.exception.UserAlreadyHasSubscriptionException;
import sk.kasv.infinity.model.Subscription;

import java.util.List;

public interface SubscriptionDao {

  Subscription getById(long id);

  Subscription getUserLatestSubscription(long userId);
  List<Subscription> getUserSubscriptions(long userId);
  Subscription addSubscription(Subscription subscription, Long userId) throws UserAlreadyHasSubscriptionException;
}

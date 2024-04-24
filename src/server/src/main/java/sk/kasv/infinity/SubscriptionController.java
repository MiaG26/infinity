package sk.kasv.infinity;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;
import sk.kasv.infinity.data.DaoFactory;
import sk.kasv.infinity.data.SubscriptionDao;
import sk.kasv.infinity.data.UserDao;
import sk.kasv.infinity.exception.UserAlreadyExistsException;
import sk.kasv.infinity.exception.UserAlreadyHasSubscriptionException;
import sk.kasv.infinity.exception.UserEmailAlreadyInUseException;
import sk.kasv.infinity.model.CreditCard;
import sk.kasv.infinity.model.Subscription;
import sk.kasv.infinity.model.User;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/subscription")

public class SubscriptionController {

  SubscriptionDao subscriptionDao = DaoFactory.INSTANCE.getSubscriptionDao();

  @RequestMapping(value = "/add", method = RequestMethod.POST)
  public Subscription addSubscription(@RequestBody Subscription subscription) {
    try {
      return subscriptionDao.addSubscription(subscription, subscription.getUserId());
    } catch (UserAlreadyHasSubscriptionException e) {
      throw new RuntimeException(e);
    }
  }

  @GetMapping("/history/{userId}")
  public List<Subscription> getUserSubscriptions(@PathVariable("userId") Long userId) {
    return subscriptionDao.getUserSubscriptions(userId);
  }

  @GetMapping("/{userId}")
  public Subscription getUserLatestSubscription(@PathVariable("userId") Long userId) {
    return subscriptionDao.getUserLatestSubscription(userId);
  }
}

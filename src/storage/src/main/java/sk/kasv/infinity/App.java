package sk.kasv.infinity;

import sk.kasv.infinity.data.DaoFactory;
import sk.kasv.infinity.exception.UserAlreadyExistsException;
import sk.kasv.infinity.model.Address;
import sk.kasv.infinity.model.CreditCard;
import sk.kasv.infinity.model.Subscription;
import sk.kasv.infinity.model.User;

import java.util.Date;

public class App {

  public static void main(String[] args) throws Exception {
    //System.out.println(DaoFactory.INSTANCE.getUserDao().getById(1));
    //System.out.println(DaoFactory.INSTANCE.getCreditCardDao().getByUserId(1));
    //System.out.println(DaoFactory.INSTANCE.getUserDao().addUser(new User(null, "Mirka", "Partlova", 35, "mirka.partlova@gmail.com")));
    //System.out.println(DaoFactory.INSTANCE.getUserDao().addUser(new User(null, "Peter", "Nagy", 35, "peter.nagy@gmail.com")));
    //System.out.println(DaoFactory.INSTANCE.getUserDao().addUser(new User(null, "Marek", "Koleno", 35, "marek.koleno@gmail.com")));
    //System.out.println(DaoFactory.INSTANCE.getAddressDao().addAddress(new Address(null, null, "Hemerkova", 33, "04001", "Slovakia"), 1L));
    //System.out.println(DaoFactory.INSTANCE.getAddressDao().addAddress(new Address(null, null, "Popradska", 21, "04001", "Slovakia"), 2L));
    //System.out.println(DaoFactory.INSTANCE.getCreditCardDao().addCreditCard(new CreditCard(null, null, "4406280358612374", 486), 1L));
    //System.out.println(DaoFactory.INSTANCE.getSubscriptionDao().addSubscription(new Subscription(null, null, new Date(), new Date()), 1L));
    //System.out.println(DaoFactory.INSTANCE.getSubscriptionDao().getSubscriptionByUserId(1));
  }
}

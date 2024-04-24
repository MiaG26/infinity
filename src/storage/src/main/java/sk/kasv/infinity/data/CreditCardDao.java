package sk.kasv.infinity.data;


import sk.kasv.infinity.exception.CreditCardAlreadyExistsException;
import sk.kasv.infinity.model.CreditCard;

import java.util.List;

public interface CreditCardDao {

  CreditCard getByUserId(long id);

  List<CreditCard> getUserCreditCards(long userId);

  CreditCard addCreditCard(CreditCard creditCard, Long userId) throws CreditCardAlreadyExistsException;
}

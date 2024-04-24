package sk.kasv.infinity;

import org.springframework.web.bind.annotation.*;
import sk.kasv.infinity.data.CreditCardDao;
import sk.kasv.infinity.data.DaoFactory;
import sk.kasv.infinity.exception.CreditCardAlreadyExistsException;
import sk.kasv.infinity.model.CreditCard;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/creditCard")

public class CreditCardController {

  CreditCardDao creditCardDao = DaoFactory.INSTANCE.getCreditCardDao();

  @GetMapping("/{userId}")
  public List<CreditCard> getUserCreditCards(@PathVariable("userId") Long userId) {
    return creditCardDao.getUserCreditCards(userId);
  }

  @RequestMapping(value = "/add", method = RequestMethod.POST)
  public CreditCard add(@RequestBody CreditCard creditCard) {
    try {
      creditCard.setId(null);
      return creditCardDao.addCreditCard(creditCard, creditCard.getUserId());
    } catch (CreditCardAlreadyExistsException e) {
      throw new RuntimeException(e);
    }
  }
}

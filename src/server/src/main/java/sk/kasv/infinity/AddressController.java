package sk.kasv.infinity;

import org.springframework.web.bind.annotation.*;
import sk.kasv.infinity.data.AddressDao;
import sk.kasv.infinity.data.DaoFactory;
import sk.kasv.infinity.exception.AddressAlreadyExistsException;
import sk.kasv.infinity.model.Address;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/address")

public class AddressController {

  AddressDao addressDao = DaoFactory.INSTANCE.getAddressDao();

  @GetMapping("/{userId}")
  public List<Address> getUserAddresses(@PathVariable("userId") Long userId) {
    return addressDao.getUserAddresses(userId);
  }

  @RequestMapping(value = "/add", method = RequestMethod.POST)
  public Address add(@RequestBody Address address) {
    try {
      address.setId(null);
      return addressDao.addAddress(address, address.getUserId());
    } catch (AddressAlreadyExistsException e) {
      throw new RuntimeException(e);
    }
  }
}

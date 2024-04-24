package sk.kasv.infinity.data;

import sk.kasv.infinity.exception.AddressAlreadyExistsException;
import sk.kasv.infinity.model.Address;

import java.util.List;

public interface AddressDao {

  Address getById(long id);
  List<Address> getUserAddresses(long userId);
  Address addAddress(Address address, Long userId) throws AddressAlreadyExistsException;

}

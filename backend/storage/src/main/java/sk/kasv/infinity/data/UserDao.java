package sk.kasv.infinity.data;

import sk.kasv.infinity.exception.UserAlreadyExistsException;
import sk.kasv.infinity.exception.UserEmailAlreadyInUseException;
import sk.kasv.infinity.exception.UserNotExistsException;
import sk.kasv.infinity.model.User;

import java.util.List;

public interface UserDao {

  User getById(long id);

  List<User> getAll();

  User addUser(User user) throws UserAlreadyExistsException, UserEmailAlreadyInUseException;

  User updateUser(User user) throws UserNotExistsException;


}

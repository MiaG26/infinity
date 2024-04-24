package sk.kasv.infinity;


import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;
import sk.kasv.infinity.data.CourseDao;
import sk.kasv.infinity.data.DaoFactory;
import sk.kasv.infinity.data.UserDao;
import sk.kasv.infinity.exception.UserAlreadyExistsException;
import sk.kasv.infinity.exception.UserEmailAlreadyInUseException;
import sk.kasv.infinity.model.Course;
import sk.kasv.infinity.model.User;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


@CrossOrigin("*")
@RestController
@RequestMapping("")
public class UserController {

  UserDao userDao = DaoFactory.INSTANCE.getUserDao();
  CourseDao courseDao = DaoFactory.INSTANCE.getCourseDao();
  private Map<String, Token> tokens = new HashMap<>();

  @RequestMapping(value = "/login", method = {RequestMethod.POST})
  public String getToken(@RequestBody Auth auth) {
    String token = authorizeAndGetToken(auth.getLogin(), auth.getPassword());
    if (token == null) {
      throw new UnauthorizedActionException("wrong login or password");
    } else {
      //return "{\"token\": \"" + token + "\"}";
      return token;
    }
  }

  @RequestMapping(value = "/register", method = RequestMethod.POST)
  public User register(@RequestBody User user) {
    try {
      user.setId(null);
      user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
      return userDao.addUser(user);
    } catch (UserEmailAlreadyInUseException e) {
      throw new RuntimeException(e);
    } catch (UserAlreadyExistsException e) {
      throw new RuntimeException(e);
    }
  }

  @RequestMapping({"/logout/{token}"})
  public void getToken(@PathVariable String token) {
    deleteToken(token);
  }

  @RequestMapping({"/check-token/{token}"})
  public void checkToken(@PathVariable long id, @PathVariable String token) {
    if (authorizeByToken(token, id) == null) {
      throw new UnauthorizedActionException("unknown token");
    }
  }

  private String authorizeAndGetToken(String login, String password) {
    for (User user : userDao.getAll()) {
      if (user.getEmail().equals(login)) {
        return checkPasswordAndGetToken(user.getId(), password);
      }
    }
    return null;
  }

  private User authorizeByToken(String token, long id) {
    if (checkToken(token, id)) {
      return userDao.getById(id);
    }
    return null;
  }

  private boolean checkToken(String token, long id) {
    Long tokenTime = this.tokens.get(token).getTime();
    if (tokenTime == null) {
      return false;
    } else if (tokenTime + 300000L < System.currentTimeMillis()) {
      this.tokens.remove(token);
      return false;
    } else {
      this.tokens.get(token).setTime(System.currentTimeMillis());
      return true;
    }
  }

  private void deleteToken(String token) {
    this.tokens.remove(token);
  }

  private String checkPasswordAndGetToken(long id, String password) {
    if (this.tokens.size() > 1000) {
      Iterator<Map.Entry<String, Token>> it = this.tokens.entrySet().iterator();

      while (it.hasNext()) {
        Map.Entry<String, Token> entry = it.next();
        if (entry.getValue().getTime() + 300000L < System.currentTimeMillis()) {
          it.remove();
        }
      }

//      if (this.tokens.size() > 1000) {
//        throw new RuntimeException("Too many logins of the same user in 5 minutes");
//      }
    }

    boolean ok = BCrypt.checkpw(password, userDao.getById(id).getPassword());
    if (ok) {
      String token = (new BigInteger(130, new SecureRandom())).toString(32);
      this.tokens.put(token, new Token(token, System.currentTimeMillis(), id));
      return token;
    } else {
      return null;
    }
  }

  @GetMapping("/user/{token}")
  public User getUserById(@PathVariable("token") String token) {
    return authorizeByToken(token, tokens.get(token).getId());
  }

  @GetMapping("/user/courses/{userId}")
  public List<Course> getUserCourses(@PathVariable("userId") Long userId) {
    return courseDao.getUserCourses(userId);
  }


}

package sk.kasv.infinity.data;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import sk.kasv.infinity.exception.UserAlreadyExistsException;
import sk.kasv.infinity.exception.UserEmailAlreadyInUseException;
import sk.kasv.infinity.exception.UserNotExistsException;
import sk.kasv.infinity.model.Course;
import sk.kasv.infinity.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MysqlUserDao implements UserDao {

  private JdbcTemplate jdbcTemplate;

  public MysqlUserDao(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }


  @Override
  public User getById(long id) {
    String sql = "SELECT id, firstname, lastname, password, age, email FROM user WHERE id = ?";
    try {
      return jdbcTemplate.queryForObject(sql, new UserRowMapper(), id);
    } catch (EmptyResultDataAccessException e) {
      e.printStackTrace();
    }
    return null;
  }

  public List<User> getAll() {
    String sql = "SELECT id, firstname, lastname, password, age, email FROM user";
    return jdbcTemplate.query(sql, new UserResultSetExtractor());
  }

  public User addUser(User user) throws UserAlreadyExistsException, UserEmailAlreadyInUseException {
    if (user == null) {
      return null;
    }
    if (user.getId() != null) {
      throw new UserAlreadyExistsException();
    }
    if (checkUserEmailForDuplicity(user.getEmail())) {
      throw new UserEmailAlreadyInUseException();
    }
    SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
    simpleJdbcInsert.withTableName("User");
    simpleJdbcInsert.usingGeneratedKeyColumns("id");
    simpleJdbcInsert.usingColumns("firstName", "lastName", "password", "age", "email");
    Map<String, Object> values = new HashMap<>();
    values.put("firstName", user.getFirstName());
    values.put("lastName", user.getLastName());
    values.put("password", user.getPassword());
    values.put("age", user.getAge());
    values.put("email", user.getEmail());
    long id = simpleJdbcInsert.executeAndReturnKey(values).longValue();
    user.setId(id);
    return user;
  }

  public User updateUser(User user) throws UserNotExistsException {
    if (user == null) {
      return null;
    }
    if (user.getId() == null) {
      throw new UserNotExistsException();
    }
    String sql = "UPDATE user SET firstName = ?, lastName = ?, password = ?, age = ?, email = ? WHERE id = " + user.getId();
    jdbcTemplate.update(sql, user.getFirstName(), user.getLastName(), user.getPassword(), user.getAge(), user.getEmail());
    return user;
  }

  private boolean checkUserEmailForDuplicity(String email) {
    List<User> users = getAll();
    for (User user : users) {
      if (user.getEmail().equals(email)) {
        return true;
      }
    }
    return false;
  }

  private class UserRowMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
      long id = rs.getLong("id");
      String firstName = rs.getString("firstName");
      String lastName = rs.getString("lastName");
      String password = rs.getString("password");
      Integer age = rs.getInt("age");
      String email = rs.getString("email");
      return new User(id, firstName, lastName, password, age, email);
    }
  }

  private class UserResultSetExtractor implements ResultSetExtractor<List<User>> {

    @Override
    public List<User> extractData(ResultSet rs) throws SQLException, DataAccessException {
      List<User> users = new ArrayList<>();
      while (rs.next()) {
        users.add(new User(rs.getLong("id"), rs.getString("firstname"),
          rs.getString("lastname"), rs.getString("password"), rs.getInt("age"), rs.getString("email")));
      }
      return users;
    }
  }


}

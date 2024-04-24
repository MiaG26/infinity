package sk.kasv.infinity.data;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import sk.kasv.infinity.exception.UserAlreadyHasSubscriptionException;
import sk.kasv.infinity.model.Subscription;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class MysqlSubscriptionDao implements SubscriptionDao {

  private JdbcTemplate jdbcTemplate;

  public MysqlSubscriptionDao(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @Override
  public Subscription getById(long id) {
    String sql = "SELECT id, userId, startDate, endDate FROM Subscription WHERE id = ?";
    try {
      return jdbcTemplate.queryForObject(sql, new SubscriptionRowMapper(), id);
    } catch (EmptyResultDataAccessException e) {
      e.printStackTrace();
    }
    return null;
  }

  @Override
  public Subscription addSubscription(Subscription subscription, Long userId) throws UserAlreadyHasSubscriptionException {
    if (subscription == null) {
      return null;
    }
    subscription.setUserId(userId);
    Subscription latestSubscription = getUserLatestSubscription(userId);
    if (latestSubscription != null) {
      if (subscription.getStartDate().before(latestSubscription.getEndDate()) || subscription.getStartDate().equals(latestSubscription.getEndDate())) {
        throw new UserAlreadyHasSubscriptionException();
      }
    }
    SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
    simpleJdbcInsert.withTableName("Subscription");
    simpleJdbcInsert.usingGeneratedKeyColumns("id");
    simpleJdbcInsert.usingColumns("userId", "startDate", "endDate");
    Map<String, Object> values = new HashMap<>();
    values.put("userId", subscription.getUserId());
    values.put("startDate", subscription.getStartDate());
    values.put("endDate", subscription.getEndDate());
    long id = simpleJdbcInsert.executeAndReturnKey(values).longValue();
    subscription.setId(id);
    return subscription;
  }

  public List<Subscription> getUserSubscriptions(long userId) {
    String sql = "SELECT id, userId, startDate, endDate FROM subscription WHERE userId = " + userId;
    return jdbcTemplate.query(sql, new SubscriptionResultSetExtractor());
  }

  public Subscription getUserLatestSubscription(long userId) {
    List<Subscription> subs = getUserSubscriptions(userId);
    if (subs.size() == 0) {
      return null;
    }
    Subscription latest = subs.get(0);
    for (Subscription s : subs) {
      if (s.getEndDate().after(latest.getEndDate())) {
        latest = s;
      }
    }
    return latest;
  }

  private class SubscriptionRowMapper implements RowMapper<Subscription> {
    @Override
    public Subscription mapRow(ResultSet rs, int rowNum) throws SQLException {
      long id = rs.getLong("id");
      long userId = rs.getLong("userId");
      Date startDate = rs.getDate("startDate");
      Date endDate = rs.getDate("endDate");
      return new Subscription(id, userId, startDate, endDate);
    }
  }

  private class SubscriptionResultSetExtractor implements ResultSetExtractor<List<Subscription>> {

    @Override
    public List<Subscription> extractData(ResultSet rs) throws SQLException, DataAccessException {
      List<Subscription> subscriptions = new ArrayList<>();
      while (rs.next()) {
        subscriptions.add(new Subscription(rs.getLong("id"), rs.getLong("userId"), rs.getDate("startDate"),
          rs.getDate("endDate")));
      }
      return subscriptions;
    }
  }
}

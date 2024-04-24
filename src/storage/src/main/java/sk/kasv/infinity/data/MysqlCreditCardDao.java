package sk.kasv.infinity.data;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import sk.kasv.infinity.exception.CreditCardAlreadyExistsException;
import sk.kasv.infinity.model.CreditCard;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MysqlCreditCardDao implements CreditCardDao {

  private JdbcTemplate jdbcTemplate;

  public MysqlCreditCardDao(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @Override
  public CreditCard getByUserId(long userId) {
    String sql = "SELECT id, userId, number, cvv FROM CreditCard WHERE userId = ?";
    try {
      return jdbcTemplate.queryForObject(sql, new CreditCardRowMapper(), userId);
    } catch (EmptyResultDataAccessException e) {
      e.printStackTrace();
    }
    return null;
  }

  @Override
  public List<CreditCard> getUserCreditCards(long userId) {
    String sql = "SELECT id, userId, number, cvv FROM creditCard WHERE userId = " + userId;
    return jdbcTemplate.query(sql, new CreditCardResultSetExtractor());
  }

  public CreditCard addCreditCard(CreditCard creditCard, Long userId) throws CreditCardAlreadyExistsException {
    if (creditCard == null) {
      return null;
    }
    creditCard.setUserId(userId);
    if (checkUserCreditCardsForDuplicity(creditCard, userId)) {
      throw new CreditCardAlreadyExistsException();
    }
    SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
    simpleJdbcInsert.withTableName("CreditCard");
    simpleJdbcInsert.usingGeneratedKeyColumns("id");
    simpleJdbcInsert.usingColumns("userId", "number", "cvv");
    Map<String, Object> values = new HashMap<>();
    values.put("userId", creditCard.getUserId());
    values.put("number", creditCard.getNumber());
    values.put("cvv", creditCard.getCvv());
    long id = simpleJdbcInsert.executeAndReturnKey(values).longValue();
    creditCard.setId(id);
    return creditCard;
  }

  private boolean checkUserCreditCardsForDuplicity(CreditCard newCreditCard, Long userId) {
    List<CreditCard> creditCards = getUserCreditCards(userId);
    for (CreditCard creditCard : creditCards) {
      if (creditCard.equals(newCreditCard)) {
        return true;
      }
    }
    return false;
  }

  private class CreditCardRowMapper implements RowMapper<CreditCard> {
    @Override
    public CreditCard mapRow(ResultSet rs, int rowNum) throws SQLException {
      long id = rs.getLong("id");
      long userId = rs.getLong("userId");
      String number = rs.getString("number");
      Integer cvv = rs.getInt("cvv");
      return new CreditCard(id, userId, number, cvv);
    }
  }

  private class CreditCardResultSetExtractor implements ResultSetExtractor<List<CreditCard>> {

    @Override
    public List<CreditCard> extractData(ResultSet rs) throws SQLException, DataAccessException {
      List<CreditCard> creditCards = new ArrayList<>();
      while (rs.next()) {
        creditCards.add(new CreditCard(rs.getLong("id"), rs.getLong("userId"), rs.getString("number"),
          rs.getInt("cvv")));
      }
      return creditCards;
    }
  }
}

package sk.kasv.infinity.data;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import sk.kasv.infinity.exception.AddressAlreadyExistsException;
import sk.kasv.infinity.model.Address;
import sk.kasv.infinity.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MysqlAddressDao implements AddressDao{

  private JdbcTemplate jdbcTemplate;

  public MysqlAddressDao(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @Override
  public Address getById(long id) {
    String sql = "SELECT id, userId, street, number, postalCode, country FROM address WHERE id = ?";
    try {
      return jdbcTemplate.queryForObject(sql, new AddressRowMapper(), id);
    } catch (EmptyResultDataAccessException e) {
      e.printStackTrace();
    }
    return null;
  }

  @Override
  public List<Address> getUserAddresses(long userId) {
    String sql = "SELECT id, userId, street, number, postalCode, country FROM address WHERE userId = "+userId;
    return jdbcTemplate.query(sql, new MysqlAddressDao.AddressResultSetExtractor());
  }

  @Override
  public Address addAddress(Address address, Long userId) throws AddressAlreadyExistsException {
    if (address == null) {
      return null;
    }
    address.setUserId(userId);
    if (checkUserAddressesForDuplicity(address, userId)) {
      throw new AddressAlreadyExistsException();
    }
    SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
    simpleJdbcInsert.withTableName("Address");
    simpleJdbcInsert.usingGeneratedKeyColumns("id");
    simpleJdbcInsert.usingColumns("userId", "street", "number", "postalCode", "country");
    Map<String, Object> values = new HashMap<>();
    values.put("userId", address.getUserId());
    values.put("street", address.getStreet());
    values.put("number", address.getNumber());
    values.put("postalCode", address.getPostalCode());
    values.put("country", address.getCountry());
    long id = simpleJdbcInsert.executeAndReturnKey(values).longValue();
    address.setId(id);
    return address;
  }

  private boolean checkUserAddressesForDuplicity(Address newAddress, Long userId) {
    List<Address> addresses = getUserAddresses(userId);
    for (Address address : addresses) {
      if(address.equals(newAddress)) {
        return true;
      }
    }
    return false;
  }

  private class AddressRowMapper implements RowMapper<Address> {
    @Override
    public Address mapRow(ResultSet rs, int rowNum) throws SQLException {
      long id = rs.getLong("id");
      long userId = rs.getLong("userId");
      String street = rs.getString("street");
      Integer number = rs.getInt("number");
      String postalCode = rs.getString("postalCode");
      String country = rs.getString("country");
      return new Address(id, userId, street, number, postalCode, country);
    }
  }

  private class AddressResultSetExtractor implements ResultSetExtractor<List<Address>> {

    @Override
    public List<Address> extractData(ResultSet rs) throws SQLException, DataAccessException {
      List<Address> addresses = new ArrayList<>();
      while (rs.next()) {
        addresses.add(new Address(rs.getLong("id"), rs.getLong("userId"), rs.getString("street"),
          rs.getInt("number"), rs.getString("postalCode"), rs.getString("country")));
      }
      return addresses;
    }
  }


}

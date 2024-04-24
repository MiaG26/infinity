package sk.kasv.infinity.data;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import sk.kasv.infinity.model.Address;
import sk.kasv.infinity.model.Subscription;

public enum DaoFactory {

  INSTANCE;
  private JdbcTemplate jdbcTemplate;
  private UserDao userDao;
  private CreditCardDao creditCardDao;

  private AddressDao addressDao;

  private SubscriptionDao subscriptionDao;

  private CourseDao courseDao;

  private VideoDao videoDao;

  private CommentDao commentDao;
  private boolean testing = false;

  private JdbcTemplate getJdbcTemplate() {
    if (jdbcTemplate == null) {
      MysqlDataSource dataSource = new MysqlDataSource();
      dataSource.setUser("infinity");
      dataSource.setPassword("infinity");
      if (testing) {
        dataSource.setUrl("jdbc:mysql://localhost:3306/infinity_test?"
          + "serverTimezone=Europe/Bratislava");
      } else {
        dataSource.setUrl("jdbc:mysql://localhost:3306/infinity?"
          + "serverTimezone=Europe/Bratislava");
      }
      jdbcTemplate = new JdbcTemplate(dataSource);
    }
    return jdbcTemplate;
  }

  public UserDao getUserDao() {
    if (userDao == null) {
      userDao = new MysqlUserDao(getJdbcTemplate());
    }
    return userDao;

  }

  public CreditCardDao getCreditCardDao() {
    if (creditCardDao == null) {
      creditCardDao = new MysqlCreditCardDao(getJdbcTemplate());
    }
    return creditCardDao;

  }

  public AddressDao getAddressDao() {
    if (addressDao == null) {
      addressDao = new MysqlAddressDao(getJdbcTemplate());
    }
    return addressDao;

  }

  public SubscriptionDao getSubscriptionDao() {
    if (subscriptionDao == null) {
      subscriptionDao = new MysqlSubscriptionDao(getJdbcTemplate());
    }
    return subscriptionDao;

  }

  public CourseDao getCourseDao() {
    if (courseDao == null) {
      courseDao = new MysqlCourseDao(getJdbcTemplate());
    }
    return courseDao;

  }

  public VideoDao getVideoDao() {
    if (videoDao == null) {
      videoDao = new MysqlVideoDao(getJdbcTemplate());
    }
    return videoDao;

  }

  public CommentDao getCommentDao() {
    if (commentDao == null) {
      commentDao = new MysqlCommentDao(getJdbcTemplate());
    }
    return commentDao;

  }

}

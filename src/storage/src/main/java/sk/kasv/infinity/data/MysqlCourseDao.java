package sk.kasv.infinity.data;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import sk.kasv.infinity.model.Course;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MysqlCourseDao implements CourseDao {

  private JdbcTemplate jdbcTemplate;

  public MysqlCourseDao(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @Override
  public Course getCourseById(long id) {
    String sql = "SELECT id, name, description, price FROM Course WHERE id = ?";
    try {
      return jdbcTemplate.queryForObject(sql, new CourseRowMapper(), id);
    } catch (EmptyResultDataAccessException e) {
      e.printStackTrace();
    }
    return null;
  }

  public List<Course> getAll() {
    String sql = "SELECT id, name, description, price FROM Course";
    return jdbcTemplate.query(sql, new CourseResultSetExtractor());
  }

  @Override
  public Course addCourse(Course course) {
    if (course == null) {
      return null;
    }
    SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
    simpleJdbcInsert.withTableName("Course");
    simpleJdbcInsert.usingGeneratedKeyColumns("id");
    simpleJdbcInsert.usingColumns("name", "description", "price");
    Map<String, Object> values = new HashMap<>();
    values.put("name", course.getName());
    values.put("description", course.getDescription());
    values.put("price", course.getPrice());
    long id = simpleJdbcInsert.executeAndReturnKey(values).longValue();
    course.setId(id);
    return course;
  }

  public List<Course> getUserCourses(long userId) {
    String sql = "SELECT id, name, description, price FROM Course JOIN UserCourses ON Course.id = UserCourses.courseId WHERE userId = " + userId;
    return jdbcTemplate.query(sql, new CourseResultSetExtractor());
  }

  public void addCourseToUser(long userId, long courseId) {
    SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
    simpleJdbcInsert.withTableName("UserCourses");
    simpleJdbcInsert.usingColumns("userId", "courseId");
    Map<String, Object> values = new HashMap<>();
    values.put("userId", userId);
    values.put("courseId", courseId);
    simpleJdbcInsert.execute(values);
  }

  private class CourseRowMapper implements RowMapper<Course> {
    @Override
    public Course mapRow(ResultSet rs, int rowNum) throws SQLException {
      long id = rs.getLong("id");
      String name = rs.getString("name");
      String description = rs.getString("description");
      Integer price = rs.getInt("price");
      return new Course(id, name, description, price);
    }
  }

  private class CourseResultSetExtractor implements ResultSetExtractor<List<Course>> {

    @Override
    public List<Course> extractData(ResultSet rs) throws SQLException, DataAccessException {
      List<Course> courses = new ArrayList<>();
      while (rs.next()) {
        courses.add(new Course(rs.getLong("id"), rs.getString("name"), rs.getString("description"),
          rs.getInt("price")));
      }
      return courses;
    }
  }
}

package sk.kasv.infinity.data;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import sk.kasv.infinity.model.Video;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MysqlVideoDao implements VideoDao{

  private JdbcTemplate jdbcTemplate;

  public MysqlVideoDao(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }
  @Override
  public Video getVideoById(long id) {
    String sql = "SELECT id, courseId, name, description FROM Video WHERE id = ?";
    try {
      return jdbcTemplate.queryForObject(sql, new VideoRowMapper(), id);
    } catch (EmptyResultDataAccessException e) {
      e.printStackTrace();
    }
    return null;
  }
  @Override
  public List<Video> getAllVideosByCourseId(long courseId) {
    String sql = "SELECT id, courseId, name, description FROM Video WHERE courseId = "+ courseId;
    return jdbcTemplate.query(sql, new VideoResultSetExtractor());
  }

  @Override
  public Video addVideo(Video video, long courseId) {
    if (video == null) {
      return null;
    }
    video.setCourseId(courseId);
    SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
    simpleJdbcInsert.withTableName("Video");
    simpleJdbcInsert.usingGeneratedKeyColumns("id");
    simpleJdbcInsert.usingColumns("courseId", "name", "description");
    Map<String, Object> values = new HashMap<>();
    values.put("courseId", video.getCourseId());
    values.put("name", video.getName());
    values.put("description", video.getDescription());
    long id = simpleJdbcInsert.executeAndReturnKey(values).longValue();
    video.setId(id);
    return video;
  }

  private class VideoRowMapper implements RowMapper<Video> {
    @Override
    public Video mapRow(ResultSet rs, int rowNum) throws SQLException {
      long id = rs.getLong("id");
      long courseId = rs.getLong("courseId");
      String name = rs.getString("name");
      String description = rs.getString("description");
      return new Video(id, courseId, description, description);
    }
  }

  private class VideoResultSetExtractor implements ResultSetExtractor<List<Video>> {

    @Override
    public List<Video> extractData(ResultSet rs) throws SQLException, DataAccessException {
      List<Video> videos = new ArrayList<>();
      while (rs.next()) {
        videos.add(new Video(rs.getLong("id"), rs.getLong("courseId"), rs.getString("name"),
          rs.getString("description")));
      }
      return videos;
    }
  }
}

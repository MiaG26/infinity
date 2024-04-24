package sk.kasv.infinity.data;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import sk.kasv.infinity.model.Comment;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MysqlCommentDao implements CommentDao{

  private JdbcTemplate jdbcTemplate;

  public MysqlCommentDao(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @Override
  public Comment getCommentById(long id) {
    String sql = "SELECT id, courseId, name, description FROM Video WHERE id = ?";
    try {
      return jdbcTemplate.queryForObject(sql, new CommentRowMapper(), id);
    } catch (EmptyResultDataAccessException e) {
      e.printStackTrace();
    }
    return null;
  }
  public List<Comment> getAllCommentsByVideoId(long videoId) {
    String sql = "SELECT id, videoId, userId, text FROM Comment WHERE videoId = "+videoId;
    return jdbcTemplate.query(sql, new CommentResultSetExtractor());
  }

  @Override
  public Comment addComment(Comment comment, long videoId, long userId) {
    if (comment == null) {
      return null;
    }
    comment.setUserId(userId);
    comment.setVideoId(videoId);
    SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
    simpleJdbcInsert.withTableName("Comment");
    simpleJdbcInsert.usingGeneratedKeyColumns("id");
    simpleJdbcInsert.usingColumns("videoId", "userId", "text");
    Map<String, Object> values = new HashMap<>();
    values.put("videoId", comment.getVideoId());
    values.put("userId", comment.getUserId());
    values.put("text", comment.getText());
    long id = simpleJdbcInsert.executeAndReturnKey(values).longValue();
    comment.setId(id);
    return comment;
  }

  private class CommentRowMapper implements RowMapper<Comment> {
    @Override
    public Comment mapRow(ResultSet rs, int rowNum) throws SQLException {
      long id = rs.getLong("id");
      long videoId = rs.getLong("videoId");
      long userId = rs.getLong("userId");
      String text = rs.getString("text");
      return new Comment(id, videoId, userId, text);
    }
  }

  private class CommentResultSetExtractor implements ResultSetExtractor<List<Comment>> {

    @Override
    public List<Comment> extractData(ResultSet rs) throws SQLException, DataAccessException {
      List<Comment> comments = new ArrayList<>();
      while (rs.next()) {
        comments.add(new Comment(rs.getLong("id"), rs.getLong("videoId"), rs.getLong("userId"),
          rs.getString("text")));
      }
      return comments;
    }
  }
}

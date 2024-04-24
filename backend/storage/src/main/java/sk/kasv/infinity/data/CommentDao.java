package sk.kasv.infinity.data;

import sk.kasv.infinity.model.Comment;

import java.util.List;

public interface CommentDao {

  Comment getCommentById(long id);

  List<Comment> getAllCommentsByVideoId(long id);

  Comment addComment(Comment comment, long videoId, long userId);
}

package sk.kasv.infinity;

import org.springframework.web.bind.annotation.*;
import sk.kasv.infinity.data.CommentDao;
import sk.kasv.infinity.data.DaoFactory;
import sk.kasv.infinity.data.VideoDao;
import sk.kasv.infinity.model.Comment;
import sk.kasv.infinity.model.Video;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/comment")

public class CommentController {

  CommentDao commentDao = DaoFactory.INSTANCE.getCommentDao();

  @GetMapping("/{id}")
  public Comment getCommentById(@PathVariable("id") Long commentId) {
    return commentDao.getCommentById(commentId);
  }

  @RequestMapping(value = "/add", method = RequestMethod.POST)
  public Comment add(@RequestBody Comment comment) {
    comment.setId(null);
    return commentDao.addComment(comment, comment.getVideoId(), comment.getUserId());
  }


}

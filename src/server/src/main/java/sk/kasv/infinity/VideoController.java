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
@RequestMapping("/video")

public class VideoController {

  VideoDao videoDao = DaoFactory.INSTANCE.getVideoDao();

  CommentDao commentDao = DaoFactory.INSTANCE.getCommentDao();

  @GetMapping("/{id}")
  public Video getVideoById(@PathVariable("id") Long videoId) {
    return videoDao.getVideoById(videoId);
  }

  @GetMapping("/{videoId}/comments")
  public List<Comment> getAllCommentsByVideoId(@PathVariable("videoId") Long videoId) {
    return commentDao.getAllCommentsByVideoId(videoId);
  }

  @RequestMapping(value = "/add", method = RequestMethod.POST)
  public Video add(@RequestBody Video video) {
    video.setId(null);
    return videoDao.addVideo(video, video.getCourseId());
  }
}

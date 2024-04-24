package sk.kasv.infinity.data;

import sk.kasv.infinity.model.Video;

import java.util.List;

public interface VideoDao {

  Video getVideoById(long id);

  List<Video> getAllVideosByCourseId(long courseId);

  Video addVideo(Video video, long courseId);
}

package sk.kasv.infinity;

import org.springframework.web.bind.annotation.*;
import sk.kasv.infinity.data.CourseDao;
import sk.kasv.infinity.data.DaoFactory;
import sk.kasv.infinity.data.VideoDao;
import sk.kasv.infinity.exception.CreditCardAlreadyExistsException;
import sk.kasv.infinity.model.Course;
import sk.kasv.infinity.model.CreditCard;
import sk.kasv.infinity.model.Video;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/course")

public class CourseController {

  CourseDao courseDao = DaoFactory.INSTANCE.getCourseDao();
  VideoDao videoDao = DaoFactory.INSTANCE.getVideoDao();

  @GetMapping("/{id}")
  public Course getCourseById(@PathVariable("id") Long courseId) {
    return courseDao.getCourseById(courseId);
  }

  @GetMapping("")
  public List<Course> getAll() {
    return courseDao.getAll();
  }

  @GetMapping("/{courseId}/videos")
  public List<Video>  getAllVideosByCourseId(@PathVariable("courseId") Long courseId) {
    return videoDao.getAllVideosByCourseId(courseId);
  }

  @RequestMapping(value = "/add", method = RequestMethod.POST)
  public Course add(@RequestBody Course course) {
    course.setId(null);
    return courseDao.addCourse(course);
  }

  @RequestMapping(value = "/add/{userId}/{courseId}", method = RequestMethod.POST)
  public void addCourseToUser(@PathVariable("userId") Long userId, @PathVariable("courseId") Long courseId) {
    courseDao.addCourseToUser(userId, courseId);
  }
}

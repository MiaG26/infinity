package sk.kasv.infinity.data;

import sk.kasv.infinity.model.Course;

import java.util.List;

public interface CourseDao {

  Course getCourseById(long id);
  List<Course> getAll();
  Course addCourse(Course course);
  void addCourseToUser(long userId, long courseId);
  List<Course> getUserCourses(long userId);
}

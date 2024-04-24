package sk.kasv.infinity.model;

public class Video {

  Long id;
  Long courseId;
  String name;
  String description;

  public Video(Long id, Long courseId, String name, String description) {
    this.id = id;
    this.courseId = courseId;
    this.name = name;
    this.description = description;
  }

  public Video() {

  }

  public Long getId() {
    return id;
  }

  public Long getCourseId() {
    return courseId;
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setCourseId(Long courseId) {
    this.courseId = courseId;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  @Override
  public String toString() {
    return "Video{" +
      "id=" + id +
      ", courseId=" + courseId +
      ", name='" + name + '\'' +
      ", description='" + description + '\'' +
      '}';
  }
}

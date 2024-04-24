package sk.kasv.infinity.model;

public class Course {

  private Long id;
  private String name;
  private String description;
  private Integer price;

  public Course(Long id, String name, String description, Integer price) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.price = price;
  }

  public Course() {

  }

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }
  public Integer getPrice() {
    return price;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setPrice(Integer price) {
    this.price = price;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("Course{");
    sb.append("id=").append(id);
    sb.append(", name='").append(name).append('\'');
    sb.append(", description='").append(description).append('\'');
    sb.append(", price='").append(price).append('\'');
    sb.append('}');
    return sb.toString();
  }
}

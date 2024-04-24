package sk.kasv.infinity.model;

public class User {

  private Long id;
  private String firstName;
  private String lastName;

  private String password;
  private Integer age;
  private String email;

  public User(Long id, String firstName, String lastName, String password, Integer age, String email) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.password = password;
    this.age = age;
    this.email = email;
  }

  public User() {

  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFistName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Integer getAge() {
    return age;
  }

  public void setAge(Integer age) {
    this.age = age;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("User{");
    sb.append("id='").append(id).append('\'');
    sb.append("firstName='").append(firstName).append('\'');
    sb.append(", lastName='").append(lastName).append('\'');
    sb.append(", password='").append(password).append('\'');
    sb.append(", age=").append(age);
    sb.append(", email='").append(email).append('\'');
    sb.append('}');
    return sb.toString();
  }
}

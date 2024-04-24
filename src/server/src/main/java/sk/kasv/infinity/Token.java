package sk.kasv.infinity;

public class Token {

  private String token;
  private Long time;

  private Long id;

  public Token(String token, Long time, Long id) {
      this.token = token;
      this.time = time;
      this.id = id;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public Long getTime() {
    return time;
  }

  public void setTime(Long time) {
    this.time = time;
  }

  public Long getId() {
    return id;
  }
}

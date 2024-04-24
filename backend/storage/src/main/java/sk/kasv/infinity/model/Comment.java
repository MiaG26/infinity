package sk.kasv.infinity.model;

public class Comment {

  private Long id;
  private Long videoId;
  private Long userId;
  private String text;

  public Comment(Long id, Long videoId, Long userId, String text) {
    this.id = id;
    this.videoId = videoId;
    this.userId = userId;
    this.text = text;
  }

  public Comment() {

  }

  public Long getId() {
    return id;
  }

  public Long getVideoId() {
    return videoId;
  }

  public Long getUserId() {
    return userId;
  }

  public String getText() {
    return text;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setVideoId(Long videoId) {
    this.videoId = videoId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public void setText(String text) {
    this.text = text;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("Comment{");
    sb.append("id=").append(id);
    sb.append(", videoId=").append(videoId);
    sb.append(", userId=").append(userId);
    sb.append(", text='").append(text).append('\'');
    sb.append('}');
    return sb.toString();
  }
}

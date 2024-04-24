package sk.kasv.infinity.model;

import java.util.Date;

public class Subscription {

  private Long id;
  private Long userId;
  private Date startDate;
  private Date endDate;

  public Subscription(Long id, Long userId, Date startDate, Date endDate) {
    this.id = id;
    this.userId = userId;
    this.startDate = startDate;
    this.endDate = endDate;
  }

  public Subscription() {

  }

  public Long getId() {
    return id;
  }

  public Long getUserId() {
    return userId;
  }

  public Date getStartDate() {
    return startDate;
  }

  public Date getEndDate() {
    return endDate;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public void setStartDate(Date startDate) {
    this.startDate = startDate;
  }

  public void setEndDate(Date endDate) {
    this.endDate = endDate;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("Subscription{");
    sb.append("id=").append(id);
    sb.append(", userId=").append(userId);
    sb.append(", startDate=").append(startDate);
    sb.append(", endDate=").append(endDate);
    sb.append('}');
    return sb.toString();
  }
}

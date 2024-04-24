package sk.kasv.infinity.model;

import java.util.Objects;

public class CreditCard {

  private Long id;
  private Long userId;
  private String number;
  private Integer cvv;

  public CreditCard(Long id, Long userId, String number, Integer cvv) {
    this.id = id;
    this.userId = userId;
    this.number = number;
    this.cvv = cvv;
  }

  public CreditCard() {
  }

  public Long getId() {
    return id;
  }

  public Long getUserId() {
    return userId;
  }

  public String getNumber() {
    return number;
  }

  public Integer getCvv() {
    return cvv;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public void setNumber(String number) {
    this.number = number;
  }

  public void setCvv(Integer cvv) {
    this.cvv = cvv;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    CreditCard creditCard = (CreditCard) o;
    return Objects.equals(getUserId(), creditCard.getUserId()) && Objects.equals(getNumber(), creditCard.getNumber()) && Objects.equals(getCvv(), creditCard.getCvv());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId(), getUserId(), getNumber(), getCvv());
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("CreditCard{");
    sb.append("id=").append(id);
    sb.append(", userId=").append(userId);
    sb.append(", number='").append(number).append('\'');
    sb.append(", cvv=").append(cvv);
    sb.append('}');
    return sb.toString();
  }
}

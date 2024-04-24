package sk.kasv.infinity.model;

import java.util.Objects;

public class Address {

  private Long id;
  private Long userId;
  private String street;
  private Integer number;
  private String postalCode;
  private String country;

  public Address(Long id, Long userId, String street, Integer number, String postalCode, String country) {
    this.id = id;
    this.userId = userId;
    this.street = street;
    this.number = number;
    this.postalCode = postalCode;
    this.country = country;
  }

  public Address() {

  }

  public Long getId() {
    return id;
  }

  public Long getUserId() {
    return userId;
  }

  public String getStreet() {
    return street;
  }

  public Integer getNumber() {
    return number;
  }

  public String getPostalCode() {
    return postalCode;
  }

  public String getCountry() {
    return country;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public void setStreet(String street) {
    this.street = street;
  }

  public void setNumber(Integer number) {
    this.number = number;
  }

  public void setPostalCode(String postalCode) {
    this.postalCode = postalCode;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Address address = (Address) o;
    return Objects.equals(userId, address.getUserId()) && Objects.equals(street, address.getStreet()) &&
      Objects.equals(number, address.getNumber()) && Objects.equals(postalCode, address.getPostalCode()) &&
      Objects.equals(country, address.getCountry());
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, userId, street, number, postalCode, country);
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("Address{");
    sb.append("id=").append(id);
    sb.append(", userId=").append(userId);
    sb.append(", street='").append(street).append('\'');
    sb.append(", number=").append(number);
    sb.append(", postalCode='").append(postalCode).append('\'');
    sb.append(", country='").append(country).append('\'');
    sb.append('}');
    return sb.toString();
  }
}

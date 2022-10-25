package com.online.files.online.files.models;
import org.springframework.data.annotation.Id;


public class ProvaCustomer {

  @Id
  public String id;

  public String firstName;
  public String lastName;

  public ProvaCustomer() {}

  public ProvaCustomer(String firstName, String lastName) {
    this.firstName = firstName;
    this.lastName = lastName;
  }

  @Override
  public String toString() {
    return String.format(
        "Customer[id=%s, firstName='%s', lastName='%s']",
        id, firstName, lastName);
  }

}
package cst338.p1;

public class TeacherRecord {
  private Integer id;
  private String name;
  private String email;
  private String phone;

  public Integer getId() {
    return id;
  }

  public String getEmail() {
    return email;
  }

  public String getName() {
    return name;
  }

  public String getPhone() {
    return phone;
  }

  public TeacherRecord(Integer id, String name, String email, String phone) {
    this.id = id;
    this.name = name;
    this.email = email;
    this.phone = phone;
  }


}

package cst338.p1;

public class TeacherRecord {
  private Integer id;
  private String name;
  private String email;
  private String ssn;

  public Integer getId() {
    return id;
  }

  public String getEmail() {
    return email;
  }

  public String getName() {
    return name;
  }

  public String getSsn() {
    return ssn;
  }

  public TeacherRecord(Integer id, String name, String email, String ssn) {
    this.id = id;
    this.name = name;
    this.email = email;
    this.ssn = ssn;
  }


}

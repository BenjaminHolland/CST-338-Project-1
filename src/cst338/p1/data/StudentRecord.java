package cst338.p1.data;

public class StudentRecord {
  private Integer id;
  private String name;
  public Integer getId() {
    return id;
  }
  public String getName() {
    return name;
  }
  public StudentRecord(Integer id,String name){
    this.id=id;
    this.name=name;
  }
  
}

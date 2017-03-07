package cst338.p1.data;

public class CourseRecord {
  private Integer id;
  private String title;
  private Integer capacity;
  private String location;
  public Integer getId() {
    return id;
  }
  public String getTitle() {
    return title;
  }
  public Integer getCapacity() {
    return capacity;
  }
  public String getLocation() {
    return location;
  }
  public void setLocation(String location){
    this.location=location;
  }
  public CourseRecord(Integer id,String title,Integer capacity,String location){
    this.id=id;
    this.title=title;
    this.capacity=capacity;
    this.location=location;
  }
}

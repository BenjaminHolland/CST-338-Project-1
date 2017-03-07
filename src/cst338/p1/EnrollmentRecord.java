package cst338.p1;

public class EnrollmentRecord {
  private final Integer courseId;
  private Double score;
  public EnrollmentRecord(Integer courseId){
    this.courseId=courseId;
  }
  public Double getScore(){
    return score;
  }
  public void setScore(Double score){
    this.score=score;
  }
}

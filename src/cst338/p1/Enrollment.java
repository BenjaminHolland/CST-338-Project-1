package cst338.p1;

public class Enrollment {
  private final Integer courseId;
  private Double score;
  public Enrollment(Integer courseId){
    this.courseId=courseId;
  }
  public Double getScore(){
    return score;
  }
  public void setScore(Double score){
    this.score=score;
  }
}

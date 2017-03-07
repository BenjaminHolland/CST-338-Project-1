package cst338.p1;

import cst338.p1.data.Database;

public class Course {
  private Database context;
  private Integer studentId;
  private Integer courseId;
  public Course(Database context,Integer studentId,Integer courseId){
    this.context=context;
    this.studentId=studentId;
    this.courseId=courseId;
  }
}

package cst338.p1;

import java.util.List;

import cst338.p1.data.Database;
import cst338.p1.data.EnrollmentRecord;
import cst338.p1.data.StudentRecord;

public class Student {
  private final Database context;
  private final Integer id;
  Student(Database context,Integer id){
    this.context=context;
    
    this.id=id;
  }
  @Override
  public String toString() {
    StringBuilder bldr=new StringBuilder();
    
    try{
      StudentRecord record=context.selectStudent(id);
      List<EnrollmentRecord> enrollments=context.selectStudentCourses(id);
      bldr.append("Student Number: "+record.getId()+"\n");
      bldr.append("Name: "+record.getName()+"\n");
      bldr.append("Classes Enrolled:\n");
      Double sum=0.0;
      for(EnrollmentRecord enrollment:enrollments){
        bldr.append("\t"+enrollment.getCourseId()+": "+enrollment.getScore()+"\n");
        sum+=enrollment.getScore();
      }
      Double avg=sum/Double.valueOf(enrollments.size());
      bldr.append("Course Average: "+avg+"\n");
      return bldr.toString();
      
    }catch(StudentMissingException e){
      
      return "Student Number: "+id+"\nNo student information.";
    }
  }
  
}

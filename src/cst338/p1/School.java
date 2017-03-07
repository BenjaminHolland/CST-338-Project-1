package cst338.p1;

import java.io.IOException;

public class School {
  private final Database database;
  private final String name;
  public String getName(){
    return name;
  }
  public School(String name){
    this.name=name;
    this.database=new Database();
  }
  public void readData(String path){
      try {
        database.importData(path);
      } catch (IOException e) {
        System.out.println("Error importing data from \""+path+"\"");
        e.printStackTrace();
      }
  }
  public void schoolInfo(){
    
  }
  public void searchByEmail(){
    
  }
  public void addInstructor(Integer id,String name,String email,String phone){
    
  }
  public void addCourse(Integer id,String name,Integer capacity,String location){
    
  }
  public void addStudent(Integer id,String name){
    
  }
  public void assignInstructor(Integer classId,Integer teacherId){
    
  }
  public void register(Integer classId,Integer studentId){
    
  }
  public void unRegister(Integer classId,Integer studentId){
    
  }
  public StudentRecord getStudent(Integer studentId){
    return null;
  }
  public void graduateStudent(Integer studentId){
    
  }
  public void assignScore(Integer classId,Integer studentId,Double score){
    
  }
  public void courseInfo(Integer classId){
    
  }
  public CourseRecord getCourse(Integer classId){
    return null;
  }
  public void courseInfo(){
    
  }
  public void deleteCourse(Integer classId){
    
  }
}

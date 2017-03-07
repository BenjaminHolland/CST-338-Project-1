package cst338.p1;

import java.io.IOException;
import java.util.stream.Collectors;

public class School {
  private final Database database;
  private final String name;

  public String getName() {
    return name;
  }

  public School(String name) {
    this.name = name;
    this.database = new Database();
  }

  //
  public void readData(String path) {
    try {
      DataFile file=DataFile.load(path);
      for(TeacherRecord teacher:file.getTeacherStream().collect(Collectors.toList())){
        try{
          database.createTeacher(teacher);
        }catch(EntityDuplicateException ex){
          System.out.println("Instructor info reading failed - Employee number "+teacher.getId()+" already used.");
        }
      }
      for(CourseRecord course:file.getClassStream().collect(Collectors.toList())){
        try{
          database.createCourse(course);
        }catch(EntityDuplicateException ex){
          System.out.println("Course info reading failed - Course ID "+course.getId()+" already used.");
        }
      }
      for(StudentRecord student:file.getStudentStream().collect(Collectors.toList())){
        try{
          database.createStudent(student);
        }catch(EntityDuplicateException ex){
          System.out.println("Student info reading failed - Student ID "+student.getId()+" already used.");
        }
      }
      
      
    } catch (IOException e) {
      System.out.println("Error importing data from \"" + path + "\"");
      e.printStackTrace();
    }
    System.out.println("Done.");
  }

  public void schoolInfo() {
      //Print "School Name: [name]\n"
      //Print "Instructor Information\n"
      //For each teacher, print "\t[teacher.name]\n"
      //Print "Course Information\n"
      //For each course, print "\t[course.name]\n"
      //Print "Student Information\n"
      //For each student, print "\t[student.name]\n"
  }

  public void searchByEmail(String email) {
    //Print "Search key: [email]\n"
    //If Has Result:
    //For each teacher in result, print 
    //  "\tEmployee Number: [teacher.id]\n"
    //  "\tName: [teacher.name]\n"
    //  "\tPhone: [teacher.phone]\n"
    //  "\n
    //If No result
    //Print "\tNo employee with email [email]\n"
    
  }

  public void addInstructor(Integer id, String name, String email, String phone) {

  }

  public void addCourse(Integer id, String name, Integer capacity, String location) {
    //Add course to database. 
    //If Failed
    //Print "Course addition failed - Course number [id] already used.
  }

  public void addStudent(Integer id, String name) {
    //Add Student to database
    //If failed
    //Print "Student addtion failed - Student number [id] already used.
  }

  public void assignInstructor(Integer classId, Integer teacherId) {
    
  }

  public void register(Integer classId, Integer studentId) {

  }

  public void unRegister(Integer classId, Integer studentId) {

  }

  public StudentRecord getStudent(Integer studentId) {
    return null;
  }

  public void graduateStudent(Integer studentId) {

  }

  public void putScore(Integer classId, Integer studentId, Double score) {
    //Attempt to assign score to student in class
    //If class doesn't exist.
    //Print "Score assignment failed - Course [classId] does not exist.
    //Else if student doesn't exist.
    //Print "Score assignment failed - Student [studentId] does not exist.
    //Else if student is not enrolled in class
    //Print "Student [studentId] ([student.name]) is not enrolled in [classId]"
    
  }

  public void courseInfo(Integer classId) {
    //Print Line "Course Number: [course.id]"
    //Print Line "Instructor: [teachers for class.name seperated by ', ']"
    //Print Line "Course Title: [course.title]"
    //Print Line "Room: [course.location]"
    //Print Line "Total Enrolled: [enrolled student count]"
    //Print Line "Course Average: [average score of enrolled students]"
    
    
  }

  public CourseRecord getCourse(Integer classId) {
    return null;
  }

  public void courseInfo() {

  }

  public void deleteCourse(Integer classId) {}
}

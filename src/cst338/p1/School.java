package cst338.p1;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import java.io.IOException;
import java.util.stream.Collectors;

import cst338.p1.data.CourseRecord;
import cst338.p1.data.DataFile;
import cst338.p1.data.Database;
import cst338.p1.data.EnrollmentRecord;
import cst338.p1.data.StudentRecord;
import cst338.p1.data.TeacherRecord;

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
          database.createTeacher(teacher.getId(),teacher.getName(),teacher.getEmail(),teacher.getPhone());
        }catch(TeacherDuplicateException ex){
          System.out.println("Instructor info reading failed - Employee number "+teacher.getId()+" already used.");
        }
      }
      for(CourseRecord course:file.getClassStream().collect(Collectors.toList())){
        try{
          database.createCourse(course.getId(),course.getTitle(),course.getCapacity(),course.getLocation());
        }catch(CourseDuplicateException ex){
          System.out.println("Course info reading failed - Course ID "+course.getId()+" already used.");
        }
      }
      for(StudentRecord student:file.getStudentStream().collect(Collectors.toList())){
        try{
          database.createStudent(student.getId(),student.getName());
        }catch(StudentDuplicateException ex){
          System.out.println("Student info reading failed - Student ID "+student.getId()+" already used.");
        }
      }
      
      
    } catch (IOException e) {
      System.out.println("Error importing data from \"" + path + "\"");
      e.printStackTrace();
      throw new RuntimeException(e);
    }
    System.out.println("Done.");
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

  public void addInstructor(Integer id, String name, String email, String phone) throws TeacherDuplicateException {
    try{  
    database.createTeacher(id, name, email, phone);
    }catch(TeacherDuplicateException ex){
      System.out.println("Course addition failed - Teacher number "+id+" already used.");
    }
  }

  public void addCourse(Integer id, String name, Integer capacity, String location) {
    try{
      database.createCourse(id, name, capacity, location);
    }catch(CourseDuplicateException ex){
      System.out.println("Course addition failed - Course number "+id+" already used.");
    }
    //Add course to database. 
    //If Failed
    //Print "Course addition failed - Course number [id] already used.
  }

  public void addStudent(Integer id, String name) {
    //Add Student to database
    //If failed
    //Print "Student addtion failed - Student number [id] already used.
    try{
      database.createStudent(id, name);
    }catch(StudentDuplicateException ex){
      System.out.println("Student addition failed -Student number "+id+" already used.");
    }
  }

  public void assignInstructor(Integer courseId, Integer teacherId) {
    try {
      database.linkTeacherCourse(teacherId, courseId);
    } catch (AssignmentDuplicateException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (CourseMissingException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (TeacherMissingException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  public void register(Integer courseId, Integer studentId) {
    try {
      database.linkStudentCourse(studentId, courseId);
    } catch (EnrollmentDuplicateException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (CourseMissingException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (StudentMissingException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  public void unRegister(Integer courseId, Integer studentId) {
    try {
      database.unlinkStudentCourse(studentId, courseId);
    } catch (EnrollmentMissingException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (CourseMissingException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (StudentMissingException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  
  
  public void graduateStudent(Integer studentId) {
    
  }

  public void putScore(Integer courseId, Integer studentId, Double score) {
    
    EnrollmentRecord record;
    try {
      record = database.selectStudentCourse(studentId, courseId);
      record.setScore(score);
    } catch (EnrollmentMissingException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (CourseMissingException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (StudentMissingException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    
    //Attempt to assign score to student in class
    //If class doesn't exist.
    //Print "Score assignment failed - Course [classId] does not exist.
    //Else if student doesn't exist.
    //Print "Score assignment failed - Student [studentId] does not exist.
    //Else if student is not enrolled in class
    //Print "Student [studentId] ([student.name]) is not enrolled in [classId]"
    
  }

  public void schoolInfo() {
    
    //Print "School Name: [name]\n"
    System.out.println(name);
    //Print "Instructor Information\n"
    System.out.println("Instructor Information");
    //For each teacher, print "\t[teacher.name]\n"
    for(TeacherRecord teacher:database.selectTeachers()){
      System.out.println("\t"+teacher.getName());
    }
    //Print "Course Information\n"
    System.out.println("Course Information");
    //For each course, print "\t[course.name]\n"
    for(CourseRecord course:database.selectCourses()){
      System.out.println("\t"+course.getTitle());
    }
    //Print "Student Information\n"
    System.out.println("Student Information");
    //For each student, print "\t[student.name]\n"
    for(StudentRecord student:database.selectStudents()){
      System.out.println("\t"+student.getName());
    }
  }
  
  public void courseInfo() {

  }
  
  public void courseInfo(Integer courseId) {
      try{
      CourseRecord record=database.selectCourse(courseId);
      List<StudentRecord> enrolled=database.selectCourseStudents(courseId);
      List<TeacherRecord> teaching=database.selectCourseTeachers(courseId);
      List<Double> scores=new ArrayList<>();
      for(StudentRecord student:enrolled){
        EnrollmentRecord enrollment=database.selectStudentCourse(student.getId(), courseId);
        scores.add(enrollment.getScore());
      }
      Double average=scores.stream().collect(Collectors.averagingDouble(value->value));
      
      //Print Line "Course Number: [course.id]"
      System.out.println("Course Number: "+record.getId());
      //Print Line "Instructor: [teachers for class.name seperated by ', ']"
      System.out.print("Instructor: ");
      StringJoiner joiner=new StringJoiner(",");
      
      for(TeacherRecord teacher:teaching){
        joiner.add(teacher.getName());
      }
      System.out.println(joiner.toString());
      //Print Line "Course Title: [course.title]"
      System.out.println("Course Title: "+record.getTitle());
      //Print Line "Room: [course.location]"
      System.out.println("Room: "+record.getLocation());
      //Print Line "Total Enrolled: [enrolled student count]"
      System.out.println("Total Enrolled: "+enrolled.size());
      //Print Line "Course Average: [average score of enrolled students]"
      System.out.println("Course Average: "+average);
      }catch(CourseMissingException ex){
        //TODO: Print Error message.
      }catch(EnrollmentMissingException ex){
        //TODO: Print error message;
      }catch(StudentMissingException ex){
        //TODO: Print error message.
      }
  }
  
  public void deleteCourse(Integer courseId) {
    try {
      database.deleteCourse(courseId);
    } catch (CourseMissingException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
}

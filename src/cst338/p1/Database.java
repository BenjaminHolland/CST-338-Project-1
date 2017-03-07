package cst338.p1;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import javax.naming.OperationNotSupportedException;


/**
 * Educational Database.
 * 
 * @author Benjamin Represents a database that tracks the required entities
 */
public class Database {
  private final Map<Integer, StudentRecord> students;
  private final Map<Integer, TeacherRecord> teachers;
  private final Map<Integer, CourseRecord> courses;
  private final Map<Integer, List<Integer>> linkStudentCourse;
  private final Map<Integer, List<Integer>> linkTeacherCourse;

  
  public void createTeacher(Integer id,String name,String email,String phone){
    
  }
  
  public void deleteTeacher(Integer id){
    
  }
  
  public Optional<TeacherRecord> selectTeacher(Integer id){
    return Optional.ofNullable(teachers.get(id));
  }
  
  public Stream<TeacherRecord> selectTeachers(){
    return teachers.values().stream();
  }
  
  public void linkTeacherCourse(Integer teacherId,Integer courseId){
    
  }
  
  public void unlinkTeacherCourse(Integer teacherId,Integer courseId){
    
  }
  
  public Stream<Integer> selectTeacherCourses(Integer id){
    
  }
  
  
  public void createCourse(Integer id,String title,Integer capacity,String location){
    
  }
  
  public void deleteCourse(Integer id){
    
  }
  
  public Optional<CourseRecord> selectCourse(Integer id){
    return Optional.ofNullable(courses.get(id));
  }
  
  public Stream<CourseRecord> selectCourses(){
    return courses.values().stream();
  }
  
  public Stream<Integer> selectCourseTeachers(Integer courseId){
    
  }
  
  public Stream<Integer> selectCourseStudents(Integer courseId){
    
  }
  
  
  public void createStudent(Integer id,String name){
    
  }
  
  public void deleteStudent(Integer id){
    
  }
  
  public Optional<StudentRecord> selectStudent(Integer id){
    return Optional.ofNullable(students.get(studentId));
  }
  
  public Stream<StudentRecord> selectStudents(){
    return students.values().stream();
  }
  
  public void linkStudentCourse(Integer studentId,Integer courseId){
    
  }
  
  public void unlinkStudentCourse(Integer studentId,Integer courseId){
    
  }
  
  public Stream<EnrollmentRecord> selectStudentCourses(Integer id){
    
  }
  
    public Database() {
    students = new HashMap<>();
    teachers = new HashMap<>();
    courses = new HashMap<>();
    linkStudentCourse = new HashMap<>();
    linkTeacherCourse = new HashMap<>();

  }

}

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
  private final Map<Integer, List<EnrollmentRecord>> linkStudentCourse;
  private final Map<Integer, List<AssignmentRecord>> linkTeacherCourse;

  private void ensureTeacherExists(Integer id) throws TeacherMissingException{
    if(!teachers.containsKey(id)){
      throw new TeacherMissingException();
    }
  }
  private void ensureTeacherDoesNotExist(Integer id) throws TeacherDuplicateException{
    if(teachers.containsKey(id)){
      throw new TeacherDuplicateException();
    }
  }
  private void ensureCourseExists(Integer id) throws CourseMissingException{
    if(!courses.containsKey(id)){
      throw new CourseMissingException();
    }
  }
  private void ensureCourseDoesNotExist(Integer id) throws CourseDuplicateException{
    if(courses.containsKey(id)){
      throw new CourseDuplicateException();
    }
  }
  
  private void ensureStudentExists(Integer id) throws StudentMissingException{
    if(!students.containsKey(id)){
      throw new StudentMissingException();
    }
  }
  
  private void ensureStudentDoesNotExist(Integer id) throws StudentDuplicateException{
    if(students.containsKey(id)){
      throw new StudentDuplicateException()
    }
  }
  
  private void ensureEnrollmentExists(Integer studentId,Integer courseId) throws EnrollmentMissingException{
    if(selectStudentCourses(studentId).filter(enrollment->enrollment.getCourseId().equals(courseId)).count()==0){
      throw new EnrollmentMissingException();
    }
  }
  
  private void ensureEnrollmentDoesNotExist(Integer studentId,Integer courseId) throws EnrollmentDuplicateException{
    if(selectStudentCourses(studentId).filter(enrollment->enrollment.getCourseId().equals(courseId)).count()>0){
      throw new EnrollmentDuplicateException();
    }
  }
  
  private void ensureAssignmentExists(Integer teacherId,Integer courseId) throws AssignmentMissingException{
    if(selectTeacherCourses(teacherId).filter(assignment->assignment.getCourseId().equals(courseId)).count()==0){
      throw new AssignmentMissingException();
    }
  }
  
  private void ensureAssignmentDoesNotExist(Integer teacherId,Integer courseId) throws AssignmentDuplicateException{
    if(selectTeacherCourses(teacherId).filter(assignment->assignment.getCourseId().equals(courseId)).count()>0){
      throw new AssignmentDuplicateException();
    }
  }
  
  public void createTeacher(Integer id,String name,String email,String phone) throws TeacherDuplicateException{
    ensureTeacherDoesNotExist(id);
  }
  
  public void deleteTeacher(Integer id) throws TeacherMissingException{
    ensureTeacherExists(id);
  }
  
  public Optional<TeacherRecord> selectTeacher(Integer id){
    return Optional.ofNullable(teachers.get(id));
  }
  
  public Stream<TeacherRecord> selectTeachers(){
    return teachers.values().stream();
  }
  
  public void linkTeacherCourse(Integer teacherId,Integer courseId) throws AssignmentDuplicateException, CourseMissingException, TeacherMissingException{
    ensureTeacherExists(teacherId);
    ensureCourseExists(courseId);
    ensureAssignmentDoesNotExist(teacherId,courseId);
  }
  
  public void unlinkTeacherCourse(Integer teacherId,Integer courseId) throws AssignmentMissingException, CourseMissingException, TeacherMissingException{
    ensureTeacherExists(teacherId);
    ensureCourseExists(courseId);
    ensureAssignmentExists(teacherId, courseId);
  }
  
  public Stream<AssignmentRecord> selectTeacherCourses(Integer id) throws TeacherMissingException{
    ensureTeacherExists(id);
    
  }
  
  
  public void createCourse(Integer id,String title,Integer capacity,String location) throws CourseDuplicateException{
    ensureCourseDoesNotExist(id);
  }
  
  public void deleteCourse(Integer id) throws CourseMissingException{
    ensureCourseExists(id);
  }
  
  public Optional<CourseRecord> selectCourse(Integer id){
    return Optional.ofNullable(courses.get(id));
  }
  
  public Stream<CourseRecord> selectCourses(){
    return courses.values().stream();
  }
  
  public Stream<TeacherRecord> selectCourseTeachers(Integer courseId) throws CourseMissingException{
    ensureCourseExists(courseId);
  }
  
  public Stream<StudentRecord> selectCourseStudents(Integer courseId) throws CourseMissingException{
    ensureCourseExists(courseId);
  }
  
  public void createStudent(Integer id,String name) throws StudentDuplicateException{
    ensureStudentDoesNotExist(id);
  }
  
  public void deleteStudent(Integer id) throws StudentMissingException{
    ensureStudentExists(id);
  }
  
  public Optional<StudentRecord> selectStudent(Integer id){
    return Optional.ofNullable(students.get(id));
  }
  
  public Stream<StudentRecord> selectStudents(){
    return students.values().stream();
  }
  
  public void linkStudentCourse(Integer studentId,Integer courseId) throws EnrollmentDuplicateException, CourseMissingException, StudentMissingException{
    ensureStudentExists(studentId);
    ensureCourseExists(courseId);
    ensureEnrollmentDoesNotExist(studentId, courseId);
  }
  
  public void unlinkStudentCourse(Integer studentId,Integer courseId) throws EnrollmentMissingException, CourseMissingException, StudentMissingException{
    ensureStudentExists(studentId);
    ensureCourseExists(courseId);
    ensureEnrollmentExists(studentId, courseId);
  }
  
  public Stream<EnrollmentRecord> selectStudentCourses(Integer id) throws StudentMissingException{
    ensureStudentExists(id);
  }
  
  public EnrollmentRecord selectStudentCourse(Integer studentId,Integer courseId){
   ensureStudentExists(studentId);
   ensureCourseExists(courseId);
   ensureEnrollmentExists(studentId,courseId);
   
  }
  
    public Database() {
    students = new HashMap<>();
    teachers = new HashMap<>();
    courses = new HashMap<>();
    linkStudentCourse = new HashMap<>();
    linkTeacherCourse = new HashMap<>();

  }

}

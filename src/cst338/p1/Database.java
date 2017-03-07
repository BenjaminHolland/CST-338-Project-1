package cst338.p1;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
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
  private final Map<Integer, Map<Integer,EnrollmentRecord>> linkStudentCourse;
  private final Map<Integer, Map<Integer,AssignmentRecord>> linkTeacherCourse;

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
      throw new StudentDuplicateException();
    }
  }
  
  private void ensureEnrollmentExists(Integer studentId,Integer courseId) throws EnrollmentMissingException{
    if(!linkStudentCourse.containsKey(studentId)){
      throw new EnrollmentMissingException();
    }else{
      if(!linkStudentCourse.get(studentId).containsKey(courseId)){
        throw new EnrollmentMissingException();
      }
    }
  }
  
  private void ensureEnrollmentDoesNotExist(Integer studentId,Integer courseId) throws EnrollmentDuplicateException{
    if(linkStudentCourse.containsKey(studentId)){
      if(linkStudentCourse.get(studentId).containsKey(courseId)){
        throw new EnrollmentDuplicateException();
      }
    }
  }
  
  private void ensureAssignmentExists(Integer teacherId,Integer courseId) throws AssignmentMissingException{
    if(!linkTeacherCourse.containsKey(teacherId)){
      throw new AssignmentMissingException();
    }else{
      if(!linkTeacherCourse.get(teacherId).containsKey(courseId)){
        throw new AssignmentMissingException();
      }
    }
  }
  
  private void ensureAssignmentDoesNotExist(Integer teacherId,Integer courseId) throws AssignmentDuplicateException{
    if(linkTeacherCourse.containsKey(teacherId)){
      if(linkTeacherCourse.get(teacherId).containsKey(courseId)){
        throw new AssignmentDuplicateException();
      }
    }
  }
  
  
  public void createTeacher(Integer id,String name,String email,String phone) throws TeacherDuplicateException{
    ensureTeacherDoesNotExist(id);
    teachers.put(id, new TeacherRecord(id,name,email,phone));
  }
  
  public void deleteTeacher(Integer id) throws TeacherMissingException{
    ensureTeacherExists(id);
    teachers.remove(id);
    linkTeacherCourse.remove(id);
  }
  
  public TeacherRecord selectTeacher(Integer id) throws TeacherMissingException{
    ensureTeacherExists(id);
    return teachers.get(id);
  }
  
  public List<TeacherRecord> selectTeachers(){
    return (List<TeacherRecord>) teachers.values().stream().collect(Collectors.toList());
  }
  public List<TeacherRecord> selectTeachersByEmail(String email){
    return teachers.values().stream().filter(teacher->teacher.getEmail().equals(email)).collect(Collectors.toList());
  }
  public void linkTeacherCourse(Integer teacherId,Integer courseId) throws AssignmentDuplicateException, CourseMissingException, TeacherMissingException{
    ensureTeacherExists(teacherId);
    ensureCourseExists(courseId);
    ensureAssignmentDoesNotExist(teacherId,courseId);
    if(!linkTeacherCourse.containsKey(teacherId)){
      linkTeacherCourse.put(teacherId,new HashMap<Integer,AssignmentRecord>());
    }
    linkTeacherCourse.get(teacherId).put(courseId,new AssignmentRecord(courseId));
  }
  
  public void unlinkTeacherCourse(Integer teacherId,Integer courseId) throws AssignmentMissingException, CourseMissingException, TeacherMissingException{
    ensureTeacherExists(teacherId);
    ensureCourseExists(courseId);
    ensureAssignmentExists(teacherId, courseId);
    linkTeacherCourse.get(teacherId).remove(courseId);
    if(linkTeacherCourse.get(teacherId).isEmpty()){
      linkTeacherCourse.remove(teacherId);
    }
  }
  
  public List<AssignmentRecord> selectTeacherCourses(Integer id) throws TeacherMissingException{
    ensureTeacherExists(id);
    if(linkTeacherCourse.containsKey(id)){
      return (List<AssignmentRecord>) linkTeacherCourse.get(id).values();
    }else{
      return new ArrayList<>();
    }
  }
  
  
  public void createCourse(Integer id,String title,Integer capacity,String location) throws CourseDuplicateException{
    ensureCourseDoesNotExist(id);
    courses.put(id,new CourseRecord(id,title,capacity,location));
  }
  
  public void deleteCourse(Integer id) throws CourseMissingException{
    ensureCourseExists(id);
   
  }
  
  public CourseRecord selectCourse(Integer id) throws CourseMissingException{
    ensureCourseExists(id);
    return null;
  }
  
  public List<CourseRecord> selectCourses(){
    return null;
  }
  
  public List<TeacherRecord> selectCourseTeachers(Integer courseId) throws CourseMissingException{
    ensureCourseExists(courseId);
    return null;
  }
  
  public List<StudentRecord> selectCourseStudents(Integer courseId) throws CourseMissingException{
    ensureCourseExists(courseId);
    return null;
  }
  
  public void createStudent(Integer id,String name) throws StudentDuplicateException{
    ensureStudentDoesNotExist(id);
  }
  
  public void deleteStudent(Integer id) throws StudentMissingException{
    ensureStudentExists(id);
  }
  
  public StudentRecord selectStudent(Integer id) throws StudentMissingException{
    ensureStudentExists(id);
    return null;
  }
  
  public List<StudentRecord> selectStudents(){
    return null;
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
  
  public List<EnrollmentRecord> selectStudentCourses(Integer id) throws StudentMissingException{
    ensureStudentExists(id);
    return null;
  }
  
  public EnrollmentRecord selectStudentCourse(Integer studentId,Integer courseId) throws EnrollmentMissingException, CourseMissingException, StudentMissingException{
   ensureStudentExists(studentId);
   ensureCourseExists(courseId);
   ensureEnrollmentExists(studentId,courseId);
   return null;
   
  }
  
    public Database() {
    students = new HashMap<>();
    teachers = new HashMap<>();
    courses = new HashMap<>();
    linkStudentCourse = new HashMap<>();
    linkTeacherCourse = new HashMap<>();

  }

}

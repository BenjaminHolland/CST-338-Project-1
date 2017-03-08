package cst338.p1.data;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import cst338.p1.AssignmentDuplicateException;
import cst338.p1.AssignmentMissingException;
import cst338.p1.CourseDuplicateException;
import cst338.p1.CourseFullException;
import cst338.p1.CourseMissingException;
import cst338.p1.CourseNotEmptyException;
import cst338.p1.EnrollmentDuplicateException;
import cst338.p1.EnrollmentMissingException;
import cst338.p1.StudentDuplicateException;
import cst338.p1.StudentMissingException;
import cst338.p1.TeacherDuplicateException;
import cst338.p1.TeacherMissingException;

/**
 * Educational Database.
 * 
 * @author Benjamin Represents a database that tracks the required entities
 */

public class Database {
  private final Map<Integer, StudentRecord> students;
  private final Map<Integer, TeacherRecord> teachers;
  private final Map<Integer, CourseRecord> courses;
  private final Map<Integer, Map<Integer, EnrollmentRecord>> linkStudentCourse;
  private final Map<Integer, Map<Integer, AssignmentRecord>> linkTeacherCourse;
  
  public Boolean doesTeacherExist(Integer id){
    return teachers.containsKey(id);
  }
  public Boolean doesStudentExist(Integer id){
    return students.containsKey(id);
  }
  public Boolean doesCourseExist(Integer id){
    return courses.containsKey(id);
  }
  public Boolean doesAssignmentExist(Integer teacherId,Integer courseId){
    if(doesTeacherExist(teacherId)){
      if(doesCourseExist(courseId)){
        if(linkTeacherCourse.containsKey(teacherId)){
          if(linkTeacherCourse.get(teacherId).containsKey(courseId)){
            return true;
          }
        }
      }
    }
    return false;
  }
  public Boolean doesEnrollmentExist(Integer studentId,Integer courseId){
    if(doesStudentExist(studentId)){
      if(doesCourseExist(courseId)){
        if(linkStudentCourse.containsKey(studentId)){
          if(linkStudentCourse.get(studentId).containsKey(courseId)){
            return true;
          }
        }
      }
    }
    return false;
  }
  private void ensureTeacherExists(Integer id) throws TeacherMissingException {
    if (!teachers.containsKey(id)) {
      throw new TeacherMissingException();
    }
  }

  private void ensureTeacherDoesNotExist(Integer id) throws TeacherDuplicateException {
    if (teachers.containsKey(id)) {
      throw new TeacherDuplicateException();
    }
  }

  private void ensureCourseExists(Integer id) throws CourseMissingException {
    if (!courses.containsKey(id)) {
      throw new CourseMissingException();
    }
  }

  private void ensureCourseDoesNotExist(Integer id) throws CourseDuplicateException {
    if (courses.containsKey(id)) {
      throw new CourseDuplicateException();
    }
  }

  private void ensureStudentExists(Integer id) throws StudentMissingException {
    if (!students.containsKey(id)) {
      throw new StudentMissingException();
    }
  }

  private void ensureStudentDoesNotExist(Integer id) throws StudentDuplicateException {
    if (students.containsKey(id)) {
      throw new StudentDuplicateException();
    }
  }

  private void ensureCourseHasSpace(Integer courseId)
      throws CourseMissingException, CourseFullException {
    long enrolled = selectCourseStudents(courseId).size();
    if (enrolled >= selectCourse(courseId).getCapacity()) {
      throw new CourseFullException();
    }
  }

  private void ensureEnrollmentExists(Integer studentId, Integer courseId)
      throws EnrollmentMissingException {
    if (!linkStudentCourse.containsKey(studentId)) {
      throw new EnrollmentMissingException();
    } else {
      if (!linkStudentCourse.get(studentId).containsKey(courseId)) {
        throw new EnrollmentMissingException();
      }
    }
  }

  private void ensureEnrollmentDoesNotExist(Integer studentId, Integer courseId)
      throws EnrollmentDuplicateException {
    if (linkStudentCourse.containsKey(studentId)) {
      if (linkStudentCourse.get(studentId).containsKey(courseId)) {
        throw new EnrollmentDuplicateException();
      }
    }
  }

  private void ensureAssignmentExists(Integer teacherId, Integer courseId)
      throws AssignmentMissingException {
    if (!linkTeacherCourse.containsKey(teacherId)) {
      throw new AssignmentMissingException();
    } else {
      if (!linkTeacherCourse.get(teacherId).containsKey(courseId)) {
        throw new AssignmentMissingException();
      }
    }
  }

  private void ensureCourseIsEmpty(Integer courseId)
      throws CourseMissingException, CourseNotEmptyException {
    List<StudentRecord> students = selectCourseStudents(courseId);
    if (!students.isEmpty()) {
      throw new CourseNotEmptyException();
    }
  }

  private void ensureAssignmentDoesNotExist(Integer teacherId, Integer courseId)
      throws AssignmentDuplicateException {
    if (linkTeacherCourse.containsKey(teacherId)) {
      if (linkTeacherCourse.get(teacherId).containsKey(courseId)) {
        throw new AssignmentDuplicateException();
      }
    }
  }

  /**
   * Create Teacher
   * @param id The teachers unique id.
   * @param name The teachers full name.
   * @param email The teachers email.
   * @param phone The teachers phone number.
   * @throws TeacherDuplicateException If a teacher with a matching id already exists.
   */
  public void createTeacher(Integer id, String name, String email, String phone)
      throws TeacherDuplicateException {
    ensureTeacherDoesNotExist(id);
    teachers.put(id, new TeacherRecord(id, name, email, phone));
  }

  /**
   * Delete Teacher
   * @param id The id of the teacher to delete. 
   * @throws TeacherMissingException If no teacher with @param id exists. 
   */
  public void deleteTeacher(Integer id) throws TeacherMissingException {
    ensureTeacherExists(id);
    teachers.remove(id);
    linkTeacherCourse.remove(id);
  }

  /**
   * Read Teacher
   * @param id The id of the teacher to retrieve. 
   * @return The teacher with the given @param id
   * @throws TeacherMissingException If no teacher with @param id exists;
   */
  public TeacherRecord selectTeacher(Integer id) throws TeacherMissingException {
    ensureTeacherExists(id);
    return teachers.get(id);
  }

  /**
   * Read Teachers
   * @return A list of teachers. 
   */
  public List<TeacherRecord> selectTeachers() {
    return (List<TeacherRecord>) teachers.values().stream().collect(Collectors.toList());
  }

  /**
   * Find Teachers By Email.
   * @param email The address to match.
   * @return A list of teachers with matching email addresses.
   */
  public List<TeacherRecord> selectTeachersByEmail(String email) {
    return teachers.values().stream().filter(teacher -> teacher.getEmail().equals(email))
        .collect(Collectors.toList());
  }

  /**
   * Assign Teacher To Course.
   * @param teacherId The teachers id
   * @param courseId The courses id.
   * @throws AssignmentDuplicateException If the teacher is already assigned to the course.
   * @throws CourseMissingException If the course does not exist.
   * @throws TeacherMissingException If the teacher does not exist.
   */
  public void linkTeacherCourse(Integer teacherId, Integer courseId)
      throws AssignmentDuplicateException, CourseMissingException, TeacherMissingException {
    ensureTeacherExists(teacherId);
    ensureCourseExists(courseId);
    ensureAssignmentDoesNotExist(teacherId, courseId);
    if (!linkTeacherCourse.containsKey(teacherId)) {
      linkTeacherCourse.put(teacherId, new HashMap<Integer, AssignmentRecord>());
    }
    linkTeacherCourse.get(teacherId).put(courseId, new AssignmentRecord(courseId));
  }

  /**
   * Removes a course from a teachers workload.
   * @param teacherId The teachers id.
   * @param courseId the courses id.
   * @throws AssignmentMissingException If the teacher is not assigned to the class.
   * @throws CourseMissingException If the course does not exist.
   * @throws TeacherMissingException If the teacher does not exist.
   */
  public void unlinkTeacherCourse(Integer teacherId, Integer courseId)
      throws AssignmentMissingException, CourseMissingException, TeacherMissingException {
    ensureTeacherExists(teacherId);
    ensureCourseExists(courseId);
    ensureAssignmentExists(teacherId, courseId);
    linkTeacherCourse.get(teacherId).remove(courseId);
    if (linkTeacherCourse.get(teacherId).isEmpty()) {
      linkTeacherCourse.remove(teacherId);
    }
  }

  /**
   * Read courses assigned to a teacher.
   * @param id The id of the teacher to query.
   * @return A list containing the courses the teacher is assigned to.
   * @throws TeacherMissingException If the teacher does not exist.
   */
  public List<AssignmentRecord> selectTeacherCourses(Integer id) throws TeacherMissingException {
    ensureTeacherExists(id);
    if (linkTeacherCourse.containsKey(id)) {
      return linkTeacherCourse.get(id).values().stream().collect(Collectors.toList());
    } else {
      return new ArrayList<>();
    }
  }

  /**
   * Read Teacher Assignment Record.
   * @param teacherId The assigned teacher.
   * @param courseId The course assigned to.
   * @return An assignment record.
   * @throws TeacherMissingException If the teacher does not exist.
   * @throws CourseMissingException If the course does not exist.
   * @throws AssignmentMissingException If the teacher is not assigned to the course.
   */
  public AssignmentRecord selectTeacherCourse(Integer teacherId, Integer courseId)
      throws TeacherMissingException, CourseMissingException, AssignmentMissingException {
    ensureTeacherExists(teacherId);
    ensureCourseExists(courseId);
    ensureAssignmentExists(teacherId, courseId);
    return linkTeacherCourse.get(teacherId).get(courseId);
  }

  /**
   * Creates a course
   * @param id The unique id of the course.
   * @param title The title of the course.
   * @param capacity The maximum number of students that the course can have.
   * @param location The location of the course.
   * @throws CourseDuplicateException If a course with the same id already exists.
   */
  public void createCourse(Integer id, String title, Integer capacity, String location)
      throws CourseDuplicateException {
    ensureCourseDoesNotExist(id);
    courses.put(id, new CourseRecord(id, title, capacity, location));
  }

 
  /**
   * Deletes a course. 
   * @param id The id of the course to delete.
   * @throws CourseMissingException if the course 
   * @throws CourseNotEmptyException if the course has students still enrolled.
   */
  public void deleteCourse(Integer id) throws CourseMissingException, CourseNotEmptyException {
    ensureCourseExists(id);
    ensureCourseIsEmpty(id);
    /*
     * 
     * // Remove class from student enrollment lists. List<Integer> emptyEnrollments = new
     * ArrayList<>(); for (Entry<Integer, Map<Integer, EnrollmentRecord>> enrollment :
     * linkStudentCourse.entrySet()) { enrollment.getValue().remove(id); if
     * (enrollment.getValue().isEmpty()) { emptyEnrollments.add(enrollment.getKey()); } }
     * 
     * // Remove empty enrollment lists from the tracker. for (Integer emptyId : emptyEnrollments) {
     * linkStudentCourse.remove(emptyId); }
     */
    // Remove course from teacher assignments.
    List<Integer> emptyAssignments = new ArrayList<>();

    for (Entry<Integer, Map<Integer, AssignmentRecord>> assignment : linkTeacherCourse.entrySet()) {
      assignment.getValue().remove(id);
      if (assignment.getValue().isEmpty()) {
        emptyAssignments.add(assignment.getKey());
      }
    }

    // Remove empty association lists from the tracker.
    for (Integer emptyId : emptyAssignments) {
      linkTeacherCourse.remove(emptyId);
    }

    // remove the course from the course list.
    courses.remove(id);
  }

  /**
   * Read Course Record.
   * @param id The id of the course to retrieve. 
   * @return The course record with the matching id.
   * @throws CourseMissingException If no course with a matching id exists.
   */
  public CourseRecord selectCourse(Integer id) throws CourseMissingException {
    ensureCourseExists(id);
    return courses.get(id);
  }

  /**
   * Read Courses.
   * @return A list of courses.
   */
  public List<CourseRecord> selectCourses() {
    return courses.values().stream().collect(Collectors.toList());
  }

  /**
   * Read Teachers assigned to a course.
   * @param courseId The course to query.
   * @return A list of teachers that teach that course.
   * @throws CourseMissingException If the course does not exist.
   */
  public List<TeacherRecord> selectCourseTeachers(Integer courseId) throws CourseMissingException {
    ensureCourseExists(courseId);
    List<TeacherRecord> teachers = new ArrayList<>();
    for (Entry<Integer, Map<Integer, AssignmentRecord>> entry : linkTeacherCourse.entrySet()) {
      if (entry.getValue().containsKey(courseId)) {
        try {
          teachers.add(selectTeacher(entry.getKey()));
        } catch (TeacherMissingException ex) {
          throw new RuntimeException("Assertion Failed: Non-Existant Student Enrolled In Class.",
              ex);
        }
      }
    }
    return teachers;
  }

  /**
   * Read Students Enrolled In A Course.
   * @param courseId the id of the course to query.
   * @return A list of students enrolled in the class.
   * @throws CourseMissingException If the class does not exist.
   */
  public List<StudentRecord> selectCourseStudents(Integer courseId) throws CourseMissingException {
    ensureCourseExists(courseId);
    List<StudentRecord> enrolled = new ArrayList<>();
    for (Entry<Integer, Map<Integer, EnrollmentRecord>> entry : linkStudentCourse.entrySet()) {
      if (entry.getValue().containsKey(courseId)) {
        try {
          enrolled.add(selectStudent(entry.getKey()));
        } catch (StudentMissingException ex) {
          throw new RuntimeException("Assertion Failed: Non-Existant Student Enrolled In Class.",
              ex);
        }
      }
    }
    return enrolled;
  }

  /**
   * Create a new student.
   * @param id The unique id of the student.
   * @param name The name of the student. 
   * @throws StudentDuplicateException If a student with that name already exists.
   */
  public void createStudent(Integer id, String name) throws StudentDuplicateException {
    ensureStudentDoesNotExist(id);
    students.put(id, new StudentRecord(id, name));
  }

  public void deleteStudent(Integer id) throws StudentMissingException {
    ensureStudentExists(id);
    if (linkStudentCourse.containsKey(id)) {
      linkStudentCourse.remove(id);
    }
    students.remove(id);

  }

  public StudentRecord selectStudent(Integer id) throws StudentMissingException {
    ensureStudentExists(id);
    return students.get(id);
  }

  public List<StudentRecord> selectStudents() {
    return students.values().stream().collect(Collectors.toList());
  }

  public void linkStudentCourse(Integer studentId, Integer courseId)
      throws EnrollmentDuplicateException, CourseMissingException, StudentMissingException,
      CourseFullException {
    ensureStudentExists(studentId);
    ensureCourseExists(courseId);
    ensureEnrollmentDoesNotExist(studentId, courseId);
    ensureCourseHasSpace(courseId);
    if (!linkStudentCourse.containsKey(studentId)) {
      linkStudentCourse.put(studentId, new HashMap<>());
    }
    linkStudentCourse.get(studentId).put(courseId, new EnrollmentRecord(courseId));
  }

  public void unlinkStudentCourse(Integer studentId, Integer courseId)
      throws EnrollmentMissingException, CourseMissingException, StudentMissingException {
    ensureStudentExists(studentId);
    ensureCourseExists(courseId);
    ensureEnrollmentExists(studentId, courseId);
    linkStudentCourse.get(studentId).remove(courseId);
    if (linkStudentCourse.get(studentId).isEmpty()) {
      linkStudentCourse.remove(studentId);
    }
  }

  public List<EnrollmentRecord> selectStudentCourses(Integer id) throws StudentMissingException {
    ensureStudentExists(id);
    if (!linkStudentCourse.containsKey(id)) {
      return new ArrayList<>();
    } else {
      return linkStudentCourse.get(id).values().stream().collect(Collectors.toList());
    }
  }

  public EnrollmentRecord selectStudentCourse(Integer studentId, Integer courseId)
      throws EnrollmentMissingException, CourseMissingException, StudentMissingException {
    ensureStudentExists(studentId);
    ensureCourseExists(courseId);
    ensureEnrollmentExists(studentId, courseId);
    return linkStudentCourse.get(studentId).get(courseId);

  }

  public Database() {
    students = new HashMap<>();
    teachers = new HashMap<>();
    courses = new HashMap<>();
    linkStudentCourse = new HashMap<>();
    linkTeacherCourse = new HashMap<>();

  }

}

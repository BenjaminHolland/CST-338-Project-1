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
 * @author Benjamin
 * Represents a database that tracks the required entities
 */
public class Database {
  private final Map<Integer, StudentRecord> students;
  private final Map<Integer, TeacherRecord> teachers;
  private final Map<Integer, CourseRecord> courses;
  private final Map<Integer, List<Integer>> linkStudentCourse;
  private final Map<Integer, List<Integer>> linkTeacherCourse;
  
  /**
   * Initializes a new database.
   */
  public Database() {
    students = new HashMap<>();
    teachers = new HashMap<>();
    courses = new HashMap<>();
    linkStudentCourse = new HashMap<>();
    linkTeacherCourse = new HashMap<>();

  }

  /**
   * Deletes the specified Teacher Record. 
   * @param id The id of the teacher to delete. 
   * @throws EntityNotFoundException If there are no teachers with the specified id.
   * @
   */
  public void deleteTeacher(Integer id) throws EntityNotFoundException {
    TeacherRecord teacher = getTeacherById(id);
    teachers.remove(teacher.getId());
    linkTeacherCourse.remove(teacher.getId());
  }

  /**
   * Creates the specified teacher record. 
   * @param teacher The data to store in the teacher table.
   * @throws EntityDuplicateException if a teacher with the same id already exists. (Possibly also if the email already exists.)
   */
  public void createTeacher(TeacherRecord teacher) throws EntityDuplicateException {
    if (teachers.containsKey(teacher.getId())) {
      throw new EntityDuplicateException();
    } else {
      teachers.put(teacher.getId(), teacher);
    }
  }

  /**
   * Iterate over Teachers.
   * @return A stream of teachers.
   */
  public Stream<TeacherRecord> getTeacherStream() {
    return teachers.values().stream();
  }

  // This uses a sequential search on the teachers table. This could be accelerated by creating an
  // index on the email field, but as this isn't the goal of the assignment I've chosen to leave
  // it out. It also assumes that email addresses are unique, and so doesn't check for additional
  // matches.
  // RESOLUTION: emails are not unique. Therefor all teachers with matching emails should be returnted.
  /**
   * Attempts to find a teacher by their email.
   * @param email The email to find.
   * @return The first teacher with a matching email in the table.
   * @throws EntityNotFoundException If there is no teacher with a matching email.
   */
  public Stream<TeacherRecord> getTeachersByEmail(final String email) throws EntityNotFoundException {
    return getTeacherStream().filter(record->record.getEmail().equals(email));
  }

  /**
   * Retrieves a teacher.
   * @param id The id of the teacher to retrieve.
   * @return The record of the teacher with the matching id. 
   * @throws EntityNotFoundException If there are no teachers with a matching id.
   */
  public TeacherRecord getTeacherById(Integer id) throws EntityNotFoundException {
    TeacherRecord record = teachers.get(id);
    if (record == null) {
      throw new EntityNotFoundException();
    } else {
      return record;
    }

  }

  /**
   * Creates the specified student record in the student table. 
   * @param record The data of the student to create. 
   * @throws EntityDuplicateException If a student with a duplicate id is already in the database.
   */
  public void createStudent(StudentRecord record) throws EntityDuplicateException {
    if (students.containsKey(record)) {
      throw new EntityDuplicateException();
    } else {
      students.put(record.getId(), record);
    }
  }

  /**
   * Delets the student with the specified id in the student table. 
   * @param id The id of the student to delete. 
   * @throws EntityNotFoundException If there is no student with a matching id.
   */
  public void deleteStudent(Integer id) throws EntityNotFoundException {
    StudentRecord record = getStudentById(id);
    // Remove record;
    students.remove((Object) record.getId());

    // Remove references to courses.
    linkStudentCourse.remove((Object) record.getId());
  }

  /**
   * Retrieves the student with the specified id.
   * @param id The id of the student to retrieve. 
   * @return The record of the student with the specified id.
   * @throws EntityNotFoundException If there is no student with a matching id found.
   */
  public StudentRecord getStudentById(Integer id) throws EntityNotFoundException {
    StudentRecord record = students.get(id);
    if (record == null) {
      throw new EntityNotFoundException();
    } else {
      return record;
    }
  }

  /**
   * Iterates over students.
   * @return A stream of Student Records.
   */
  public Stream<StudentRecord> getStudentStream() {
    return students.values().stream();
  }

  /**
   * Creates a course.
   * @param course The data of the course to store in the database. 
   * @throws EntityDuplicateException If there's already a course with a matching id.
   */
  public void createCourse(CourseRecord course) throws EntityDuplicateException {
    if (courses.containsKey(course.getId())) {
      throw new EntityDuplicateException();
    } else {
      courses.put(course.getId(), course);
    }

  }

  /**
   * Deletes the specified course.
   * @param id The id of the course to delete. 
   * @throws EntityNotFoundException if there is no course with a matching id. 
   */
  public void deleteCourse(Integer id) throws EntityNotFoundException {
    CourseRecord record = getCourseById(id);
    courses.remove(record.getId());
    for (List<Integer> classList : linkStudentCourse.values()) {
      classList.remove((Object) record.getId());
    }
    for (List<Integer> classList : linkTeacherCourse.values()) {
      classList.remove((Object) record.getId());
    }
  }

  /**
   * Retrieves a specified course.
   * @param id The id of the course record to retireve. 
   * @return The Course Record with a matching id. 
   * @throws EntityNotFoundException If there was no course with a matching id. 
   */
  public CourseRecord getCourseById(Integer id) throws EntityNotFoundException {
    CourseRecord record = courses.get(id);
    if (record == null) {
      throw new EntityNotFoundException();
    } else {
      return record;
    }
  }

  /**
   * Specify that the given teacher is assigned to a given course.
   * @param teacher The teacher to assign to the course.
   * @param course The course to assign the teacher to.
   * @throws EntityDuplicateException if the teacher is already assigned to the class.
   * @throws EntityNotFoundException If the specified Teacher or Course is not already in the database. 
   */
  public void linkTeacherCourse(Integer teacherId, Integer courseId)
      throws EntityDuplicateException,EntityNotFoundException {
    
  }

  /**
   * Specify the given teacher is no longer assigned to a given course.
   * @param teacher The teacher to remove from the course.  
   * @param course The course to remove the teacher from.
   * @throws EntityNotFoundException If the teacher is not assigned to the course, or the specified course or teacher is not in the database. 
   */
  public void unlinkTeacherCourse(Integer teacherId, Integer courseId)
      throws EntityNotFoundException {
  }

  //TODO: write link/unlink methods for Student/Course linkages.
  /**
   * Specify that the given teacher is assigned to a given course.
   * @param teacher The teacher to assign to the course.
   * @param course The course to assign the teacher to.
   * @throws EntityDuplicateException if the teacher is already assigned to the class.
   * @throws EntityNotFoundException If the specified Teacher or Course is not already in the database. 
   */
  public void linkStudentCourse(Integer studentId, Integer courseId)
      throws EntityDuplicateException,EntityNotFoundException {
    
  }

  /**
   * Specify the given teacher is no longer assigned to a given course.
   * @param teacher The teacher to remove from the course.  
   * @param course The course to remove the teacher from.
   * @throws EntityNotFoundException If the teacher is not assigned to the course, or the specified course or teacher is not in the database. 
   */
  public void unlinkStudentCourse(Integer studentId, Integer courseId)
      throws EntityNotFoundException {
    
  }
  
  private void ensureExistantStudent(Integer studentId) throws EntityNotFoundException{
    if(!students.containsKey(studentId)){
      throw new EntityNotFoundException();
    }
  }
  private void ensureExistantTeacher(Integer teacherId) throws EntityNotFoundException{
    if(!teachers.containsKey(teacherId)){
      throw new EntityNotFoundException();
    }
  }
  public Stream<CourseRecord> getCoursesForStudent(Integer studentId) throws EntityNotFoundException{
    ensureExistantStudent(studentId);
    if(linkStudentCourse.containsKey(studentId)){
      return linkStudentCourse.get(studentId).stream().map(courseId->courses.get(courseId)).filter(record->record!=null);
    }else{
      return Stream.empty();
    }
  }
  public Stream<CourseRecord> getCoursesForTeacher(Integer teacherId){
    ensureExistantTeacher(teacherId);
    if(linkTeacherCourse.containsKey(teacherId)){
      return linkTeacherCourse.get(teacherId).stream().map(courseId->courses.get(courseId)).filter(record->record!=null);
      
    }else{
      return Stream.empty();
    }
  }
  public Stream<TeacherRecord> getTeachersForCourse(Integer courseId){
    return null;
  }
  public Stream<StudentRecord> getStudentsForCourse(Integer courseId){
    return null;
  }
  
  /**
   * Iterate over courses. 
   * @return A stream of course records.
   */
  public Stream<CourseRecord> getCourseStream() {
    return courses.values().stream();
  }

  /**
   * Import data from the specified path into the database. 
   * @param path The path of the data file to read from.
   * @throws IOException If there was an error reading the file.
   */
  public void importData(String path) throws IOException {
    DataFile newData = DataFile.load(path);

    // If we were cool and allowed to use packages, we could do these the things in parallel.
    newData.getStudentStream().forEach(cur -> students.put(cur.getId(), cur));
    newData.getClassStream().forEach(cur -> courses.put(cur.getId(), cur));
    newData.getTeacherStream().forEach(cur -> teachers.put(cur.getId(), cur));

  }
}

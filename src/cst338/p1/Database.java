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


  private void ensureExistantStudentCourseLink(Integer studentId,Integer courseId) throws EntityNotFoundException{
    if(!linkStudentCourse.containsKey(studentId)){
      throw new EntityNotFoundException();
    }else if(!linkStudentCourse.get(studentId).contains(courseId)){
      throw new EntityNotFoundException();
    }
  }
  
  private void ensureExistantCourse(Integer courseId) throws EntityNotFoundException {
    if (!courses.containsKey(courseId)) {
      throw new EntityNotFoundException();
    }
  }
  
  private void ensureExistantTeacherCourseLinnke(Integer teacherId,Integer courseId) throws EntityNotFoundException{
    if(!linkTeacherCourse.containsKey(teacherId)){
      throw new EntityNotFoundException();
    }else if(!linkTeacherCourse.containsKey(courseId)){
      throw new EntityNotFoundException();
    }
  }
  private void ensureNonExistantStudentCourseLink(Integer studentId,Integer courseId) throws EntityDuplicateException{
    if(linkStudentCourse.containsKey(studentId)&&linkStudentCourse.get(studentId).contains(courseId)){
      throw new EntityDuplicateException();
    }
  }
  private void ensureNonExistantTeacherCourseLinnke(Integer teacherId,Integer courseId) throws EntityDuplicateException{
    if(linkTeacherCourse.containsKey(teacherId)&&linkTeacherCourse.get(teacherId).contains(courseId)){
      throw new EntityDuplicateException();
    }
  }
  private void ensureExistantStudent(Integer studentId) throws EntityNotFoundException {
    if (!students.containsKey(studentId)) {
      throw new EntityNotFoundException();
    }
  }

  private void ensureExistantTeacher(Integer teacherId) throws EntityNotFoundException {
    if (!teachers.containsKey(teacherId)) {
      throw new EntityNotFoundException();
    }
  }

  private void ensureNonExistantTeacher(Integer teacherId) throws EntityDuplicateException {
    if (teachers.containsKey(teacherId)) {
      throw new EntityDuplicateException();
    }
  }

  private void ensureNonExistantStudent(Integer studentId) throws EntityDuplicateException {
    if (students.containsKey(studentId)) {
      throw new EntityDuplicateException();
    }
  }

  private void ensureNonExistantCourse(Integer courseId) throws EntityDuplicateException {
    if (courses.containsKey(courseId)) {
      throw new EntityDuplicateException();
    }
  }

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
   * 
   * @param id The id of the teacher to delete.
   * @throws EntityNotFoundException If there are no teachers with the specified id. @
   */
  public void deleteTeacher(Integer id) throws EntityNotFoundException {
    TeacherRecord teacher = getTeacherById(id);
    teachers.remove(teacher.getId());
    linkTeacherCourse.remove(teacher.getId());
  }

  /**
   * Creates the specified teacher record.
   * 
   * @param teacher The data to store in the teacher table.
   * @throws EntityDuplicateException if a teacher with the same id already exists. (Possibly also
   *         if the email already exists.)
   */
  public void createTeacher(TeacherRecord teacher) throws EntityDuplicateException {
    ensureNonExistantTeacher(teacher.getId());
    teachers.put(teacher.getId(), teacher);
    linkTeacherCourse.put(teacher.getId(), new ArrayList<>());
  }

  /**
   * Iterate over Teachers.
   * 
   * @return A stream of teachers.
   */
  public Stream<TeacherRecord> getTeacherStream() {
    return teachers.values().stream();
  }

  // This uses a sequential search on the teachers table. This could be accelerated by creating an
  // index on the email field, but as this isn't the goal of the assignment I've chosen to leave
  // it out. It also assumes that email addresses are unique, and so doesn't check for additional
  // matches.
  // RESOLUTION: emails are not unique. Therefor all teachers with matching emails should be
  // returnted.
  /**
   * Attempts to find a teacher by their email.
   * 
   * @param email The email to find.
   * @return The first teacher with a matching email in the table.
   * @throws EntityNotFoundException If there is no teacher with a matching email.
   */
  public Stream<TeacherRecord> getTeachersByEmail(final String email)
      throws EntityNotFoundException {
    return getTeacherStream().filter(record -> record.getEmail().equals(email));
  }

  /**
   * Retrieves a teacher.
   * 
   * @param id The id of the teacher to retrieve.
   * @return The record of the teacher with the matching id.
   * @throws EntityNotFoundException If there are no teachers with a matching id.
   */
  public TeacherRecord getTeacherById(Integer id) throws EntityNotFoundException {
    ensureExistantTeacher(id);
    return teachers.get(id);
  }

  /**
   * Creates the specified student record in the student table.
   * 
   * @param record The data of the student to create.
   * @throws EntityDuplicateException If a student with a duplicate id is already in the database.
   */
  public void createStudent(StudentRecord record) throws EntityDuplicateException {
    ensureNonExistantStudent(record.getId());
    students.put(record.getId(), record);
    linkStudentCourse.put(record.getId(), new ArrayList<>());
  }

  /**
   * Delets the student with the specified id in the student table.
   * 
   * @param id The id of the student to delete.
   * @throws EntityNotFoundException If there is no student with a matching id.
   */
  public void deleteStudent(Integer id) throws EntityNotFoundException {
   
    StudentRecord record = getStudentById(id);
    // Remove record;
    students.remove((Object) record.getId());

    // Remove references to courses.
    linkStudentCourse.remove(record.getId());
  }

  /**
   * Retrieves the student with the specified id.
   * 
   * @param id The id of the student to retrieve.
   * @return The record of the student with the specified id.
   * @throws EntityNotFoundException If there is no student with a matching id found.
   */
  public StudentRecord getStudentById(Integer id) throws EntityNotFoundException {
    ensureExistantStudent(id);
    return students.get(id);
    
  }

  /**
   * Iterates over students.
   * 
   * @return A stream of Student Records.
   */
  public Stream<StudentRecord> getStudentStream() {
    return students.values().stream();
  }

  /**
   * Creates a course.
   * 
   * @param course The data of the course to store in the database.
   * @throws EntityDuplicateException If there's already a course with a matching id.
   */
  public void createCourse(CourseRecord course) throws EntityDuplicateException {
    ensureNonExistantCourse(course.getId());
    courses.put(course.getId(), course);
  }

  /**
   * Deletes the specified course.
   * 
   * @param id The id of the course to delete.
   * @throws EntityNotFoundException if there is no course with a matching id.
   */
  public void deleteCourse(Integer id) throws EntityNotFoundException {
    CourseRecord record = getCourseById(id);
    courses.remove(record.getId());
    
    //Remove references to this course from students schedules.
    for (List<Integer> classList : linkStudentCourse.values()) {
      classList.remove((Object) record.getId());
    }
    
    //Remove references to this course from teachers schedules.
    for (List<Integer> classList : linkTeacherCourse.values()) {
      classList.remove((Object) record.getId());
    }
  }

  /**
   * Retrieves a specified course.
   * 
   * @param id The id of the course record to retireve.
   * @return The Course Record with a matching id.
   * @throws EntityNotFoundException If there was no course with a matching id.
   */
  public CourseRecord getCourseById(Integer id) throws EntityNotFoundException {
    ensureExistantCourse(id);
    return courses.get(id);

  }

  /**
   * Specify that the given teacher is assigned to a given course.
   * 
   * @param teacher The teacher to assign to the course.
   * @param course The course to assign the teacher to.
   * @throws EntityDuplicateException if the teacher is already assigned to the class.
   * @throws EntityNotFoundException If the specified Teacher or Course is not already in the
   *         database.
   */
  public void linkTeacherCourse(Integer teacherId, Integer courseId)
      throws EntityDuplicateException, EntityNotFoundException {
    ensureExistantCourse(courseId);
    ensureExistantTeacher(teacherId);
    ensureNonExistantTeacherCourseLinnke(teacherId, courseId);
    
  }

  /**
   * Specify the given teacher is no longer assigned to a given course.
   * 
   * @param teacher The teacher to remove from the course.
   * @param course The course to remove the teacher from.
   * @throws EntityNotFoundException If the teacher is not assigned to the course, or the specified
   *         course or teacher is not in the database.
   * @throws EntityDuplicateException 
   */
  public void unlinkTeacherCourse(Integer teacherId, Integer courseId)
      throws EntityNotFoundException, EntityDuplicateException {
    ensureExistantCourse(courseId);
    ensureExistantTeacher(teacherId);
    ensureExistantTeacherCourseLinnke(teacherId, courseId);
  }

  // TODO: write link/unlink methods for Student/Course linkages.
  /**
   * Specify that the given teacher is assigned to a given course.
   * 
   * @param teacher The teacher to assign to the course.
   * @param course The course to assign the teacher to.
   * @throws EntityDuplicateException if the teacher is already assigned to the class.
   * @throws EntityNotFoundException If the specified Teacher or Course is not already in the
   *         database.
   */
  public void linkStudentCourse(Integer studentId, Integer courseId)
      throws EntityDuplicateException, EntityNotFoundException {
      ensureExistantStudent(studentId);
      ensureExistantCourse(courseId);
      ensureNonExistantStudentCourseLink(studentId, courseId);
  }

  /**
   * Specify the given teacher is no longer assigned to a given course.
   * 
   * @param teacher The teacher to remove from the course.
   * @param course The course to remove the teacher from.
   * @throws EntityNotFoundException If the teacher is not assigned to the course, or the specified
   *         course or teacher is not in the database.
   */
  public void unlinkStudentCourse(Integer studentId, Integer courseId)
      throws EntityNotFoundException {
      ensureExistantStudent(studentId);
      ensureExistantCourse(courseId);
      ensureExistantStudentCourseLink(studentId, courseId);
  }



  

  public Stream<CourseRecord> getCoursesForStudent(Integer studentId)
      throws EntityNotFoundException {
    ensureExistantStudent(studentId);
    if (linkStudentCourse.containsKey(studentId)) {
      return linkStudentCourse.get(studentId).stream().map(courseId -> courses.get(courseId))
          .filter(record -> record != null);
    } else {
      return Stream.empty();
    }
  }

  public Stream<CourseRecord> getCoursesForTeacher(Integer teacherId)
      throws EntityNotFoundException {
    ensureExistantTeacher(teacherId);
    if (linkTeacherCourse.containsKey(teacherId)) {
      return linkTeacherCourse.get(teacherId).stream().map(courseId -> courses.get(courseId))
          .filter(record -> record != null);
    } else {
      return Stream.empty();
    }
  }

  public Stream<TeacherRecord> getTeachersForCourse(Integer courseId)
      throws EntityNotFoundException {
    ensureExistantCourse(courseId);

    return linkTeacherCourse.entrySet().stream()
        .filter(entry -> entry.getValue().contains(courseId))
        .map(entry -> teachers.get(entry.getKey())).filter(record -> record != null);
  }

  public Stream<StudentRecord> getStudentsForCourse(Integer courseId)
      throws EntityNotFoundException {
    ensureExistantCourse(courseId);
    return linkStudentCourse.entrySet().stream()
        .filter(entry -> entry.getValue().contains(courseId))
        .map(entry -> students.get(entry.getKey())).filter(record -> record != null);


  }

  /**
   * Iterate over courses.
   * 
   * @return A stream of course records.
   */
  public Stream<CourseRecord> getCourseStream() {
    return courses.values().stream();
  }

  /**
   * Import data from the specified path into the database.
   * 
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

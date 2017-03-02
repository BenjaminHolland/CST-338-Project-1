package cst338.p1;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;


public class Database {
  private final Map<Integer, StudentRecord> students;
  private final Map<Integer, TeacherRecord> teachers;
  private final Map<Integer, CourseRecord> courses;
  private final Map<Integer, List<Integer>> linkStudentCourse;
  private final Map<Integer, List<Integer>> linkTeacherCourse;


  public Database() {
    students = new HashMap<>();
    teachers = new HashMap<>();
    courses = new HashMap<>();
    linkStudentCourse = new HashMap<>();
    linkTeacherCourse = new HashMap<>();

  }

  public void deleteTeacher(Integer id) throws EntityNotFoundException {
    TeacherRecord teacher = getTeacherById(id);
    teachers.remove(teacher.getId());
    linkTeacherCourse.remove(teacher.getId());
  }

  public void createTeacher(TeacherRecord teacher) throws EntityDuplicateException {
    if (teachers.containsKey(teacher.getId())) {
      throw new EntityDuplicateException();
    } else {
      teachers.put(teacher.getId(), teacher);
    }
  }

  public Stream<TeacherRecord> getTeacherStream() {
    return teachers.values().stream();
  }

  // This uses a sequential search on the teachers table. This could be accelerated by creating an
  // index on the email field, but as this isn't the goal of the assignment I've chosen to leave
  // it out. It also assumes that email addresses are unique, and so doesn't check for additional
  // matches.
  public TeacherRecord getTeacherByEmail(final String email) throws EntityNotFoundException {
    Optional<TeacherRecord> recordOption =
        getTeacherStream().filter(record -> record.getEmail().equals(email)).findFirst();
    if (!recordOption.isPresent()) {
      throw new EntityNotFoundException();
    } else {
      return recordOption.get();
    }
  }

  public TeacherRecord getTeacherById(Integer id) throws EntityNotFoundException {
    TeacherRecord record = teachers.get(id);
    if (record == null) {
      throw new EntityNotFoundException();
    } else {
      return record;
    }

  }

  public void createStudent(StudentRecord record) throws EntityDuplicateException {
    if (students.containsKey(record)) {
      throw new EntityDuplicateException();
    } else {
      students.put(record.getId(), record);
    }
  }

  public void deleteStudent(Integer id) throws EntityNotFoundException {
    StudentRecord record = getStudentById(id);
    // Remove record;
    students.remove((Object) record.getId());

    // Remove references to courses.
    linkStudentCourse.remove((Object) record.getId());
  }

  public StudentRecord getStudentById(Integer id) throws EntityNotFoundException {
    StudentRecord record = students.get(id);
    if (record == null) {
      throw new EntityNotFoundException();
    } else {
      return record;
    }
  }

  public Stream<StudentRecord> getStudentStream() {
    return students.values().stream();
  }

  public void createCourse(CourseRecord course) throws EntityDuplicateException {
    if (courses.containsKey(course.getId())) {
      throw new EntityDuplicateException();
    } else {
      courses.put(course.getId(), course);
    }

  }

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

  public CourseRecord getCourseById(Integer id) throws EntityNotFoundException {
    CourseRecord record = courses.get(id);
    if (record == null) {
      throw new EntityNotFoundException();
    } else {
      return record;
    }
  }

  public void linkTeacherCourse(TeacherRecord teacher, CourseRecord course)
      throws EntityDuplicateException {
    if (!linkTeacherCourse.containsKey(teacher.getId())) {
      linkTeacherCourse.put(teacher.getId(), new ArrayList<>());
    }
    if (linkTeacherCourse.get(teacher.getId()).contains(course.getId())) {
      throw new EntityDuplicateException();
    } else {
      linkTeacherCourse.get(teacher.getId()).add(course.getId());
    }
  }

  public void unlinkTeacherCourse(TeacherRecord teacher, CourseRecord course)
      throws EntityNotFoundException {
    if (!linkTeacherCourse.containsKey(teacher.getId())) {
      throw new EntityNotFoundException();
    } else {
      if (!linkTeacherCourse.get(teacher.getId()).contains(course.getId())) {
        throw new EntityNotFoundException();
      } else {
        linkTeacherCourse.get(teacher.getId()).remove((Object) course.getId());
      }
    }
  }

  //TODO: write link/unlink methods for Student/Course linkages.
  public Stream<CourseRecord> getCourseStream() {
    return courses.values().stream();
  }

  public void importData(String path) throws IOException {
    DataFile newData = DataFile.load(path);

    // If we were cool and allowed to use packages, we could do these the things in parallel.
    newData.getStudentStream().forEach(cur -> students.put(cur.getId(), cur));
    newData.getClassStream().forEach(cur -> courses.put(cur.getId(), cur));
    newData.getTeacherStream().forEach(cur -> teachers.put(cur.getId(), cur));

  }
}

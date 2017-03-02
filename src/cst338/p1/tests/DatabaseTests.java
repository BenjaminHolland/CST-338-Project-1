package cst338.p1.tests;

import static org.junit.Assert.*;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import cst338.p1.CourseRecord;
import cst338.p1.Database;
import cst338.p1.EntityDuplicateException;
import cst338.p1.EntityNotFoundException;
import cst338.p1.StudentRecord;
import cst338.p1.TeacherRecord;

public class DatabaseTests {
  @Rule
  public ExpectedException thrown = ExpectedException.none();
  private final TeacherRecord stdTeacherRecord =
      new TeacherRecord(1000, "A. Teacher", "ateacher@school.edu", "123-45-6789");
  private final StudentRecord stdStudentRecord = new StudentRecord(2000, "A. Student");
  private final CourseRecord stdCourseRecord =
      new CourseRecord(3000, "CLS-3000 Introduction To Subject", 10, "ROOM 0");

  public void deleteStudent_nominal() throws EntityNotFoundException, EntityDuplicateException {
    Database db=new Database();
    
    // Add Student
    db.createStudent(stdStudentRecord);
    // Add Class
    db.createCourse(stdCourseRecord);
    // Link Student to Class
    db.linkStudentCourse(stdStudentRecord.getId(), stdCourseRecord.getId());
    // Delete student
    db.deleteStudent(stdStudentRecord.getId());
    // Ensure student no longer exists.
    assertEquals(0,db.getStudentStream().collect(Collectors.counting()).intValue());
    // Ensure class still exists.
    db.getCourseById(stdCourseRecord.getId());
    // Ensure student is no longer in class.
    assertEquals(0,db.getStudentsForCourse(stdCourseRecord.getId()).count());

  }

  public void deleteStudent_missing() throws EntityNotFoundException {
    // Expect an EntityNotFound Exception.
    thrown.expect(EntityNotFoundException.class);
    // Call Delete student.
    Database db=new Database();
    db.deleteStudent(stdStudentRecord.getId());
  }

  public void deleteTeacher_nominal() throws EntityNotFoundException, EntityDuplicateException {
    Database db=new Database();
    // Add Teacher.
    db.createTeacher(stdTeacherRecord);
    // Add Class.
    db.createCourse(stdCourseRecord);
    // Link teacher to class.
    db.linkTeacherCourse(stdTeacherRecord.getId(),stdCourseRecord.getId());
    // Delete teacher.
    db.deleteTeacher(stdTeacherRecord.getId());
    // Ensure teacher no longer exists.
    assertEquals(0, db.getTeacherStream().count());
    // Ensure class still exists.
    db.getCourseById(stdCourseRecord.getId());
    // Ensure teacher is no longer assigned to the class.
    assertEquals(0, db.getTeachersForCourse(stdCourseRecord.getId()));
 
  }

  public void deleteTeacher_missing() throws EntityNotFoundException {
    // Expect an EntityNotFound Exception.
    thrown.expect(EntityNotFoundException.class);
    Database db=new Database();
    // Call deleteTeacher
    db.deleteTeacher(stdTeacherRecord.getId());
   
  }

  public void deleteCourse_nominal() throws EntityNotFoundException, EntityDuplicateException {

    Database db=new Database();
    // Add Teacher.
    db.createTeacher(stdTeacherRecord);
    // Add Student.
    db.createStudent(stdStudentRecord);
    // Add Course.
    db.createCourse(stdCourseRecord);
    // Link Teacher to Course.
    db.linkTeacherCourse(stdTeacherRecord.getId(),stdCourseRecord.getId());
    // Link Student to Course.
    db.linkStudentCourse(stdStudentRecord.getId(),stdCourseRecord.getId());
    // Delete course.
    db.deleteCourse(stdCourseRecord.getId());
    // Ensure course no longer exists.
    assertEquals(0,db.getCourseStream().count());
    // Ensure teacher still exists.
    db.getTeacherById(stdTeacherRecord.getId());
    // Ensure student still exists.
    db.getStudentById(stdStudentRecord.getId());
    // Ensure teacher is no longer assigned to course.
    assertEquals(0,db.getCoursesForTeacher(stdTeacherRecord.getId()).count());
    // Ensure student is no longer in course.
    assertEquals(0,db.getCoursesForStudent(stdStudentRecord.getId()).count());

  }

  public void deleteCourse_missing() throws EntityNotFoundException {
    Database db=new Database();
    //Expect EntityNotFoundException
    thrown.expect(EntityNotFoundException.class);
    //Call deleteCourse
    db.deleteCourse(stdCourseRecord.getId());
    
  }



  @Test
  public void createStudent_nominal() throws EntityDuplicateException {
    Database db = new Database();
    db.createStudent(new StudentRecord(1000, "Bob Joe"));
    List<StudentRecord> records = db.getStudentStream().collect(Collectors.toList());
    assertEquals(1, records.size());
    assertEquals(Integer.valueOf(1000), records.get(0).getId());
    assertEquals("Bob Joe", records.get(0).getName());
  }

  @Test
  public void createStudent_duplicate() throws EntityDuplicateException {
    thrown.expect(EntityDuplicateException.class);
    Database db = new Database();
    db.createStudent(new StudentRecord(1000, "Ogger Blog"));
    db.createStudent(new StudentRecord(1000, "Booger Ooger"));
  }

  @Test
  public void createTeacher_nominal() throws EntityDuplicateException {
    Database db = new Database();
    db.createTeacher(new TeacherRecord(1000, "Hop Slammin", "hslammin@yas.edu", "123-45-6789"));
    List<TeacherRecord> records = db.getTeacherStream().collect(Collectors.toList());
    assertEquals(1, records.size());
    assertEquals(Integer.valueOf(1000), records.get(0).getId());
    assertEquals("Hop Slammin", records.get(0).getName());
    assertEquals("hslammin@yas.edu", records.get(0).getEmail());
    assertEquals("123-45-6789", records.get(0).getSsn());
  }

  @Test
  public void createTeacher_duplicate() throws EntityDuplicateException {
    Database db = new Database();
    thrown.expect(EntityDuplicateException.class);
    db.createTeacher(
        new TeacherRecord(1000, "Teachmaster 5000", "tm5000@robots.edu", "927-92-0019"));
    db.createTeacher(
        new TeacherRecord(1000, "Teachmaster 5000", "tm5000@robots.edu", "927-92-0019"));

  }

  @Test
  public void createCourse_nominal() throws EntityDuplicateException {
    Database db = new Database();
    db.createCourse(new CourseRecord(1000, "BOB-1000 Introduction to Derp", 30, "LOL-50"));
    List<CourseRecord> records = db.getCourseStream().collect(Collectors.toList());
    assertEquals(1, records.size());
    assertEquals(Integer.valueOf(1000), records.get(0).getId());
    assertEquals("BOB-1000 Introduction to Derp", records.get(0).getTitle());
    assertEquals(Integer.valueOf(30), records.get(0).getCapacity());
    assertEquals("LOL-50", records.get(0).getLocation());
  }

  @Test
  public void createCourse_duplicate() throws EntityDuplicateException {
    Database db = new Database();
    thrown.expect(EntityDuplicateException.class);
    db.createCourse(new CourseRecord(1000, "ECO-1000 Introduction to Fish", 30, "OCEAN-10"));
    db.createCourse(new CourseRecord(1000, "ECO-1000 Introduction to Fish", 30, "OCEAN-10"));

  }

  private void linkStudentCourse_nominal_core(Database db)
      throws EntityNotFoundException, EntityDuplicateException {
    db.createStudent(new StudentRecord(1000, "Yar Ogg"));
    db.createCourse(new CourseRecord(2000, "HIS-2000 Mastadon Hunting", 30, "CAVE-99"));
    db.linkStudentCourse(1000, 2000);
    List<StudentRecord> courseStudents = db.getStudentsForCourse(2000).collect(Collectors.toList());
    assertEquals(1, courseStudents.size());
    assertEquals(Integer.valueOf(1000), courseStudents.get(0).getId());

    List<CourseRecord> studentCourses = db.getCoursesForStudent(1000).collect(Collectors.toList());
    assertEquals(1, studentCourses.size());
    assertEquals(Integer.valueOf(2000), studentCourses.get(0).getId());

  }

  @Test
  public void linkStudentCourse_nominal() throws EntityDuplicateException, EntityNotFoundException {
    Database db = new Database();
    linkStudentCourse_nominal_core(db);
  }

  @Test
  public void linkStudentCourse_duplicate()
      throws EntityDuplicateException, EntityNotFoundException {
    Database db = new Database();
    db.createStudent(new StudentRecord(1000, "Yar Ogg"));
    db.createCourse(new CourseRecord(2000, "HIS-2000 Mastadon Hunting", 30, "CAVE-99"));
    db.linkStudentCourse(1000, 2000);
    thrown.expect(EntityDuplicateException.class);
    db.linkStudentCourse(1000, 2000);
  }

  @Test
  public void linkStudentCourse_missingStudent()
      throws EntityDuplicateException, EntityNotFoundException {
    Database db = new Database();
    db.createCourse(
        new CourseRecord(1000, "CST-1000 Introduction to 4th-Dimensional Computers", 10, "EXT-5"));
    thrown.expect(EntityNotFoundException.class);
    db.linkStudentCourse(2000, 1000);

  }

  @Test
  public void linkStudentCourse_missingCourse()
      throws EntityDuplicateException, EntityNotFoundException {
    Database db = new Database();
    db.createStudent(new StudentRecord(1000, "Joe Jim Joseph Juperm"));
    thrown.expect(EntityNotFoundException.class);
    db.linkStudentCourse(1000, 2000);

  }

  private void linkTeacherCourse_nominal_core(Database db)
      throws EntityNotFoundException, EntityDuplicateException {
    db.createTeacher(new TeacherRecord(1000, "Hort Mra", "hmra@rock.edu", "000-00-0498"));
    db.createCourse(new CourseRecord(2000, "HIS-2000 Mastadon Hunting", 30, "CAVE-99"));
    db.linkTeacherCourse(1000, 2000);
    List<TeacherRecord> courseTeachers = db.getTeachersForCourse(2000).collect(Collectors.toList());
    assertEquals(1, courseTeachers.size());
    assertEquals(Integer.valueOf(1000), courseTeachers.get(0).getId());

    List<CourseRecord> teacherCourses = db.getCoursesForTeacher(1000).collect(Collectors.toList());
    assertEquals(1, teacherCourses.size());
    assertEquals(Integer.valueOf(2000), teacherCourses.get(0).getId());

  }

  @Test
  public void linkTeacherCourse_nominal() throws EntityNotFoundException, EntityDuplicateException {
    Database db = new Database();
    linkTeacherCourse_nominal_core(db);
  }

  @Test
  public void linkTeacherCourse_duplicate()
      throws EntityDuplicateException, EntityNotFoundException {
    Database db = new Database();
    db.createTeacher(new TeacherRecord(1000, "Hort Mra", "hmra@rock.edu", "000-00-0498"));
    db.createCourse(new CourseRecord(2000, "HIS-2000 Mastadon Hunting", 30, "CAVE-99"));
    db.linkTeacherCourse(1000, 2000);
    thrown.expect(EntityDuplicateException.class);
    db.linkTeacherCourse(1000, 2000);
  }

  @Test
  public void linkTeacherCourse_missingTeacher()
      throws EntityDuplicateException, EntityNotFoundException {
    Database db = new Database();
    db.createCourse(
        new CourseRecord(1000, "CST-1000 Introduction to 4th-Dimensional Computers", 10, "EXT-5"));
    thrown.expect(EntityNotFoundException.class);
    db.linkTeacherCourse(2000, 1000);


  }

  @Test
  public void linkTeacherCourse_missingCourse()
      throws EntityDuplicateException, EntityNotFoundException {
    Database db = new Database();
    db.createTeacher(
        new TeacherRecord(1000, "Yopopplize Baboporingo", "ybaboporingo@where.edu", "998-00-1882"));
    thrown.expect(EntityNotFoundException.class);
    db.linkTeacherCourse(2000, 1000);
  }


  @Test
  public void unlinkStudentCourse_nominal()
      throws EntityDuplicateException, EntityNotFoundException {
    Database db = new Database();
    linkStudentCourse_nominal_core(db);
    db.unlinkStudentCourse(1000, 2000);
    List<StudentRecord> courseStudents = db.getStudentsForCourse(2000).collect(Collectors.toList());
    assertEquals(0, courseStudents.size());
    List<CourseRecord> studentCourses = db.getCoursesForStudent(1000).collect(Collectors.toList());
    assertEquals(0, studentCourses.size());
  }

  @Test
  public void unlinkStudentCourse_missing()
      throws EntityDuplicateException, EntityNotFoundException {
    Database db = new Database();
    db.createStudent(new StudentRecord(1000, "Yababalola Xyboobalooba"));
    db.createCourse(new CourseRecord(1000, "ZZZ-1000 Introduction to Sleep.", 10, "BED-10"));
    thrown.expect(EntityNotFoundException.class);
    db.unlinkStudentCourse(1000, 1000);
  }

  @Test
  public void unlinkStudentCourse_missingStudent()
      throws EntityDuplicateException, EntityNotFoundException {
    Database db = new Database();
    db.createCourse(new CourseRecord(500, "MTX-500 Advanced Bullet-Time", 10, "BLN-100"));
    thrown.expect(EntityNotFoundException.class);
    db.unlinkStudentCourse(1000, 500);

  }

  @Test
  public void unlinkStudentCourse_missingCourse()
      throws EntityDuplicateException, EntityNotFoundException {
    Database db = new Database();

    db.createStudent(new StudentRecord(3000, "Momna Shep"));
    thrown.expect(EntityNotFoundException.class);
    db.unlinkStudentCourse(3000, 2);
    fail("Not yet implemented.");
  }

  @Test
  public void unlinkTeacherCourse_nominal()
      throws EntityNotFoundException, EntityDuplicateException {
    Database db = new Database();
    linkTeacherCourse_nominal_core(db);
    db.unlinkTeacherCourse(1000, 2000);
    List<TeacherRecord> courseTeachers = db.getTeachersForCourse(2000).collect(Collectors.toList());
    assertEquals(0, courseTeachers.size());
    List<CourseRecord> teacherCourses = db.getCoursesForTeacher(1000).collect(Collectors.toList());
    assertEquals(0, teacherCourses.size());
  }

  @Test
  public void unlinkTeacherCourse_missing()
      throws EntityDuplicateException, EntityNotFoundException {
    Database db = new Database();
    db.createTeacher(new TeacherRecord(1000, "Yababalola Xyboobalooba",
        "yxyboobalooba@gababbawabba.edu", "102-302-4021"));
    db.createCourse(new CourseRecord(1000, "ZZZ-1000 Introduction to Sleep.", 10, "BED-10"));
    thrown.expect(EntityNotFoundException.class);
    db.unlinkStudentCourse(1000, 1000);
    fail("Not yet implemented.");
  }

  @Test
  public void unlinkTeacherCourse_missingTeacher()
      throws EntityDuplicateException, EntityNotFoundException {
    Database db = new Database();
    db.createCourse(new CourseRecord(500, "MTX-500 Advanced Bullet-Time", 10, "BLN-100"));
    thrown.expect(EntityNotFoundException.class);
    db.unlinkTeacherCourse(1000, 500);
  }

  @Test
  public void unlinkTeacherCourse_missingCourse()
      throws EntityDuplicateException, EntityNotFoundException {
    Database db = new Database();

    db.createTeacher(new TeacherRecord(3000, "Momna Shep", "mshep@hep.edu", "123-43-5029"));
    thrown.expect(EntityNotFoundException.class);
    db.unlinkTeacherCourse(3000, 2);
    fail("Not yet implemented.");
  }
}

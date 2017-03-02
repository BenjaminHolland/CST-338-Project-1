package cst338.p1.tests;

import static org.junit.Assert.*;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import cst338.p1.CourseRecord;
import cst338.p1.Database;
import cst338.p1.EntityDuplicateException;
import cst338.p1.EntityNotFoundException;
import cst338.p1.StudentRecord;
import cst338.p1.TeacherRecord;

public class DatabaseTests {

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
  public void createStudent_duplicate() {
    fail("Not yet implemented");
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
  public void createTeacher_duplicateId() {
    fail("Not yet implemented");
  }

  @Test
  public void createTeacher_duplicateEmail() {
    fail("Not yet implemented");
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
  public void createCourse_duplicate() {
    fail("Not yet implemented");
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
  public void linkStudentCourse_duplicate() {
    fail("Not yet implemented.");
  }

  @Test
  public void linkStudentCourse_missingStudent() {
    fail("Not yet implemented.");
  }

  @Test
  public void linkStudentCourse_missingCourse() {
    fail("Not yet implemented.");
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
  public void linkTeacherCourse_duplicate() {
    fail("Not yet implemented.");
  }

  @Test
  public void linkTeacherCourse_missingTeacher() {
    fail("Not yet implemented.");
  }

  @Test
  public void linkTeacherCourse_missingCourse() {
    fail("Not yet implemented.");
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
  public void unlinkStudentCourse_missing() {
    fail("Not yet implemented.");
  }

  @Test
  public void unlinkStudentCourse_missingStudent() {
    fail("Not yet implemented.");
  }

  @Test
  public void unlinkStudentCourse_missingCourse() {
    fail("Not yet implemented.");
  }

  @Test
  public void unlinkTeacherCourse_nominal() throws EntityNotFoundException, EntityDuplicateException {
    Database db=new Database();
    linkTeacherCourse_nominal_core(db);
    db.unlinkTeacherCourse(1000, 2000);
    List<TeacherRecord> courseTeachers = db.getTeachersForCourse(2000).collect(Collectors.toList());
    assertEquals(0, courseTeachers.size());
    List<CourseRecord> teacherCourses = db.getCoursesForTeacher(1000).collect(Collectors.toList());
    assertEquals(0, teacherCourses.size());
  }

  @Test
  public void unlinkTeacherCourse_missing() {
    fail("Not yet implemented.");
  }

  @Test
  public void unlinkTeacherCourse_missingTeacher() {
    fail("Not yet implemented.");
  }

  @Test
  public void unlinkTeacherCourse_missingCourse() {
    fail("Not yet implemented.");
  }
}

package cst338.p1.tests;

import static org.junit.Assert.*;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import cst338.p1.CourseDuplicateException;
import cst338.p1.CourseFullException;
import cst338.p1.CourseMissingException;
import cst338.p1.CourseRecord;
import cst338.p1.Database;
import cst338.p1.EntityDuplicateException;
import cst338.p1.EntityMissingException;
import cst338.p1.StudentDuplicateException;
import cst338.p1.StudentMissingException;
import cst338.p1.StudentRecord;
import cst338.p1.TeacherDuplicateException;
import cst338.p1.TeacherMissingException;
import cst338.p1.TeacherRecord;

public class DatabaseTests {
  @Rule
  public ExpectedException thrown = ExpectedException.none();
  private final TeacherRecord stdTeacherRecord =
      new TeacherRecord(1000, "A. Teacher", "ateacher@school.edu", "123-45-6789");
  private final StudentRecord stdStudentRecord = new StudentRecord(2000, "A. Student");
  private final CourseRecord stdCourseRecord =
      new CourseRecord(3000, "CLS-3000 Introduction To Subject", 10, "ROOM 0");

  @Test
  public void testCreateTeacher_nominal() throws TeacherDuplicateException {
    Database db=new Database();
    db.createTeacher(100, "Teacher 1", "t1@school.edu","555-123-4567");
    List<TeacherRecord> teachers=db.selectTeachers();
    assertEquals(1,teachers.size());
    assertEquals(Integer.valueOf(100),teachers.get(0).getId());
    assertEquals("Teacher 1",teachers.get(0).getName());
    assertEquals("t1@school.edu",teachers.get(0).getEmail());
    assertEquals("555-123-4567",teachers.get(0).getPhone());
  }

  @Test
  public void testCreateTeacher_duplicate() throws TeacherDuplicateException {
    Database db=new Database();
    db.createTeacher(100, "Teacher 1", "t1@school.edu","555-123-4567");
    thrown.expect(TeacherDuplicateException.class);
    db.createTeacher(100, "Teacher 1", "t1@school.edu","555-123-4567");
  }

  @Test
  public void testDeleteTeacher_nominal() throws TeacherMissingException, TeacherDuplicateException {
    Database db=new Database();
    db.createTeacher(100, "Teacher 1", "t1@school.edu","555-123-4567");
    db.deleteTeacher(100);
    List<TeacherRecord> teachers=db.selectTeachers();
    assertEquals(0,teachers.size());
    
  }

  @Test
  public void testDeleteTeacher_missing() throws TeacherMissingException {
    Database db=new Database();
    thrown.expect(TeacherMissingException.class);
    db.deleteTeacher(100);
  }

  @Test
  public void testSelectTeacher_nominal() throws TeacherMissingException, TeacherDuplicateException {
    Database db=new Database();
    db.createTeacher(100, "Teacher 1", "t1@school.edu","555-123-4567");
    TeacherRecord record=db.selectTeacher(100);
    assertEquals(Integer.valueOf(100),record.getId());
    
  }

  @Test
  public void testSelectTeacher_missing() throws TeacherMissingException {
    Database db=new Database();
    thrown.expect(TeacherMissingException.class);
    db.selectTeacher(100);
  }

  @Test
  public void testSelectTeacherByEmail_nominal() throws TeacherMissingException, TeacherDuplicateException {
    Database db=new Database();
    db.createTeacher(100, "Teacher 1", "t1@school.edu", "555-123-4567");
    db.createTeacher(200, "Temp 1", "t1@school.edu", "555-234-5678");
    db.createTeacher(300, "Teacher 2", "t2@school.edu", "555-345-6789");
    List<TeacherRecord> zero=db.selectTeachersByEmail("none@school.edu");
    List<TeacherRecord> one=db.selectTeachersByEmail("t2@school.edu");
    List<TeacherRecord> two=db.selectTeachersByEmail("t1@school.edu");
    assertEquals(0,zero.size());
    assertEquals(1,one.size());
    assertEquals(Integer.valueOf(300),one.get(0).getId());
    assertEquals(2,two.size());
    assertEquals(Integer.valueOf(100),two.get(0).getId());
    assertEquals(Integer.valueOf(200),two.get(1).getId());
  }

  
  
  
  @Test
  public void testCreateStudent_nominal() throws StudentDuplicateException {
    Database db=new Database();
    db.createStudent(100, "Student 1");
    List<TeacherRecord> teachers=db.selectTeachers();
    assertEquals(1,teachers.size());
    assertEquals(Integer.valueOf(100),teachers.get(0).getId());
    assertEquals("Teacher 1",teachers.get(0).getName());
    assertEquals("t1@school.edu",teachers.get(0).getEmail());
    assertEquals("555-123-4567",teachers.get(0).getPhone());
  }

  @Test
  public void testCreateStudent_duplicate() throws StudentDuplicateException {
    Database db=new Database();

    db.createStudent(100, "Student 1");
    thrown.expect(StudentDuplicateException.class);

    db.createStudent(100, "Student 1");
  }

  @Test
  public void testDeleteStudent_nominal() throws StudentMissingException, StudentDuplicateException {
    Database db=new Database();
    db.createStudent(100, "Student 1");
    db.deleteStudent(100);
    List<TeacherRecord> teachers=db.selectTeachers();
    assertEquals(0,teachers.size());
    
  }

  @Test
  public void testDeleteStudent_missing() throws StudentMissingException {
    Database db=new Database();
    thrown.expect(TeacherMissingException.class);
    db.deleteStudent(100);
  }

  @Test
  public void testSelectStudent_nominal() throws StudentMissingException,  StudentDuplicateException {
    Database db=new Database();
    db.createStudent(100, "Student 1");
    StudentRecord record=db.selectStudent(100);
    assertEquals(Integer.valueOf(100),record.getId());
    
  }

  @Test
  public void testSelectStudent_missing() throws StudentMissingException {
    Database db=new Database();
    thrown.expect(StudentMissingException.class);
    db.selectStudent(100);
  }
  
  @Test
  public void testCreateCourse_nominal() throws CourseDuplicateException {
    Database db=new Database();
    db.createCourse(100, "CRS-100 A Course",10,"ROOM 1");
    List<CourseRecord> courses=db.selectCourses();
    assertEquals(1,courses.size());
    assertEquals(Integer.valueOf(100),courses.get(0).getId());
    assertEquals("CRS-100 A Course",courses.get(0).getTitle());
    assertEquals(Integer.valueOf(10),courses.get(0).getCapacity());
    assertEquals("ROOM 1",courses.get(0).getLocation());
  }

  @Test
  public void testCreateCourse_duplicate() throws CourseDuplicateException {
    Database db=new Database();
    db.createCourse(100, "CRS-100 A Course",10,"ROOM 1");
    thrown.expect(CourseDuplicateException.class);
    db.createCourse(100, "CRS-100 A Course",10,"ROOM 1");
 
  }

  @Test
  public void testDeleteCourse_nominal() throws CourseMissingException, CourseDuplicateException {
    Database db=new Database();
    db.createCourse(100, "CRS-100 A Course",10,"ROOM 1");
    db.deleteCourse(100);
    List<CourseRecord> courses=db.selectCourses();
    assertEquals(0,courses.size());
    
  }

  @Test
  public void testDeleteCourse_missing() throws CourseMissingException {
    Database db=new Database();
    thrown.expect(CourseMissingException.class);
    db.deleteCourse(100);
  }

  @Test
  public void testSelectCourse_nominal() throws CourseDuplicateException, CourseMissingException {
    Database db=new Database();
    db.createCourse(100, "CRS-100 A Course",10,"ROOM 1");
    CourseRecord record=db.selectCourse(100);
    assertEquals(Integer.valueOf(100),record.getId());
    
  }

  @Test
  public void testSelectCourse_missing() throws CourseMissingException {
    Database db=new Database();
    thrown.expect(CourseMissingException.class);
    db.selectCourse(100);
  }
  
}

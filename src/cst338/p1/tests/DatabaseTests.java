package cst338.p1.tests;

import static org.junit.Assert.*;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import cst338.p1.CourseFullException;
import cst338.p1.CourseRecord;
import cst338.p1.Database;
import cst338.p1.EntityDuplicateException;
import cst338.p1.EntityMissingException;
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
}

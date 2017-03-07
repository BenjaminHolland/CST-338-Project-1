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
  }

  @Test
  public void testSelectTeacher_missing() throws TeacherMissingException {
    Database db=new Database();
    thrown.expect(TeacherMissingException.class);
    db.selectTeacher(100);
  }

  @Test
  public void testSelectTeacherByEmail_nominal() throws TeacherMissingException {
    
  }

 

}

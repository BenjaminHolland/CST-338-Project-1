package cst338.p1.tests;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import cst338.p1.StudentDuplicateException;
import cst338.p1.StudentMissingException;
import cst338.p1.data.Database;
import cst338.p1.data.StudentRecord;
import cst338.p1.data.TeacherRecord;

public class DatabaseStudentTests {
  @Rule
  public ExpectedException thrown = ExpectedException.none();


  @Test
  public void ensureCreatedOnCreate() throws StudentDuplicateException {
    Database db = new Database();
    db.createStudent(100, "Student 1");
    List<StudentRecord> students = db.selectStudents();
    assertEquals(1, students.size());
    assertEquals(Integer.valueOf(100), students.get(0).getId());
    assertEquals("Student 1", students.get(0).getName());

  }

  @Test
  public void ensureExceptionOnCreateWithDuplicateId() throws StudentDuplicateException {
    Database db = new Database();

    db.createStudent(100, "Student 1");
    thrown.expect(StudentDuplicateException.class);

    db.createStudent(100, "Student 1");
  }

  @Test
  public void ensureDeletedOnDelete()
      throws StudentMissingException, StudentDuplicateException {
    Database db = new Database();
    db.createStudent(100, "Student 1");
    db.deleteStudent(100);
    List<TeacherRecord> teachers = db.selectTeachers();
    assertEquals(0, teachers.size());

  }

  @Test
  public void ensureExceptionOnDeleteWithMissingId() throws StudentMissingException {
    Database db = new Database();
    thrown.expect(StudentMissingException.class);
    db.deleteStudent(100);
  }

  @Test
  public void ensureCorrectOnReadSingle()
      throws StudentMissingException, StudentDuplicateException {
    Database db = new Database();
    db.createStudent(100, "Student 1");
    StudentRecord record = db.selectStudent(100);
    assertEquals(Integer.valueOf(100), record.getId());

  }

  @Test
  public void ensureExceptionOnReadSingleWithMissingId() throws StudentMissingException {
    Database db = new Database();
    thrown.expect(StudentMissingException.class);
    db.selectStudent(100);
  }

}

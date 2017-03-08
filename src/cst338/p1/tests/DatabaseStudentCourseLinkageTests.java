package cst338.p1.tests;

import static org.junit.Assert.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import cst338.p1.CourseDuplicateException;
import cst338.p1.CourseFullException;
import cst338.p1.CourseMissingException;
import cst338.p1.EnrollmentDuplicateException;
import cst338.p1.StudentDuplicateException;
import cst338.p1.StudentMissingException;
import cst338.p1.data.Database;

public class DatabaseStudentCourseLinkageTests {
  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Test
  public void ensureEnrolledOnLinked()
      throws CourseDuplicateException, StudentDuplicateException, EnrollmentDuplicateException,
      CourseMissingException, StudentMissingException, CourseFullException {
    Database db = new Database();
    db.createStudent(100, "StudeXtnt");
    db.createCourse(100, "100 - Course", 10, "ROOM 1");
    db.linkStudentCourse(100, 100);
    assertTrue(db.doesEnrollmentExist(100, 100));
  }

  @Test
  public void ensureExceptionOnEnrolledIfAlreadyEnrolled()
      throws StudentDuplicateException, CourseDuplicateException, EnrollmentDuplicateException,
      CourseMissingException, StudentMissingException, CourseFullException {
    Database db = new Database();
    db.createStudent(100, "Student");
    db.createCourse(100, "100 - Course", 10, "ROOM 1");
    db.linkStudentCourse(100, 100);
    thrown.expect(EnrollmentDuplicateException.class);
    db.linkStudentCourse(100, 100);
    fail("Should have thrown exception.");
  }

  @Test
  public void ensureExceptionOnEnrolledIfMissingStudent()
      throws CourseDuplicateException, EnrollmentDuplicateException, CourseMissingException,
      StudentMissingException, CourseFullException {
    Database db = new Database();
    db.createCourse(100, "100 - Course", 10, "ROOM 1");
    thrown.expect(StudentMissingException.class);
    db.linkStudentCourse(100, 100);
    fail("Should have thrown exception.");
  }

  @Test
  public void ensureExceptionOnEnrolledIfMissingCourse()
      throws EnrollmentDuplicateException, CourseMissingException, StudentMissingException,
      CourseFullException, StudentDuplicateException {
    Database db = new Database();
    db.createStudent(100, "Student");
    thrown.expect(CourseMissingException.class);
    db.linkStudentCourse(100, 100);
    fail("Should have thrown exception.");
  }

  @Test
  public void ensureExceptionOnEnrolledIfCourseFull()
      throws StudentDuplicateException, EnrollmentDuplicateException, CourseMissingException,
      StudentMissingException, CourseFullException, CourseDuplicateException {
    Database db = new Database();
    db.createCourse(100, "100 - Course", 10, "ROOM 1");

    for (int i = 0; i < 10; i++) {
      db.createStudent(i, "Student");
      db.linkStudentCourse(i, 100);
    }
    db.createStudent(11, "Bob");
    thrown.expect(CourseFullException.class);
    db.linkStudentCourse(11, 100);
    fail("Should have thrown exception.");
  }


}

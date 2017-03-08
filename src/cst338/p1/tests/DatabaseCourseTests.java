package cst338.p1.tests;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import cst338.p1.CourseDuplicateException;
import cst338.p1.CourseMissingException;
import cst338.p1.CourseNotEmptyException;
import cst338.p1.data.CourseRecord;
import cst338.p1.data.Database;

public class DatabaseCourseTests {
  @Rule
  public ExpectedException thrown = ExpectedException.none();
  @Test
  public void ensureCreatedOnCreate() throws CourseDuplicateException {
    Database db = new Database();
    db.createCourse(100, "CRS-100 A Course", 10, "ROOM 1");
    List<CourseRecord> courses = db.selectCourses();
    assertEquals(1, courses.size());
    assertEquals(Integer.valueOf(100), courses.get(0).getId());
    assertEquals("CRS-100 A Course", courses.get(0).getTitle());
    assertEquals(Integer.valueOf(10), courses.get(0).getCapacity());
    assertEquals("ROOM 1", courses.get(0).getLocation());
  }

  @Test
  public void ensureExceptionOnCreateWithDuplicate() throws CourseDuplicateException {
    Database db = new Database();
    db.createCourse(100, "CRS-100 A Course", 10, "ROOM 1");
    thrown.expect(CourseDuplicateException.class);
    db.createCourse(100, "CRS-100 A Course", 10, "ROOM 1");

  }

  @Test
  public void ensureDeletedOnDelete() throws CourseMissingException, CourseDuplicateException, CourseNotEmptyException {
    Database db = new Database();
    db.createCourse(100, "CRS-100 A Course", 10, "ROOM 1");
    db.deleteCourse(100);
    List<CourseRecord> courses = db.selectCourses();
    assertEquals(0, courses.size());

  }

  @Test
  public void ensureExceptionOnDeleteWithMissing() throws CourseMissingException, CourseNotEmptyException {
    Database db = new Database();
    thrown.expect(CourseMissingException.class);
    db.deleteCourse(100);
  }

  @Test
  public void ensureCorrectOnReadSingle() throws CourseDuplicateException, CourseMissingException {
    Database db = new Database();
    db.createCourse(100, "CRS-100 A Course", 10, "ROOM 1");
    CourseRecord record = db.selectCourse(100);
    assertEquals(Integer.valueOf(100), record.getId());

  }

  @Test
  public void ensureExceptionOnSelectSingleWithMissingId() throws CourseMissingException {
    Database db = new Database();
    thrown.expect(CourseMissingException.class);
    db.selectCourse(100);
  }

}

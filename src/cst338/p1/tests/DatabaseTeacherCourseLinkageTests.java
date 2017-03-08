package cst338.p1.tests;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import cst338.p1.AssignmentDuplicateException;
import cst338.p1.AssignmentMissingException;
import cst338.p1.CourseDuplicateException;
import cst338.p1.CourseFullException;
import cst338.p1.CourseMissingException;
import cst338.p1.CourseNotEmptyException;
import cst338.p1.EnrollmentDuplicateException;
import cst338.p1.EnrollmentMissingException;
import cst338.p1.StudentDuplicateException;
import cst338.p1.StudentMissingException;
import cst338.p1.TeacherDuplicateException;
import cst338.p1.TeacherMissingException;
import cst338.p1.data.AssignmentRecord;
import cst338.p1.data.Database;
import cst338.p1.data.EnrollmentRecord;
import cst338.p1.data.StudentRecord;
import cst338.p1.data.TeacherRecord;

public class DatabaseTeacherCourseLinkageTests {

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Test
  public void ensureAssignedOnLinked() throws AssignmentDuplicateException, CourseMissingException,
      TeacherMissingException, CourseDuplicateException, TeacherDuplicateException {
    Database db = new Database();
    db.createTeacher(100, "A. Teacher", "emial", "");
    db.createCourse(100, "100 - Course", 10, "ROOM 1");
    db.linkTeacherCourse(100, 100);
    assertTrue(db.doesAssignmentExist(100, 100));
  }

  @Test
  public void ensureExceptionOnAssignmentIfAlreadyAssigned()
      throws AssignmentDuplicateException, CourseMissingException, TeacherMissingException,
      CourseDuplicateException, TeacherDuplicateException {
    Database db = new Database();
    int courseId = 100;
    db.createTeacher(courseId, "Teacher", "email", "phone");
    db.createCourse(courseId, "100 - Course", 10, "ROOM 1");
    db.linkTeacherCourse(courseId, courseId);
    thrown.expect(AssignmentDuplicateException.class);
    db.linkTeacherCourse(courseId, courseId);
    fail("Should have thrown exception.");
  }

  @Test
  public void ensureExceptionOnAssignedIfMissingTeacher() throws AssignmentDuplicateException,
      CourseMissingException, TeacherMissingException, CourseDuplicateException {
    Database db = new Database();
    db.createCourse(100, "100 - Course", 10, "ROOM 1");
    thrown.expect(TeacherMissingException.class);
    db.linkTeacherCourse(100, 100);
    fail("Should have thrown exception.");
  }

  @Test
  public void ensureExceptionOnAssignedIfMissingCourse() throws TeacherDuplicateException,
      EnrollmentDuplicateException, CourseMissingException, StudentMissingException,
      CourseFullException, AssignmentDuplicateException, TeacherMissingException {
    Database db = new Database();
    db.createTeacher(100, "A", "email", "phone");
    thrown.expect(CourseMissingException.class);
    db.linkTeacherCourse(100, 100);
    fail("Should have thrown exception.");
  }

  @Test
  public void ensureUnassignedOnUnassigned() throws EnrollmentMissingException,
      CourseMissingException, StudentMissingException, EnrollmentDuplicateException,
      CourseFullException, CourseDuplicateException, StudentDuplicateException {
    Database db = new Database();
    db.createStudent(100, "Studetnt");
    db.createCourse(100, "100 - Course", 10, "ROOM 1");
    db.linkStudentCourse(100, 100);
    db.unlinkStudentCourse(100, 100);
    assertFalse(db.doesEnrollmentExist(100, 100));
  }

  @Test
  public void ensureExceptionOnUnassignedfMissingTeacher() throws AssignmentMissingException,
      CourseMissingException, TeacherMissingException, CourseDuplicateException {
    Database db = new Database();
    db.createCourse(100, "100 - Course", 10, "ROOM 1");
    thrown.expect(TeacherMissingException.class);
    db.unlinkTeacherCourse(100, 100);

    fail("Should have thrown exception.");
  }

  @Test
  public void ensureExceptionOnUnassignIfMissingCourse() throws AssignmentMissingException,
      CourseMissingException, TeacherMissingException, TeacherDuplicateException {
    Database db = new Database();
    db.createTeacher(100, "A", "email", "phone");
    thrown.expect(CourseMissingException.class);
    db.unlinkTeacherCourse(100, 101);
    fail("Should have thrown exception.");
  }

  @Test
  public void ensureExceptionOnUnassignIfNotAssigned()
      throws AssignmentMissingException, CourseMissingException, TeacherMissingException,
      TeacherDuplicateException, CourseDuplicateException {
    Database db = new Database();
    db.createCourse(100, "100 - Course", 10, "ROOM 1");
    db.createTeacher(100, "A", "email", "phone");
    thrown.expect(AssignmentMissingException.class);
    db.unlinkTeacherCourse(100, 100);
    fail("Should have thrown exception.");
  }

  @Test
  public void ensureCorrectOnCoursesForTeacherQuery()
      throws TeacherMissingException, AssignmentMissingException, CourseMissingException,
      AssignmentDuplicateException, CourseDuplicateException, TeacherDuplicateException {
    Database db = new Database();
    db.createTeacher(100, "Seth", "email", "phone");
    db.createCourse(2000, "A", 1, "Room 1");
    db.createCourse(2001, "B", 1, "Room 1");
    db.createCourse(2002, "C", 1, "Room 1");
    db.createCourse(2003, "D", 1, "Room 1");
    db.linkTeacherCourse(100, 2000);
    db.linkTeacherCourse(100, 2001);
    db.linkTeacherCourse(100, 2002);
    db.linkTeacherCourse(100, 2003);
    db.unlinkTeacherCourse(100, 2000);
    db.unlinkTeacherCourse(100, 2001);
    db.linkTeacherCourse(100, 2000);
    List<AssignmentRecord> assignments = db.selectTeacherCourses(100);
    Map<Integer, Boolean> expectedRecords = new HashMap<>();
    expectedRecords.put(2000, false);
    expectedRecords.put(2002, false);
    expectedRecords.put(2003, false);
    for (AssignmentRecord assignment : assignments) {
      if (expectedRecords.containsKey(assignment.getCourseId())) {
        expectedRecords.put(assignment.getCourseId(), true);
      } else {
        fail("Unexpected enrollment detected.");
      }
    }
    for (Entry<Integer, Boolean> entry : expectedRecords.entrySet()) {
      if (!entry.getValue()) {
        fail("Missing assignment for class " + entry.getKey());
      }
    }
  }

  @Test
  public void ensureCorrectOnTeachersForCourseQuery()
      throws CourseMissingException, AssignmentDuplicateException, TeacherMissingException,
      AssignmentMissingException, CourseDuplicateException, TeacherDuplicateException {
    Database db = new Database();
    db.createTeacher(100, "Seth", "email", "phone");
    db.createTeacher(101, "Champ", "email", "phone");
    db.createTeacher(102, "Glah", "email", "phone");
    db.createCourse(2000, "A", 3, "Room 1");
    db.linkTeacherCourse(100, 2000);
    db.linkTeacherCourse(101, 2000);
    db.linkTeacherCourse(102, 2000);
    db.linkTeacherCourse(101, 2000);
    List<TeacherRecord> teachers = db.selectCourseTeachers(2000);
    assertEquals(Integer.valueOf(101), teachers.get(0).getId());
  }
}

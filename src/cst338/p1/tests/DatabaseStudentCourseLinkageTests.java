package cst338.p1.tests;

import static org.junit.Assert.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import cst338.p1.CourseDuplicateException;
import cst338.p1.CourseFullException;
import cst338.p1.CourseMissingException;
import cst338.p1.CourseNotEmptyException;
import cst338.p1.EnrollmentDuplicateException;
import cst338.p1.EnrollmentMissingException;
import cst338.p1.StudentDuplicateException;
import cst338.p1.StudentMissingException;
import cst338.p1.data.Database;
import cst338.p1.data.EnrollmentRecord;
import cst338.p1.data.StudentRecord;

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
  
  @Test public void ensureUnenrolledOnUnenroll() throws StudentDuplicateException, CourseDuplicateException, EnrollmentDuplicateException, CourseMissingException, StudentMissingException, CourseFullException, EnrollmentMissingException{
    Database db = new Database();
    db.createStudent(100, "Studetnt");
    db.createCourse(100, "100 - Course", 10, "ROOM 1");
    db.linkStudentCourse(100, 100);
    db.unlinkStudentCourse(100,100);
    assertFalse(db.doesEnrollmentExist(100, 100));
  }
  
  @Test public void ensureExceptionOnUnenrollIfMissingStudent() throws CourseDuplicateException, EnrollmentDuplicateException, CourseMissingException, StudentMissingException, CourseFullException, EnrollmentMissingException, StudentDuplicateException{
    Database db = new Database();
    db.createCourse(100, "100 - Course", 10, "ROOM 1");
    db.createStudent(100, "Student");
    db.linkStudentCourse(100, 100);
    db.deleteStudent(100);
    thrown.expect(StudentMissingException.class);
    db.unlinkStudentCourse(100,100);

    fail("Should have thrown exception.");
  }
  
  @Test public void ensureExceptionOnUnenrollIfMissingCourse() throws CourseMissingException, CourseNotEmptyException, EnrollmentDuplicateException, StudentMissingException, CourseFullException, StudentDuplicateException, CourseDuplicateException, EnrollmentMissingException{
    Database db = new Database();
    db.createStudent(100, "Student");
    thrown.expect(CourseMissingException.class);
    db.unlinkStudentCourse(100,101);
    fail("Should have thrown exception.");
  }
  
  @Test public void ensureExceptionOnUnenrollIfNotEnrolled() throws EnrollmentMissingException, CourseMissingException, StudentMissingException, StudentDuplicateException, CourseDuplicateException{
    Database db = new Database();
    db.createCourse(100, "100 - Course", 10, "ROOM 1");
    db.createStudent(100, "Student");
    thrown.expect(EnrollmentMissingException.class);
    db.unlinkStudentCourse(100,100);
    fail("Should have thrown exception.");
  }
  
  @Test public void ensureCorrectOnCoursesForStudentQuery() throws CourseDuplicateException, StudentDuplicateException, EnrollmentDuplicateException, CourseMissingException, StudentMissingException, CourseFullException, EnrollmentMissingException{
    Database db=new Database();
    db.createStudent(100,"Seth");
    db.createCourse(2000, "A", 1,"Room 1");
    db.createCourse(2001, "B", 1,"Room 1");
    db.createCourse(2002, "C", 1,"Room 1");
    db.createCourse(2003, "D", 1,"Room 1");
    db.linkStudentCourse(100,2000);
    db.linkStudentCourse(100,2001);
    db.linkStudentCourse(100,2002);
    db.linkStudentCourse(100,2003);
    db.unlinkStudentCourse(100,2000);
    db.unlinkStudentCourse(100,2001);
    db.linkStudentCourse(100,2000);
    List<EnrollmentRecord> enrollments=db.selectStudentCourses(100);
    Map<Integer,Boolean> expectedRecords=new HashMap<>();
    expectedRecords.put(2000,false);
    expectedRecords.put(2002,false);
    expectedRecords.put(2003,false);
    for(EnrollmentRecord enrollment:enrollments){
      if(expectedRecords.containsKey(enrollment.getCourseId())){
        expectedRecords.put(enrollment.getCourseId(),true);
      }else{
        fail("Unexpected enrollment detected.");
      }
    }
    for(Entry<Integer,Boolean> entry:expectedRecords.entrySet()){
      if(!entry.getValue()){
        fail("Missing enrollment for class "+entry.getKey());
      }
    }
  }
  @Test public void ensureCorrectOnStudentsForCourseQuery() throws CourseMissingException, EnrollmentDuplicateException, StudentMissingException, CourseFullException, EnrollmentMissingException, CourseDuplicateException, StudentDuplicateException{
    Database db=new Database();
    db.createStudent(100,"Seth");
    db.createStudent(101,"Champ");
    db.createStudent(102,"Glah");
    db.createCourse(2000, "A", 3,"Room 1");
    db.linkStudentCourse(100,2000);
    db.linkStudentCourse(101,2000);
    db.linkStudentCourse(102,2000);
    db.unlinkStudentCourse(102,2000);
    db.unlinkStudentCourse(101, 2000);
    db.linkStudentCourse(101, 2000);
    db.selectCourseStudents(2000);
    List<StudentRecord> students=db.selectCourseStudents(2000);
    Map<Integer,Boolean> expectedRecords=new HashMap<>();
    expectedRecords.put(100,false);
    expectedRecords.put(101,false);
    for(StudentRecord student:students){
      if(expectedRecords.containsKey(student.getId())){
        expectedRecords.put(student.getId(),true);
      }
      else{
        fail("Unexpected enrollment.");
      }
    }
    for(Entry<Integer,Boolean> entry:expectedRecords.entrySet()){
      if(!entry.getValue()){
        fail("Missing class for student "+entry.getKey());
      }
    }
  }
}

package cst338.p1.tests;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import cst338.p1.TeacherDuplicateException;
import cst338.p1.TeacherMissingException;
import cst338.p1.data.Database;
import cst338.p1.data.TeacherRecord;


public class DatabaseTeacherTests {
  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Test
  public void ensureCreatedOnCreate() throws TeacherDuplicateException{
    Database db=new Database();
    db.createTeacher(100, "A. Teacher", "ateacher@school.edu", "555-000-0000");
    assertTrue(db.doesTeacherExist(100));
  }
 
  @Test
  public void ensureExceptionOnCreateWithDuplicateId() throws TeacherDuplicateException{
    Database db=new Database();
    db.createTeacher(100, "A. Teacher", "ateacher@school.edu", "555-000-0000");
    thrown.expect(TeacherDuplicateException.class);
    db.createTeacher(100, "A. Teacher", "ateacher@school.edu", "555-000-0000");
    fail("Should have throw exception.");
  }
  @Test
  public void ensureDeletedOnDelete() throws TeacherDuplicateException, TeacherMissingException{
    Database db=new Database();
    db.createTeacher(100,"A. Teacher", "ateacher@school.edu", "555-000-0000");
    db.deleteTeacher(100);
    assertFalse(db.doesTeacherExist(100));
  }
  @Test
  public void ensureExceptionOnDeleteWithMissingId() throws TeacherMissingException{
    Database db=new Database();
    thrown.expect(TeacherMissingException.class);
    db.deleteTeacher(100);
    fail("Should have thrown exception.");
  }
  
  @Test
  public void ensureAllReturnedOnReadAll() throws TeacherDuplicateException{
    Database db=new Database();
    for(int i=0;i<10;i++){
      db.createTeacher(i, "A. Teacher", "ateacher@school.edu","555-000-0000"); 
    }
    List<TeacherRecord> records=db.selectTeachers();
    assertEquals(10,records.size());
  }
  @Test
  public void ensureCorrectReturnedOnReadSingle() throws TeacherDuplicateException, TeacherMissingException{
    Database db=new Database();
    db.createTeacher(100,"A. Teacher","ateacher@school.edu","555-000-0000");
    TeacherRecord record=db.selectTeacher(100);
    assertEquals(Integer.valueOf(100),record.getId());
  }
  @Test
  public void ensureExceptionOnReadWithMissingId() throws TeacherMissingException{
    Database db=new Database();
    thrown.expect(TeacherMissingException.class);
    TeacherRecord recor=db.selectTeacher(100);
    fail("Should have thrown an exception.");
  }
}

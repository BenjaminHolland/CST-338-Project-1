package cst338.p1.tests;

import static org.junit.Assert.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import cst338.p1.TeacherDuplicateException;
import cst338.p1.data.Database;


public class DatabaseTeacherTests {
  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Test
  public void ensureCreatedOnCreate() throws TeacherDuplicateException{
    Database db=new Database();
    db.createTeacher(100, "A. Teacher", "ateacher@school.edu", "555-000-0000");
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
  public void ensureDeletedOnDelete(){
    
  }
  @Test
  public void ensureExceptionOnDeleteWithMissingId(){
    
  }
  
  @Test
  public void ensureAllReturnedOnReadAll(){
    
  }
  @Test
  public void ensureCorrectReturnedOnReadSingle(){
    
  }
  @Test
  public void ensureExceptionOnReadWithMissingId(){
    
  }

}

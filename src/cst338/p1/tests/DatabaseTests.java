package cst338.p1.tests;

import static org.junit.Assert.*;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import cst338.p1.CourseRecord;
import cst338.p1.Database;
import cst338.p1.EntityDuplicateException;
import cst338.p1.StudentRecord;
import cst338.p1.TeacherRecord;

public class DatabaseTests {

  @Test
  public void createStudent_nominal() throws EntityDuplicateException {
    Database db=new Database();
    db.createStudent(new StudentRecord(1000,"Bob Joe"));
    List<StudentRecord> records=db.getStudentStream().collect(Collectors.toList());
    assertEquals(1, records.size());
    assertEquals(Integer.valueOf(1000), records.get(0).getId());
    assertEquals("Bob Joe", records.get(0).getName());
  }
  @Test
  public void createStudent_duplicate() {
    fail("Not yet implemented");
  }
  @Test
  public void createTeacher_nominal() throws EntityDuplicateException {
    Database db=new Database();
    db.createTeacher(new TeacherRecord(1000,"Hop Slammin","hslammin@yas.edu","123-45-6789"));
    List<TeacherRecord> records=db.getTeacherStream().collect(Collectors.toList());
    assertEquals(1, records.size());
    assertEquals(Integer.valueOf(1000), records.get(0).getId());
    assertEquals("Hop Slammin", records.get(0).getName());
    assertEquals("hslammin@yas.edu", records.get(0).getEmail());
    assertEquals("123-45-6789",records.get(0).getSsn());
  }
  @Test
  public void createTeacher_duplicateId() {
    fail("Not yet implemented");
  }
  @Test
  public void createTeacher_duplicateEmail() {
    fail("Not yet implemented");
  }
  @Test
  public void createCourse_nominal() throws EntityDuplicateException {
    Database db=new Database();
    db.createCourse(new CourseRecord(1000,"BOB-1000 Introduction to Derp",30,"LOL-50"));
    List<CourseRecord> records=db.getCourseStream().collect(Collectors.toList());
    assertEquals(1, records.size());
    assertEquals(Integer.valueOf(1000), records.get(0).getId());
    assertEquals("BOB-1000 Introduction to Derp", records.get(0).getTitle());
    assertEquals(Integer.valueOf(30), records.get(0).getCapacity());
    assertEquals("LOL-50",records.get(0).getLocation());
  }
  @Test
  public void createCourse_duplicate() {
    fail("Not yet implemented");
  }
  @Test
  public void linkStudentCourse_nominal(){
   fail("Not yet implemented."); 
  }
  @Test
  public void linkStudentCourse_duplicate(){
   fail("Not yet implemented."); 
  }
  @Test
  public void linkStudentCourse_missingStudent(){
   fail("Not yet implemented."); 
  }
  
  @Test
  public void linkStudentCourse_missingCourse(){
   fail("Not yet implemented."); 
  }
  @Test
  public void linkTeacherCourse_nominal(){
   fail("Not yet implemented."); 
  }
  @Test
  public void linkTeacherCourse_duplicate(){
   fail("Not yet implemented."); 
  }
  @Test
  public void linkTeacherCourse_missingTeacher(){
   fail("Not yet implemented."); 
  }
  
  @Test
  public void linkTeacherCourse_missingCourse(){
   fail("Not yet implemented."); 
  }

  
  @Test
  public void unlinkStudentCourse_nominal(){
   fail("Not yet implemented."); 
  }
  @Test
  public void unlinkStudentCourse_missing(){
   fail("Not yet implemented."); 
  }
  @Test
  public void unlinkStudentCourse_missingStudent(){
   fail("Not yet implemented."); 
  }
  
  @Test
  public void unlinkStudentCourse_missingCourse(){
   fail("Not yet implemented."); 
  }
  @Test
  public void unlinkTeacherCourse_nominal(){
   fail("Not yet implemented."); 
  }
  @Test
  public void unlinkTeacherCourse_missing(){
   fail("Not yet implemented."); 
  }
  @Test
  public void unlinkTeacherCourse_missingTeacher(){
   fail("Not yet implemented."); 
  }
  
  @Test
  public void unlinkTeacherCourse_missingCourse(){
   fail("Not yet implemented."); 
  }
}

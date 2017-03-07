package cst338.p1.tests;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import cst338.p1.Course;
import cst338.p1.School;

public class DefinedTest2 {

  @Test
  public void test() throws IOException {
    File dataFile=new File("./data/sample_test1.txt");
    School SCD = new School("SCD");
    Course course1;
    System.out.println("===== Read Data =====");
    SCD.readData(dataFile.getCanonicalPath());
    SCD.addInstructor(700, "E. Tao", "tao@csumb.edu", "777-777-1234");
    SCD.addCourse(300, "CST300 – ProSem", 70, "BIT 110");
    SCD.addCourse(499, "CST499 – iOS Dev", 15, "BIT 104");
    SCD.assignInstructor (205, 200);
    SCD.assignInstructor (306, 100);
    SCD.register (306, 7777);
    SCD.register (306, 8888);
    SCD.putScore (306, 7777, 98.54);
    SCD.unRegister (306, 8888);
    System.out.println("\n===== Error Messages =====");
    SCD.addCourse(306, "CST306 – GUI Dev", 25, "BIT 120");
    SCD.putScore (306, 8888, 58.75);
    SCD.register (306, 9999);
    SCD.assignInstructor (499, 900);
    System.out.println("\n===== Detailed Course Info =====");
    SCD.courseInfo(306);
    course1 = SCD.getCourse(205);
    course1.updateLocation("Library 104");
    System.out.println("\n===== Detailed Course Info 2 =====");
    SCD.courseInfo(205);
    System.out.println("\n===== Detailed Course Info 3 =====");
    SCD.courseInfo();
    SCD.deleteCourse(306);
    SCD.deleteCourse(338);
    System.out.println("\n===== Detailed Course Info 4 =====");
    SCD.courseInfo();
    System.out.println("\n===== Good Job! Bye! =====");
  }

}

package cst338.p1.tests;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.junit.Test;
import org.omg.CORBA.Environment;

import cst338.p1.School;

public class SchoolTests {

  @Test
  public void testSchoolDataRead__sample_test1() throws IOException {
    File inputFile=new File("./data/sample_test1.txt");
    School csumb=new School("CSUMB");
    csumb.readData(inputFile.getCanonicalPath());
    csumb.schoolInfo();
  }
  
  @Test
  public void testSchoolDataRead__sample_test2() throws IOException {
    File inputFile=new File("./data/sample_test2.txt");
    School csumb=new School("CSUMB");
    csumb.readData(inputFile.getCanonicalPath());
    csumb.schoolInfo();
  }

}

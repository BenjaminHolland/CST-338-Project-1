package cst338.p1.tests;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import cst338.p1.School;

public class DefinedTest1 {

  //TODO: Fix spacing anomonly after recieving instructions from instructor.
  @Test
  public void run() throws IOException {
    File data1=new File("./data/sample_test1.txt");
    File data2=new File("./data/sample_test2.txt");
    
    School SCD = new School("SCD");
    System.out.println("\n===== Read Data 1 =====");
    SCD.readData(data1.getCanonicalPath().toString());
    System.out.println("\n===== School Info 1 =====");
    SCD.schoolInfo();
    System.out.println("\n===== Read Data 2 =====");
    SCD.readData(data2.getCanonicalPath().toString());
    System.out.println("\n===== School Info 2 =====");
    SCD.schoolInfo();
    System.out.println("\n===== Search by email =====");
    SCD.searchByEmail("ybyun@csumb.edu");
    System.out.println("\n===== Search by email (fail) =====");
    SCD.searchByEmail("byun@csumb.edu");
    System.out.println("\n===== End of SchoolDemo1 =====");
  }

}

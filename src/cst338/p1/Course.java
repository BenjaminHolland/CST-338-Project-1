package cst338.p1;
/*
 * Title: Project 1
 * Abstract: Educational Database System. 
 * Author: Benjamin Holland.
 * ID: 4338
 * Creation Date: 3/1/2017;
 */
import cst338.p1.data.Database;

public class Course {
  private Database context;
  private Integer id;
  public Course(Database context,Integer id){
    this.context=context;
    this.id=id;
  }
  public void updateLocation(String newLocation){
    try{
      context.selectCourse(id).setLocation(newLocation);
    }catch(CourseMissingException ex){
      //TODO:FIX THIS ERROR
      throw new RuntimeException("TEMPORARY ERROR STATE: MISSING COURSE",ex);
    }
  }
}

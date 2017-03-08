package cst338.p1;

import cst338.p1.data.Database;

public class Student {
  private final Database context;
  private final Integer id;
  Student(Database context,Integer id){
    this.context=context;
    this.id=id;
  }
}

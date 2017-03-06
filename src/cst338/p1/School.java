package cst338.p1;

import java.io.IOException;

public class School {
  private final Database database;
  private final String name;
  public String getName(){
    return name;
  }
  public School(String name){
    this.name=name;
    this.database=new Database();
  }
  public void readData(String path){
      try {
        database.importData(path);
      } catch (IOException e) {
        System.out.println("Error importing data from \""+path+"\"");
        e.printStackTrace();
      }
  }
}

package cst338.p1;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cst338.p1.data.AssignmentRecord;
import cst338.p1.data.CourseRecord;
import cst338.p1.data.Database;
import cst338.p1.data.StudentRecord;
import cst338.p1.data.TeacherRecord;

public class Instructor {
  private final Database context;
  private final Integer id;

  Instructor(Database context, Integer id) {
    this.context = context;
    this.id = id;
  }

  @Override
  public String toString() {
    try {
      TeacherRecord record = context.selectTeacher(id);
      List<AssignmentRecord> teaching = context.selectTeacherCourses(id);


      StringBuilder bldr = new StringBuilder();
      bldr.append("Instructor Number: " + id + "\n");
      bldr.append("Name: " + record.getName() + "\n");
      bldr.append("Courses Teaching:\n");

      for (AssignmentRecord assignment : teaching) {
        try {
          CourseRecord course = context.selectCourse(assignment.getCourseId());
          List<StudentRecord> enrolled = context.selectCourseStudents(course.getId());
          bldr.append("\t" + course.getId() + ": " + enrolled.size() + " enrolled\n");
        } catch (CourseMissingException e) {
          throw new RuntimeException("Assertion Failure: Course both does and does not exist.", e);
        }
      }
      return bldr.toString();
    } catch (TeacherMissingException e) {
      throw new RuntimeException(
          "UNDEFINED BEHAVIOR: Teacher info queried after teacher removed from database.", e);
    }

  }


}

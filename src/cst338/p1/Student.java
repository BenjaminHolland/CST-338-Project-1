package cst338.p1;

import java.util.List;
import java.util.stream.Collectors;

public class Student {
  private final Database context;
  private final Integer id;

  Student(Database context, Integer id) {
    this.context = context;
    this.id = id;
  }

  public Integer getId() {
    return id;
  }

  public Boolean exists() {
    try {
      context.selectStudent(id);
    } catch (StudentMissingException ex) {
      return false;
    }
    return true;
  }

  public String getName() throws StudentMissingException {
    return context.selectStudent(id).getName();
  }

  public List<Course> getCourses() throws StudentMissingException{
    return context.selectStudentCourses(id).stream().map(entry->new Course(context,id,entry.getCourseId())).collect(Collectors.toList());
  }
  
  public Course getCourse(Integer courseId) throws EnrollmentMissingException, CourseMissingException, StudentMissingException{  
    context.selectStudentCourse(id, courseId); //Forces checks for enrollment. Not very nice, but works. 
    return new Course(context,id,courseId);
  }
}

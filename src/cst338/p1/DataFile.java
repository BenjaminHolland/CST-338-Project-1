package cst338.p1;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.stream.Stream;

/***
 * Represents a File with educational database records in it. 
 * @author Benjamin
 *
 */
public class DataFile {
  private static CourseRecord parseClassRecord(String classRecordLine) {
    StringTokenizer tokenizer = new StringTokenizer(classRecordLine, ",");

    Integer id = Integer.parseUnsignedInt(tokenizer.nextToken());
    String title = tokenizer.nextToken();
    Integer capacity = Integer.parseUnsignedInt(tokenizer.nextToken());
    String location = tokenizer.nextToken();
    return new CourseRecord(id, title, capacity, location);
  }

  private static StudentRecord parseStudentRecord(String studentRecordLine) {
    StringTokenizer tokenizer = new StringTokenizer(studentRecordLine, ",");

    Integer id = Integer.parseUnsignedInt(tokenizer.nextToken());
    String name = tokenizer.nextToken();
    return new StudentRecord(id, name);
  }

  private static TeacherRecord parseTeacherRecord(String teacherRecordLine) {
    StringTokenizer tokenizer = new StringTokenizer(teacherRecordLine, ",");
    Integer id = Integer.parseUnsignedInt(tokenizer.nextToken());
    String name = tokenizer.nextToken();
    String email = tokenizer.nextToken();
    String phone = tokenizer.nextToken();
    return new TeacherRecord(id, name, email, phone);
  }
  /**
   * Loads data from a file into memory. 
   * @param path The path with the record data.
   * @return A DataFile object that contains the data in the file.
   * @throws IOException If there was an error reading the file. 
   * @note This is mainly to prevent exceptions from being thrown from a constructor, which I'm not particularly fond of. 
   */
  public static DataFile load(String path) throws IOException {
    File fileStream = new File(path);
    Scanner fileScanner = new Scanner(fileStream);
    DataFile fileData = new DataFile();

    parseTeacherSection(fileScanner, fileData);

    parseClassSection(fileScanner, fileData);

    parseStudentSection(fileScanner, fileData);

    fileScanner.close();

    return fileData;

  }

  private static void parseTeacherSection(Scanner fileScanner, DataFile fileData) {
    String countLine;
    Integer count;
    countLine = fileScanner.nextLine();
    count = Integer.parseUnsignedInt(countLine);
    for (int idx = 0; idx < count; idx++) {
      TeacherRecord curTeacher = parseTeacherRecord(fileScanner.nextLine());
      fileData.teachers.add(curTeacher);
    }
  }

  private static void parseClassSection(Scanner fileScanner, DataFile fileData) {
    String countLine;
    Integer count;
    countLine = fileScanner.nextLine();
    count = Integer.parseUnsignedInt(countLine);
    for (int idx = 0; idx < count; idx++) {
      CourseRecord curClass = parseClassRecord(fileScanner.nextLine());
      fileData.classes.add(curClass);
    }
  }

  private static void parseStudentSection(Scanner fileScanner, DataFile fileData) {
    String countLine;
    Integer count;
    countLine = fileScanner.nextLine();
    count = Integer.parseUnsignedInt(countLine);
    for (int idx = 0; idx < count; idx++) {
      StudentRecord curStudent = parseStudentRecord(fileScanner.nextLine());
      fileData.students.add(curStudent);
    }
  }

  private final List<CourseRecord> classes;
  private final List<StudentRecord> students;
  private final List<TeacherRecord> teachers;

  private DataFile() {
    classes = new ArrayList<>();
    students = new ArrayList<>();
    teachers = new ArrayList<>();
  }

  /**
   * Classes
   * @return A stream of Course Records. 
   */
  public Stream<CourseRecord> getClassStream() {
    return classes.stream();
  }

  /**
   * Students.
   * @return A stream of student records. 
   */
  public Stream<StudentRecord> getStudentStream() {
    return students.stream();
  }

  /**
   * Teachers
   * @return a stream of teachers. 
   */
  public Stream<TeacherRecord> getTeacherStream() {
    return teachers.stream();
  }

}

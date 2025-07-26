import java.util.*;
import java.io.*;

public class StudentManagementSystem {
    private List<Student> students;
    private static final String FILE_NAME = "students.dat";

    public StudentManagementSystem() {
        students = loadFromFile();
    }

    public void addStudent(Student student) {
        if (student.getName().isEmpty() || student.getRollNumber().isEmpty() || student.getGrade().isEmpty()) {
            System.out.println("All fields are required.");
            return;
        }
        students.add(student);
        saveToFile();
    }

    public void removeStudent(String rollNumber) {
        students.removeIf(s -> s.getRollNumber().equals(rollNumber));
        saveToFile();
    }

    public Student searchStudent(String rollNumber) {
        for (Student s : students) {
            if (s.getRollNumber().equals(rollNumber)) {
                return s;
            }
        }
        return null;
    }

    public void displayAllStudents() {
        if (students.isEmpty()) {
            System.out.println("No students available.");
        } else {
            for (Student s : students) {
                System.out.println(s);
            }
        }
    }

    public void editStudent(String rollNumber, String newName, String newGrade) {
        Student s = searchStudent(rollNumber);
        if (s != null) {
            if (!newName.isEmpty()) s.setName(newName);
            if (!newGrade.isEmpty()) s.setGrade(newGrade);
            saveToFile();
        } else {
            System.out.println("Student not found.");
        }
    }

    private void saveToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(students);
        } catch (IOException e) {
            System.out.println("Error saving data.");
        }
    }

    private List<Student> loadFromFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            return (List<Student>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return new ArrayList<>();
        }
    }
}
import java.util.ArrayList;
import java.util.Scanner;

public class StudentGradeTracker {
    static class Student {
        String name;
        double grade;

        Student(String name, double grade) {
            this.name = name;
            this.grade = grade;
        }
    }

    public static void main(String[] args) {
        ArrayList<Student> students = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== Student Grade Manager ===");

        System.out.print("Enter the number of students: ");
        int count = scanner.nextInt();
        scanner.nextLine();

        for (int i = 0; i < count; i++) {
            System.out.print("\nEnter name of student " + (i + 1) + ": ");
            String name = scanner.nextLine();

            System.out.print("Enter grade for " + name + ": ");
            double grade = scanner.nextDouble();
            scanner.nextLine();

            students.add(new Student(name, grade));
        }

        double total = 0;
        double highest = Double.MIN_VALUE;
        double lowest = Double.MAX_VALUE;

        for (Student student : students) {
            total += student.grade;
            if (student.grade > highest) highest = student.grade;
            if (student.grade < lowest) lowest = student.grade;
        }

        double average = total / students.size();

        System.out.println("\n=== Summary Report ===");
        for (Student student : students) {
            System.out.println("Name: " + student.name + " | Grade: " + student.grade);
        }

        System.out.println("\nAverage Score: " + average);
        System.out.println("Highest Score: " + highest);
        System.out.println("Lowest Score: " + lowest);

        scanner.close();
    }
}

// STUDENT GRADE CALCULATOR

import java.util.Scanner;
import java.util.SortedMap;

public class project01 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("enter a number of students : ");
        int numstudent = scanner.nextInt();

        for(int i = 1; i<= numstudent; i++) {
            System.out.print("enter name of student: ");

            String name = scanner.next();
            scanner.nextLine();
            System.out.print("enter student marks (out of 100): ");





            int marks = scanner.nextInt();

            String grade;

            if (marks >= 90) {
                grade = "A";

            } else if (marks >= 80) {
                grade = "B";

            } else if (marks >= 70) {
                grade ="C+";


            } else if (marks >= 60) {
                grade = "C";

            } else if (marks >= 33) {
                grade = "pass";
            } else {
                grade = "fail";

            }
            System.out.println("Enter a name " + name);
            System.out.println("Enter a marks "+ marks);
            System.out.println("Enter a grade"+ grade);

            if(grade.equals("A") || grade.equals("B")){
                System.out.println("STUDENT PERFORM WELL");

            } else if(grade.equals("C")) {
                System.out.println("GOOD ");


            } else if (grade.equals("just pass")) {
                System.out.println("STUDENT NEED INPROVEMENT");

            }
            else {
                System.out.println("STUDENT IS FAIL");
            }
            System.out.println();



        }
        scanner.close();
    }
}

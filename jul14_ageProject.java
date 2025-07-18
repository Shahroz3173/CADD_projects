import java.util.Scanner;

public class jul14_ageProject {


        public static void main(String[] args) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("ENTER A NUMBER OF candidate :");
            int numCandidates = scanner.nextInt();

            for (int i = 0; i < numCandidates; i++) {

                System.out.print("ENTER A NAME OF CANDIDATE :");
                String name = scanner.next();

                System.out.println("ENTER CANDIDATE'S AGE :");
                int age = scanner.nextInt();

                if (age < 18) {
                    System.out.println("THIS CANDIDATE MINOR. ");
                }
                else if (age >= 18 && age < 21) {
                    System.out.println("THIS CANDIDATE HAVE TO COMPLETE DEGREE. ");
                } else if (age >= 21 && age <24) {
                    System.out.println("THIS CANDIDATE HAVE TO TIME FOR PPREPARATION FOR JOB. ");
                } else if (age >= 24 && age <=60) {
                    System.out.println("THIS CANDIDATE IS ELIGIBLE FOR JOB ");

                } else if (age > 60) {
                    System.out.println(" NOW THIS CANDIDATE HAVE TO RETIRED FROM JOB. ");
                }

                System.out.println("CANDIDATE NAME    : " + name );
                System.out.println("AGE               : " + age );
                //System.out.println("GRAD            : " + grade);
            }

            System.out.println("THANK YOU");

        }

}

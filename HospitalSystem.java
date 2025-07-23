import java.util.ArrayList;
import java.util.Scanner;

class Patient {
    String name;
    int age;
    String disease;
    String admitDate;
    String dischargeDate;
    boolean isAdmitted;

    Patient(String name, int age, String disease) {
        this.name = name;
        this.age = age;
        this.disease = disease;
        this.isAdmitted = true;
    }

    void admit(String date) {
        this.admitDate = date;
        this.isAdmitted = true;
    }

    void discharge(String date) {
        this.dischargeDate = date;
        this.isAdmitted = false;
    }

    void display() {
        System.out.println("\n--- Patient Info ---");
        System.out.println("Name   : " + name);
        System.out.println("Age    : " + age);
        System.out.println("Disease: " + disease);
        System.out.println("Status : " + (isAdmitted ? "Admitted" : "Discharged"));

        if (isAdmitted) {
            System.out.println("Admit Date    : " + admitDate);
        } else {
            System.out.println("Discharge Date: " + dischargeDate);
        }
    }
}

public class HospitalSystem {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<Patient> patients = new ArrayList<>();

        while (true) {
            System.out.println("\n=== Hospital Menu ===");
            System.out.println("1. Admit Patient");
            System.out.println("2. Discharge Patient");
            System.out.println("3. Show All Patients");
            System.out.println("4. Exit");
            System.out.print("Enter choice: ");
            int ch = sc.nextInt();
            sc.nextLine(); // clear buffer

            if (ch == 1) {
                System.out.print("Enter patient name: ");
                String name = sc.nextLine();
                System.out.print("Enter age: ");
                int age = sc.nextInt();
                sc.nextLine();
                System.out.print("Enter disease: ");
                String disease = sc.nextLine();
                System.out.print("Enter admit date: ");
                String admitDate = sc.nextLine();

                Patient p = new Patient(name, age, disease);
                p.admit(admitDate);
                patients.add(p);
                System.out.println("Patient admitted successfully.");

            } else if (ch == 2) {
                System.out.print("Enter patient name to discharge: ");
                String name = sc.nextLine();
                boolean found = false;
                for (Patient p : patients) {
                    if (p.name.equalsIgnoreCase(name) && p.isAdmitted) {
                        System.out.print("Enter discharge date: ");
                        String dischargeDate = sc.nextLine();
                        p.discharge(dischargeDate);
                        System.out.println("Patient discharged.");
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    System.out.println("Patient not found or already discharged.");
                }

            } else if (ch == 3) {
                if (patients.isEmpty()) {
                    System.out.println("No patient records available.");
                } else {
                    for (Patient p : patients) {
                        p.display();
                    }
                }

            } else if (ch == 4) {
                System.out.println("Exiting...");
                break;
            } else {
                System.out.println("Invalid choice.");
            }
        }

        sc.close();
    }
}

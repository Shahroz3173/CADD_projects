import java.sql.*;
import java.util.Scanner;

public class aug05_JDBC_registration {

    static final String DB_URL = "jdbc:mysql://localhost:3306/online_course_registration_system";
    static final String DB_USER = "root";
    static final String DB_PASS = "Shahroz@3173";

    static Connection con;

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            System.out.println("Connected to database successfully.");

            while (true) {
                System.out.println("\n1. Register\n2. Login\n3. Exit");
                System.out.print("Choose: ");
                int choice = sc.nextInt();
                sc.nextLine();

                switch (choice) {
                    case 1:
                        register(sc);
                        break;
                    case 2:
                        login(sc);
                        break;
                    case 3:
                        System.out.println("Goodbye!");
                        return;
                    default:
                        System.out.println("Invalid choice.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void register(Scanner sc) throws SQLException {
        System.out.print("Enter your name: ");
        String name = sc.nextLine();
        System.out.print("Enter your email: ");
        String email = sc.nextLine();

        String sql = "INSERT INTO students (student_name, student_email) VALUES (?, ?)";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, name);
        ps.setString(2, email);
        ps.executeUpdate();
        System.out.println("Registration successful.");
    }

    static void login(Scanner sc) throws SQLException {
        System.out.print("Enter your email: ");
        String email = sc.nextLine();

        String sql = "SELECT * FROM students WHERE student_email = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, email);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            int studentId = rs.getInt("student_id");
            System.out.println("Login successful. Welcome, " + rs.getString("student_name") + "!");
            userMenu(sc, studentId);
        } else {
            System.out.println("User not found.");
        }
    }

    static void userMenu(Scanner sc, int studentId) throws SQLException {
        while (true) {
            System.out.println("\n1. View My Courses\n2. Add Course\n3. Logout");
            System.out.print("Choose: ");
            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    viewMyCourses(studentId);
                    break;
                case 2:
                    addCourseToStudent(sc, studentId);
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    static void viewMyCourses(int studentId) throws SQLException {
        String sql = "SELECT courses.course_name, courses.course_description FROM courses INNER JOIN enrollments ON "
                + "courses.course_id = enrollments.course_id "
                + "WHERE enrollments.student_id = ?;";

        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, studentId);
        ResultSet rs = ps.executeQuery();

        System.out.println("\nYour Courses:");
        while (rs.next()) {
            System.out.println("- " + rs.getString("course_name") + ": " + rs.getString("course_description"));
        }
    }

    static void addCourseToStudent(Scanner sc, int studentId) throws SQLException {
        while (true) {
            System.out.println("\nAvailable Courses:");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM courses");
            while (rs.next()) {
                System.out.println(rs.getInt("course_id") + ". " + rs.getString("course_name"));
            }
            System.out.println("0. Back");

            System.out.print("Enter Course ID to enroll (or 0 to go back): ");
            int courseId = sc.nextInt();

            if (courseId == 0) {
                System.out.println("Returning to previous menu...");
                return;
            }


            String checkCourseSql = "SELECT * FROM courses WHERE course_id = ?";
            PreparedStatement checkStmt = con.prepareStatement(checkCourseSql);
            checkStmt.setInt(1, courseId);
            ResultSet checkRs = checkStmt.executeQuery();

            if (!checkRs.next()) {
                System.out.println("Invalid Course ID. Please try again.");
                continue;
            }


            String sql = "INSERT INTO enrollments (student_id, course_id) VALUES (?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, studentId);
            ps.setInt(2, courseId);
            ps.executeUpdate();

            System.out.println("Course added successfully!");
            return;
        }
    }

}

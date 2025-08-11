import java.sql.*;
import java.security.MessageDigest;
import java.util.Scanner;

public class FinancialManagementSystem {

    // ===== Database Connection =====
    static class DatabaseConnection {
        private static final String URL = "jdbc:mysql://localhost:3306/financial_management_system";
        private static final String USER = "root"; // change if needed
        private static final String PASS = "Shahroz@3173"; // change if needed

        public static Connection getConnection() throws Exception {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASS);
        }
    }

    // ===== User Service =====
    static class UserService {
        public boolean createAccount(String username, String password, String fullName, String email, String phone) {
            String sql = "INSERT INTO users (username, password, full_name, email, phone, balance) VALUES (?, ?, ?, ?, ?, 0.00)";
            try (Connection con = DatabaseConnection.getConnection();
                 PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, username);
                ps.setString(2, hashPassword(password));
                ps.setString(3, fullName);
                ps.setString(4, email);
                ps.setString(5, phone);
                return ps.executeUpdate() > 0;
            } catch (Exception e) {
                System.out.println("Error creating account: " + e.getMessage());
            }
            return false;
        }

        public int login(String username, String password) {
            String sql = "SELECT user_id, password FROM users WHERE username = ?";
            try (Connection con = DatabaseConnection.getConnection();
                 PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, username);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    String storedHash = rs.getString("password");
                    if (storedHash.equals(hashPassword(password))) {
                        return rs.getInt("user_id");
                    }
                }
            } catch (Exception e) {
                System.out.println("Login error: " + e.getMessage());
            }
            return -1;
        }

        public void viewAccount(int userId) {
            String sql = "SELECT * FROM users WHERE user_id = ?";
            try (Connection con = DatabaseConnection.getConnection();
                 PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setInt(1, userId);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    System.out.println("\n=== My Account ===");
                    System.out.println("Name: " + rs.getString("full_name"));
                    System.out.println("Email: " + rs.getString("email"));
                    System.out.println("Phone: " + rs.getString("phone"));
                    System.out.println("Balance: ₹ " + rs.getBigDecimal("balance"));
                }
            } catch (Exception e) {
                System.out.println("Error viewing account: " + e.getMessage());
            }
        }

        public void updateAccount(int userId, String name, String email, String phone) {
            String sql = "UPDATE users SET full_name=?, email=?, phone=? WHERE user_id=?";
            try (Connection con = DatabaseConnection.getConnection();
                 PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, name);
                ps.setString(2, email);
                ps.setString(3, phone);
                ps.setInt(4, userId);
                ps.executeUpdate();
                System.out.println("Account updated successfully!");
            } catch (Exception e) {
                System.out.println("Error updating account: " + e.getMessage());
            }
        }

        public void deleteAccount(int userId) {
            String sql = "DELETE FROM users WHERE user_id = ?";
            try (Connection con = DatabaseConnection.getConnection();
                 PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setInt(1, userId);
                ps.executeUpdate();
                System.out.println("Account deleted successfully!");
            } catch (Exception e) {
                System.out.println("Error deleting account: " + e.getMessage());
            }
        }

        private String hashPassword(String password) throws Exception {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());
            StringBuilder hex = new StringBuilder();
            for (byte b : hash) {
                hex.append(String.format("%02x", b));
            }
            return hex.toString();
        }
    }

    // ===== Transaction Service =====
    static class TransactionService {
        public void credit(int userId, double amount, String desc) {
            String updateBal = "UPDATE users SET balance = balance + ? WHERE user_id = ?";
            String insertTxn = "INSERT INTO transactions (user_id, type, amount, description) VALUES (?, 'CREDIT', ?, ?)";
            try (Connection con = DatabaseConnection.getConnection()) {
                con.setAutoCommit(false);

                try (PreparedStatement ps1 = con.prepareStatement(updateBal);
                     PreparedStatement ps2 = con.prepareStatement(insertTxn)) {

                    ps1.setDouble(1, amount);
                    ps1.setInt(2, userId);
                    ps1.executeUpdate();

                    ps2.setInt(1, userId);
                    ps2.setDouble(2, amount);
                    ps2.setString(3, desc);
                    ps2.executeUpdate();

                    con.commit();
                    System.out.println("₹" + amount + " credited successfully.");
                } catch (Exception e) {
                    con.rollback();
                    System.out.println("Credit failed: " + e.getMessage());
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

        public void debit(int userId, double amount, String desc) {
            String checkBal = "SELECT balance FROM users WHERE user_id = ?";
            String updateBal = "UPDATE users SET balance = balance - ? WHERE user_id = ?";
            String insertTxn = "INSERT INTO transactions (user_id, type, amount, description) VALUES (?, 'DEBIT', ?, ?)";

            try (Connection con = DatabaseConnection.getConnection()) {
                con.setAutoCommit(false);

                try (PreparedStatement check = con.prepareStatement(checkBal)) {
                    check.setInt(1, userId);
                    ResultSet rs = check.executeQuery();
                    if (rs.next() && rs.getDouble("balance") >= amount) {

                        try (PreparedStatement ps1 = con.prepareStatement(updateBal);
                             PreparedStatement ps2 = con.prepareStatement(insertTxn)) {

                            ps1.setDouble(1, amount);
                            ps1.setInt(2, userId);
                            ps1.executeUpdate();

                            ps2.setInt(1, userId);
                            ps2.setDouble(2, amount);
                            ps2.setString(3, desc);
                            ps2.executeUpdate();

                            con.commit();
                            System.out.println("₹" + amount + " debited successfully.");
                        }
                    } else {
                        System.out.println("Insufficient balance!");
                    }
                } catch (Exception e) {
                    con.rollback();
                    System.out.println("Debit failed: " + e.getMessage());
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

        public void viewTransactions(int userId) {
            String sql = "SELECT * FROM transactions WHERE user_id = ? ORDER BY transaction_date DESC";
            try (Connection con = DatabaseConnection.getConnection();
                 PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setInt(1, userId);
                ResultSet rs = ps.executeQuery();
                System.out.println("\n=== Transaction History ===");
                while (rs.next()) {
                    System.out.printf("%d | %s | ₹%.2f | %s | %s%n",
                            rs.getInt("transaction_id"),
                            rs.getString("type"),
                            rs.getDouble("amount"),
                            rs.getString("description"),
                            rs.getTimestamp("transaction_date"));
                }
            } catch (Exception e) {
                System.out.println("Error viewing transactions: " + e.getMessage());
            }
        }

        public void updateTransaction(int userId, int txnId, double amount, String desc) {
            String sql = "UPDATE transactions SET amount=?, description=? WHERE transaction_id=? AND user_id=?";
            try (Connection con = DatabaseConnection.getConnection();
                 PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setDouble(1, amount);
                ps.setString(2, desc);
                ps.setInt(3, txnId);
                ps.setInt(4, userId);
                ps.executeUpdate();
                System.out.println("Transaction updated successfully!");
            } catch (Exception e) {
                System.out.println("Error updating transaction: " + e.getMessage());
            }
        }

        public void deleteTransaction(int userId, int txnId) {
            String sql = "DELETE FROM transactions WHERE transaction_id=? AND user_id=?";
            try (Connection con = DatabaseConnection.getConnection();
                 PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setInt(1, txnId);
                ps.setInt(2, userId);
                ps.executeUpdate();
                System.out.println("Transaction deleted successfully!");
            } catch (Exception e) {
                System.out.println("Error deleting transaction: " + e.getMessage());
            }
        }

        public void creditBatch(int userId, int batchSize, Scanner sc) {
            try(Connection conn = DatabaseConnection.getConnection();) {

                String transactionSQL = "INSERT INTO transactions (user_id, type, amount, description) VALUES (?, 'CREDIT', ?, ?)";
                String updateBalanceSQL = "UPDATE users SET balance = balance + ? WHERE user_id = ?";

                conn.setAutoCommit(false);

                try (PreparedStatement psTransaction = conn.prepareStatement(transactionSQL);
                     PreparedStatement psUpdateBalance = conn.prepareStatement(updateBalanceSQL)) {

                    for (int i = 0; i < batchSize; i++) {
                        System.out.print("Enter amount for credit #" + (i + 1) + ": ");
                        double amount = Double.parseDouble(sc.nextLine());

                        System.out.print("Enter description for credit #" + (i + 1) + ": ");
                        String description = sc.nextLine();

                        psTransaction.setInt(1, userId);
                        psTransaction.setDouble(2, amount);
                        psTransaction.setString(3, description);
                        psTransaction.addBatch();

                        psUpdateBalance.setDouble(1, amount);
                        psUpdateBalance.setInt(2, userId);
                        psUpdateBalance.addBatch();
                    }

                    psTransaction.executeBatch();
                    psUpdateBalance.executeBatch();

                    conn.commit();
                    System.out.println("Batch credit successful for " + batchSize + " transactions.");

                } catch (Exception e) {
                    conn.rollback();
                    System.out.println("Error in batch credit. Transaction rolled back.");
                    e.printStackTrace();
                } finally {
                    conn.setAutoCommit(true);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }



    // ===== Main Menu =====
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        UserService userService = new UserService();
        TransactionService txnService = new TransactionService();

        while (true) {
            System.out.println("\n=== Financial Management System ===");
            System.out.println("1. Login");
            System.out.println("2. Create New Account");
            System.out.println("3. Exit");
            System.out.print("Choose: ");
            int choice = sc.nextInt();
            sc.nextLine();

            if (choice == 1) {
                System.out.print("Username: ");
                String username = sc.nextLine();
                System.out.print("Password: ");
                String password = sc.nextLine();

                int userId = userService.login(username, password);
                if (userId != -1) {
                    System.out.println("Login successful!");
                    boolean loggedIn = true;
                    while (loggedIn) {
                        System.out.println("\n--- Main Menu ---");
                        System.out.println("1. View My Account");
                        System.out.println("2. Credit Money");
                        System.out.println("3. Debit Money");
                        System.out.println("4. View My Transactions");
                        System.out.println("5. Update Transaction");
                        System.out.println("6. Delete Transaction");
                        System.out.println("7. Update My Account");
                        System.out.println("8. Delete My Account");
                        System.out.println("9. Logout");
                        System.out.println("10.creditBatch");

                        System.out.print("Choose: ");
                        int opt = sc.nextInt();
                        sc.nextLine();

                        switch (opt) {
                            case 1:
                                userService.viewAccount(userId);
                                break;
                            case 2:
                                System.out.print("Amount: ");
                                double cAmt = sc.nextDouble(); sc.nextLine();
                                System.out.print("Description: ");
                                String cDesc = sc.nextLine();
                                txnService.credit(userId, cAmt, cDesc);
                                break;
                            case 3:
                                System.out.print("Amount: ");
                                double dAmt = sc.nextDouble(); sc.nextLine();
                                System.out.print("Description: ");
                                String dDesc = sc.nextLine();
                                txnService.debit(userId, dAmt, dDesc);
                                break;
                            case 4:
                                txnService.viewTransactions(userId);
                                break;
                            case 5:
                                System.out.print("Transaction ID: ");
                                int utid = sc.nextInt();
                                System.out.print("New Amount: ");
                                double uAmt = sc.nextDouble(); sc.nextLine();
                                System.out.print("New Description: ");
                                String uDesc = sc.nextLine();
                                txnService.updateTransaction(userId, utid, uAmt, uDesc);
                                break;
                            case 6:
                                System.out.print("Transaction ID: ");
                                int dtid = sc.nextInt(); sc.nextLine();
                                txnService.deleteTransaction(userId, dtid);
                                break;
                            case 7:
                                System.out.print("New Name: ");
                                String nName = sc.nextLine();
                                System.out.print("New Email: ");
                                String nEmail = sc.nextLine();
                                System.out.print("New Phone: ");
                                String nPhone = sc.nextLine();
                                userService.updateAccount(userId, nName, nEmail, nPhone);
                                break;
                            case 8:
                                userService.deleteAccount(userId);
                                loggedIn = false;
                                break;
                            case 9:
                                loggedIn = false;
                                break;
                            case 10:
                                System.out.print("Enter batch size: ");
                                int batchSize = sc.nextInt(); sc.nextLine();
                                txnService.creditBatch(userId, batchSize, sc);
                                break;



                            default:
                                System.out.println("Invalid choice.");
                        }
                    }
                } else {
                    System.out.println("Invalid username or password.");
                }
            }
            else if (choice == 2) {
                System.out.print("Username: ");
                String username = sc.nextLine();
                System.out.print("Password: ");
                String password = sc.nextLine();
                System.out.print("Full Name: ");
                String fullName = sc.nextLine();
                System.out.print("Email: ");
                String email = sc.nextLine();
                System.out.print("Phone: ");
                String phone = sc.nextLine();

                if (userService.createAccount(username, password, fullName, email, phone)) {
                    System.out.println("Account created successfully!");
                } else {
                    System.out.println("Failed to create account.");
                }
            }
            else if (choice == 3) {
                System.out.println("Goodbye!");
                break;
            }
            else {
                System.out.println("Invalid choice.");
            }
        }
        sc.close();
    }
}


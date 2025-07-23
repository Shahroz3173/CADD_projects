
import java.util.ArrayList;
import java.util.Scanner;

class Book {
    String title;
    String author;
    String issueDate;
    String returnDate;
    boolean isIssued;

    Book(String title, String author) {
        this.title = title;
        this.author = author;
        this.isIssued = false;
    }

    void issue(String issueDate, String returnDate) {
        this.isIssued = true;
        this.issueDate = issueDate;
        this.returnDate = returnDate;
    }

    void returnBook() {
        this.isIssued = false;
        this.issueDate = null;
        this.returnDate = null;
    }

    void display() {
        System.out.println("Title: " + title + ", Author: " + author + ", Issued: " + isIssued);
        if (isIssued) {
            System.out.println("  Issue Date: " + issueDate + ", Return Date: " + returnDate);
        }
    }
}

public class LibrarySystem {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<Book> books = new ArrayList<>();

        while (true) {
            System.out.println("\n--- Library Menu ---");
            System.out.println("1. Add Book");
            System.out.println("2. Issue Book");
            System.out.println("3. Return Book");
            System.out.println("4. Show All Books");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");
            int ch = sc.nextInt();
            sc.nextLine(); // clear buffer

            if (ch == 1) {
                System.out.print("Enter title: ");
                String title = sc.nextLine();
                System.out.print("Enter author: ");
                String author = sc.nextLine();
                books.add(new Book(title, author));
                System.out.println("Book added successfully.");
            } else if (ch == 2) {
                System.out.print("Enter book title to issue: ");
                String title = sc.nextLine();
                boolean found = false;
                for (Book book : books) {
                    if (book.title.equalsIgnoreCase(title) && !book.isIssued) {
                        System.out.print("Enter issue date: ");
                        String issueDate = sc.nextLine();
                        System.out.print("Enter return date: ");
                        String returnDate = sc.nextLine();
                        book.issue(issueDate, returnDate);
                        System.out.println("Book issued.");
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    System.out.println("Book not found or already issued.");
                }
            } else if (ch == 3) {
                System.out.print("Enter book title to return: ");
                String title = sc.nextLine();
                boolean found = false;
                for (Book book : books) {
                    if (book.title.equalsIgnoreCase(title) && book.isIssued) {
                        book.returnBook();
                        System.out.println("Book returned.");
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    System.out.println("Book not found or not issued.");
                }
            } else if (ch == 4) {
                for (Book book : books) {
                    book.display();
                }
            } else if (ch == 5) {
                System.out.println("Exiting...");
                break;
            } else {
                System.out.println("Invalid choice.");
            }
        }

        sc.close();
    }
}

import java.sql.*;
import java.util.Scanner;
import java.lang.*;

public class BookRegistrationSystem {
    static final String DB_URL = "jdbc:mysql://localhost:3306/book_db";
    static final String USER = "root";
    static final String PASS = "123456";

    static Connection conn;
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        try {
	    Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            while (true) {
                System.out.println("\nBook Registration System");
                System.out.println("1. Add Book");
                System.out.println("2. View Books");
                System.out.println("3. Search Book by Title");
                System.out.println("4. Update Book");
                System.out.println("5. Delete Book");
                System.out.println("6. Exit");
                System.out.print("Choose an option: ");
                int choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {
                    case 1 -> addBook();
                    case 2 -> viewBooks();
                    case 3 -> searchBook();
                    case 4 -> updateBook();
                    case 5 -> deleteBook();
                    case 6 -> {
                        conn.close();
                        System.exit(0);
                    }
                    default -> System.out.println("Invalid option.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void addBook() throws SQLException {
        System.out.print("Enter title: ");
        String title = scanner.nextLine();
        System.out.print("Enter author: ");
        String author = scanner.nextLine();
        System.out.print("Enter ISBN: ");
        String isbn = scanner.nextLine();
        System.out.print("Enter price: ");
        double price = Double.parseDouble(scanner.nextLine());
        System.out.print("Enter published year: ");
        int year = Integer.parseInt(scanner.nextLine());

        String sql = "INSERT INTO books (title, author, isbn, price, published_year) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, title);
        stmt.setString(2, author);
        stmt.setString(3, isbn);
        stmt.setDouble(4, price);
        stmt.setInt(5, year);
        stmt.executeUpdate();
        System.out.println("Book added successfully!");
    }

    static void viewBooks() throws SQLException {
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM books");
        while (rs.next()) {
            System.out.printf("%d | %s | %s | %s | %.2f | %d%n",
                    rs.getInt("id"),
                    rs.getString("title"),
                    rs.getString("author"),
                    rs.getString("isbn"),
                    rs.getDouble("price"),
                    rs.getInt("published_year"));
        }
    }

    static void searchBook() throws SQLException {
        System.out.print("Enter title to search: ");
        String title = scanner.nextLine();
        String sql = "SELECT * FROM books WHERE title LIKE ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, "%" + title + "%");
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            System.out.printf("%d | %s | %s | %s | %.2f | %d%n",
                    rs.getInt("id"),
                    rs.getString("title"),
                    rs.getString("author"),
                    rs.getString("isbn"),
                    rs.getDouble("price"),
                    rs.getInt("published_year"));
        }
    }

    static void updateBook() throws SQLException {
        System.out.print("Enter book ID to update: ");
        int id = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter new title: ");
        String title = scanner.nextLine();
        System.out.print("Enter new author: ");
        String author = scanner.nextLine();
        System.out.print("Enter new price: ");
        double price = Double.parseDouble(scanner.nextLine());

        String sql = "UPDATE books SET title=?, author=?, price=? WHERE id=?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, title);
        stmt.setString(2, author);
        stmt.setDouble(3, price);
        stmt.setInt(4, id);
        int rows = stmt.executeUpdate();
        if (rows > 0) {
            System.out.println("Book updated successfully!");
        } else {
            System.out.println("Book ID not found.");
        }
    }

    static void deleteBook() throws SQLException {
        System.out.print("Enter book ID to delete: ");
        int id = Integer.parseInt(scanner.nextLine());
        String sql = "DELETE FROM books WHERE id=?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);
        int rows = stmt.executeUpdate();
        if (rows > 0) {
            System.out.println("Book deleted successfully!");
        } else {
            System.out.println("Book ID not found.");
        }
    }
}

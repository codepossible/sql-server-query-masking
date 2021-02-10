package testjdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 * Hello world!
 */
public final class App {
    private App() {
    }

    /**
     * Says hello to the world.
     * @param args The arguments of the program.
     */
    public static void main(String[] args) {
        System.out.println("Connecting to the database...");

        // Create a variable for the connection string.
        String connectionUrl = "jdbc:sqlserver://localhost:1433;databaseName=ContactMaster;user=sa;password=sqlonLinux!2019";
        try (Connection con = DriverManager.getConnection(connectionUrl); Statement stmt = con.createStatement();) {
            System.out.println("Retrieving list of contacts...\n");
            String SQL = "EXECUTE AS User='testdde'; SELECT * FROM dbo.Contacts";
            boolean results = stmt.execute(SQL);
            if (results) {
                ResultSet rs = stmt.getResultSet();

                // Iterate through the data in the result set and display it.
                System.out.println("First Name" + "\t" + "Last Name");
                while (rs.next()) {
                    System.out.println(rs.getString("FirstName") + "\t\t" + rs.getString("LastName"));
                }
            }
        }
        // Handle any errors that may have occurred.
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

}


import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/job_platform";
    private static final String USER = "root";
    private static final String PASS = "ikove12";

    private static Connection connection = null;

    static {
        try {
            Class.forName(JDBC_DRIVER);
            System.out.println("✅ JDBC Driver loaded successfully!");
        } catch (ClassNotFoundException e) {
            System.err.println("❌ JDBC Driver not found: " + e.getMessage());
        }
    }

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("✅ Database connected successfully!");
        }
        return connection;
    }

    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("✅ Database connection closed.");
            }
        } catch (SQLException e) {
            System.err.println("❌ Error closing connection: " + e.getMessage());
        }
    }

    public static void displayDatabaseInfo() {
        try {
            Connection conn = getConnection();
            DatabaseMetaData dbMeta = conn.getMetaData();

            System.out.println("\n=== Database Information ===");
            System.out.println("Database Product: " + dbMeta.getDatabaseProductName());
            System.out.println("Database Version: " + dbMeta.getDatabaseProductVersion());
            System.out.println("Driver Name: " + dbMeta.getDriverName());
            System.out.println("Driver Version: " + dbMeta.getDriverVersion());
            System.out.println("Max Connections: " + dbMeta.getMaxConnections());
            System.out.println("------------------------------");

        } catch (SQLException e) {
            System.err.println("❌ Error getting database metadata: " + e.getMessage());
        }
    }
}
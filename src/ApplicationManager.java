import java.sql.*;
import java.util.*;

public class ApplicationManager {
    private List<Applicant> applicants;

    public ApplicationManager() {
        applicants = new ArrayList<>();
    }

    public void submitApplication(Applicant applicant, String jobTitle) throws InvalidApplicationException {
        if (applicant == null || jobTitle == null || jobTitle.isEmpty()) {
            throw new InvalidApplicationException("Invalid application data!");
        }
        if (applicant.getAge() < 18) {
            throw new InvalidApplicationException("Age must be 18 or above!");
        }
        if (applicant.getFullName().isEmpty()) {
            throw new InvalidApplicationException("Full name is required!");
        }
        if (applicant.getUniversity().isEmpty()) {
            throw new InvalidApplicationException("University is required!");
        }

        int applicantId = saveApplicantToDatabase(applicant);

        if (applicantId > 0) {
            applicant.setApplicantId(applicantId);
            applicant.setAppliedJob(jobTitle);
            applicants.add(applicant);
            saveApplicationToDatabase(applicantId, jobTitle);
            sendConfirmation(applicant.getEmail(), applicant.getFullName(), jobTitle);
            System.out.println("✅ Application saved to database!");
        } else {
            throw new InvalidApplicationException("Failed to save application to database!");
        }
    }

    private int saveApplicantToDatabase(Applicant applicant) {
        String sql = "INSERT INTO applicants (username, full_name, email, age, university, description, portfolio) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, applicant.getName());
            pstmt.setString(2, applicant.getFullName());
            pstmt.setString(3, applicant.getEmail());
            pstmt.setInt(4, applicant.getAge());
            pstmt.setString(5, applicant.getUniversity());
            pstmt.setString(6, applicant.getDescription());
            pstmt.setString(7, applicant.getPortfolioLink());

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }

        } catch (SQLException e) {
            System.err.println("❌ Error saving applicant: " + e.getMessage());
        }

        return -1;
    }

    private void saveApplicationToDatabase(int applicantId, String jobTitle) {
        String sql = "INSERT INTO applications (applicant_id, job_title, application_date, status) VALUES (?, ?, NOW(), ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, applicantId);
            pstmt.setString(2, jobTitle);
            pstmt.setString(3, "PENDING");

            pstmt.executeUpdate();
            System.out.println("✅ Application record saved to database!");

        } catch (SQLException e) {
            System.err.println("❌ Error saving application record: " + e.getMessage());
        }
    }

    public static void sendConfirmation(String email, String fullName, String job) {
        System.out.println("\n========================================");
        System.out.println("📧 NOTIFICATION SENT TO: " + email);
        System.out.println("========================================");
        System.out.println("Dear " + fullName + ",");
        System.out.println("Your application for " + job + " was successful!");
        System.out.println("Please stand by for further notifications.");
        System.out.println("Thank you for using Job Application Platform!");
        System.out.println("========================================\n");
    }

    public void loadData() {
        loadAllApplicants();
    }

    private void loadAllApplicants() {
        applicants.clear();
        String sql = "SELECT * FROM applicants ORDER BY applicant_id DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Applicant app = new Applicant(
                        rs.getString("username"),
                        rs.getString("email"),
                        rs.getInt("age"),
                        rs.getString("full_name"),
                        rs.getString("university"),
                        rs.getString("description"),
                        rs.getString("portfolio")
                );
                app.setApplicantId(rs.getInt("applicant_id"));

                String jobSql = "SELECT job_title FROM applications WHERE applicant_id = ? ORDER BY application_id DESC LIMIT 1";
                try (PreparedStatement pstmt = conn.prepareStatement(jobSql)) {
                    pstmt.setInt(1, app.getApplicantId());
                    ResultSet jobRs = pstmt.executeQuery();
                    if (jobRs.next()) {
                        app.setAppliedJob(jobRs.getString("job_title"));
                    }
                }

                applicants.add(app);
            }

            System.out.println("✅ Loaded " + applicants.size() + " applicants from database.");

        } catch (SQLException e) {
            System.err.println("❌ Error loading applicants: " + e.getMessage());
        }
    }

    public void displayApplicants() {
        if (applicants.isEmpty()) {
            System.out.println("\nNo applicants yet.");
            return;
        }
        System.out.println("\n=== All Applicants ===");
        for (Applicant app : applicants) {
            app.displayInfo();
            System.out.println("------------------------");
        }
    }

    public void displayDatabaseStats() {
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {

            String[] queries = {
                    "SELECT COUNT(*) as count FROM applicants",
                    "SELECT COUNT(*) as count FROM applications",
                    "SELECT COUNT(*) as count FROM jobs"
            };

            String[] labels = {"Applicants", "Applications", "Jobs"};

            System.out.println("\n=== Database Statistics ===");
            for (int i = 0; i < queries.length; i++) {
                ResultSet rs = stmt.executeQuery(queries[i]);
                if (rs.next()) {
                    System.out.println(labels[i] + ": " + rs.getInt("count"));
                }
            }
            System.out.println("------------------------------");

        } catch (SQLException e) {
            System.err.println("❌ Error getting stats: " + e.getMessage());
        }
    }
}

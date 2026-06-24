import java.sql.*;
import java.util.*;

public class JobDatabase {
    private List<Job> jobs;

    public JobDatabase() {
        jobs = new ArrayList<>();
        initializeJobs();
    }

    private void initializeJobs() {
        if (getAllJobs().size() > 0) {
            loadJobsFromDatabase();
            return;
        }

        String sql = "INSERT INTO jobs (title, category) VALUES (?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            String[][] jobData = {
                    {"Full Stack Developer", "Software Engineering"},
                    {"Mobile Developer", "Software Engineering"},
                    {"DevOps Engineer", "Software Engineering"},
                    {"Structural Engineer", "Civil Engineering"},
                    {"Transportation Engineer", "Civil Engineering"},
                    {"Environmental Engineer", "Civil Engineering"},
                    {"Automotive Engineer", "Mechanical Engineering"},
                    {"Aerospace Engineer", "Mechanical Engineering"},
                    {"Robotics Engineer", "Mechanical Engineering"}
            };

            for (String[] job : jobData) {
                pstmt.setString(1, job[0]);
                pstmt.setString(2, job[1]);
                pstmt.executeUpdate();
            }

            System.out.println("✅ Default jobs inserted into database!");
            loadJobsFromDatabase();

        } catch (SQLException e) {
            System.err.println("❌ Error initializing jobs: " + e.getMessage());
        }
    }

    private void loadJobsFromDatabase() {
        jobs.clear();
        String sql = "SELECT job_id, title, category FROM jobs ORDER BY category, title";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Job job = new Job(
                        rs.getInt("job_id"),
                        rs.getString("title"),
                        rs.getString("category")
                );
                jobs.add(job);
            }

            System.out.println("✅ Loaded " + jobs.size() + " jobs from database.");

        } catch (SQLException e) {
            System.err.println("❌ Error loading jobs: " + e.getMessage());
        }
    }

    public List<String> getCategories() {
        Set<String> categories = new HashSet<>();
        for (Job job : jobs) {
            categories.add(job.getCategory());
        }
        return new ArrayList<>(categories);
    }

    public List<String> getJobsByCategory(String category) {
        List<String> jobTitles = new ArrayList<>();
        for (Job job : jobs) {
            if (job.getCategory().equalsIgnoreCase(category)) {
                jobTitles.add(job.getTitle());
            }
        }
        return jobTitles;
    }

    public Job getJobByTitle(String title) {
        for (Job job : jobs) {
            if (job.getTitle().equalsIgnoreCase(title)) {
                return job;
            }
        }
        return null;
    }

    public List<Job> getAllJobs() {
        return jobs;
    }
}
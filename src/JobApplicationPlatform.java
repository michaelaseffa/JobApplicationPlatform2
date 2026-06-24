import java.util.*;

public class JobApplicationPlatform {
    private static Scanner scanner = new Scanner(System.in);
    private static JobDatabase db;
    private static ApplicationManager manager;
    private static Applicant currentApplicant;
    private static String selectedJob = "";

    static {
        System.out.println("╔══════════════════════════════════════════════════════════╗");
        System.out.println("║           JOB APPLICATION PLATFORM                      ║");
        System.out.println("║           with JDBC Database Integration                ║");
        System.out.println("╚══════════════════════════════════════════════════════════╝\n");

        DatabaseConnection.displayDatabaseInfo();

        db = new JobDatabase();
        manager = new ApplicationManager();
        manager.loadData();
    }

    public static void main(String[] args) {
        try {
            run();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            DatabaseConnection.closeConnection();
            System.out.println("\nThank you for using Job Application Platform!");
            scanner.close();
        }
    }

    private static void run() {
        if (!login()) {
            System.out.println("Login failed!");
            return;
        }

        selectJob();
        fillRequirements();
        submitApplication();
        manager.displayApplicants();
        manager.displayDatabaseStats();
    }

    private static boolean login() {
        System.out.println("===== LOGIN =====");
        System.out.print("Enter email: ");
        String email = scanner.nextLine().trim();

        System.out.print("Enter username: ");
        String username = scanner.nextLine().trim();

        if (email.isEmpty()  username.isEmpty()  !email.contains("@")) {
            System.out.println("Invalid credentials!");
            return false;
        }

        currentApplicant = new Applicant(username, email);

        currentApplicant.welcomeGreeting();
        System.out.println("Welcome, " + currentApplicant.getName() + "! 👋");
        return true;
    }

    private static void selectJob() {
        System.out.println("\n===== JOB CATEGORIES =====");
        List<String> categories = db.getCategories();

        for (int i = 0; i < categories.size(); i++) {
            System.out.println((i + 1) + ". " + categories.get(i));
        }

        int choice = getChoice(1, categories.size());
        String category = categories.get(choice - 1);

        List<String> jobs = db.getJobsByCategory(category);
        System.out.println("\nAvailable jobs in " + category + ":");
        for (int i = 0; i < jobs.size(); i++) {
            System.out.println((i + 1) + ". " + jobs.get(i));
        }

        int jobChoice = getChoice(1, jobs.size());
        selectedJob = jobs.get(jobChoice - 1);

        System.out.println("\n✅ Selected: " + selectedJob);
    }

    private static int getChoice(int min, int max) {
        while (true) {
            try {
                System.out.print("Enter choice (" + min + "-" + max + "): ");
                int choice = Integer.parseInt(scanner.nextLine().trim());
                if (choice >= min && choice <= max) {
                    return choice;
                }
                System.out.println("Invalid choice!");
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number!");
            }
        }
    }

    private static void fillRequirements() {
        System.out.println("\n===== JOB REQUIREMENTS =====");
        System.out.println("Position: " + selectedJob);
        System.out.println("Please fill in your details:\n");

        System.out.print("Enter your full name: ");
        String fullName = scanner.nextLine().trim();
        while (fullName.isEmpty()) {
            System.out.print("Full name is required. Enter again: ");
            fullName = scanner.nextLine().trim();
        }
        currentApplicant.setFullName(fullName);
      System.out.print("Enter your age: ");
        int age = 0;
        while (true) {
            try {
                age = Integer.parseInt(scanner.nextLine().trim());
                if (age >= 18) break;
                System.out.print("Age must be 18+. Enter again: ");
            } catch (NumberFormatException e) {
                System.out.print("Enter valid number: ");
            }
        }
        currentApplicant.setAge(age);

        System.out.print("Enter your university (where you got your BSc degree): ");
        String university = scanner.nextLine().trim();
        while (university.isEmpty()) {
            System.out.print("University is required. Enter again: ");
            university = scanner.nextLine().trim();
        }
        currentApplicant.setUniversity(university);

        System.out.print("Brief description about yourself: ");
        String desc = scanner.nextLine().trim();
        if (desc.isEmpty()) desc = "Experienced professional";
        currentApplicant.setDescription(desc);

        System.out.print("Portfolio link (or press Enter to skip): ");
        String portfolio = scanner.nextLine().trim();
        if (!portfolio.isEmpty()) {
            currentApplicant.setPortfolioLink(portfolio);
        }

        System.out.println("\n✅ Requirements completed!");
    }

    private static void submitApplication() {
        try {
            try {
                manager.submitApplication(currentApplicant, selectedJob);
                System.out.println("🎉 APPLICATION SUBMITTED SUCCESSFULLY!");
                currentApplicant.displayInfo();
            } catch (InvalidApplicationException | NullPointerException e) {
                System.err.println("❌ " + e.getMessage());
                throw e;
            }
        } catch (InvalidApplicationException e) {
            System.err.println("Application failed: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
        } finally {
            System.out.println("Application process ended.");
        }
    }
}

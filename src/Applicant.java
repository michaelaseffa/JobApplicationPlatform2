import java.io.Serializable;

public class Applicant extends Person implements Serializable {
    private static final long serialVersionUID = 1L;
    private String fullName;
    private String university;
    private String description;
    private String portfolioLink;
    private String appliedJob;
    private int applicantId;

    public Applicant(String name, String email) {
        super(name, email);
        this.fullName = "";
        this.university = "";
        this.description = "";
        this.portfolioLink = "";
        this.appliedJob = "";
        this.applicantId = -1;
    }

    public Applicant(String name, String email, int age, String fullName, String university, String description, String portfolioLink) {
        super(name, email, age);
        this.fullName = fullName;
        this.university = university;
        this.description = description;
        this.portfolioLink = portfolioLink;
        this.appliedJob = "";
        this.applicantId = -1;
    }

    @Override
    public void displayInfo() {
        System.out.println("\n--- Applicant Details ---");
        System.out.println("ID: " + (applicantId > 0 ? applicantId : "Not assigned"));
        System.out.println("Username: " + getName());
        System.out.println("Full Name: " + (fullName.isEmpty() ? "Not provided" : fullName));
        System.out.println("Email: " + getEmail());
        System.out.println("Age: " + getAge());
        System.out.println("University: " + (university.isEmpty() ? "Not provided" : university));
        System.out.println("Description: " + (description.isEmpty() ? "Not provided" : description));
        System.out.println("Portfolio: " + (portfolioLink.isEmpty() ? "Not provided" : portfolioLink));
        System.out.println("Applied for: " + (appliedJob.isEmpty() ? "None" : appliedJob));
    }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getUniversity() { return university; }
    public void setUniversity(String university) { this.university = university; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getPortfolioLink() { return portfolioLink; }
    public void setPortfolioLink(String portfolioLink) { this.portfolioLink = portfolioLink; }

    public String getAppliedJob() { return appliedJob; }
    public void setAppliedJob(String appliedJob) { this.appliedJob = appliedJob; }

    public int getApplicantId() { return applicantId; }
    public void setApplicantId(int applicantId) { this.applicantId = applicantId; }
}
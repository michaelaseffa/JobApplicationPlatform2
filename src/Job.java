import java.io.Serializable;

public class Job implements Serializable {
    private static final long serialVersionUID = 1L;
    private String title;
    private String category;
    private int jobId;

    public Job(String title, String category) {
        this.title = title;
        this.category = category;
        this.jobId = -1;
    }

    public Job(int jobId, String title, String category) {
        this.jobId = jobId;
        this.title = title;
        this.category = category;
    }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public int getJobId() { return jobId; }
    public void setJobId(int jobId) { this.jobId = jobId; }

    @Override
    public String toString() {
        return title + " (" + category + ")";
    }
}
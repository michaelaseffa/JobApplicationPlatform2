import java.io.Serializable;

public abstract class Person implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private String email;
    private int age;
    protected static int totalUsers = 0;
    protected static final String COMPANY_NAME = "Job Application Platform";

    public Person(String name, String email) {
        this.name = name;
        this.email = email;
        this.age = 0;
        totalUsers++;
    }

    public Person(String name, String email, int age) {
        this.name = name;
        this.email = email;
        this.age = age;
        totalUsers++;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public static int getTotalUsers() { return totalUsers; }
    public static String getCompanyName() { return COMPANY_NAME; }

    public abstract void displayInfo();

    public final void welcomeGreeting() {
        System.out.println("\n=== Welcome to " + COMPANY_NAME + " ===");
    }
}
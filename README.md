Group Memebers: Daniel Wagaye ; Imran Ahmed ; Kebron Teshome ; Kedija Nejmudin ; Michael Aseffa.
                   "Job Application Platform" 
The Job Application Platform is a comprehensive Java-based console application developed as a OOP to demonstrate the practical implementation of Object-Oriented Programming concepts.
File I/O operations, and JDBC database integration. The system provides a complete job application workflow where users can log in, browse engineering job categories, submit applications, and receive confirmation notifications—all while showcasing advanced Java programming techniques.
Object-Oriented Programming (OOP) Concepts
1. Classes and Objects
Multiple classes including Person, Applicant, Job, JobDatabase, ApplicationManager, and JobApplicationPlatfor
2. Constructors
Multiple constructors with constructor overloading in Person and Applicant classes
3. Static Keyword
totalUsers counter tracking total user registrations
4. Access Modifiers
Private fields for encapsulation
Protected static variables for inheritance
Public methods for external interaction
5. Getters and Setters
6. Inheritance
Applicant class extends abstract Person class
7. Super Keyword
Used in Applicant constructor to call parent class constructor
Accessing parent class fields and methods
8. Overriding
displayInfo() method overridden in Applicant class
Runtime polymorphism through method overriding
9. Polymorphism
Upcasting: Person person = new Applicant()
Method overriding demonstrates runtime polymorphism
Different implementations of the same method
10. Final Keyword
COMPANY_NAME declared as final (constant)
welcomeGreeting() method declared as final (cannot be overridden)
11. Overloading
Multiple constructors in Person and Applicant classes

Exception Handling
1. Custom Exceptions
InvalidApplicationException for application-specific errors
Extends Java's built-in Exception class
2. Try-Catch Blocks
Multiple try-catch blocks for graceful error handling
Multi try-catch for handling different exception types
3. Throws Keyword
Methods declare exceptions using the throws clause
4. Finally Clause
Resource cleanup (closing file streams, database connections)
Ensures resources are released even if exceptions occur

File I/O Operations
1. Object Serialization
Used to persist Applicant and Job objects
ObjectOutputStream for saving objects to .ser files
ObjectInputStream for loading objects from .ser files
2. Byte Streams
FileInputStream and FileOutputStream for binary data
Used in serialization operations
3. Character Streams
FileWriter and PrintWriter for text file operations
Creates human-readable application logs
4. I/O Hierarchy
Demonstrates the Java I/O class hierarchy
Proper use of different stream types
5. File Class
Checking file existence (file.exists())
File management operations
6. Scanner Class
User input handling throughout the application
7. PrintWriter Class
Writing formatted text to files
Generating application logs
8. DataInputStream and DataOutputStream
.
Database Connectivity
MySQL database integration using JDBC
DriverManager for connection establishment
Connection URL, username, and password configuration
 PreparedStatement (SQL Injection Prevention)





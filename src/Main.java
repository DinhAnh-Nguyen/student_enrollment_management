import models.Student;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class Main
{
    // Data structures/utilities/variables
    private static final Map<String, Student> students = new LinkedHashMap<>();
    private static int studentCounter = 1;
    private static final Scanner input = new Scanner(System.in);

    private static String promptLine(String prompt)
    {
        System.out.print(prompt);
        return input.nextLine().trim();
    }

    private static String promptLineAllowBlank(String prompt)
    {
        System.out.print(prompt);
        return input.nextLine();
    }

    private static int promptInt(String prompt)
    {
        while (true)
        {
            System.out.print(prompt);
            String s = input.nextLine().trim();
            try
            {
                return Integer.parseInt(s);
            }
            catch (NumberFormatException e)
            {
                System.out.println("Invalid integer input. Please try again.");
            }
        }
    }

    private static double promptDouble(String prompt)
    {
        while (true)
        {
            System.out.print(prompt);
            String s = input.nextLine().trim();

            try
            {
                return Double.parseDouble(s);
            }
            catch (NumberFormatException e)
            {
                System.out.println("Invalid double input. Please try again.");
            }
        }
    }

    private static void editFieldString(String fieldPrompt, Supplier<String> getter, Consumer<String> setter)
    {
        String current = getter.get();
        String newVal = promptLineAllowBlank(fieldPrompt + " (current: " + current + "): ");

        if (!newVal.isBlank())
        {
            setter.accept(newVal.trim());
            System.out.println("Field updated.");
        }
        else
        {
            System.out.println("No changes made.");
        }
    }

    private static void editFieldInt(String fieldPrompt, Supplier<Integer> getter, Consumer<Integer> setter)
    {
        String current = String.valueOf(getter.get());
        String line = promptLineAllowBlank(fieldPrompt + " (current: " + current + "): ");

        if (!line.isBlank())
        {
            try
            {
                int val = Integer.parseInt(line.trim());
                setter.accept(val);
                System.out.println("Field updated.");
            }
            catch (NumberFormatException e)
            {
                System.out.println("Invalid integer input. Please try again.");
            }
        }
        else
        {
            System.out.println("No changes made.");
        }
    }

    private static void editGPA(Student student)
    {
        System.out.println("Enter GPA (0.0 - 4.0), leave blank to skip. Current GPA: " + student.getGpaPrev());
        String newGpa = promptLineAllowBlank("> ");

        if (!newGpa.isBlank())
        {
            try
            {
                double number = Double.parseDouble(newGpa.trim());

                if (number >= 0.0 && number <= 4.0)
                {
                    student.setGpaPrev(number);
                    System.out.println("GPA updated.");
                }
                else
                {
                    System.out.println("Invalid GPA range.");
                }
            }
            catch (NumberFormatException e)
            {
                System.out.println("Invalid double input. No changes made.");
            }
        }
        else
        {
            System.out.println("No changes made.");
        }
    }

    // Admin login
    private static boolean adminLogin()
    {
        String[] usernames = {"Mark", "Daniel", "Ethan", "Donald"};
        String[] passwords = {"mark123", "daniel678", "ethan999", "donald345"};
        boolean loggedIn = false;
        int attempt = 0;

        System.out.println("=== SAIT Enrollment System Login ===");

        while (attempt < 3 && !loggedIn)
        {
            String username = promptLine("Enter username: ");
            String password = promptLine("Enter password: ");

            for (int i = 0; i < usernames.length; i++)
            {
                if (username.equals(usernames[i]) && password.equals(passwords[i]))
                {
                    loggedIn = true;
                    break;
                }
            }

            if (!loggedIn)
            {
                System.out.println("Invalid login credentials. Please try again (Attempt " + (attempt + 1) + " of 3 ).");
                attempt =  attempt + 1;
            }
        }

        if (loggedIn)
        {
            System.out.println("Login successful. Welcome " + promptLastEnteredUsernameNote());
            return true;
        }
        else
        {
            System.out.println("Too many failed attempts. Access denied.");
            return false;
        }
    }

    // Username variables
    private static String lastEnteredUsername = "";

    private static String promptLastEnteredUsernameNote()
    {
        return lastEnteredUsername;
    }

    // Override prompt line used above for username to store it
    private static String promptLineStoreUsername(String prompt)
    {
        System.out.print(prompt);
        String v = input.nextLine().trim();
        lastEnteredUsername = v;
        return v;
    }

    // Rewire the username prompt in login to store the user's text
    private static String promptLine(String prompt, boolean storeAsUsername)
    {
        if (storeAsUsername)
        {
            return promptLineStoreUsername(prompt);
        }

        return promptLine(prompt);
    }

    // Re-implement adminLogin to use the storing prompt (to exactly print the name that succeeded)
    static
    {
        // Shadow the previous method by redefining with the correct storing prompt
    }

    /*private static boolean adminLoginExact()
    {
        String[] usernames = {"Mark", "Daniel", "Ethan", "Donald"};
        String[] passwords = {"mark123", "daniel678", "ethan999", "donald345"};
        boolean loggedIn = false;
        int attempt = 0;

        System.out.println("=== SAIT Enrollment System Login ===");

        while (attempt < 3 && !loggedIn)
        {
            String username = promptLine("Enter username: ", true);
            String password = promptLine("Enter password: ");

            for (int i = 0; i < usernames.length; i++)
            {
                if (username.equals(usernames[i]) && password.equals(passwords[i]))
                {
                    loggedIn = true;
                    break;
                }
            }

            if (!loggedIn)
            {
                System.out.println("Invalid login credentials. Please try again (Attempt " + (attempt + 1) + " of 3 ).");
                attempt = attempt + 1;
            }
        }

        if (loggedIn)
        {
            System.out.println("Login successful. Welcome " + lastEnteredUsername);
            return true;
        }
        else
        {
            System.out.println("Too many failed attempts. Access denied.");
            return false;
        }
    }*/

    // Add student
    private static void addStudent()
    {
        System.out.println("=== Add New Student ===");
        Student student = new Student();

        student.setFirstName(promptLine("Enter first name: "));
        student.setLastName(promptLine("Enter last name: "));
        student.setDateOfBirth(promptLine("Enter date of birth (YYYY-MM-DD): "));
        student.setGender(promptLine("Enter gender: "));

        while (true)
        {
            double gpa = promptDouble("Enter GPA (0.0 - 4.0): ");

            if (gpa >= 0.0 && gpa <= 4.0)
            {
                student.setGpaPrev(gpa);
                break;
            }
            else
            {
                System.out.println("Invalid GPA range.");
            }
        }

        student.setCurrentSemester(promptInt("Enter current semester number: "));
        student.setProgram(promptLine("Enter program name: "));

        while (true)
        {
            int number = promptInt("Enter number of courses (0 - 12): ");

            if (number >= 0 && number <= 12)
            {
                student.setNumCourses(number);
                break;
            }
            else
            {
                System.out.println("Invalid number of courses. Must be 0 - 12.");
            }
        }

        String id = student.getFirstName() + "_" + student.getLastName() + "_" + studentCounter;
        studentCounter = studentCounter + 1;

        if (students.containsKey(id))
        {
            System.out.println("Error: A student with this name already exists. Try again.");
            return;
        }

        students.put(id, student);
        System.out.println("Student added successfully with ID: " + id);
    }

    // Modify student
    private static void modifyStudent()
    {
        System.out.println("=== Modify Student ===");
        String id = promptLine("Enter student ID: ");

        if (!students.containsKey(id))
        {
            System.out.println("Error: No student with this ID.");
            return;
        }

        Student student = students.get(id);
        String choice;

        System.out.println("Current Record:");
        System.out.println(student.getFirstName() + " " + student.getLastName() + " | " + student.getProgram() +
                " | GPA: " + student.getGpaPrev());

        do
        {
            System.out.println();
            System.out.println("Select what to edit:");
            System.out.println("1. First Name");
            System.out.println("2. Last Name");
            System.out.println("3. Date of Birth");
            System.out.println("4. Gender");
            System.out.println("5. GPA");
            System.out.println("6. Current Semester");
            System.out.println("7. Program");
            System.out.println("8. Number of Courses");
            System.out.println("Q. Exit");
            String raw = promptLine("");

            choice = raw;

            switch (choice)
            {
                case "1" -> editFieldString("Enter new first name", student::getFirstName, student::setFirstName);
                case "2" -> editFieldString("Enter new last name", student::getLastName, student::setLastName);
                case "3" -> editFieldString("Enter new date of birth (YYYY-MM-DD)", student::getDateOfBirth,
                        student::setDateOfBirth);
                case "4" -> editFieldString("Enter gender", student::getGender, student::setGender);
                case "5" -> editGPA(student);
                case "6" -> editFieldInt("Enter new current semester", student::getCurrentSemester, student::setCurrentSemester);
                case "7" -> editFieldString("Enter new program name", student::getProgram, student::setProgram);
                case "8" ->
                {
                    String current = String.valueOf(student.getNumCourses());
                    String line = promptLineAllowBlank("Enter new number of courses (0 - 12) (current: " + current + "): ");

                    if (!line.isBlank())
                    {
                        try
                        {
                            int val = Integer.parseInt(line.trim());

                            if (val >= 0 && val <= 12)
                            {
                                student.setNumCourses(val);
                                System.out.println("Field updated.");
                            }
                            else
                            {
                                System.out.println("Invalid range (0 - 12). No change made.");
                            }
                        }
                        catch (NumberFormatException e)
                        {
                            System.out.println("Invalid integer. No changes made.");
                        }
                    }
                    else
                    {
                        System.out.println("No changes made.");
                    }
                }
                case "Q", "q" -> System.out.println("Finished editing.");
                default ->
                {
                    if (!choice.equalsIgnoreCase("Q"))
                    {
                        System.out.println("Invalid choice. Please try again.");
                    }
                }

            }
        }
        while (!choice.equalsIgnoreCase("Q"));

        students.put(id, student);
        System.out.println("Student record updated.");
    }

    // Remove student
    private static void removeStudent()
    {
        System.out.println("=== Remove Student ===");
        String id = promptLine("Enter student ID: ");

        if (students.containsKey(id))
        {
            String confirm = promptLine("Are you sure you want to remove this student? [Y/N] ");

            if (confirm.equalsIgnoreCase("Y"))
            {
                students.remove(id);
                System.out.println("Student removed.");
            }
            else
            {
                System.out.println("Action cancelled.");
            }
        }
        else
        {
            System.out.println("Student not found.");
        }
    }

    // List all students
    private static void listStudents()
    {
        System.out.println("=== List of Students ===");

        if (students.isEmpty())
        {
            System.out.println("No student records found.");
            return;
        }

        for (Map.Entry<String, Student> entry : students.entrySet())
        {
            String id = entry.getKey();
            Student student = entry.getValue();

            System.out.println(id + " | " + student.getFirstName() + " " + student.getLastName() + " | " + student.getDateOfBirth() +
                    " | " + student.getGender() + " | GPA: " + student.getGpaPrev() + " | Semester: " + student.getCurrentSemester() +
                    " | Program: " + student.getProgram() + " | Courses: " + student.getNumCourses());
        }
    }

    // Main menu
    private static void mainMenu()
    {
        System.out.println("=== Welcome to Student Enrollment Management System ===");

        if (!adminLogin())
        {
            return;
        }

        String choice;

        do
        {
            System.out.println();
            System.out.println("1. Add Student");
            System.out.println("2. Modify Student");
            System.out.println("3. Remove Student");
            System.out.println("4. List of All Students");
            System.out.println("Q. Quit");

            choice = promptLine("");

            switch (choice)
            {
                case "1" -> addStudent();
                case "2" -> modifyStudent();
                case "3" -> removeStudent();
                case "4" -> listStudents();
                case "Q", "q" -> System.out.println("System shutting down... Goodbye.");
                default ->
                {
                    if (!choice.equalsIgnoreCase("Q"))
                    {
                        System.out.println("Invalid choice. Please try again.");
                    }
                }
            }
        }
        while (!choice.equalsIgnoreCase("Q"));
    }

    public static void main(String[] args)
    {
        mainMenu();
    }
}
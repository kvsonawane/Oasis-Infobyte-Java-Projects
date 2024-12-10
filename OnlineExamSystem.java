import java.util.ArrayList;
import java.util.List;
import java.util.*;

class User2 {
    private String username;
    private String password;
    private String name;

    public User2(String username, String password, String name) {
        this.username = username;
        this.password = password;
        this.name = name;
    }

    public boolean authenticate(String inputPassword) {
        return this.password.equals(inputPassword);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

class Exam {
    public static final int EXAM_DURATION_MINUTES = 30;
    private List<Question> questions;
    private List<Integer> userAnswers;

    public Exam() {
        questions = new ArrayList<>();
        userAnswers = new ArrayList<>();
        initializeQuestions();
    }

    private void initializeQuestions() {
        // Add sample questions
        questions.add(new Question("Which statement is true about Java?",
                List.of("Java is a sequence-dependent programming language", "Java is a code dependent programming language", "Java is a platform-dependent programming language", "Java is a platform-independent programming language"), 3));
        questions.add(new Question("Which component is used to compile, debug and execute the java programs?",
                List.of("JRE", "JIT", "JDK", "JVM"), 2));
        questions.add(new Question("Which one of the following is not a Java feature?",
                List.of("Object-oriented", "Use of pointers", "Portable", "Dynamic and Extensible"), 1));
        questions.add(new Question("Which of these cannot be used for a variable name in Java?",
                List.of("identifier & keyword", "identifier", "keyword", "none of the mentioned"), 2));
        questions.add(new Question("What will be the output of the following Java code?\n" +
                "\n" +
                "    class increment {\n" +
                "        public static void main(String args[]) \n" +
                "        {        \n" +
                "             int g = 3;\n" +
                "             System.out.print(++g * 8);\n" +
                "        } \n" +
                "    }",
                List.of("32", "33", "24", "25"), 0));
        questions.add(new Question("Which environment variable is used to set the java path?",
                List.of("MAVEN_Path", "JavaPATH", "JAVA", "JAVA_HOME"), 3));
        questions.add(new Question(" What will be the output of the following Java program?\n" +
                "\n" +
                "class output {\n" +
                "        public static void main(String args[]) \n" +
                "        {\n" +
                "            double a, b,c;\n" +
                "            a = 3.0/0;\n" +
                "            b = 0/4.0;\n" +
                "            c=0/0.0;\n" +
                " \n" +
                "\t    System.out.println(a);\n" +
                "            System.out.println(b);\n" +
                "            System.out.println(c);\n" +
                "        } \n" +
                "    }",
                List.of("NaN", "Infinity", "0.0", "all of the mentioned"), 3));
        questions.add(new Question("What will be the output of the following Java program?\n" +
                "\n" +
                "    class variable_scope \n" +
                "    {\n" +
                "        public static void main(String args[]) \n" +
                "        {\n" +
                "            int x;\n" +
                "            x = 5;\n" +
                "            {\n" +
                "\t        int y = 6;\n" +
                "\t        System.out.print(x + \" \" + y);\n" +
                "            }\n" +
                "            System.out.println(x + \" \" + y);\n" +
                "        } \n" +
                "    }",
                List.of("Compilation Error", "Runtime Error", "5 6 5 6", "5 6 5"), 0));
        questions.add(new Question("What will be the error in the following Java code?\n" +
                "\n" +
                "    byte b = 50;\n" +
                "    b = b * 50;",
                List.of("b cannot contain value 50",
                        "b cannot contain value 100," + "limited by its range",
                        "No error in this code",
                        " * operator has converted b * 50 into int, which can not be converted to byte without casting"), 3));
        questions.add(new Question("Which of the following is a type of polymorphism in Java Programming?",
                List.of("Multiple polymorphism", "Compile time polymorphism", "Multilevel polymorphism", "Execution time polymorphism"), 1));


        // Initialize user answers with -1 (unanswered)
        for (int i = 0; i < questions.size(); i++) {
            userAnswers.add(-1);
        }
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setAnswer(int questionIndex, int answerIndex) {
        userAnswers.set(questionIndex, answerIndex);
    }

    public int calculateScore() {
        int score = 0;
        for (int i = 0; i < questions.size(); i++) {
            if (userAnswers.get(i) == questions.get(i).getCorrectAnswerIndex()) {
                score++;
            }
        }
        return score;
    }
}
class Question {
    private String questionText;
    private List<String> options;
    private int correctAnswerIndex;

    public Question(String questionText, List<String> options, int correctAnswerIndex) {
        this.questionText = questionText;
        this.options = options;
        this.correctAnswerIndex = correctAnswerIndex;
    }

    public String getQuestionText() {
        return questionText;
    }

    public List<String> getOptions() {
        return options;
    }

    public int getCorrectAnswerIndex() {
        return correctAnswerIndex;
    }
}

public class OnlineExamSystem {
    private Map<String, User2> users;
    private User2 currentUser;
    private Scanner scanner;
    private Exam currentExam;

    public OnlineExamSystem() {
        users = new HashMap<>();
        scanner = new Scanner(System.in);
        // Add some sample users
        users.put("user1", new User2("user1", "password1", "Karan"));
        users.put("user2", new User2("user2", "password2", "Vamsi"));
    }

    public void start() {
        while (true) {
            if (currentUser == null) {
                login();
            } else {
                showMainMenu();
            }
        }
    }

    private void login() {
        System.out.println("\n=== Login ===");
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        User2 user = users.get(username);
        if (user != null && user.authenticate(password)) {
            currentUser = user;
            System.out.println("Login successful. Welcome, " + user.getName() + "!");
        } else {
            System.out.println("Invalid username or password. Please try again.");
        }
    }

    private void showMainMenu() {
        while (true) {
            System.out.println("\n=== Main Menu ===");
            System.out.println("1. Update Profile");
            System.out.println("2. Change Password");
            System.out.println("3. Start Exam");
            System.out.println("4. Logout");
            System.out.print("Enter your choice (1-4): ");

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number between 1 and 4.");
                continue;
            }

            switch (choice) {
                case 1:
                    updateProfile();
                    break;
                case 2:
                    changePassword();
                    break;
                case 3:
                    startExam();
                    break;
                case 4:
                    logout();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void updateProfile() {
        System.out.println("\n=== Update Profile ===");
        System.out.print("Enter new name: ");
        String newName = scanner.nextLine();
        currentUser.setName(newName);
        System.out.println("Profile updated successfully.");
    }

    private void changePassword() {
        System.out.println("\n=== Change Password ===");
        System.out.print("Enter current password: ");
        String currentPassword = scanner.nextLine();
        if (currentUser.authenticate(currentPassword)) {
            System.out.print("Enter new password: ");
            String newPassword = scanner.nextLine();
            currentUser.setPassword(newPassword);
            System.out.println("Password changed successfully.");
        } else {
            System.out.println("Incorrect current password. Password not changed.");
        }
    }

    private void startExam() {
        currentExam = new Exam();
        System.out.println("\n=== Starting Exam ===");
        System.out.println("You have " + Exam.EXAM_DURATION_MINUTES + " minutes to complete the exam.");
        System.out.println("Press Enter to start the exam...");
        scanner.nextLine();

        long startTime = System.currentTimeMillis();
        long endTime = startTime + (Exam.EXAM_DURATION_MINUTES * 60 * 1000);

        for (int i = 0; i < currentExam.getQuestions().size(); i++) {
            Question question = currentExam.getQuestions().get(i);
            System.out.println("\nQuestion " + (i + 1) + ": " + question.getQuestionText());
            for (int j = 0; j < question.getOptions().size(); j++) {
                System.out.println((char)('A' + j) + ". " + question.getOptions().get(j));
            }

            System.out.print("Your answer (A-" + (char)('A' + question.getOptions().size() - 1) + "): ");
            String answer = scanner.nextLine().toUpperCase();

            if (answer.length() == 1 && answer.charAt(0) >= 'A' && answer.charAt(0) < 'A' + question.getOptions().size()) {
                currentExam.setAnswer(i, answer.charAt(0) - 'A');
            } else {
                System.out.println("Invalid answer. Skipping question.");
            }

            long currentTime = System.currentTimeMillis();
            if (currentTime >= endTime) {
                System.out.println("\nTime's up! Exam auto-submitted.");
                break;
            }
        }

        submitExam();
    }

    private void submitExam() {
        int score = currentExam.calculateScore();
        System.out.println("\n=== Exam Finished ===");
        System.out.println("Your score: " + score + " out of " + currentExam.getQuestions().size());
        currentExam = null;
    }

    private void logout() {
        System.out.println("Logging out. Goodbye, " + currentUser.getName() + "!");
        currentUser = null;
    }

    public static void main(String[] args) {
        OnlineExamSystem system = new OnlineExamSystem();
        system.start();
    }
}
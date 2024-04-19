import java.util.Random;
import java.util.Scanner;

public class ImprovedGuessingGame {
    private static final int EASY_MAX_RANGE = 50;
    private static final int EASY_MAX_ATTEMPTS = 7;
    private static final int MEDIUM_MAX_RANGE = 100;
    private static final int MEDIUM_MAX_ATTEMPTS = 5;
    private static final int HARD_MAX_RANGE = 200;
    private static final int HARD_MAX_ATTEMPTS = 3;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        int totalScore = 0;

        System.out.println("Welcome to the Improved Number Guessing Game!");

        while (true) {
            System.out.println("Choose the difficulty level:");
            System.out.println("1. Easy");
            System.out.println("2. Medium");
            System.out.println("3. Hard");
            System.out.println("4. Quit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            if (choice == 4) {
                System.out.println("Thank you for playing!");
                break;
            }

            int maxRange;
            int maxAttempts;

            switch (choice) {
                case 1:
                    maxRange = EASY_MAX_RANGE;
                    maxAttempts = EASY_MAX_ATTEMPTS;
                    break;
                case 2:
                    maxRange = MEDIUM_MAX_RANGE;
                    maxAttempts = MEDIUM_MAX_ATTEMPTS;
                    break;
                case 3:
                    maxRange = HARD_MAX_RANGE;
                    maxAttempts = HARD_MAX_ATTEMPTS;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    continue;
            }

            int number = random.nextInt(maxRange) + 1;
            int attempts = 0;

            System.out.printf("Guess the number between 1 and %d. You have %d attempts.\n", maxRange, maxAttempts);

            while (attempts < maxAttempts) {
                System.out.print("Enter your guess: ");
                int guess = scanner.nextInt();
                attempts++;

                if (guess == number) {
                    int score = calculateScore(maxAttempts, attempts);
                    totalScore += score;
                    System.out.printf("Congratulations! You guessed the number in %d attempts. Score: %d\n", attempts, score);
                    break;
                } else {
                    System.out.println("Incorrect guess. Try again.");

                    if (guess < number) {
                        System.out.println("The number is higher.");
                    } else {
                        System.out.println("The number is lower.");
                    }
                }
            }

            if (attempts == maxAttempts) {
                System.out.println("Out of attempts!");
                System.out.printf("The correct number was: %d\n", number);
            }
        }

        System.out.printf("Total Score: %d\n", totalScore);
        scanner.close();
    }

    private static int calculateScore(int maxAttempts, int attemptsTaken) {
        // Higher score for fewer attempts
        double scorePercentage = (double) attemptsTaken / maxAttempts;
        int maxScore = 100;
        return (int) (maxScore * (1 - scorePercentage));
    }
}

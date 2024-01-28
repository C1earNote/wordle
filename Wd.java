package Wordle;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Wd {
    public static void main(String[] args) {
        String word = WordDecide();
        System.out.println("You have 6 tries to guess the correct word");

        GuessWord(word);
    }

    public static String WordDecide() {
        try (BufferedReader allW = new BufferedReader(new FileReader("/home/tanishq/Documents/Java Code/Wordle/AllW.txt"))) {
            Random random = new Random();
            double stopProb = 0.1;
            String line;

            // Store lines in an ArrayList
            ArrayList<String> lines = new ArrayList<>();

            while ((line = allW.readLine()) != null) {
                lines.add(line);
            }

            // Randomly print lines until the stopping condition is met
            int randomIndex = 0;
            while (random.nextDouble() > stopProb) {
                // Randomly select an index from the ArrayList
                randomIndex = random.nextInt(lines.size());
            }

            // Print the last selected line
            System.out.println(lines.get(randomIndex));
            return lines.get(randomIndex);

        } catch (IOException e) {
            e.printStackTrace();
            return "Error";
        }
    }

    public static void GuessWord(String word) {
        Scanner scan = new Scanner(System.in);
        boolean c = false;
        word = word.toLowerCase();

        for (int i = 1; i <= 6; i++) {
            System.out.print("Attempt " + i + ": ");
            String guess = scan.nextLine().toLowerCase(); // Convert the guess to lowercase

            if (guess.length() != 5) {
                System.out.println("The word length should be equal to 5");
                i--;
                continue;  // Skip the rest of the loop for an invalid input
            }

            // Check if the guessed word exists in the main data file
            if (!isWordInFile(guess)) {
                System.out.println("The word does not exist in the main data file. Try again.");
                i--;
                continue;
            }

            // Check for correct letters at correct positions
            for (int j = 0; j < word.length(); j++) {
                if (word.charAt(j) == guess.charAt(j)) {
                    System.out.println("#" + (j + 1) + " letter is correct.");
                } else if (word.indexOf(guess.charAt(j)) != -1) {
                    System.out.println(guess.charAt(j) + " is in the word at a different place");
                }
            }

            // Check if the entire word is correct
            if (word.equalsIgnoreCase(guess)) { // Case-insensitive comparison
                System.out.println("Congratulations! You have guessed the word correctly.");
                c = true;
                break;  // Exit the loop if the word is correct
            } else {
                System.out.println("Wrong word.");
            }
        }

        if (!c) {
            System.out.println("You've run out of attempts. The correct word was: " + word);
        }

        scan.close();
    }

    public static boolean isWordInFile(String guess) {
        try (BufferedReader allW = new BufferedReader(new FileReader("/home/tanishq/Documents/Java Code/Wordle/AllW.txt"))) {
            String line;

            while ((line = allW.readLine()) != null) {
                if (line.trim().equalsIgnoreCase(guess)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }
}

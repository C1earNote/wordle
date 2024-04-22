package Wordle.Wordle; // Updated package declaration to match folder structure

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class Wd {
    public static void main(String[] args) {
        String word = wordDecide();
        if (word.equals("Error")) {
            System.out.println("Could not load word. Exiting.");
            return;
        }
        System.out.println("You have 6 tries to guess the correct word");
        guessWord(word);
    }

    public static String wordDecide() {
        String word = "Error";
        try (BufferedReader AllW = new BufferedReader(new FileReader("AllW.txt"))) {
            String line;
            int lineNumber = 0;
            Random random = new Random();
            while ((line = AllW.readLine()) != null) {
                lineNumber++;
                if (random.nextInt(lineNumber) == 0) {
                    word = line;
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading words: " + e.getMessage());
        }
        return word;
    }

    public static void guessWord(String word) {
        Scanner scan = new Scanner(System.in);
        boolean guessedCorrectly = false;
        word = word.toLowerCase();

        for (int attempt = 1; attempt <= 6; attempt++) {
            System.out.print("Attempt " + attempt + ": ");
            String guess = scan.nextLine().toLowerCase();

            if (guess.length() != word.length()) {
                System.out.println("The word length should be " + word.length());
                attempt--;
                continue;
            }

            if (!isWordInFile(guess)) {
                System.out.println("The word does not exist.");
                attempt--;
                continue;
            }

            int correctPositions = 0;
            int correctLetters = 0;
            for (int j = 0; j < word.length(); j++) {
                if (word.charAt(j) == guess.charAt(j)) {
                    correctPositions++;
                } else if (word.contains(String.valueOf(guess.charAt(j)))) {
                    correctLetters++;
                }
            }

            if (correctPositions == word.length()) {
                System.out.println("Congratulations! You have guessed the word correctly.");
                guessedCorrectly = true;
                break;
            } else {
                System.out.println("#" + correctPositions + " letter(s) in correct position(s).");
                System.out.println("#" + correctLetters + " letter(s) in the word but in different position(s).");
            }
        }

        if (!guessedCorrectly) {
            System.out.println("You've run out of attempts. The correct word was: " + word);
        }

        scan.close();
    }

    public static boolean isWordInFile(String guess) {
        try (BufferedReader AllW = new BufferedReader(new FileReader("AllW.txt"))) {
            String line;
            while ((line = AllW.readLine()) != null) {
                if (line.trim().equalsIgnoreCase(guess)) {
                    return true;
                }
            }
        } catch (IOException e) {
            System.out.println("Error checking word: " + e.getMessage());
        }
        return false;
    }
}

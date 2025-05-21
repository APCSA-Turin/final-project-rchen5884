package com.example;

import javax.swing.*;

import java.awt.Image;
import java.net.URL;
import org.json.JSONObject;
import java.util.Scanner;

public class App extends JFrame {
    private JLabel displayField;
    private static String currentImageUrl;
    
    public static void main(String[] args) {
        DogURL url = new DogURL();
        currentImageUrl = url.getURL();
        System.out.println(currentImageUrl);
        if (currentImageUrl.isEmpty()) {
            System.err.println("Failed to get image URL from API");
            return;
        }

        // Show GUI immediately
        SwingUtilities.invokeLater(() -> new App(currentImageUrl));
        
        // Start the guessing game in console
        startGuessingGame(getRandomDog(currentImageUrl));
    }

    public App(String imageUrl) {
        setTitle("Guess the Dog Breed"); // title of the GUI screen
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // the GUI closes when the program is closed
        setSize(800, 600); // the size of the GUI screen
        
        try {
            // Load image with better scaling
            ImageIcon originalIcon = new ImageIcon(new URL(imageUrl)); // Make an "ImageIcon" object using the generated URL
            // Creates the image based on the ImageIcon and sets the dimensions of the image
            Image scaledImage = originalIcon.getImage().getScaledInstance(750, 550, Image.SCALE_SMOOTH); 
            displayField = new JLabel(new ImageIcon(scaledImage)); //
            displayField.setHorizontalAlignment(JLabel.CENTER);
            add(displayField);
        } catch (Exception e) { // if the image somehow doesn't load (this should never happen)
            System.out.println("Error loading image: " + e.getMessage()); // Prints the error in the console
            add(new JLabel("Could not load image", JLabel.CENTER)); // lets the user know the image couldn't load in the GUI
        }
        
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void startGuessingGame(String correctDog) {
        // Small delay to ensure GUI is up
        try { Thread.sleep(500); } catch (InterruptedException e) {}
        
        Scanner scanner = new Scanner(System.in);
        int count = 5;
        Hint h = new Hint(correctDog);
        String hint = h.getHint();

        System.out.println("\nGuess the dog breed shown in the window!");
        
        while (count > 0) {
            System.out.println("\nYou have " + count + " guesses left!");
            
            if (count == 1) {
                System.out.println("Type 'hint' for a hint.");
            }
            
            System.out.print("Your guess: ");
            String guess = scanner.nextLine().trim().toLowerCase();
            
            if (guess.equals(correctDog.toLowerCase())) { // if the guess is correct
                System.out.println("\nYou win! It's a " + correctDog + "!");
                break;
            } else if (count == 1 && guess.equals("hint")) { // if they wish to get a hint
                System.out.println("Hint: " + hint);
            } else { // if they get it wrong
                count--; // remove one guess
                if (count == 0) { // if they run out of guesses, end the program
                    System.out.println("\nYou ran out of guesses.");
                    System.out.println("The correct breed was: " + correctDog);
                } else { // if they still have guesses left, allow them to guess again
                    System.out.println("Try again!");
                }
            }
        }
        scanner.close();
    }

    public static String getRandomDog(String imageUrl) {
        int startIdx = imageUrl.indexOf("breeds/") + 7;
        String temp = imageUrl.substring(startIdx);
        int endIdx = temp.indexOf("/");
        String breed = temp.substring(0, endIdx);

        return breed.contains("-") ? breed.substring(0, breed.indexOf("-")) : breed;
    }
}
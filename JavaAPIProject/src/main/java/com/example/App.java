package com.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import javax.imageio.ImageIO;
import java.io.IOException;

public class App extends JFrame {
    private JLabel imageLabel;
    private JLabel promptLabel;
    private JTextField guessField;
    private JButton submitButton;
    private JLabel hintLabel;
    private JLabel statsLabel;
    
    private String correctDog;
    private int guessesLeft = 5;
    private double wins = 0;
    private int rounds = 0;
    
    public App() {
        setTitle("Dog Breed Guessing Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 700);
        setLayout(new BorderLayout());
        
        // Image display
        imageLabel = new JLabel("", JLabel.CENTER);
        imageLabel.setPreferredSize(new Dimension(750, 550));
        add(imageLabel, BorderLayout.CENTER);
        
        // Game controls panel
        JPanel controlPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // Adds this text onto the GUI at the specified coordinates
        promptLabel = new JLabel("Guess the dog breed:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        controlPanel.add(promptLabel, gbc);
        
        // Allows the user to guess the answer
        guessField = new JTextField(20);
        gbc.gridx = 1;
        controlPanel.add(guessField, gbc);
        
        // Allows the user to enter their answer
        submitButton = new JButton("Submit");
        gbc.gridx = 2;
        controlPanel.add(submitButton, gbc);
        
        // Establishes the location the hint will appear at
        hintLabel = new JLabel(" ");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        controlPanel.add(hintLabel, gbc);
        
        // Establishes the location the players' winrate will appear at
        statsLabel = new JLabel(" ");
        gbc.gridy = 2;
        controlPanel.add(statsLabel, gbc);
        
        // Adds all of the previously established things onto the GUI
        add(controlPanel, BorderLayout.SOUTH);
        
        // Set up event handlers
        submitButton.addActionListener(this::handleGuess);
        guessField.addActionListener(this::handleGuess);
        
        // Start first round
        startNewRound();
        
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    public void startNewRound() {
        rounds++;
        guessesLeft = 5;
        
        try {
            // Get random dog image
            String imageUrl = new DogURL().getURL();
            correctDog = getRandomDog(imageUrl);
            
            // Load and display image
            Image image = ImageIO.read(new URL(imageUrl)).getScaledInstance(750, 550, Image.SCALE_SMOOTH);
            imageLabel.setIcon(new ImageIcon(image));
            
            // Update UI
            promptLabel.setText("Guess the dog breed (" + guessesLeft + " guesses left):");
            hintLabel.setText(" ");
            guessField.setText("");
            guessField.requestFocus();
            
        } catch (IOException e) { // has a 0% chance of happening
            System.out.println("bruh");
        }
    }
    
    public void handleGuess(ActionEvent e) {
        String guess = guessField.getText().trim().toLowerCase();
        
        if (guess.equals(correctDog.toLowerCase())) {
            // Correct guess
            wins++;
            showResult(true);
        } else {
            // Incorrect guess
            guessesLeft--;
            
            if (guessesLeft == 1) { // Displays the hint once guesses reaches one
                hintLabel.setText("Hint: " + new Hint(correctDog).getHint());
            }
            
            if (guessesLeft > 0) { // Allows them to retry
                promptLabel.setText("Try again! (" + guessesLeft + " guesses left):");
                guessField.setText("");
                guessField.requestFocus();
            } else { // if they run out of guesses
                showResult(false);
            }
        }
    }
    
    public void showResult(boolean won) {
        String message = "";
        if (won) {
            message = "You win! It's a " + correctDog + "!" ;
        }
        else {
            message = "Out of guesses! It was a " + correctDog + ".";
        }
        // Have a popup displaying if the player has won or not
        JOptionPane.showMessageDialog(this, message, "Round Over", JOptionPane.INFORMATION_MESSAGE);
        
        // Update stats
        double winRate = (rounds == 0) ? 0 : (wins / rounds) * 100;
        statsLabel.setText(String.format("Wins: %d/%d (%.1f%%)", (int)wins, rounds, winRate));
        
        // Ask to play again
        int option = JOptionPane.showConfirmDialog(this, "Play again?", "Continue?", JOptionPane.YES_NO_OPTION);
        
        // If they click yes
        if (option == JOptionPane.YES_OPTION) {
            startNewRound();
        } else { // If they click no
            System.exit(0);
        }
    }
    
    public String getRandomDog(String imageUrl) {
        int startIdx = imageUrl.indexOf("breeds/") + 7;
        String temp = imageUrl.substring(startIdx);
        int endIdx = temp.indexOf("/");
        String breed = temp.substring(0, endIdx);

        return breed.contains("-") ? breed.substring(0, breed.indexOf("-")) : breed;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(App::new);
    }
}
 

// package com.example;

// import javax.swing.*;
// import java.awt.Image;
// import java.net.URL;
// import java.util.Scanner;

// public class App extends JFrame {
//     private JLabel displayField;
//     private static String currentImageUrl;
//     private static boolean play = true;
//     private static double wins = 0.0;
//     private static int rounds = 0;
//     private static Scanner mainScanner = new Scanner(System.in); // Single scanner for entire program
    
//     public static void main(String[] args) {
//         while (play) {
//             DogURL url = new DogURL();
//             currentImageUrl = url.getURL();
//             System.out.println(currentImageUrl);
            
//             if (currentImageUrl.isEmpty()) {
//                 System.err.println("Failed to get image URL from API");
//                 continue; // Skip to next iteration instead of returning
//             }

//             // Show GUI immediately
//             SwingUtilities.invokeLater(() -> {
//                 JFrame frame = new App(currentImageUrl);
//                 frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Changed to dispose
//             });
            
//             // Start the guessing game
//             startGuessingGame(getRandomDog(currentImageUrl));
            
//             // Calculate and display win rate
//             double winRate = (rounds == 0) ? 0 : (wins / rounds) * 100;
//             System.out.printf("Current stats: %.1f%% win rate (%d wins out of %d rounds)%n", 
//                             winRate, (int)wins, rounds);
//         }
//         mainScanner.close(); // Close scanner only when completely done
//     }

//     public App(String imageUrl) {
//         setTitle("Guess the Dog Breed");
//         setSize(800, 600);
        
//         try {
//             ImageIcon originalIcon = new ImageIcon(new URL(imageUrl));
//             Image scaledImage = originalIcon.getImage().getScaledInstance(750, 550, Image.SCALE_SMOOTH);
//             displayField = new JLabel(new ImageIcon(scaledImage));
//             displayField.setHorizontalAlignment(JLabel.CENTER);
//             add(displayField);
//         } catch (Exception e) {
//             System.out.println("Error loading image: " + e.getMessage());
//             add(new JLabel("Could not load image", JLabel.CENTER));
//         }
        
//         setLocationRelativeTo(null);
//         setVisible(true);
//     }

//     public static void startGuessingGame(String correctDog) {
//         try { Thread.sleep(500); } catch (InterruptedException e) {}
        
//         int count = 5;
//         Hint h = new Hint(correctDog);
//         String hint = h.getHint();

//         System.out.println("\nGuess the dog breed shown in the window!");
        
//         while (count > 0) {
//             System.out.println("\nYou have " + count + " guesses left!");
            
//             if (count == 1) {
//                 System.out.println("Type 'hint' for a hint.");
//             }
            
//             System.out.print("Your guess: ");
//             String guess = mainScanner.nextLine().trim().toLowerCase();
            
//             if (guess.equals(correctDog.toLowerCase())) {
//                 System.out.println("\nYou win! It's a " + correctDog + "!");
//                 wins++;
//                 rounds++;
//                 askToPlayAgain();
//                 return;
//             } else if (count == 1 && guess.equals("hint")) {
//                 System.out.println("Hint: " + hint);
//             } else {
//                 count--;
//                 if (count == 0) {
//                     System.out.println("\nYou ran out of guesses.");
//                     System.out.println("The correct breed was: " + correctDog);
//                     rounds++;
//                     askToPlayAgain();
//                 } else {
//                     System.out.println("Try again!");
//                 }
//             }
//         }
//     }

//     private static void askToPlayAgain() {
//         System.out.print("Would you like to play again? (yes/no): ");
//         String replay = mainScanner.nextLine().trim().toLowerCase();
//         play = replay.equals("yes") || replay.equals("y");
        
//         if (!play) {
//             double winRate = (rounds == 0) ? 0 : (wins / rounds) * 100;
//             System.out.println("\nFinal Results:");
//             System.out.println("Wins: " + (int)wins + " out of " + rounds + " rounds");
//             System.out.println("Win Rate: " + String.format("%.1f", winRate) + "%");
//             System.out.println("Thanks for playing!");
//         }
//     }

//     public static String getRandomDog(String imageUrl) {
//         int startIdx = imageUrl.indexOf("breeds/") + 7;
//         String temp = imageUrl.substring(startIdx);
//         int endIdx = temp.indexOf("/");
//         String breed = temp.substring(0, endIdx);

//         return breed.contains("-") ? breed.substring(0, breed.indexOf("-")) : breed;
//     }
// }
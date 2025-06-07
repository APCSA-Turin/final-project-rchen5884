package com.example;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

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
        
        // This is the image display
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
            
            // Updates UI after each guess
            promptLabel.setText("Guess the dog breed (" + guessesLeft + " guesses left):");
            hintLabel.setText(" ");
            guessField.setText("");
            guessField.requestFocus();
            
        } catch (IOException e) { // has a 0% chance of happening
            System.out.println("bruh");
        }
    }
    
    public void handleGuess(ActionEvent e) {
        String guess = guessField.getText().trim().toLowerCase(); // makes their guess lowercase
        
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
        double winRate = 0;
        if (rounds == 0) {
            winRate = 0;
        }
        else {
            winRate = (wins / rounds) * 100;
        }

        // In the showResult method, replace the statsLabel.setText() line with:
        String statsText = "Games: " + rounds + " | Wins: " + (int)wins + " | Success Rate: " + Math.round(winRate) + " %";
        statsLabel.setText(statsText);
        
        // Ask to play again
        int option = JOptionPane.showConfirmDialog(this, "Play again?", "Continue?", JOptionPane.YES_NO_OPTION);
        
        // If they click yes
        if (option == JOptionPane.YES_OPTION) {
            startNewRound();
        } else { // If they click no
            System.exit(0);
        }
    }
    
    public String getRandomDog(String imageUrl) { // gets the dog breed from the URL link itself
        int startIdx = imageUrl.indexOf("breeds/") + 7;
        String temp = imageUrl.substring(startIdx);
        int endIdx = temp.indexOf("/");
        String breed = temp.substring(0, endIdx);

        if (breed.contains("-")) { // cuts the String off of the location of the dash (after the dash is irrelevant things)
            return breed.substring(0, breed.indexOf("-"));
        }
        return breed;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(App::new);
    }
}
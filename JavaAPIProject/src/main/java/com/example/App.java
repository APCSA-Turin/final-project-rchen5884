package com.example;
import javax.swing.*;
import java.net.URL;
import org.json.JSONObject;
import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class App extends JFrame {
    JFrame frame;
    JLabel displayField;
    ImageIcon image;
    public static void main( String[] args ) {
        Scanner scanner = new Scanner(System.in);

        String correctDog = getRandomDog();
        // System.out.println(correctDog);
        // App i = new App(getURL());
        
        int count = 5;
        String underlines = "";
        for (int i = 0; i < correctDog.length() - 2; i++) {
            underlines += "_";
        }

        underlines = correctDog.substring(0,1) + underlines;
        String hint = underlines + correctDog.substring(correctDog.length() - 1);

        

        for (int i = 0; i < count; i--) {
            System.out.println("You have " + count + " guesses left!");
            System.out.println("What breed of dog is this?: " );
            System.out.println("");
            if (count == 1) {
                System.out.println("Type Hint for a hint. ");
            }
            String guess = scanner.nextLine();
            if (guess.equals(correctDog)) {
                System.out.println("You win!");
                break;
            }
            else {
                if (count == 1 && guess.equals("Hint")) {
                    System.out.println(hint);
                    count++;
                }
                if (count == 1) {
                    System.out.println("You ran out of guesses.");
                    System.out.println("The correct dog breed was: " + correctDog);
                    break;
                }
            }
            count--;
        }

        

    }


    public App(String thing) {
        frame = new JFrame("Image Display Test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        try {
            image = new ImageIcon(getClass().getResource(thing));
            displayField = new JLabel(thing);
        } 
        catch (Exception e) {
            System.out.println("Image cannot be found");
        }
        frame.setSize(400,400);
        frame.setVisible(true);
    }


    public static String getRandomDog() {
        String image = getURL();
        System.out.println("Image URL: " + image);

        int startIdx = image.indexOf("breeds") + 7;
        String temp = image.substring(startIdx);
        int endIdx = temp.indexOf("/");
        String guess = temp.substring(0, endIdx);

        if (guess.indexOf("-") >= 0) {
            return guess.substring(0, guess.indexOf("-"));
        }
        return guess;
    }

    public static String getURL() {
        String data = "";
        try {
            // Get JSON data from API
            String JSONdata = API.getData("https://dog.ceo/api/breeds/image/random");
            data = JSONdata;
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        try {
            // Parse the JSON string
            JSONObject obj = new JSONObject(data);
            String image = obj.getString("message"); // Extract the image URL
        } catch (Exception e) {
            e.printStackTrace();
        }

        JSONObject obj = new JSONObject(data);
        String image = obj.getString("message"); // Extract image URL
        return image;
    }
}
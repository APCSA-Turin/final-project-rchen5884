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
        String data = "";
        try {
            // Get JSON data from API
            String JSONdata = API.getData("https://dog.ceo/api/breeds/image/random");
            System.out.println(JSONdata);
            data = JSONdata;
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        try {
            // Parse the JSON string
            JSONObject obj = new JSONObject(data);
            String image = obj.getString("message"); // Extract the image URL
            System.out.println(image);    
        } catch (Exception e) {
            e.printStackTrace();
        }

        JSONObject obj = new JSONObject(data);
        String image = obj.getString("message"); // Extract image URL
        System.out.println("Image URL: " + image);

        App i = new App(image);
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
}
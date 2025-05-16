package com.example;
import javax.swing.*;
import java.net.URL;
import org.json.JSONObject;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
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

        // Display in a GUI window
        JFrame frame = new JFrame("Dog Image");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);
        ImageIcon imageIcon = new ImageIcon(new URL(image));
        JLabel label = new JLabel(imageIcon);
        frame.add(label);
        frame.setVisible(true);
    }
}
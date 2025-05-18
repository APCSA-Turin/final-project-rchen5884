package com.example;
import javax.swing.*;

public class Image extends JFrame{
    JFrame frame;
    JLabel displayField;
    ImageIcon image;

    public Image() {
        frame = new JFrame("Image Display Test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        try {
            image = new ImageIcon(getClass().getResource());
        } 
        catch(Exception e) {
            System.out.println("Image cannot be found");
        }
    }
    public static void main(String[] args) {
        
    }
}

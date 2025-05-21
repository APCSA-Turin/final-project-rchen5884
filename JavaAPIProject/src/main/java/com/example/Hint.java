package com.example;

import javax.swing.*;

import java.awt.Image;
import java.net.URL;
import org.json.JSONObject;
import java.util.Scanner;

public class Hint {
    private String hint;
    public Hint(String correctDog) {
        String underlines = "_".repeat(correctDog.length() - 2);
        hint = correctDog.substring(0, 1) + underlines + 
               correctDog.substring(correctDog.length() - 1);
    }

    public String getHint() {
        return hint;
    }

}

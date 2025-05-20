package com.example;
/*EASY WAY BUT REQUIRES MAVEN DEPENDENCY.. WE WILL TALK ABOUT MAVEN NEXT PAGE*/
import org.json.JSONObject;
    
public class JSONObjectProcessing {
public static void main(String[] args) {
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
    }
}

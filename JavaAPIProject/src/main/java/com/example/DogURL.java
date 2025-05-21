package com.example;

import org.json.JSONObject;

public class DogURL {
    private String URL;
    public DogURL() {
        try { 
            String JSONdata = API.getData("https://dog.ceo/api/breeds/image/random"); // gets a random string of JSON data for a dog
            JSONObject obj = new JSONObject(JSONdata); // make the string jsondata into an object of JSONOBject type
            URL = obj.getString("message"); // returns the URL of the dog
        } catch (Exception e) { // if the link doesn't work (shouldn't happen ever)
            e.printStackTrace(); 
        }
    }

    public String getURL() {
        return URL;
    }
}

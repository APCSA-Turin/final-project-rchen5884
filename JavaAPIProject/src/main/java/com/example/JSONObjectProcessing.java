package com.example;
/*EASY WAY BUT REQUIRES MAVEN DEPENDENCY.. WE WILL TALK ABOUT MAVEN NEXT PAGE*/
import org.json.JSONObject;
    
public class JSONObjectProcessing {
public static void main(String[] args) {
    /*this string is just an example, you would be pulling the json data from a server*/
    String jsonString = "{\"name\":\"Pikachu\",\"type\":\"Electric\",\"level\":5}";
    //create the JSON object 
    JSONObject obj = new JSONObject(jsonString);
    //you can get the value of the key by calling the getString(key) method of JSON Object
    String name = obj.getString("name");
    String type = obj.getString("type");
    int level = obj.getInt("level");            
    System.out.println(name + " is an " + type + " type at level " + level);
    }
}

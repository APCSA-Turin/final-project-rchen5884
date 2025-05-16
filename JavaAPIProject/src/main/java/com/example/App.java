package com.example;
import java.io.IOException;
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
            String JSONdata = API.getData("https://dog.ceo/api/breeds/image/random");
            System.out.println(JSONdata);
            data = JSONdata;
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        JSONObject obj = new JSONObject(data);
        String image = obj.toString();

        int idx = image.indexOf(",");
        int idx2 = image.indexOf(":");
        image = image.substring(idx2+1, idx);
        
        System.out.println(image);
    }
}

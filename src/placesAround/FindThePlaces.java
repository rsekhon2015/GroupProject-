package placesAround;


import java.io.*;
import java.net.*;
/**
 * Created by benjaminhowland on 11/16/15.
 */


public class FindThePlaces {

    public void myjavaclient() throws Exception {

        //here we will take in the locations find the places within 3 miles and send them to the pint files

        URL USER_SITE = new URL("some input from the user");
        URL BASE_API = new URL("https://maps.googleapis.com/maps/api/place");
        //THIS IS THE KEY! This is needed for using the places api!!!!
        String API_KEY = "AIzaSyC0gSFl_-gZR0XZ1Rzvne20AF7T8H4Qblg";

        HttpURLConnection connection = (HttpURLConnection) USER_SITE.openConnection();

        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        PrintWriter out = new PrintWriter(connection.getOutputStream());
        String bars = "Bars: " + URLEncoder.encode("input from google", "UTF-8");
        String food = "Food: " + URLEncoder.encode("input from google", "UTF-8");
        String schools = "Schools: " + URLEncoder.encode("input from google", "UTF-8");
        String gas = "Gas Stations: " + URLEncoder.encode("input from google", "UTF-8");
        String restaurants = "Restaurants: " + URLEncoder.encode("input from google", "UTF-8");

        out.println("<p>" + bars + food + schools + gas + restaurants + "</p>");
        out.close();

    }
}

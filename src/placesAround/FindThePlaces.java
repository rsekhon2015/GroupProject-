package placesAround;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import sun.rmi.runtime.Log;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by benjaminhowland on 11/16/15.
 */

@WebService
public class FindThePlaces {

    //here we will take in the locations find the places within 3 miles and send them to the print files
    //to printout in HTML so people can put them on their site easy

    private static final String BASE_API = "https://maps.googleapis.com/maps/api/place";
    //THIS IS THE KEY! This is needed for using the places api!!!!
    private static final String API_KEY = "AIzaSyC0gSFl_-gZR0XZ1Rzvne20AF7T8H4Qblg";
    private static final String TYPE_AUTOCOMPLETE = "/autocomplete";
    private static final String TYPE_DETAILS = "/details";
    private static final String TYPE_SEARCH = "/search";
    private static final String LOG_TAG = "PlacesGroupProject";
    private static final String OUT_JSON = "/json";
    static Logger log = Logger.getLogger(FindThePlaces.class.getName());


    public static ArrayList<Place> autocomplete(String input) {
        ArrayList<Place> resultList = null;

        HttpURLConnection conn = null;
        StringBuilder jsonResults = new StringBuilder();
        try {
            StringBuilder sb = new StringBuilder(BASE_API);
            sb.append(TYPE_AUTOCOMPLETE);
            sb.append(OUT_JSON);
            sb.append("?sensor=false");
            sb.append("&key=" + API_KEY);
            sb.append("&input=" + URLEncoder.encode(input, "utf8"));

            URL url = new URL(sb.toString());
            conn = (HttpURLConnection) url.openConnection();
            InputStreamReader in = new InputStreamReader(conn.getInputStream());

            int read;
            char[] buff = new char[1024];
            while ((read = in.read(buff)) != -1) {
                jsonResults.append(buff, 0, read);
            }
        } catch (MalformedURLException e) {
            log.debug("Error processing Places API URL", e);
            return resultList;
        } catch (IOException e) {
            log.debug("Error connecting to Places API", e);
            return resultList;
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }

        try {
            // Create a JSON object hierarchy from the results
            JSONObject jsonObj = new JSONObject(jsonResults.toString());
            JSONArray predsJsonArray = jsonObj.getJSONArray("predictions");

            // Extract the Place descriptions from the results
            resultList = new ArrayList<Place>(predsJsonArray.length());
            for (int i = 0; i < predsJsonArray.length(); i++) {
                Place place = new Place();
                place.reference = predsJsonArray.getJSONObject(i).getString("reference");
                place.name = predsJsonArray.getJSONObject(i).getString("description");
                resultList.add(place);
            }
        } catch (JSONException e) {
            log.debug("Error processing JSON results", e);
        }

        return resultList;
    }

    @WebMethod
    public static ArrayList<Place> search(String keyword, double lat, double lng, int radius) {
        ArrayList<Place> resultList = null;

        HttpURLConnection conn = null;
        StringBuilder jsonResults = new StringBuilder();
        try {
            StringBuilder sb = new StringBuilder(BASE_API);
            sb.append(TYPE_SEARCH);
            sb.append(OUT_JSON);
            sb.append("?sensor=false");
            sb.append("&key=" + API_KEY);
            sb.append("&keyword=" + URLEncoder.encode(keyword, "utf8"));
            sb.append("&location=" + String.valueOf(lat) + "," + String.valueOf(lng));
            sb.append("&radius=" + String.valueOf(radius));

            URL url = new URL(sb.toString());
            conn = (HttpURLConnection) url.openConnection();
            InputStreamReader in = new InputStreamReader(conn.getInputStream());

            int read;
            char[] buff = new char[1024];
            while ((read = in.read(buff)) != -1) {
                jsonResults.append(buff, 0, read);
            }
        } catch (MalformedURLException e) {
            log.debug("Error processing Places API URL", e);
            return resultList;
        } catch (IOException e) {
            log.debug("Error connecting to Places API", e);
            return resultList;
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }

        try {
            // Create a JSON object hierarchy from the results
            JSONObject jsonObj = new JSONObject(jsonResults.toString());
            JSONArray predsJsonArray = jsonObj.getJSONArray("results");

            // Extract the Place descriptions from the results
            resultList = new ArrayList<Place>(predsJsonArray.length());
            for (int i = 0; i < predsJsonArray.length(); i++) {
                Place place = new Place();
                place.reference = predsJsonArray.getJSONObject(i).getString("reference");
                place.name = predsJsonArray.getJSONObject(i).getString("name");
                resultList.add(place);
            }
        } catch (JSONException e) {
            log.debug("Error processing JSON results", e);
        }

        return resultList;
    }

    public static Place details(String reference) {
        HttpURLConnection conn = null;
        StringBuilder jsonResults = new StringBuilder();
        try {
            StringBuilder sb = new StringBuilder(BASE_API);
            sb.append(TYPE_DETAILS);
            sb.append(OUT_JSON);
            sb.append("?sensor=false");
            sb.append("&key=" + API_KEY);
            sb.append("&reference=" + URLEncoder.encode(reference, "utf8"));

            URL url = new URL(sb.toString());
            conn = (HttpURLConnection) url.openConnection();
            InputStreamReader in = new InputStreamReader(conn.getInputStream());

            // Load the results into a StringBuilder
            int read;
            char[] buff = new char[1024];
            while ((read = in.read(buff)) != -1) {
                jsonResults.append(buff, 0, read);
            }
        } catch (MalformedURLException e) {
            log.debug("Error processing Places API URL", e);
            return null;
        } catch (IOException e) {
            log.debug("Error connecting to Places API", e);
            return null;
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }

        Place place = null;
        try {
            // Create a JSON object hierarchy from the results
            JSONObject jsonObj = new JSONObject(jsonResults.toString()).getJSONObject("result");

            place = new Place();
            place.name = jsonObj.getString("name");
            place.formatted_address = jsonObj.getString("formatted_address");
            if (jsonObj.has("formatted_phone_number")) {
                place.formatted_phone_number = jsonObj.getString("formatted_phone_number");
            }
        } catch (JSONException e) {
            log.debug("Error processing JSON results", e);
        }

        return place;
    }


    protected void placesAroundLocation(String address) {

        //being called by Main
        //planning phase
    }


}


/**
package placesAround;


import java.io.*;
import java.net.*;

 * Created by benjaminhowland on 11/16/15.
 */

/*
public class FindThePlaces {

    public void myjavaclient() throws Exception {

        //here we will take in the locations find the places within 3 miles and send them to the pint files

        URL USER_SITE = new URL("some input from the user");
        URL BASE_API = new URL("https://maps.googleapis.com/maps/api/place");
        //THIS IS THE KEY! This is needed for using the places api!!!!
        String API_KEY = "AIzaSyC0gSFl_-gZR0XZ1Rzvne20AF7T8H4Qblg";

        HttpURLConnection connection = (HttpURLConnection) USER_SITE.openConnection();

        //get the stuff from google





        //post to site
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
    */


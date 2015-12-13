package example;
/*
import com.sun.jersey.api.container.httpserver.HttpServerFactory;
import com.sun.net.httpserver.HttpServer;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
*/
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;



/**
 * Created by Ranjit on 12/12/2015.

// The Java class will be hosted at the URI path "/gp"
@Path("/gp")
public class GooglePlacesGroupProject {
    // The Java method will process HTTP GET requests
    @GET
    // The Java method will produce content identified by the MIME Media type "text/plain"
    @Produces("text/plain")
    public String getClichedMessage() {
        // Return some cliched textual content
        return "Hello World";
    }

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServerFactory.create("http://localhost:9998/");
        server.start();

        System.out.println("Server running");
        System.out.println("Visit: http://localhost:9998/gp");
        System.out.println("Hit return to stop...");
        System.in.read();
        System.out.println("Stopping server");
        server.stop(0);
        System.out.println("Server stopped");
    }
}
*/

/**
 * @author Ranjit Sekhon
 * 12/12/2015
 */
public class PlacesService {
    private final Logger logger = Logger.getLogger(String.valueOf(PlacesService.class));

    private static final String PLACES_API_BASE = "https://maps.googleapis.com/maps/api/place";
    private static final String TYPE_AUTOCOMPLETE = "/autocomplete";
    private static final String TYPE_DETAILS = "/details";
    private static final String TYPE_SEARCH = "/search";
    private static final String OUT_JSON = "/json";

    // KEY!
    private static final String API_KEY = "AIzaSyCoMDYtoiZegI871v33nMp0OklyYBAGFS4"; //Google Key


    public ArrayList<Place> autocomplete(String input) {
        ArrayList<Place> resultList = null;

        HttpURLConnection conn = null;
        StringBuilder jsonResults = new StringBuilder();
        try {

            URL url = new URL(PLACES_API_BASE + TYPE_AUTOCOMPLETE + OUT_JSON + "?sensor=false" + "&key=" + API_KEY + "&input=" + URLEncoder.encode(input, "utf8"));
            conn = (HttpURLConnection) url.openConnection();
            InputStreamReader in = new InputStreamReader(conn.getInputStream());

            int read;
            char[] buff = new char[1024];
            while ((read = in.read(buff)) != -1) {
                jsonResults.append(buff, 0, read);
            }
        } catch (MalformedURLException e) {
            logger.isLoggable(Level.INFO);
            //("Error processing Places API URL", e);
            return null;
        } catch (IOException e) {
            logger.isLoggable(Level.INFO);
            //logger.error("Error connecting to Places API", e);
            return null;
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
            logger.isLoggable(Level.INFO);
            //logger.error("Error processing JSON results", e);
        }

        return resultList;
    }

    public ArrayList<Place> search(String keyword, double lat, double lng, int radius) {
        ArrayList<Place> resultList = null;

        HttpURLConnection conn = null;
        StringBuilder jsonResults = new StringBuilder();
        try {

            URL url = new URL(PLACES_API_BASE + TYPE_SEARCH + OUT_JSON + "?sensor=false" + "&key=" + API_KEY + "&keyword=" + URLEncoder.encode(keyword, "utf8") + "&location=" + String.valueOf(lat) + "," + String.valueOf(lng) + "&radius=" + String.valueOf(radius));
            conn = (HttpURLConnection) url.openConnection();
            InputStreamReader in = new InputStreamReader(conn.getInputStream());

            int read;
            char[] buff = new char[1024];
            while ((read = in.read(buff)) != -1) {
                jsonResults.append(buff, 0, read);
            }
        } catch (MalformedURLException e) {
            logger.isLoggable(Level.INFO);
            //logger.error("Error processing Places API URL", e);
            return null;
        } catch (IOException e) {
            logger.isLoggable(Level.INFO);
            //logger.error("Error connecting to Places API", e);
            return null;
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
            logger.isLoggable(Level.INFO);
        }

        return resultList;
    }

    public Place details(String reference) {
        HttpURLConnection conn = null;
        StringBuilder jsonResults = new StringBuilder();
        try {

            URL url = new URL(PLACES_API_BASE + TYPE_DETAILS + OUT_JSON + "?sensor=false" + "&key=" + API_KEY + "&reference=" + URLEncoder.encode(reference, "utf8"));
            conn = (HttpURLConnection) url.openConnection();
            InputStreamReader in = new InputStreamReader(conn.getInputStream());

            // Load the results into a StringBuilder
            int read;
            char[] buff = new char[1024];
            while ((read = in.read(buff)) != -1) {
                jsonResults.append(buff, 0, read);
            }
        } catch (MalformedURLException e) {
            logger.isLoggable(Level.INFO);
           // logger.error("Error processing Places API URL", e);
            return null;
        } catch (IOException e) {
            logger.isLoggable(Level.INFO);
            //logger.error("Error connecting to Places API", e);
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
            place.icon = jsonObj.getString("icon");
            place.name = jsonObj.getString("name");
            place.formattedAddress = jsonObj.getString("formattedAddress");
           if (jsonObj.has("formatted_phone_number")) {
                place.formatted_phone_number = jsonObj.getString("formatted_phone_number");
            }
        } catch (JSONException e) {
            logger.isLoggable(Level.INFO);
            //logger.error("Error processing JSON results", e);
        }

        return place;
    }
}
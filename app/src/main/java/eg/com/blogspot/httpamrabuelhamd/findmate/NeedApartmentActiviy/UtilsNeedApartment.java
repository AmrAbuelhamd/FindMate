package eg.com.blogspot.httpamrabuelhamd.findmate.NeedApartmentActiviy;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import eg.com.blogspot.httpamrabuelhamd.findmate.NeedApartmentActiviy.singleAprtmentDataPublisher.SingleAprtmentData;

/**
 * Created by amro mohamed on 3/2/2018.
 */

public abstract class UtilsNeedApartment {
    private static final String egyptJson = "{\"regions\":[{\"regionID\":\"1.28.\",\"regionName\":\"اسوان\",\"chCount\":15,\"subRegions\":[{\"regionID\":\"1.28.3.\",\"regionName\":\"ادفو\",\"chCount\":37},{\"regionID\":\"1.28.8.\",\"regionName\":\"ارض يعقوب\",\"chCount\":0},{\"regionID\":\"1.28.1.\",\"regionName\":\"اسوان\",\"chCount\":9},{\"regionID\":\"1.28.9.\",\"regionName\":\"الحصايا\",\"chCount\":0},{\"regionID\":\"1.28.12.\",\"regionName\":\"السيل الجديد\",\"chCount\":0},{\"regionID\":\"1.28.10.\",\"regionName\":\"السيل الريفى\",\"chCount\":0},{\"regionID\":\"1.28.15.\",\"regionName\":\"النجع البحرى\",\"chCount\":1},{\"regionID\":\"1.28.7.\",\"regionName\":\"النوبه\",\"chCount\":0},{\"regionID\":\"1.28.13.\",\"regionName\":\"بينان قبلى\",\"chCount\":1},{\"regionID\":\"1.28.4.\",\"regionName\":\"كوم أمبو\",\"chCount\":22},{\"regionID\":\"1.28.11.\",\"regionName\":\"كيما\",\"chCount\":0},{\"regionID\":\"1.28.5.\",\"regionName\":\"مدينة نصر\",\"chCount\":38},{\"regionID\":\"1.28.2.\",\"regionName\":\"مدينة نصر\",\"chCount\":0},{\"regionID\":\"1.28.14.\",\"regionName\":\"مركز أبو سمبل\",\"chCount\":1},{\"regionID\":\"1.28.6.\",\"regionName\":\"مركز دراو\",\"chCount\":9}]},{\"regionID\":\"1.25.\",\"regionName\":\"اسيوط\",\"chCount\":15,\"subRegions\":[{\"regionID\":\"1.25.4.\",\"regionName\":\"ابنوب\",\"chCount\":19},{\"regionID\":\"1.25.5.\",\"regionName\":\"ابو تيج\",\"chCount\":14},{\"regionID\":\"1.25.3.\",\"regionName\":\"اسيوط\",\"chCount\":37},{\"regionID\":\"1.25.6.\",\"regionName\":\"البدارى\",\"chCount\":20},{\"regionID\":\"1.25.8.\",\"regionName\":\"الغنايم\",\"chCount\":8},{\"regionID\":\"1.25.9.\",\"regionName\":\"القوصيه\",\"chCount\":33},{\"regionID\":\"1.25.14.\",\"regionName\":\"النخيله\",\"chCount\":0},{\"regionID\":\"1.25.1.\",\"regionName\":\"اول اسيوط\",\"chCount\":9},{\"regionID\":\"1.25.2.\",\"regionName\":\"ثان اسيوط\",\"chCount\":6},{\"regionID\":\"1.25.10.\",\"regionName\":\"ديروط\",\"chCount\":45},{\"regionID\":\"1.25.7.\",\"regionName\":\"ساحل سليم\",\"chCount\":17},{\"regionID\":\"1.25.11.\",\"regionName\":\"صدفا\",\"chCount\":18},{\"regionID\":\"1.25.15.\",\"regionName\":\"مدينة أسيوط الجديدة\",\"chCount\":0},{\"regionID\":\"1.25.13.\",\"regionName\":\"مركز الفتح\",\"chCount\":24},{\"regionID\":\"1.25.12.\",\"regionName\":\"منفلوط\",\"chCount\":27}]},{\"regionID\":\"1.2.\",\"regionName\":\"الاسكندريه\",\"chCount\":52},{\"regionID\":\"1.19.\",\"regionName\":\"الاسماعيلية\",\"chCount\":10},{\"regionID\":\"1.29.\",\"regionName\":\"الاقصـر\",\"chCount\":8},{\"regionID\":\"1.31.\",\"regionName\":\"البحر الاحمر\",\"chCount\":7},{\"regionID\":\"1.18.\",\"regionName\":\"البحيرة\",\"chCount\":29},{\"regionID\":\"1.21.\",\"regionName\":\"الجيزه\",\"chCount\":25},{\"regionID\":\"1.12.\",\"regionName\":\"الدقهلية\",\"chCount\":26},{\"regionID\":\"1.4.\",\"regionName\":\"السويس\",\"chCount\":9},{\"regionID\":\"1.13.\",\"regionName\":\"الشرقيه\",\"chCount\":26},{\"regionID\":\"1.16.\",\"regionName\":\"الغربيه\",\"chCount\":20},{\"regionID\":\"1.23.\",\"regionName\":\"الفيوم\",\"chCount\":10},{\"regionID\":\"1.1.\",\"regionName\":\"القاهره\",\"chCount\":64},{\"regionID\":\"1.14.\",\"regionName\":\"القليوبيه\",\"chCount\":18},{\"regionID\":\"1.17.\",\"regionName\":\"المنوفيه\",\"chCount\":12},{\"regionID\":\"1.24.\",\"regionName\":\"المنيا\",\"chCount\":14},{\"regionID\":\"1.32.\",\"regionName\":\"الوادى الجديد\",\"chCount\":3},{\"regionID\":\"1.22.\",\"regionName\":\"بنى سويف\",\"chCount\":11},{\"regionID\":\"1.3.\",\"regionName\":\"بورسعيد\",\"chCount\":26},{\"regionID\":\"1.35.\",\"regionName\":\"جنوب سيناء\",\"chCount\":8},{\"regionID\":\"1.11.\",\"regionName\":\"دمياط\",\"chCount\":10},{\"regionID\":\"1.26.\",\"regionName\":\"سوهاج\",\"chCount\":23},{\"regionID\":\"1.36.\",\"regionName\":\"سيناء\",\"chCount\":3},{\"regionID\":\"1.34.\",\"regionName\":\"شمال سيناء\",\"chCount\":15},{\"regionID\":\"1.27.\",\"regionName\":\"قنــا\",\"chCount\":18},{\"regionID\":\"1.15.\",\"regionName\":\"كفرالشيخ\",\"chCount\":18},{\"regionID\":\"1.33.\",\"regionName\":\"مرسى مطروح\",\"chCount\":7}],\"selectedVal\":\"\",\"countChildren\":true,\"totPath\":\"مصر\"}";
    /** Tag for the log messages */
    private static final String LOG_TAG = UtilsNeedApartment.class.getSimpleName();
    private UtilsNeedApartment(){}
    /**
     * extract the governorates from the json
     * @return Array of strings
     */
    public static List<SingleAprtmentData> fetchApartmentData(String requestUrl) {
        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        // Extract relevant fields from the JSON response and create a list of {@link Earthquake}s
        List<SingleAprtmentData> aprtments = extractApartmentFromJson(jsonResponse);

        // Return the list of {@link SingleApartmentData}s
        return aprtments;
    }
    //todo change this so that this parsing matches emad json
    /**
     * Return a list of {@link SingleAprtmentData} objects that has been built up from
     * parsing the given JSON response.
     */
    private static List<SingleAprtmentData> extractApartmentFromJson(String aprtmentJSON) {
        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(aprtmentJSON)) {
            return null;
        }

        // Create an empty ArrayList that we can start adding apartment to
        List<SingleAprtmentData> earthquakes = new ArrayList<>();

        // Try to parse the JSON response string. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {

            // Create a JSONObject from the JSON response string
            JSONObject baseJsonResponse = new JSONObject(aprtmentJSON);

            // Extract the JSONArray associated with the key called "features",
            // which represents a list of features (or earthquakes).
            JSONArray earthquakeArray = baseJsonResponse.getJSONArray("features");

            // For each earthquake in the earthquakeArray, create an {@link Earthquake} object
            for (int i = 0; i < earthquakeArray.length(); i++) {

                // Get a single earthquake at position i within the list of earthquakes
                JSONObject currentEarthquake = earthquakeArray.getJSONObject(i);

                // For a given earthquake, extract the JSONObject associated with the
                // key called "properties", which represents a list of all properties
                // for that earthquake.
                JSONObject properties = currentEarthquake.getJSONObject("properties");

                // Extract the value for the key called "mag"
                double magnitude = properties.getDouble("mag");

                // Extract the value for the key called "place"
                String location = properties.getString("place");

                // Extract the value for the key called "time"
                long time = properties.getLong("time");

                // Extract the value for the key called "url"
                String url = properties.getString("url");

                // Create a new {@link Earthquake} object with the magnitude, location, time,
                // and url from the JSON response.
//                SingleAprtmentData earthquake = new SingleAprtmentData(magnitude, location, time, url);

                // Add the new {@link Earthquake} to the list of earthquakes.
//                earthquakes.add(earthquake);
            }

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }

        // Return the list of earthquakes
        return earthquakes;
    }
    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");//todo change this if needed
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // Closing the input stream could throw an IOException, which is why
                // the makeHttpRequest(URL url) method signature specifies than an IOException
                // could be thrown.
                inputStream.close();
            }
        }
        return jsonResponse;
    }
    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }
    /**
     * Returns new URL object from the given string URL.
     */
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }
        return url;
    }
    static ArrayList<String> getGovernorates(){
        //tODO try change this approach later of creating a lot of Arrraylists
        ArrayList<String> arrayList = new ArrayList<>();
        try {
            JSONArray regionsArray = getJsonArray();
            arrayList =  traversingJsonArray(regionsArray);
        }catch (Exception e){

        }
        return arrayList;
    }
    private static JSONArray getJsonArray()throws Exception{
        JSONObject rootJsonObject = new JSONObject(egyptJson);
        JSONArray regionsArray = rootJsonObject.getJSONArray("regions");
        return regionsArray;
    }
    private static ArrayList<String> traversingJsonArray(JSONArray jsonArray)throws Exception{
        ArrayList<String> egyptGovernorates = new ArrayList<>();

        for (int i=0; i < jsonArray.length(); i++){
            JSONObject governorateObject = jsonArray.getJSONObject(i);
            egyptGovernorates.add(governorateObject.getString("regionName"));
        }
        return egyptGovernorates;
    }
    static ArrayList<String> getSubregions(int position){
        position--;
        ArrayList<String> arrayList = new ArrayList<>();
        try {
            JSONArray regionsArray = getJsonArray();
            JSONObject specificRegion = regionsArray.getJSONObject(position);
            JSONArray subRegoin = specificRegion.getJSONArray("subRegions");
            Log.v("TESTY","sdfsa"+subRegoin.getJSONObject(0).getString("regionName"));
            arrayList=  traversingJsonArray(subRegoin);
        }catch (Exception e){
            Log.v("exception","e= "+e);
        }
        return arrayList;
    }
}

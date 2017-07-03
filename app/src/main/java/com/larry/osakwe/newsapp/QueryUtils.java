package com.larry.osakwe.newsapp;

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

import static com.larry.osakwe.newsapp.MainActivity.LOG_TAG;

/**
 * Created by Larry Osakwe on 7/2/2017.
 */

public final class QueryUtils {

    private QueryUtils() {

    }

    public static ArrayList<Review> extractReviews(String reviewJSON) {
        if (TextUtils.isEmpty(reviewJSON)) {
            return null;
        }

        ArrayList<Review> reviews = new ArrayList<>();

        URL url = createURL(reviewJSON);

        String jsonResponse = null;

        try {
            jsonResponse = makeHttpRequest(url);
            JSONObject baseJsonResponse = new JSONObject(jsonResponse);
            JSONArray resultsArray = baseJsonResponse.getJSONArray("results");

            for (int i = 0; i < resultsArray.length(); i++) {
                JSONObject review = resultsArray.getJSONObject(i);
                JSONObject game = review.getJSONObject("game");
                String gameTitle = game.getString("name");
                int gameID = game.getInt("id");
                //String date = review.getString("public_date");
                String author = review.getString("reviewer");
                int rating = review.getInt("score");
                String aUrl = review.getString("site_detail_url");
                String platform = review.getString("platforms");



                reviews.add(new Review(gameTitle, gameID, 222, author, rating, aUrl, "test", platform));

            }

        } catch (JSONException e) {
            Log.e(LOG_TAG, "Problem parsing the review JSON results", e);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem parsing the review JSON results", e);
        }

        return  reviews;
    }

    private static URL createURL(String reviewJSON) {
        URL url = null;
        try {
            url = new URL(reviewJSON);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error with creating URL ", e);
        }
        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the review JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

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

}

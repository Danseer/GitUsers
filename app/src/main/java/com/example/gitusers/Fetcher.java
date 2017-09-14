package com.example.gitusers;

/**
 * Created by Константин on 14.09.2017.
 */

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Konstantin on 20.07.2017.
 */

public class Fetcher {
    private static final String TAG = "Fetcher";
    //private static final String API_KEY = "5a5bfa35ca6eee70a392c4dd5ab4d81c";

    public String getJSONString(String URLSpec) throws IOException {

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(URLSpec)
                .build();
        Response response = client.newCall(request).execute();
        String result = response.body().string();

        return result;
    }

    public List<gallery_item> fetchItems(String method, String text) {
        List<gallery_item> itemsList = new ArrayList<>();
        try {
            String url = Uri.parse("https://api.flickr.com/services/rest/")
                    .buildUpon()
                    .appendQueryParameter("method", method)
                    .appendQueryParameter("text", text)
                    .appendQueryParameter("per_page", "500")
                    .appendQueryParameter("api_key", API_KEY)
                    .appendQueryParameter("format", "json")
                    .appendQueryParameter("nojsoncallback", "1")
                    .appendQueryParameter("extras", "owner_name,url_s,url_m")
                    .build().toString();

            String jsonString = getJSONString(url);
            JSONObject jsonBody = new JSONObject(jsonString);
            parseItems(itemsList, jsonBody);

        } catch (IOException e) {
            Log.e(TAG, "Ощибка загрузки данных", e);
        } catch (JSONException e) {
            Log.e(TAG, "Ошибка парсинга JSON", e);
        }
        return itemsList;
    }

    private void parseItems(List<gallery_item> items, JSONObject jsonBody) throws IOException, JSONException {
        JSONObject photosJSONObject = jsonBody.getJSONObject("photos");
        JSONArray photoJSONArray = photosJSONObject.getJSONArray("photo");

        for (int i = 0; i < photoJSONArray.length(); i++) {
            JSONObject photoJSONObject = photoJSONArray.getJSONObject(i);
            gallery_item item = new gallery_item();
            item.setId(photoJSONObject.getString("id"));
            item.setCaption(photoJSONObject.getString("title"));
            item.setOwner_name(photoJSONObject.getString("ownername"));


            if (!photoJSONObject.has("url_s")) continue;
            item.setUrls(photoJSONObject.getString("url_s"));

            if (!photoJSONObject.has("url_m")) continue;
            item.setUrlm(photoJSONObject.getString("url_m"));


            items.add(item);
        }

    }


}

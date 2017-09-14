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

    public String getJSONString(String URLSpec) throws IOException {

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(URLSpec)
                .build();
        Response response = client.newCall(request).execute();
        String result = response.body().string();

        return result;
    }

    public List<User> fetchItems() {
        List<User> itemsList = new ArrayList<>();
        try {
            String url = "https://api.github.com/users";


            String jsonString = getJSONString(url);
            Log.e("url", url);
            Log.e("jsonString", jsonString);


            parseItems(itemsList, jsonString);

        } catch (IOException e) {
            Log.e(TAG, "Ощибка загрузки данных", e);
        } catch (JSONException e) {
            Log.e(TAG, "Ошибка парсинга JSON", e);
        }
        return itemsList;
    }

    private void parseItems(List<User> items, String fromServer) throws IOException, JSONException {
        JSONArray usersJSONArray = new JSONArray(fromServer);

        for (int i = 0; i < usersJSONArray.length(); i++) {
            JSONObject userJSONObject = usersJSONArray.getJSONObject(i);
            User item = new User();
            item.setLogin(userJSONObject.getString("login"));
            Log.e("login ", item.getLogin());
            item.setAvatarUrl(userJSONObject.getString("avatar_url"));
            Log.e("url_avatar ", item.getAvatarUrl());
            items.add(item);
        }

    }


}

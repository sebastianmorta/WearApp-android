package com.example.projekt3;

import android.app.IntentService;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.AsyncTask;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
class weatherTask extends AsyncTask<String, Void, Void>{

    String URL,API,selected_date;
    private String tester;
    private String temp;
    TextView tescik;
    static float Temperature;

    public weatherTask(String URL, String API, String selected_date,TextView t){
        this.URL=URL;
        this.API=API;
        this.selected_date=selected_date;
        this.tescik=t;
    }

    @Override
    protected Void doInBackground(final String... strings) {

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(URL)
                .get()
                .addHeader("x-rapidapi-host", "community-open-weather-map.p.rapidapi.com")
                .addHeader("x-rapidapi-key", API)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String myResponse = response.body().string();
                    tester=myResponse;
                    try {
                        final JSONObject jsonObj = new JSONObject(tester);
                        JSONObject weather;

                        for(int i = 0; i < 40; i++)
                        {
                            String e = jsonObj.getJSONArray("list").getJSONObject(i).getString("dt_txt");
                            SimpleDateFormat tak =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            Date current_date_json = tak.parse(e);
                            SimpleDateFormat nie =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            Date users_date = nie.parse(selected_date);

                            if(current_date_json.equals(users_date))
                            {
                                weather = jsonObj.getJSONArray("list").getJSONObject(i).getJSONObject("main");
                                temp = weather.getString("temp");
                                float a = Float.parseFloat(temp)-273.15f;
                                Temperature = a;
                                temp = String.valueOf(Math.round(a))+ "°C";
                                tescik.setText(temp+"\n"+ selected_date);

                                break;
                            }

                        }
                    } catch (JSONException | ParseException e) {
                        e.printStackTrace();
                    }

                }
            }
        });
        return null;
    }
}
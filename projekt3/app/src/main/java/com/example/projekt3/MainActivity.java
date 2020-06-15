package com.example.projekt3;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.icu.text.SimpleDateFormat;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.TimePicker;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity  {

    private TextView tescik;
    private static final String TAG = "MainActivity";
    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private TimePickerDialog.OnTimeSetListener mTimeSetListener;
        String selected_date;
        Float temp;
        String tester;
        String tester2;
        String API = "9949d487d4fa9c74158008eca4a46750";
        String CITY = "Wroclaw";
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            mDisplayDate = (TextView)findViewById(R.id.textViewDate);
            mDisplayDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Calendar cal = Calendar.getInstance();
                    int year = cal.get(Calendar.YEAR);
                    int month = cal.get(Calendar.MONTH);
                    int day = cal.get(Calendar.DAY_OF_MONTH);

                    DatePickerDialog dialog = new DatePickerDialog(
                            MainActivity.this,
                            android.R.style.Theme_Holo_Light_Dialog,
                            mDateSetListener, year, month, day);

                    DatePicker dp = dialog.getDatePicker();
                    dp.setMinDate(cal.getTimeInMillis());
                    cal.add(Calendar.DAY_OF_MONTH,6);
//                    dialog.getDatePicker().setMinDate(Calendar.getInstance().getTimeInMillis());
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog.show();
                }
            });


            mDateSetListener = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    month+=1;

                    Calendar c = Calendar.getInstance();
                    int hour=c.get(Calendar.HOUR);
                    int min=0;
                    TimePickerDialog timePickerDialog= new TimePickerDialog(
                            MainActivity.this,
                            android.R.style.Theme_Holo_Light_Dialog,
                            mTimeSetListener,
                            hour, min, true);
                    timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    timePickerDialog.show();

                    Log.d(TAG, "onDateSet: date: " + year + "-" +month+"-"+dayOfMonth+"-" +hour+"-" +min);
                    if(month<10){   if(dayOfMonth<10) selected_date = String.valueOf(year)+"-0"+String.valueOf(month)+"-0"+String.valueOf(dayOfMonth)+" ";
                                    else selected_date = String.valueOf(year)+"-0"+String.valueOf(month)+"-"+String.valueOf(dayOfMonth)+" ";
                    }else {         if(dayOfMonth<10) selected_date = String.valueOf(year)+"-"+String.valueOf(month)+"-0"+String.valueOf(dayOfMonth)+" ";
                                    else selected_date = String.valueOf(year)+"-"+String.valueOf(month)+"-"+String.valueOf(dayOfMonth)+" "; }

                    Log.d(TAG,selected_date);
//                    tescik = findViewById(R.id.textViewtest);
//
//                    OkHttpClient client = new OkHttpClient();
//                    new weatherTask().execute();
                }
            };
            mTimeSetListener= new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                    int h = hourOfDay;
                    if((h%3)!=0){ h-=(h%3); }
                    selected_date += String.valueOf(h) + ":00:00";
                    Log.d(TAG,"aktualna data do wszystkiego"+selected_date);
                    tescik = findViewById(R.id.textViewtest);

                    OkHttpClient client = new OkHttpClient();
                    new weatherTask().execute();
                }
            };



        }



    class weatherTask extends AsyncTask<String, Void, Void> {

    @Override
    protected void onPreExecute() {
        super.onPreExecute();


//        findViewById(R.id.loader).setVisibility(View.VISIBLE);
//        // findViewById(R.id.mainContainer).setVisibility(View.GONE);
//        findViewById(R.id.errorText).setVisibility(View.GONE);
    }

    @Override
    protected Void doInBackground(final String... strings) {
        //String response = HttpRequest.excuteGet("https://api.openweathermap.org/data/2.5/weather?q=" + CITY + "&units=metric&appid=" + API);
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://community-open-weather-map.p.rapidapi.com/forecast?q=" + CITY)
                .get()
                .addHeader("x-rapidapi-host", "community-open-weather-map.p.rapidapi.com")
                .addHeader("x-rapidapi-key", "2d48d2bf10mshd845969cdb76016p1a1668jsn9acde4e25dc1")
                .build();

        //Response response = client.newCall(request).execute();
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
                    //                      tescik.setText("testeaasdr2");
                    try {
                        final JSONObject jsonObj = new JSONObject(tester);
                        JSONObject weather;

                        for(int i = 0; i < 16; i++)
                        {
                            String e = jsonObj.getJSONArray("list").getJSONObject(i).getString("dt_txt");
                            SimpleDateFormat tak =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            Date current_date_json = tak.parse(e);
                            SimpleDateFormat nie =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            Date users_date = nie.parse(selected_date);

                            if(current_date_json.equals(users_date))
                            {
                                weather = jsonObj.getJSONArray("list").getJSONObject(i).getJSONObject("main");
                                tester2 = weather.getString("temp");
                                float a = Float.parseFloat(tester2)-273.15f;
                                tester2 = String.valueOf(a);
                                tescik.setText(tester2+"\n"+ selected_date);
                                break;
                            }

                        }
                        //tester2 = weather.getString("main");
//                            tester2 = weather.getString("temp");
                    } catch (JSONException | ParseException e) {
                        e.printStackTrace();
                    }
                    //zobaczymy co to
//                        MainActivity.this.runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                tescik.setText(tester2);
//                            }
//                        });

                }
            }
        });
//
//            try {
//                JSONObject jsonObj = new JSONObject(tester);
//                JSONObject list = jsonObj.getJSONObject("list");
////                JSONObject  = jsonObj.getJSONObject();
////                JSONObject  = jsonObj.getJSONObject();
////                JSONObject  = jsonObj.getJSONObject();
////                JSONObject  = jsonObj.getJSONObject();
////                JSONObject  = jsonObj.getJSONObject();
////                JSONObject  = jsonObj.getJSONObject();
////                JSONObject  = jsonObj.getJSONObject();
////                JSONObject  = jsonObj.getJSONObject();
//
//            }catch (JSONException e) {
//
//            }
        return null;
    }

//            Response response = null;
//            try {
//                response = client.newCall(request).execute();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            return response;

//        @Override
//        protected void onPostExecute(String result) {
//

}
}

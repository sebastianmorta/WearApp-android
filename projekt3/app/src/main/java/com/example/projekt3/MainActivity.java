package com.example.projekt3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.icu.text.SimpleDateFormat;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_LOCATION_PERMISSION = 1;
    private ProgressBar progressBar;
    private TextView textViewLatLong;
    private EditText editTextLocation;
    private TextView tescik;
    private static final String TAG = "MainActivity";
    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private TimePickerDialog.OnTimeSetListener mTimeSetListener;
    String selected_date;

    String API = "98dc0f0402msh27da9f9ed7c338bp144148jsn25fd970a5e46";
    String API2 = "2d48d2bf10mshd845969cdb76016p1a1668jsn9acde4e25dc1";
    String CITY = "Wroclaw";

    String URL = "https://community-open-weather-map.p.rapidapi.com/forecast?q=";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//        findViewById(R.id.buttontst).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Ion.with(getApplicationContext()).load("https://www.zalando.pl/odziez-meska-koszulki/_brazowy/?price_from=115&price_to=403&upper_material=d%C5%BCersej").asString().setCallback(new FutureCallback<String>() {
//                    @Override
//                    public void onCompleted(Exception e, String result) {
//                        Log.d(TAG, "data            "+ result);
//
//                    }
//                });
//
//            }
//        });
        textViewLatLong= findViewById(R.id.textViewtest2);
        progressBar = findViewById(R.id.loader);
        editTextLocation = findViewById(R.id.ed_location);
        final Switch simple_switch = (Switch) findViewById(R.id.switchLocation);
        simple_switch.setTypeface(ResourcesCompat.getFont(this  ,R.font.caveat_brush));
        findViewById(R.id.floatingbtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(simple_switch.isChecked())
                {
                    if (ContextCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(
                                    MainActivity.this,
                                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                    REQUEST_CODE_LOCATION_PERMISSION);
                            Log.d(TAG, "jest mamy to mordo w chuj");
//                            OkHttpClient client = new OkHttpClient();
//                            new weatherTask(URL,API,selected_date,tescik).execute();
                    } else { getCurrentLocation();  Log.d(TAG, "jest mamy tofasfdasdfasdfadsf mordo w chuj");}
                }
                else if(!editTextLocation.getText().equals(""))
                    {
                        CITY = String.valueOf(editTextLocation.getText());
                        URL ="https://community-open-weather-map.p.rapidapi.com/forecast?q="+ CITY;
                        Log.d(TAG, "chuj nie mamy tego ");
//                        OkHttpClient client = new OkHttpClient();
//                        new weatherTask(URL,API,selected_date,tescik).execute();
                    }
                OkHttpClient client = new OkHttpClient();
                new weatherTask(URL,API,selected_date,tescik).execute();

                Log.d(TAG, "button clicked");


            }
        });
        findViewById(R.id.floatingbtn1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, WearActivity.class);
                startActivity(intent);
            }
        });

        mDisplayDate = (TextView) findViewById(R.id.textViewDate);
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

                cal.add(Calendar.DAY_OF_MONTH, 6);
//                    dialog.getDatePicker().setMinDate(Calendar.getInstance().getTimeInMillis());
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });


        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month += 1;

                Calendar c = Calendar.getInstance();
                int hour = c.get(Calendar.HOUR);
                int min = 0;
                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        MainActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog,
                        mTimeSetListener,
                        hour, min, true);
                timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                timePickerDialog.show();

                Log.d(TAG, "onDateSet: date: " + year + "-" + month + "-" + dayOfMonth + "-" + hour + "-" + min);
                if (month < 10) {
                    if (dayOfMonth < 10)
                        selected_date = String.valueOf(year) + "-0" + String.valueOf(month) + "-0" + String.valueOf(dayOfMonth) + " ";
                    else
                        selected_date = String.valueOf(year) + "-0" + String.valueOf(month) + "-" + String.valueOf(dayOfMonth) + " ";
                } else {
                    if (dayOfMonth < 10)
                        selected_date = String.valueOf(year) + "-" + String.valueOf(month) + "-0" + String.valueOf(dayOfMonth) + " ";
                    else
                        selected_date = String.valueOf(year) + "-" + String.valueOf(month) + "-" + String.valueOf(dayOfMonth) + " ";
                }

                Log.d(TAG, selected_date);
//                    tescik = findViewById(R.id.textViewtest);
//
//                    OkHttpClient client = new OkHttpClient();
//                    new weatherTask().execute();
            }
        };
        mTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                int h = hourOfDay;
                if ((h % 3) != 0)
                    h -= (h % 3);

                selected_date += String.valueOf(h) + ":00:00";
                Log.d(TAG, "aktualna data do wszystkiego" + selected_date);
                tescik = findViewById(R.id.textViewtest);

//                OkHttpClient client = new OkHttpClient();
//                new weatherTask().execute();
            }
        };


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_LOCATION_PERMISSION && grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getCurrentLocation();
            } else {
                Toast.makeText(this, "permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void getCurrentLocation() {

        progressBar.setVisibility(View.VISIBLE);
        final LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(3000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        LocationServices.getFusedLocationProviderClient(MainActivity.this)
                .requestLocationUpdates(locationRequest, new LocationCallback() {
                    @Override
                    public void onLocationResult(LocationResult locationResult) {
                        super.onLocationResult(locationResult);
                        LocationServices.getFusedLocationProviderClient(MainActivity.this)
                                .removeLocationUpdates(this);
                        if (locationResult != null && locationResult.getLocations().size() > 0) {
                                            int latestLocationIndex = locationResult.getLocations().size()-1;
                                            double lat = locationResult.getLocations().get(latestLocationIndex).getLatitude();
                                            double lon = locationResult.getLocations().get(latestLocationIndex).getLongitude();
                                            textViewLatLong.setText(String.format("Latitude:%s\nLongtitude:%s",lat,lon));
                                            URL = "https://community-open-weather-map.p.rapidapi.com/forecast?lat="+lat+"&lon="+lon;
                                        OkHttpClient client = new OkHttpClient();
                                        new weatherTask(URL,API,selected_date,tescik).execute();
                        }
                        progressBar.setVisibility(View.GONE);
                    }
                }, Looper.getMainLooper());
    }

//    class weatherTask extends AsyncTask<String, Void, Void> {
//
//    @Override
//    protected Void doInBackground(final String... strings) {
//
//        OkHttpClient client = new OkHttpClient();
//        Request request = new Request.Builder()
//                .url(URL)
//                .get()
//                .addHeader("x-rapidapi-host", "community-open-weather-map.p.rapidapi.com")
//                .addHeader("x-rapidapi-key", API)
//                .build();
//
//        client.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(@NotNull Call call, @NotNull IOException e) {
//                e.printStackTrace();
//            }
//
//            @Override
//            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
//                if (response.isSuccessful()) {
//                    final String myResponse = response.body().string();
//                    tester=myResponse;
//                    try {
//                        final JSONObject jsonObj = new JSONObject(tester);
//                        JSONObject weather;
//
//                        for(int i = 0; i < 16; i++)
//                        {
//                            String e = jsonObj.getJSONArray("list").getJSONObject(i).getString("dt_txt");
//                            SimpleDateFormat tak =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                            Date current_date_json = tak.parse(e);
//                            SimpleDateFormat nie =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                            Date users_date = nie.parse(selected_date);
//
//                            if(current_date_json.equals(users_date))
//                            {
//                                weather = jsonObj.getJSONArray("list").getJSONObject(i).getJSONObject("main");
//                                tester2 = weather.getString("temp");
//                                float a = Float.parseFloat(tester2)-273.15f;
//                                tester2 = String.valueOf(a);
//                                tescik.setText(tester2+"\n"+ selected_date);
//                                break;
//                            }
//
//                        }
//                    } catch (JSONException | ParseException e) {
//                        e.printStackTrace();
//                    }
//
//                }
//            }
//        });
//        return null;
//    }
//
//
//}
}

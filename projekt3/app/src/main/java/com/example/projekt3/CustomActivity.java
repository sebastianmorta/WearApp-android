package com.example.projekt3;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.content.res.ResourcesCompat;

import static com.example.projekt3.Calculations.Web_URL;

public class CustomActivity extends Activity {

    Switch s1,s2,s3,s4,s5,s6;
    static String Color_String = "/_";
    static String Season_String;
    private static final String TAG = "CustomActivity";
    SwitchCompat season;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom);
        initializeFonts();
        season = findViewById(R.id.seasonbtn);
        season.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(season.isChecked()) Season_String ="/?season_name=jesień-zima";
                else Season_String ="/?season_name=wiosna-lato";
            }
        });
        Button btn = findViewById(R.id.buttonweb);
        btn.setTypeface(ResourcesCompat.getFont(this  ,R.font.caveat_brush));
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getColorString();
                Calculations calculations = new Calculations();
                Log.d(TAG, Web_URL);
                Uri webpage = Uri.parse(Web_URL);
                Intent webIntent = new Intent(Intent.ACTION_VIEW, webpage);
                startActivity(webIntent);
            }
        });
    }

    public void launchWebsite(View view){

    }


    private void initializeFonts()
    {
        s1=findViewById(R.id.switch1);
        s1.setTypeface(ResourcesCompat.getFont(this  ,R.font.caveat_brush));
        s2=findViewById(R.id.switch2);
        s2.setTypeface(ResourcesCompat.getFont(this  ,R.font.caveat_brush));
        s3=findViewById(R.id.switch3);
        s3.setTypeface(ResourcesCompat.getFont(this  ,R.font.caveat_brush));
        s4=findViewById(R.id.switch4);
        s4.setTypeface(ResourcesCompat.getFont(this  ,R.font.caveat_brush));
        s5=findViewById(R.id.switch5);
        s5.setTypeface(ResourcesCompat.getFont(this  ,R.font.caveat_brush));
        s6=findViewById(R.id.switch6);
        s6.setTypeface(ResourcesCompat.getFont(this  ,R.font.caveat_brush));



    }
    private void getColorString(){
        Switch[] t = new Switch[]{s1,s2,s3,s4,s5,s6};
        for(int i = 0; i<6;i++)
        {
            if(t[i].isChecked())
            {
                Color_String+=t[i].getText()+".";
            }

        }
        Color_String = Color_String.substring(0, Color_String.length()-1);
        Log.d(TAG,"kolory:  "+Color_String);
    }

}

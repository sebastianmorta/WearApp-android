package com.example.projekt3;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class WearActivity extends Activity {

    private static final String TAG = "WearActivity";
    enum CLOTHES {
        HAT,
        SHIRT,
        SHOES,
        TRAUSERS
    };

    static String gear_element;
    FloatingActionButton floatingActionButton;
    RadioGroup radioGroup;
    RadioButton radioButton;
    RadioButton Shirt;
    RadioButton Trousers;
    RadioButton Shoes;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wear);

        float temp =weatherTask.Temperature;
        radioGroup=findViewById(R.id.radioGroup2);

        findViewById(R.id.floatingbtn2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int radioId = radioGroup.getCheckedRadioButtonId();
                radioButton = findViewById(radioId);
                try {
                    gear_element = String.valueOf(radioButton.getText());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(WearActivity.this, CustomActivity.class);
                startActivity(intent);

            }
        });
    }

}

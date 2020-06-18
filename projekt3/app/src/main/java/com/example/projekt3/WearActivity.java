package com.example.projekt3;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class WearActivity extends Activity {


    enum CLOTHES {
        HAT,
        SHIRT,
        SHOES,
        TRAUSERS
    };


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

        radioGroup=findViewById(R.id.radioGroup2);

        findViewById(R.id.floatingbtn2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int radioId = radioGroup.getCheckedRadioButtonId();
                radioButton = findViewById(radioId);
                Intent intent = new Intent(WearActivity.this, CustomActivity.class);
                startActivity(intent);
//                Toast.makeText(this, "Selected "+String.valueOf(radioId), Toast.LENGTH_SHORT).show();
            }
        });
    }
//    public void checkButton(View v){
//
//
//    }
}

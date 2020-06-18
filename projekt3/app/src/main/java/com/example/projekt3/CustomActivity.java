package com.example.projekt3;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Switch;

import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;

public class CustomActivity extends Activity {

    Switch s1,s2,s3,s4,s5,s6;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom);
        initializeFonts();

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
}

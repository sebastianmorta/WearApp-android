package com.example.projekt3;

import android.widget.Switch;

public class Calculations
{

    String Gear_element;
    static String Web_URL;

    public Calculations(){
        temperature_calculations();

        Web_URL="https://www.zalando.pl/"+Gear_element+CustomActivity.Color_String+CustomActivity.Season_String;
    }


    void temperature_calculations()
    {
        float temp = weatherTask.Temperature;
        String gear = WearActivity.gear_element;
        switch (gear){
            case "shirt":{
                Gear_element ="odziez-meska";
                if (temp<10){
                    Gear_element+="-kurtki";
                    break;
                }
                else if(temp>10 && temp<20){
                    Gear_element+="-bluzy-kardigany";
                    break;
                }
                else {
                    Gear_element+="-basic";
                    break;
                }
            }
            case "trausers":{
                Gear_element ="/odziez-meska";
                if (temp<10){
                    Gear_element+="-jeansy-straight-leg";
                    break;
                }
                else if(temp>10 && temp<20){
                    Gear_element+="-jeansy";
                    break;
                }
                else {
                    Gear_element+="-spodnie-szorty";
                    break;
                }
            }
            case "shoes":{
                Gear_element ="/obuwie-meskie";
                if (temp<10){
                    Gear_element+="-kozaki";
                    break;
                }
                else if(temp>10 && temp<20){
                    Gear_element+="-tenisowki-trampki";
                    break;
                }
                else {
                    Gear_element+="-polbuty";
                    break;
                }
            }
        }

    }
    void color_calculations()
    {


    }
    void element_calculations()
    {

    }
}

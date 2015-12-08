package com.example.mwinkler3.fishtankapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioGroup;
import android.content.Intent;

public class MainActivity extends Activity {

    int otherId;
    int id;
    public static int background;
    public static int fishId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onBackgroundRadioButtonClicked(View view) {
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.backgroundGroup);
        id = radioGroup.getCheckedRadioButtonId();
        switch(id) {
            case R.id.skrunken_bikini_bottom:
                background = R.drawable.bikini_bottom;
                break;

            case R.id.skrunken_bikini_bottom_2:
                background = R.drawable.bikini_bottom_2;
                break;

            case R.id.skrunken_little_mermaid:
                background = R.drawable.little_mermaid;
                break;

            case R.id.skrunken_little_mermaid_2:
                background = R.drawable.little_mermaid_2;
                break;
        }
    }

    public void onFishRadioButtonClicked (View view) {
        RadioGroup secondRadioGroup = (RadioGroup)findViewById(R.id.fishGroup);
        otherId = secondRadioGroup.getCheckedRadioButtonId();
        switch(otherId) {
            case R.id.one_fish:
                fishId = R.drawable.yellow_swim_right;
                break;

            case R.id.two_fish:
                fishId = R.drawable.green_swim_right;
                break;

            case R.id.orange_fish:
                fishId = R.drawable.gold_swim_right;
                break;

            case R.id.blue_fish:
                fishId = R.drawable.tuna_swim_right;
                break;
        }
    }

    public void onStartClick (View view) {
        Intent intent = new Intent(this, FishTankActivity.class);
        startActivity(intent);
    }

}
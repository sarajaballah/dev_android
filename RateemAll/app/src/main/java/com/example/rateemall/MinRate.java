package com.example.rateemall;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

public class MinRate extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_min_rate);
        Intent parent = getIntent();
        String minRate = parent.getStringExtra("MinRate");
        TextView setMin = findViewById(R.id.setMin);
        setMin.setText("(" + minRate + " is the current minimum)");
    }

    public void setButton (View view) {
        RatingBar rating = findViewById(R.id.rateMin);
        Intent intent = new Intent();
        intent.putExtra("MinRate", String.valueOf(rating.getRating()));
        setResult(RESULT_OK, intent);
        finish();
    }
}
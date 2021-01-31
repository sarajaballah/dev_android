package com.example.rateemall;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class RateMySite extends AppCompatActivity {

    private String site;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_my_site);
        Intent parent = getIntent();
        site = parent.getStringExtra("Site");
        TextView review = findViewById(R.id.review);
        review.append(site);
    }

    public void submitButton (View view) {
        RatingBar rating = findViewById(R.id.rate);
        Intent intent = new Intent();
        intent.putExtra("RatingScore", String.valueOf(rating.getRating()));
        intent.putExtra("Site", site);
        setResult(RESULT_OK, intent);
        finish();
    }
}
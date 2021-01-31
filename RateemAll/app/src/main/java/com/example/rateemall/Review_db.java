package com.example.rateemall;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class Review_db {
    public final static int VERSION_DB = 1;
    private SQLiteDatabase bdd;
    private ReviewDatabase myBase;

    public Review_db (Context context) {
        myBase = new ReviewDatabase(context, "Review", null, VERSION_DB);
    }

    public void open() {
        bdd = myBase.getWritableDatabase();
        if (bdd == null) {
            System.out.println("Database does not exist!");
        }
    }

    public void close() {
        bdd.close();
    }

    public SQLiteDatabase getBdd() {
        return bdd;
    }

    public long insertReview (SiteReview review) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("site", review.getSite());
        contentValues.put("rate", review.getRate());
        return bdd.insert("Review", null, contentValues);
    }

    public void updateReview (SiteReview review) {
        String query="UPDATE "+ "Review" + " SET rate = "+review.getRate()+" WHERE site = '"+review.getSite()+"';";
        bdd.execSQL(query);
    }

    public SiteReview getReviewBySite (String site) {
        Cursor c = bdd.query("Review", new String[] {"site","rate"}, "site like \""+site+"\"", null, null, null, null, null);
        return cursorToReview(c);
    }

    public SiteReview cursorToReview (Cursor c) {
        if (c.getCount() == 0) {
            return null;
        }
        c.moveToFirst();
        String site = c.getString(c.getColumnIndex("site"));
        String rate = c.getString(c.getColumnIndex("rate"));
        c.close();
        return new SiteReview(site, rate);
    }

    public ArrayList getAllReviews () {
        ArrayList reviewList = new ArrayList();
        Cursor c = bdd.rawQuery("SELECT site, rate FROM Review", null);
        if (c.getCount() == 0) {
            return null;
        }
        c.moveToFirst();
        while (!c.isAfterLast()) {
            String site = c.getString(c.getColumnIndex("site"));
            String rate = c.getString(c.getColumnIndex("rate"));
            reviewList.add(site + " has rating of " + rate);
            c.moveToNext();
        }
        c.close();
        return reviewList;
    }
}

package com.example.rateemall;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ReviewDatabase extends SQLiteOpenHelper {
    private final static String CREATE_TABLE = "create table Review("
                +"id integer primary key autoincrement,"
                +"rate varchar(4),"
                +"site varchar(50));";

        public ReviewDatabase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("drop table Review;");
        }
}

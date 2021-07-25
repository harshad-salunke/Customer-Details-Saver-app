package com.example.omsai.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;
import com.example.omsai.data.CustomerContract.Table;
import com.google.android.material.tabs.TabLayout;

public class CustomerhelperDb extends SQLiteOpenHelper {

    public static final String DATABASE_NAME="omsai.db";
    private static final int DATABASE_VERSION=1;


    public CustomerhelperDb(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {


        String SQL_CREATE_PETS_TABLE =  "CREATE TABLE " + Table.TABLE + " ("
                + Table._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Table.NAME + " TEXT DEFAULT 'Empty', "
                + Table.DESCRIP + " TEXT DEFAULT 'Empty', "
                + Table.DATE + " TEXT DEFAULT 'Empty', "
                + Table.Mobile+ " INTEGER DEFAULT 0, "
                + Table.PAYMENTs + " INTEGER  );";

        db.execSQL(SQL_CREATE_PETS_TABLE);

}

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

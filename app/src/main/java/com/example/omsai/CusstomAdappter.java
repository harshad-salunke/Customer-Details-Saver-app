package com.example.omsai;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;

import com.example.omsai.data.CustomerContract;

public class CusstomAdappter  extends CursorAdapter {
    public CusstomAdappter(Context context,  Cursor c) {
        super(context, c,0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list,parent,false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView name=view.findViewById(R.id.name);
        name.setText(cursor.getString(cursor.getColumnIndex(CustomerContract.Table.NAME)));
        TextView dec=view.findViewById(R.id.des);
        dec.setText(cursor.getString(cursor.getColumnIndex(CustomerContract.Table.DESCRIP)));

    }
}
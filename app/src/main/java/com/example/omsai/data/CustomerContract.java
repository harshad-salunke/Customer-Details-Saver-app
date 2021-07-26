package com.example.omsai.data;

import android.net.Uri;
import android.provider.BaseColumns;



public class CustomerContract {
   public    CustomerContract(){}
public static final String CONTENT_AUTHORITY="com.example.android.omsai";
   public static final Uri BASE_CONTENT_URI= Uri.parse("content://"+CONTENT_AUTHORITY);
   public static final String PATH_PETS="customer";

    public static class Table implements BaseColumns {
        public static final   Uri CONTENT_URI=Uri.withAppendedPath(BASE_CONTENT_URI,PATH_PETS);
        public final static String TABLE="customers";
        public final static String _ID="_id";

        public final static String NAME="name";
        public final static String DATE="date";
        public final static String Mobile="date2";
        public final static String Total="Total";
        public final static String DESCRIP="discription";
        public final static String PAYMENTs="payments";

    }
}

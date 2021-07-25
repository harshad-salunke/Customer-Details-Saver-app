package com.example.omsai.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;
import com.example.omsai.data.CustomerContract.Table;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.security.Provider;
import com.example.omsai.data.CustomerContract;
public class CustomerProvider extends ContentProvider {
    public static final int customer=100;
    public static final int customer_id=101;
    private static final UriMatcher sUrimacher=new UriMatcher(UriMatcher.NO_MATCH);
    static {
        sUrimacher.addURI(CustomerContract.CONTENT_AUTHORITY,CustomerContract.PATH_PETS,customer);
        sUrimacher.addURI(CustomerContract.CONTENT_AUTHORITY,CustomerContract.PATH_PETS+"/#",customer_id);
    }
    CustomerhelperDb mDbhelper;

    @Override
    public boolean onCreate() {
        mDbhelper=new CustomerhelperDb(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteDatabase database=mDbhelper.getReadableDatabase();
        Cursor cursor=null;


        int match=sUrimacher.match(uri);
        switch (match){
            case customer:
                cursor=database.query(Table.TABLE,projection,selection,selectionArgs,null,null,sortOrder);
                break;
            case customer_id:
                selection= Table._ID+"=?";
                selectionArgs=new String[]{String.valueOf(ContentUris.parseId(uri))};
                cursor=database.query(Table.TABLE,projection,selection,selectionArgs,null,null,sortOrder);
                break;
            default:
                Log.d("harshad","unkown query");
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        final int match=sUrimacher.match(uri);
        switch (match){
            case customer:
                return insertcustomer(uri,values);

        }
        return null;
    }

    private Uri insertcustomer(Uri uri, ContentValues values) {
        SQLiteDatabase database=mDbhelper.getWritableDatabase();
        Long id=database.insert(CustomerContract.Table.TABLE,null,values);
        if (id==-1){
            Toast.makeText(getContext(), "Somthing is wrong Please Contact Harshad salunke", Toast.LENGTH_SHORT).show();
        }

        return ContentUris.withAppendedId(uri,id);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase database=mDbhelper.getWritableDatabase();
        final  int match=sUrimacher.match(uri);
        switch (match){
            case customer:
                database.delete(Table.TABLE,selection,selectionArgs);
                break;
            case customer_id:
                selection= Table._ID+"=?";
                selectionArgs=new String[]{String.valueOf(ContentUris.parseId(uri))};
                database.delete(Table.TABLE,selection,selectionArgs);
                break;
            default:
                Log.d("badddd","deletion is not supported for"+uri);
        }
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        int macth=sUrimacher.match(uri);
        switch (macth){
            case customer_id:
                selection= Table._ID+"=?";
                selectionArgs=new String[]{String.valueOf(ContentUris.parseId(uri))};
                return updatePet(uri,values,selection,selectionArgs);
        }
        return 0;
    }

    private int updatePet(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        SQLiteDatabase db=mDbhelper.getWritableDatabase();
        db.update(Table.TABLE,values,selection,selectionArgs);
        return 0;
    }
}

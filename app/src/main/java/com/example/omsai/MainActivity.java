package com.example.omsai;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import com.example.omsai.data.CustomerContract.Table;
import com.example.omsai.data.CustomerContract;
import com.example.omsai.data.CustomerhelperDb;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    FloatingActionButton fb;
    CustomerhelperDb mDbhelper;
    ListView listView;
    int count=3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fb = findViewById(R.id.floatbutton);
        listView = findViewById(R.id.list_view);
        mDbhelper = new CustomerhelperDb(this);
        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Add_Customer.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onStart() {
        Log.d("harshad ", "on start");
        super.onStart();
        Display();
    }

    public final void Display() {
//
        SQLiteDatabase db = mDbhelper.getReadableDatabase();
        String projection[] = {
                Table._ID,
                Table.NAME,
                Table.DESCRIP,
                Table.DATE,
                Table.Mobile,
                Table.PAYMENTs


        };
        Cursor cursor = getContentResolver().query(Table.CONTENT_URI, projection, null, null, null);
        CusstomAdappter adappter = new CusstomAdappter(this, cursor);
        listView.setAdapter(adappter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, Add_Customer.class);
                Uri current = ContentUris.withAppendedId(Table.CONTENT_URI, id);
                intent.setData(current);
                startActivity(intent);


            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int select = item.getItemId();

        if (select == R.id.delete_data) {

            Toast.makeText(this, "Are You Sure! Please Click "+count+" time on Delete to Delete all Data", Toast.LENGTH_SHORT).show();
            if(count==1){
                getContentResolver().delete(Table.CONTENT_URI, null, null);
                Toast.makeText(this, "All Data is Deleted", Toast.LENGTH_SHORT).show();
                count=3;
            }
            Display();
            count--;
        }

        return true;
    }

}
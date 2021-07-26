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
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.omsai.data.CustomerContract.Table;
import com.example.omsai.data.CustomerContract;
import com.example.omsai.data.CustomerhelperDb;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    FloatingActionButton fb;
    CustomerhelperDb mDbhelper;
    ListView listView=null;
    int count=3;
    TextView toatal_sum;
    Uri uri;
    ImageView imageView;
    VideoView videoView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent=new Intent(this,intro.class);
        startActivity(intent);

        setContentView(R.layout.activity_main);
        toatal_sum=findViewById(R.id.sum_total);

        fb = findViewById(R.id.floatbutton);
        listView = findViewById(R.id.list_view);
        mDbhelper = new CustomerhelperDb(this);

        imageView=findViewById(R.id.empty_image);


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
        int n=cursor.getCount();
        if(n!=0){
            imageView.setVisibility(View.GONE);
        }
        if(n==0){
            imageView.setVisibility(View.VISIBLE);
        }
        Log.d("check_list","this is the empty "+n);
        int sum=0;
        while (cursor.moveToNext()){
            int total=cursor.getInt(cursor.getColumnIndex(Table.PAYMENTs));
            sum=sum+total;
        }
        toatal_sum.setText(sum+"");
        Log.d("salunke",sum+"");
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
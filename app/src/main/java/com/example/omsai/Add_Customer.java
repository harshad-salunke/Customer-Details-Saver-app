package com.example.omsai;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.omsai.data.CustomerContract;
import com.example.omsai.data.CustomerhelperDb;
import com.example.omsai.data.CustomerContract.Table;
import com.google.android.material.tabs.TabLayout;

import java.text.SimpleDateFormat;
import java.util.Date;


public class Add_Customer extends AppCompatActivity {
    CustomerhelperDb mDataHelper;
    EditText name,des,mobile,date,payment;
    Uri data=Table.CONTENT_URI;
    Boolean person_edit=true;
    Uri person_delete=null;
    Boolean cheek=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_customer);
        mDataHelper=new CustomerhelperDb(this);
        name=findViewById(R.id.c_name);
        des=findViewById(R.id.c_des);
        mobile=findViewById(R.id.c_number);
        date=findViewById(R.id.c_date);
        date.setText(getDATE());
        payment=findViewById(R.id.pay);

        Intent intent=getIntent();
        Uri currenturi=intent.getData();
        if(currenturi==null){
            setTitle("Add Details");

        }
        else {

            setTitle("Edit Person");
            person_edit=true;
            data=currenturi;
            person_edit=false;
            person_delete=currenturi;
            String projection[] = {
                    Table._ID,
                    Table.NAME,
                    Table.DESCRIP,
                    Table.DATE,
                    Table.Mobile,
                    Table.PAYMENTs


            };

            Cursor cursor=getContentResolver().query(currenturi,projection,null,null,null);
            while (cursor.moveToNext()){
                Log.d("name_harshad",cursor.getString(cursor.getColumnIndex(Table.NAME)));
                name.setText(cursor.getString(cursor.getColumnIndex(Table.NAME)));
                des.setText(cursor.getString(cursor.getColumnIndex(Table.DESCRIP)));
                date.setText(cursor.getString(cursor.getColumnIndex(Table.DATE)));
                mobile.setText(cursor.getString(cursor.getColumnIndex(Table.Mobile)));
                payment.setText(cursor.getLong(cursor.getColumnIndex(Table.PAYMENTs))+"");
            }

        }

    }

    private String getDATE() {
        SimpleDateFormat formatter=new SimpleDateFormat("dd/MM/yyyy");
        Date date=new Date();
        String result=formatter.format(date);
        return result;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.editing,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int select=item.getItemId();

        if(select==R.id.action_save){
            insetCustomerData();

            if(cheek){
                finish();
            }

        }
        if(select==R.id.delete){
            if(person_delete==null){
                Toast.makeText(this, "Please select the person to delete thier data", Toast.LENGTH_SHORT).show();
            }
            else {
                int n=getContentResolver().delete(person_delete,null,null);
                if(cheek){
                    finish();
                }

            }

        }
        return true;
    }

    private void insetCustomerData() {
        String put_name=name.getText().toString().trim();
        String put_des=des.getText().toString().trim();
        String put_mobile=mobile.getText().toString().trim();
        String put_date=date.getText().toString().trim();
        String pay=payment.getText().toString().trim();
        Long put_pay=null;
if(!pay.isEmpty()){
    put_pay=Long.parseLong(pay);
}

if(put_pay==null || put_name.isEmpty()){
    cheek=false;
    Toast.makeText(this, "Name and Payment Compulsory", Toast.LENGTH_SHORT).show();
}

else {
    cheek=true;
    SQLiteDatabase  db=mDataHelper.getWritableDatabase();
    ContentValues values=new ContentValues();
    values.put(Table.NAME,put_name);
    values.put(Table.DESCRIP,put_des);
    values.put(Table.DATE,put_date);
    values.put(Table.Mobile,put_mobile);
    values.put(Table.PAYMENTs,put_pay);



    Uri uri=data;

    if(person_edit){
        uri=getContentResolver().insert(uri,values);
    }
    else {
        int number=getContentResolver().update(uri,values,null,null);

    }
    person_edit=true;

    if (uri==null) {
        Toast.makeText(this, "Somew Error ", Toast.LENGTH_SHORT).show();
    }
    Toast.makeText(this, "Data is Saved", Toast.LENGTH_SHORT).show();
}


    }
}
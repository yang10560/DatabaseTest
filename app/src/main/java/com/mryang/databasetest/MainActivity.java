package com.mryang.databasetest;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private MyDatabaseHelper dbHelper;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new MyDatabaseHelper(this,"BookStore.db",null,3);
        Button createButton = (Button) findViewById(R.id.create);
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbHelper.getWritableDatabase();
            }
        });

        Button addButton = (Button) findViewById(R.id.add_data);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase db =dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("name","Jane Eyre");
                values.put("author","Charlotte Bronte");
                values.put("pages",500);
                values.put("price",82.36);
                db.insert("Book",null,values);
                values.clear();
                values.put("name","Scarlet and Black");
                values.put("author","Marie-Henri Beyle");
                values.put("pages",632);
                values.put("price",52.36);
                db.insert("Book",null,values);
            }
        });
        Button updateButton = (Button)findViewById(R.id.update_data);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase db =dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("pages",520);
                db.update("Book",values,"name = ?",new String[]{"Jane Eyre"});
            }
        });

        Button deleteButton = (Button) findViewById(R.id.delete_data);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase db =dbHelper.getWritableDatabase();

               db.delete("Book","pages > ?",new String[]{"600"});
            }
        });

        Button queryButton = (Button) findViewById(R.id.query_data);
        queryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                Cursor cursor = db.query("Book",null,null,null,null,null,null);
                if(cursor.moveToFirst()){
                    do{
                        String name = cursor.getString(cursor.getColumnIndex("name"));
                        String author = cursor.getString(cursor.getColumnIndex("author"));
                        int  pages = cursor.getInt(cursor.getColumnIndex("pages"));
                        double price = cursor.getDouble(cursor.getColumnIndex("price"));
                        Log.d(TAG, name);
                        Log.d(TAG, author);
                        Log.d(TAG, String.valueOf(pages));
                        Log.d(TAG, String.valueOf(price));

                    }while (cursor.moveToNext());

                }
                cursor.close();

            }
        });
    }
}

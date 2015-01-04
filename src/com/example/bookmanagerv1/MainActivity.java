package com.example.bookmanagerv1;

import android.support.v7.app.ActionBarActivity;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class MainActivity extends ActionBarActivity {
	SQLiteDatabase db; 
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db=openOrCreateDatabase("BookManagerDB", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS BOOKS(BOOKID INTEGER PRIMARY KEY,BOOKNAME VARCHAR,BCDATE VARCHAR);");
        db.execSQL("CREATE TABLE IF NOT EXISTS PERSON(PERSONID INTEGER PRIMARY KEY,PERSONNAME VARCHAR);");
        db.execSQL("CREATE TABLE IF NOT EXISTS PERSON_BOOK(PERSONID INTEGER,BOOKID INTEGER);");
        db.execSQL("CREATE TABLE IF NOT EXISTS PERSON_SPEND(PERSONID INTEGER,BOOKID INTEGER,AMOUNT INTEGER,SDATE VARCHAR,ITEMNAME VARCHAR);");
        
        showMessage("Success", "Tables created");
        
        
    }

    public void CreateBookpressed(View view){
    	
    	Intent intent=new Intent(this,CreateBookActivity.class);
    	startActivity(intent);
    
    }
    public void ManageBookpressed(View view){
    	
    	Intent intent=new Intent(this,ManageBookActivity.class);
    	startActivity(intent);
    
    }
    
    
    
    
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    public void showMessage(String title,String message)
    {
    	Builder builder=new Builder(this);
    	builder.setCancelable(true);
    	builder.setTitle(title);
    	builder.setMessage(message);
    	builder.show();
	}
    
    
}

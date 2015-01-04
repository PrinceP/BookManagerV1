package com.example.bookmanagerv1;

import java.net.ContentHandler;
import java.util.ArrayList;
import java.util.Calendar;


import android.support.v7.app.ActionBarActivity;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ListActivity;
import android.app.AlertDialog.Builder;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import android.app.DatePickerDialog;


@SuppressLint({ "NewApi", "DefaultLocale" }) public class CreateBookActivity  extends ListActivity implements OnClickListener {

	    EditText textContent,editbook,editdate;
	    Button submit,createbutton,selectdate;
	    ListView mainListview;
	    SQLiteDatabase db;
	    
	 
	    private static ArrayList<String> ListviewContent  ; 
	    private static class ListViewAdapter extends BaseAdapter {
	    private LayoutInflater mInflater;
	    public ListViewAdapter(Context context) {
	    	
	        mInflater = LayoutInflater.from(context);
        }
	    public int getCount() {
	        return ListviewContent.size();
	    }
	    public Object getItem(int position) {
	        return position;
	    }
	    public long getItemId(int position) {
	        return position;
	    }
	    public View getView(int position, View convertView, ViewGroup parent) {
	        ListContent holder;
            if (convertView == null) {
	            convertView = mInflater.inflate(R.layout.listviewinflate, null);

	            holder = new ListContent();
	            holder.text = (TextView) convertView.findViewById(R.id.TextView01);
	            holder.text.setCompoundDrawables(convertView.getResources().getDrawable(R.drawable.icon), null, null, null);

	            convertView.setTag(holder);
	        } else {
	            holder = (ListContent) convertView.getTag();
	        }
            holder.text.setText(ListviewContent.get(position));
	            return convertView;
	    }

	    static class ListContent {
	        TextView text;

	    }
	}
	  
	    public void selectDate(View view) {
	    	DialogFragment newFragment = new SelectDateFragment();
	    	newFragment.show(getFragmentManager(), "DatePicker");
	    	} 
	    public void populateSetDate(int year, int month, int day) {
	    	editdate.setText(month+"/"+day+"/"+year);
	        	
	    }
 
	    
	    
	    public class SelectDateFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
	    	public Dialog onCreateDialog(Bundle savedInstanceState) {
	    	final Calendar calendar = Calendar.getInstance();
	    	int yy = calendar.get(Calendar.YEAR);
	    	int mm = calendar.get(Calendar.MONTH);
	    	int dd = calendar.get(Calendar.DAY_OF_MONTH);
	    	return new DatePickerDialog(getActivity(), this, yy, mm, dd);
	    	}

			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear,
					int dayOfMonth) {
				// TODO Auto-generated method stub
				populateSetDate(year, monthOfYear+1, dayOfMonth);
				
			}
	    }

	    
	    
	    
	    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_book);
		
		textContent=(EditText)findViewById(R.id.EditText01);
        submit=(Button)findViewById(R.id.Button01);
        submit.setOnClickListener(this);
        ListviewContent = new ArrayList<String>();
        editbook=(EditText) findViewById(R.id.editText1CreateBook);
        editdate=(EditText) findViewById(R.id.editText2CreateBook);
        
        createbutton = (Button) findViewById(R.id.CreateActivitybutton1);
        createbutton.setOnClickListener(this);
        db=openOrCreateDatabase("BookManagerDB", Context.MODE_PRIVATE, null);
        
        setListAdapter(new ListViewAdapter(this));
        db.execSQL("DELETE FROM BOOKS");
        db.execSQL("DELETE FROM PERSON");
        db.execSQL("DELETE FROM PERSON_BOOK");
		
	}
	@Override
	protected void onRestart(){
		ListviewContent = new ArrayList<String>();
		setListAdapter(new ListViewAdapter(this));
		System.out.println("onrestart");
		super.onRestart();
	}
	
	
	
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.create_book, menu);
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
	
	
	public void onClick(View v) {
		if(v==submit)
		{   if(textContent.length()>0){ 
			ListviewContent.add(textContent.getText().toString());
			textContent.setText("");
			setListAdapter(new ListViewAdapter(this));
		    }
		}
		else if(v==createbutton){			
			if(editbook.getText().toString().trim().length()==0){
				{
		         showMessage("Error","Enter the name of Book");
				 return;
				}
			}
			if(editdate.getText().toString().trim().length()==0){
				{
		         showMessage("Error","Enter the creation date");
				 return;
				}
			}
			if(ListviewContent.isEmpty()){
				{
			     showMessage("Error","Please add the persons");
			     return;
				}
			}
			if(ListviewContent.size()==1){
				{
				     showMessage("Confusion","One person involved,Why");
				     return;
				}
			}	
			
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append("SELECT BOOKNAME FROM BOOKS WHERE UPPER(BOOKNAME) = ");
			stringBuilder.append(editbook.getText().toString().toUpperCase());
			Cursor b=db.rawQuery(stringBuilder.toString(), null);
    		if(b.getCount()!=0)
    		{
    			showMessage("Error", "Book Name Exists");
    			return;
    		}
    	 
			
			
			
			
			
			 ContentValues values1= new ContentValues();
 		     
 		     values1.put("BOOKNAME", editbook.getText().toString());
 		     values1.put("BCDATE", editdate.getText().toString());
 		     long  X;
 		     db.execSQL("UPDATE OR IGNORE BOOKS SET BOOKID = "+(X=db.insert("BOOKS", null, values1))+ " WHERE BOOKID IS NULL " );
 		     
 	        
			 for (String element:ListviewContent) {
				 ContentValues values2= new ContentValues();
	 		     values2.put("PERSONNAME", element.trim());
	             long Y;  		    
  	 		     db.execSQL("UPDATE OR IGNORE PERSON SET PERSONID = "+(Y=db.insert("PERSON", null, values2))+" WHERE PERSONID IS NULL");
	 		     
	 		     db.execSQL("INSERT INTO PERSON_BOOK VALUES ( "+ Y + "," + X+" )");
	 		     
			 }
			 
			 
			 showMessage("Success", "Book added");
			 
			 Cursor c=db.rawQuery("SELECT * FROM BOOKS", null);
	    		if(c.getCount()==0)
	    		{
	    			showMessage("Error", "No records found");
	    			return;
	    		}
	    		StringBuffer buffer=new StringBuffer();
	    		while(c.moveToNext())
	    		{
	    			buffer.append("BookID: "+c.getString(0)+"\n");
	    			buffer.append("Name: "+c.getString(1)+"\n");
	    			buffer.append("Date: "+c.getString(2)+"\n\n");
	    		}
	    		showMessage("Book Details", buffer.toString());
                
	    		Cursor d=db.rawQuery("SELECT * FROM PERSON", null);
	    		if(d.getCount()==0)
	    		{
	    			showMessage("Error", "No records found");
	    			return;
	    		}
	    		StringBuffer buffer2=new StringBuffer();
	    		while(d.moveToNext())
	    		{
	    			buffer2.append("PerID: "+d.getString(0)+"\n");
	    			buffer2.append("Name: "+d.getString(1)+"\n");
	    		}
	    		showMessage("Person Details", buffer2.toString());

	    		Cursor e=db.rawQuery("SELECT * FROM PERSON_BOOK", null);
	    		if(e.getCount()==0)
	    		{
	    			showMessage("Error", "No records found");
	    			return;
	    		}
	    		StringBuffer buffer3=new StringBuffer();
	    		while(e.moveToNext())
	    		{
	    			buffer3.append("PerID: "+e.getString(0)+"\n");
	    			buffer3.append("BookID: "+e.getString(1)+"\n");
	    		}
	    		showMessage("Person Book Details", buffer3.toString());

			 
			
			}
		
		
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



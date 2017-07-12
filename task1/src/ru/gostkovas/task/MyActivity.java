package ru.gostkovas.task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import android.app.Activity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.widget.Toast;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
//import android.database.sqlite.SQLiteOpenHelper;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.content.Intent;
import android.widget.Button;
import android.view.View.OnClickListener;
import java.text.SimpleDateFormat;
import android.graphics.Color;
//import android.view.LayoutInflater;
//import android.view.ViewGroup.LayoutParams;
//import android.widget.LinearLayout;
import android.widget.TextView;
import java.sql.Date;

//import ru.gostkovas.task.R;

public class MyActivity extends Activity implements OnClickListener, OnItemClickListener, LocationListener {

  private static final int CM_DELETE_ID = 1;
  // maps key-names
  final String ATTRIBUTE_ID = "id";
  final String ATTRIBUTE_NAME = "name";
  final String ATTRIBUTE_TEXT = "text";
  final String ATTRIBUTE_VIP = "vip";
  final String ATTRIBUTE_IMAGE = "image";
  final String ATTRIBUTE_DATE = "date";
  ListView listview;
  SimpleAdapter sAdapter;
  ArrayList<Map<String, Object>> notes;
  Button btnCreate;
  DBHelper dbHelper;
  SimpleDateFormat sdf;
  ContentValues cv;
  SQLiteDatabase db;
  LocationManager lm;
  Location loc;

  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);

	lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
    lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);

	//	set date format
	sdf = new SimpleDateFormat("HH:mm:ss");	//"dd.MM.yyyy"
	// geting saved instance after change screen orientation
	notes = (ArrayList) getLastNonConfigurationInstance();
//      notes = (ArrayList) onRetainCustomNonConfigurationInstance();
	// creating new arraylist
    if(notes == null) notes = new ArrayList<>();//Map<String, Object>
    // array name-keys maps (from read-out)
    String[] from = { ATTRIBUTE_NAME, ATTRIBUTE_DATE };
    // array view-elements (template item.xml)
	int[] to = { R.id.tvName, R.id.tvDate };
    // create adapter
    sAdapter = new MySimpleAdapter(this, notes, R.layout.item, from, to);

    // find listview in main.xml (write-in)
    listview = (ListView) findViewById(R.id.lvNotes);
	// set adapter for listview
    listview.setAdapter(sAdapter);
	// registering context menu for listview
    registerForContextMenu(listview);

	btnCreate = (Button) findViewById(R.id.btnCreate);
    btnCreate.setOnClickListener(this);

	listview.setOnItemClickListener(this);

	dbHelper = new DBHelper(this);
	//
    cv = new ContentValues();
    //
    db = dbHelper.getWritableDatabase();

	if(savedInstanceState == null) {
	  readFromDB();
	}
	
	// update adapter
	sAdapter.notifyDataSetChanged();
  }

  //============== EDIT NOTE ==============
  @Override
  public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    //	prepare intent & call ViewActivity
	Intent intent = new Intent(this, ViewActivity.class);
    intent.putExtra("name", (String) notes.get(position).get(ATTRIBUTE_NAME));
    intent.putExtra("text", (String) notes.get(position).get(ATTRIBUTE_TEXT));
	intent.putExtra("vip", (String) notes.get(position).get(ATTRIBUTE_VIP));
	intent.putExtra("pos", position);
    startActivityForResult(intent, 2);
  }

  //============== CREATE NOTE ==============
  @Override
  public void onClick(View v) {
	switch (v.getId()) {
      case R.id.btnCreate:
	    loc = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
	    //	call ActivityEdit
        Intent intent = new Intent(this, ActivityEdit.class);
        startActivityForResult(intent, 1);
      break;
      default:
      break;
    }
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {

    if (data == null) {return;}

	// Return from ActivityEdit (Create onClick)
	if(requestCode == 1) {

	  Note note = new Note();
      note.setDate(new Date(System.currentTimeMillis()));
	  note.setName(data.getStringExtra("name"));
	  note.setText(data.getStringExtra("text"));
	  note.setVip(data.getStringExtra("vip"));

      // CALL SQLite(write to DB)
	  cv.put("dbname", note.getName());
      cv.put("dbtext", note.getText());
	  cv.put("dbvip", note.getVip());
      cv.put("dbimage", note.getImage());
	  cv.put("dbdate", note.getStringDate());
      //
      long rowID = db.insert("mytable", null, cv);

      // add map to arraylist
      notes.add(fillMap(note));
      // update adapter
      sAdapter.notifyDataSetChanged();

	//	Return from ViewActivity (View-Edit onItemClick)
	} else if (requestCode == 2) {

	  Map mEdit = notes.get(data.getIntExtra("pos", 0));
	  mEdit.put(ATTRIBUTE_NAME, data.getStringExtra(ATTRIBUTE_NAME));
	  mEdit.put(ATTRIBUTE_TEXT, data.getStringExtra(ATTRIBUTE_TEXT));
	  mEdit.put(ATTRIBUTE_VIP, data.getStringExtra(ATTRIBUTE_VIP));

      // CALL SQLite(write to DB)
	  cv.put("dbname", (String) mEdit.get(ATTRIBUTE_NAME));
      cv.put("dbtext", (String) mEdit.get(ATTRIBUTE_TEXT));
	  cv.put("dbvip", (String) mEdit.get(ATTRIBUTE_VIP));
	  db.update("mytable", cv, "id = ?", new String[] { "" + (int)(mEdit.get(ATTRIBUTE_ID)) });

	  // update adapter
      sAdapter.notifyDataSetChanged();
	}
  }

  @Override
  public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
    super.onCreateContextMenu(menu, v, menuInfo);
    menu.add(0, CM_DELETE_ID, 0, "Delete Note");
  }

  @Override
  public boolean onContextItemSelected(MenuItem item) {
    if (item.getItemId() == CM_DELETE_ID) {
      // get note info
      AdapterContextMenuInfo acmi = (AdapterContextMenuInfo) item.getMenuInfo();
	  // CALL SQLite (write to DB)
	  //	finding id by acmi.position
	  int id = (int) (notes.get(acmi.position).get(ATTRIBUTE_ID));
	  int delCount = db.delete("mytable", "id = " + id, null);
      // remove map (note) from arraylist
      notes.remove(acmi.position);
      // update adapter
      sAdapter.notifyDataSetChanged();
      return true;
    }
    return super.onContextItemSelected(item);
  }


  private void readFromDB() {
    Cursor c = db.query("mytable", null, null, null, null, null, null);
    if (c.moveToFirst()) {
      do {
	    Note note = new Note();
		note.setId(c.getInt(c.getColumnIndex("id")));
		note.setName(c.getString(c.getColumnIndex("dbname")));
	    note.setText(c.getString(c.getColumnIndex("dbtext")));
		note.setVip(c.getString(c.getColumnIndex("dbvip")));
		note.setImage(c.getInt(c.getColumnIndex("dbimage")));
		note.setStringDate(c.getString(c.getColumnIndex("dbdate")));
		// add map to arraylist
        notes.add(fillMap(note));
      } while (c.moveToNext());
//		// update adapter
    }
    c.close();
  }

  private Map<String, Object> fillMap(Note note) {
    // create new map
    Map<String, Object> map = new HashMap<>();//String, Object
	map.put(ATTRIBUTE_ID, note.getId());
    map.put(ATTRIBUTE_NAME, note.getName());
	map.put(ATTRIBUTE_TEXT, note.getText());
	map.put(ATTRIBUTE_VIP, note.getVip());
	map.put(ATTRIBUTE_IMAGE, note.getImage());
	map.put(ATTRIBUTE_DATE, note.getStringDate());
	return map;
  }


  protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
  }
  protected void onPause() {
      super.onPause();
  }
  protected void onStop() {
    super.onStop();
  }
//  public Object onRetainCustomNonConfigurationInstance() {
//    return notes;
//  }
  public Object onRetainNonConfigurationInstance() {
    return notes;
  }
  protected void onDestroy() {
    super.onDestroy();
	dbHelper.close();
  }
  protected void onRestart() {
    super.onRestart();
  }
  protected void onStart() {
    super.onStart();
  }
  protected void onRestoreInstanceState(Bundle savedInstanceState) {
    super.onRestoreInstanceState(savedInstanceState);
  }
  protected void onResume() {
    super.onResume();
  }
  
  @Override
  public void onLocationChanged (Location loc) {
    if (loc != null) {
      this.loc = loc;
      double sh = loc.getLatitude(); //sh
      double dol = loc.getLongitude(); //dol
	  Toast.makeText(this, "Широта = " + sh + ", Долгота = " + dol, Toast.LENGTH_LONG).show();
    }
  }
  @Override
  public void onProviderDisabled (String provider) {
  }
  @Override
  public void onProviderEnabled (String provider) {
  }
  @Override
  public void onStatusChanged (String provider, int status, Bundle extras) {
  }
}

//Toast.makeText(this, "after", Toast.LENGTH_LONG).show();
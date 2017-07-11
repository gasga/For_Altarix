package ru.gostkovas.task;
 
//import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
//import android.content.ContentValues;
import android.content.Context;

 class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context) {
      //
      super(context, "myDB", null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
      //
      db.execSQL("create table mytable ("
          + "id integer primary key autoincrement," 
          + "dbname text,"
          + "dbtext text,"
		  + "dbvip text,"
		  + "dbimage integer,"
		  + "dbdate text" + ");");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
  }
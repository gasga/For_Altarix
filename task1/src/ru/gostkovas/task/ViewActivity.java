package ru.gostkovas.task;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.view.View.OnClickListener;
import android.widget.Button;

//import android.widget.AdapterView;
//import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;
import android.widget.EditText;
import java.sql.Date;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import android.widget.Toast;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import android.os.Environment;

//import ru.gostkovas.task.R;


public class ViewActivity extends Activity implements OnClickListener {

  Button btnClose, btnEdit, btnFileW, btnFileR;
  TextView tvName, tvText;
  int pos;
  String vip;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.view);
	
    btnClose = (Button) findViewById(R.id.btnClose);
    btnClose.setOnClickListener(this);
	btnEdit = (Button) findViewById(R.id.btnEdit);
    btnEdit.setOnClickListener(this);
	btnFileW = (Button) findViewById(R.id.btnFileW);
    btnFileW.setOnClickListener(this);
	btnFileR = (Button) findViewById(R.id.btnFileR);
    btnFileR.setOnClickListener(this);
	
	tvName = (TextView) findViewById(R.id.tvName);
	tvText = (TextView) findViewById(R.id.tvText);
	
	//	This code only for device rotation?
    Intent intent = getIntent();
	pos = intent.getIntExtra("pos", 0);	// IF NOT FOUND RETURN 0
	
	tvName.setText(intent.getStringExtra("name"));
	tvText.setText(intent.getStringExtra("text"));
	vip = intent.getStringExtra("vip");
	tvName.setTextColor(Note.getColor(vip));
	
  }

  @Override
  public void onClick(View v) {
	Intent intent;
	switch (v.getId()) {
      case R.id.btnClose:
		intent = new Intent();
	    intent.putExtra("name", tvName.getText().toString());
        intent.putExtra("text", tvText.getText().toString());
		intent.putExtra("vip", vip);
	    intent.putExtra("pos", pos);
        setResult(RESULT_OK, intent);
        finish();
      break;
	  case R.id.btnEdit:
        intent = new Intent(this, ActivityEdit.class);
        intent.putExtra("name", tvName.getText().toString());
        intent.putExtra("text", tvText.getText().toString());
		intent.putExtra("vip", vip);
        startActivityForResult(intent, 3);
      break;
	  case R.id.btnFileW:
	    writeFile();
      break;
	  case R.id.btnFileR:
	    readFile();
      break;
      default:
      break;
    }
  }
  
  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (data == null) {return;}
	
	if(requestCode == 3) {
      tvName.setText(data.getStringExtra("name"));
	  tvText.setText(data.getStringExtra("text"));
	  vip = data.getStringExtra("vip");
	  tvName.setTextColor(Note.getColor(vip));
	}
  }
  
//===================================  WRITE/READ FILES  ======================================================  
  void writeFile() {
    String fileName = tvName.getText().toString()  + ".txt";
    String fileConsist = tvText.getText().toString();
    // check storage
    if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
      return;
    }
    // geting path
    File sdPath = Environment.getExternalStorageDirectory();
    // create new folder
    sdPath = new File(sdPath.getAbsolutePath() + "/" + "MY_FILES");
    // create new dir
    sdPath.mkdirs();
    File sdFile = new File(sdPath, fileName);
    try {
      // open stream filewriter
      BufferedWriter bw = new BufferedWriter(new FileWriter(sdFile));
      // write to file
      bw.write(fileConsist);
      bw.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  void readFile() {
    String fileName = tvName.getText().toString()  + ".txt";
    // check storage
    if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
      return;
    }
    // geting path
    File sdPath = Environment.getExternalStorageDirectory();
    // create new folder
    sdPath = new File(sdPath.getAbsolutePath() + "/" + "MY_FILES");
    File sdFile = new File(sdPath, fileName);
    try {
      // open stream filereader
      BufferedReader br = new BufferedReader(new FileReader(sdFile));
      String str = "";
      // read file
	  str = br.readLine();
	  br.close();
	  Toast.makeText(this, "File = " + str, Toast.LENGTH_LONG).show();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
//==========================================================================================================================================  

  protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
	outState.putString("name", tvName.getText().toString());
	outState.putString("text", tvText.getText().toString());
	outState.putString("vip", vip);
  }
  protected void onPause() {
      super.onPause();
  }
  protected void onStop() {
    super.onStop();
  }
  protected void onDestroy() {
    super.onDestroy();
  }
  protected void onRestart() {
    super.onRestart();
  }
  protected void onStart() {
    super.onStart();
  }
  protected void onRestoreInstanceState(Bundle savedInstanceState) {
    super.onRestoreInstanceState(savedInstanceState);
	tvName.setText(savedInstanceState.getString("name"));
	tvText.setText(savedInstanceState.getString("text"));
	vip = savedInstanceState.getString("vip");
	tvName.setTextColor(Note.getColor(vip));
  }
  protected void onResume() {
    super.onResume();
  }
}

/*  
    void writeFile() {
	  String fileName = tvName.getText().toString();
	  String fileConsist = tvText.getText().toString();
    try {
      // open stream for write
      BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(openFileOutput(fileName, MODE_WORLD_WRITEABLE)));
	  // write text to file
      bw.write(fileConsist);
      // close stream
      bw.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
  void readFile() {
    String fileName = tvName.getText().toString();
    try {
      // open stream for read
      BufferedReader br = new BufferedReader(new InputStreamReader(openFileInput(fileName)));
      String str = "";
      // read file
      while ((str = br.readLine()) != null) {
      }
	  Toast.makeText(this, "File = " + str, Toast.LENGTH_LONG).show();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
*/  
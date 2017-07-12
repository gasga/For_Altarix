package ru.gostkovas.task;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.view.View.OnClickListener;
import android.widget.Button;

import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.sql.Date;

//import ru.gostkovas.task.R;


public class ActivityEdit extends Activity implements OnClickListener {
  Button btnSave;
  Button btnCancel;
  EditText etName;
  EditText etText;
  String name, text, vip, oldVip = "GRAY";
  Spinner spVip;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.edit);

    btnSave = (Button) findViewById(R.id.btnSave);
    btnSave.setOnClickListener(this);
	btnCancel = (Button) findViewById(R.id.btnCancel);
    btnCancel.setOnClickListener(this);
	etName = (EditText) findViewById(R.id.etName);
	etText = (EditText) findViewById(R.id.etText);
    spVip = (Spinner) findViewById(R.id.spVip);
	
	Intent intent = getIntent();
	if(intent != null) {
      name = intent.getStringExtra("name");
      text = intent.getStringExtra("text");
      vip = intent.getStringExtra("vip");
	  etName.setText(name);
	  etText.setText(text);
	  if ((oldVip == "GRAY") && (vip != null)) { //!!!!!!!!!!!!!!! OR "" ??????????????
	    oldVip = vip;
	  }
	}
	
	spVip.setOnItemSelectedListener(new OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
	    if(vip != "") {
		  vip = spVip.getSelectedItem().toString();
		}
		etName.setTextColor(Note.getColor(vip));
      }
	  @Override
      public void onNothingSelected(AdapterView<?> arg0) {}
    });
  }
  
  @Override
  public void onClick(View v) {
  
	Intent intent = new Intent();
	switch (v.getId()) {
      case R.id.btnSave:
        intent.putExtra("name", etName.getText().toString());
	    intent.putExtra("text", etText.getText().toString());
        intent.putExtra("vip", vip);
        break;
	  case R.id.btnCancel:
        intent.putExtra("name", name);
	    intent.putExtra("text", text);
        intent.putExtra("vip", oldVip);
        break;
      default:
        break;
    }
	
	setResult(RESULT_OK, intent);
    finish();
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
  }
  protected void onResume() {
    super.onResume();
  }
}

//		Toast.makeText(this, "vip = " + etName.getTextColors().getDefaultColor(), Toast.LENGTH_LONG).show();

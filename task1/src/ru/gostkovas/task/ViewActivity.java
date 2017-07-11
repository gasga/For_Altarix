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

//import ru.gostkovas.task.R;


public class ViewActivity extends Activity implements OnClickListener {
  Button btnClose;
  TextView tvName;
  TextView tvText;
  Button btnEdit;
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
	
	tvName = (TextView) findViewById(R.id.tvName);
	tvText = (TextView) findViewById(R.id.tvText);
	
	//	This code only for device rotation???????????
    Intent intent = getIntent();
//    String name = intent.getStringExtra("name");
//    String text = intent.getStringExtra("text");
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
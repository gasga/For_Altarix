package ru.gostkovas.task;

import android.widget.SimpleAdapter;
import android.content.Context;
import java.util.Map;
import java.util.List;
import android.widget.TextView;
import android.graphics.Color;

class MySimpleAdapter extends SimpleAdapter {

  List data;
  
  public MySimpleAdapter (Context context, List<Map<String, Object>> data, int resource, String[] from, int[] to) {	//List<? extends Map<String, ?>> data
    super(context, data, resource, from, to);
	this.data = data;
  }
  @Override
  public void setViewText(TextView v, String text) {
    super.setViewText(v, text);
 
    if (v.getId() == R.id.tvName) {
	
	  v.setTextColor(findColor(text));
    }
  }
  
  public int findColor(String text) {
    for (int i = 0; i < data.size(); i++) {
	  Map<String, Object> m = (Map<String, Object>) data.get(i);
	  String sName = (String) m.get("name");
	  if(sName == text) {
	    String sVip = (String) m.get("vip");
		return Note.getColor(sVip);
	  }
	}
	return Color.GRAY;
  }
  
}
  

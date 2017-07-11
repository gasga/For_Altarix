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
//	  v.setText(text + " gas");
//	  v.setTextColor(Color.BLUE);
//	  data.get(i).get("vip")
//      if(note != null) v.setTextColor(note.getVip());
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
  
  
  /*
  if((String)(data.get(i).get("name")) == text) {	//(Map<String, Object>)
	    return getColor((String)(data.get(i).get("vip")));	//(Map<String, Object>)
      }
  
  
  
  class MySimpleAdapter extends SimpleAdapter {
    public MySimpleAdapter(Context context,
        List<? extends Map<String, ?>> data, int resource,
        String[] from, int[] to) {
      super(context, data, resource, from, to);
    }
    @Override
    public void setViewText(TextView v, String text) {
      // метод супер-класса, который вставляет текст

      super.setViewText(v, text);
      // если нужный нам TextView, то разрисовываем 
      if (v.getId() == R.id.tvValue) {
        int i = Integer.parseInt(text);
        if (i < 0) v.setTextColor(Color.RED); else
          if (i > 0) v.setTextColor(Color.GREEN);
      }
    }
    @Override
    public void setViewImage(ImageView v, int value) {
      // метод супер-класса
      super.setViewImage(v, value);
      // разрисовываем ImageView
      if (value == negative) v.setBackgroundColor(Color.RED); else
        if (value == positive) v.setBackgroundColor(Color.GREEN);
    }
  }
  */
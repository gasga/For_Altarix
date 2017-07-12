package ru.gostkovas.task;

import java.sql.Date;
import java.text.SimpleDateFormat;
import android.graphics.Color;

public class Note {

  private int id;
  private Date date;
  private String sdate;
  private String name;
  private String text;
  private String vip;
  private int image;
//  private Image image;
//  private GPS-location;

//  public Note(Date date) {
//    this.date = date;
//  }
//  public Note() {
//  }

  public static int getColor(String vip){
    int color;
    switch (vip) {
      case "RED":
        color = Color.RED;
        break;
      case "YELLOW":
        color = Color.YELLOW;
        break;
      case "GREEN":
        color = Color.GREEN;
        break;
      default:
        color = Color.GRAY;
        break;
    }
    return color;
  }
  
  public void setId(int id) {
    this.id = id;
  }
  public void setDate(Date date) {
    this.date = date;
  }
  public void setStringDate(String sdate) {
    this.sdate = sdate;
  }
  public void setName(String name) {
    this.name = name;
  }
  public void setText(String text) {
    this.text = text;
  }
  public void setVip(String vip) {
    this.vip = vip;
  }
  public void setImage(int image) {
    this.image = image;
  }
  
  public int getId() {
    return id;
  }
  public Date getDate() {
    return date;
  }
  public String getStringDate() {
    if(date != null) {
      SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");	//"dd.MM.yyyy"
	  return sdf.format(date);
	} else return sdate;
  }
  public String getName() {
    return name;
  }
  public String getText() {
    return text;
  }
  public String getVip() {
    return vip;
  }
  public int getImage() {
    return image;
  }
  
}

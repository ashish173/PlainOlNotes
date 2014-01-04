package com.davidgassner.plainolnotes.data;

import android.annotation.SuppressLint;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class NoteItem {

	private String key;
	private String text;
	
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@SuppressLint("SimpleDateFormat")   // super lint will suppress the warnings for our sake.
	public static NoteItem getNew() {
		// this methods creates a key of time stamp and value of the Text
		Locale locale = new Locale("en_US");
		Locale.setDefault(locale);  // this makes the local set to en_US; US settings make it easier to sort the dates.
		
		String pattern = "yyyy-MM-dd HH:mm:ss Z";
		SimpleDateFormat formatter = new SimpleDateFormat(pattern);
		
		String key = formatter.format(new Date());   // using the formatter to get the current date in the above specified format
		
		NoteItem note = new NoteItem();
		note.setKey(key);
		note.setText("");
		return note;
	}
	
}

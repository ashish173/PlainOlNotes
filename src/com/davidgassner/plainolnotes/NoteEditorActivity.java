package com.davidgassner.plainolnotes;

import com.davidgassner.plainolnotes.data.NoteItem;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

public class NoteEditorActivity extends Activity {
	
	private NoteItem note;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_note_editor);
		
		// this code will get note text from the main activity  
		Intent intent = this.getIntent();                     // intent gets the ref of the same intent that was created by the calling activity
		note = new NoteItem();
		note.setKey(intent.getStringExtra("key"));            // getStringExtra will get all the putExtra values     
		note.setText(intent.getStringExtra("text"));          // sent from previous activity 
		
		// create an edittext object 
		EditText et = (EditText) findViewById(R.id.noteText); // cast for EditText
		et.setText(note.getText());                           // In case of updating a text it should fetch the existing text 
		et.setSelection(note.getText().length());             // setSelection is set to length
		
	}
		
}

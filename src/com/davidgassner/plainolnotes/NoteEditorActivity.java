package com.davidgassner.plainolnotes;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.EditText;

import com.davidgassner.plainolnotes.data.NoteItem;

public class NoteEditorActivity extends Activity {
	
	private NoteItem note;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_note_editor);
		
		// set the action bar as the home button
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		// this code will get note text from the main activity  
		Intent intent = this.getIntent();                     // intent gets the ref of the same intent that was created by the calling activity
		note = new NoteItem();
		note.setKey(intent.getStringExtra("key"));            // getStringExtra will get all the putExtra values     
		note.setText(intent.getStringExtra("text"));          // sent from previous activity 
		
		// create an edit text object 
		EditText et = (EditText) findViewById(R.id.noteText); // cast for EditText
		et.setText(note.getText());                           // In case of updating a text it should fetch the existing text 
		et.setSelection(note.getText().length());             // places the cursor to the end of text
		
		// debug info
		// Log.i("NOTE", "Length of string " + note.getText().length());
	}
	
	private void saveAndFinish() {
		EditText et = (EditText) findViewById(R.id.noteText);    // to extract noteText text we should give noteid(noteText)
		// Log.i("NOTES", "Have reached in saveAndFinish");
		String noteText = et.getText().toString();
		
		Log.i("NOTES", "Text i modified " + noteText );
		
		Intent intent = new Intent();
		intent.putExtra("key", note.getKey());
		// Debug info
		Log.i("NOTES", "value of key in Editor activity " + note.getKey());
		intent.putExtra("text", noteText);
		setResult(RESULT_OK, intent);                         // RESULT_OK is android const intent will carry the data from this 
		finish();											  // intent to the intent that called it.	
	}
		
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {          // if the item selected is home button 
			saveAndFinish();
			Log.i("NOTES", "Just passed from onOptionsItemSelected");
		}
		return false;
	}
	
	@Override
	public void onBackPressed() {
		Log.i("NOTES", "Back Button pressed");
		saveAndFinish();
	}
}

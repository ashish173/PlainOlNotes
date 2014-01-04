package com.davidgassner.plainolnotes;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

import com.davidgassner.plainolnotes.data.NoteItem;
import com.davidgassner.plainolnotes.data.NotesDataSource;

public class MainActivity extends Activity {

	private NotesDataSource datasource;   // instantiating the NoteDataSource class
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	// ??
    	super.onCreate(savedInstanceState);
        
        // attaches the main activity from resources to the current layout
        setContentView(R.layout.activity_main);
        
        // data store is instantiated from next line 
        // Context is the superclass of Activity class and thus Main Activity class
        datasource = new NotesDataSource(this);  // this for passing in the context of the main activity
        Log.i("NOTES", "datasource has been created");
        List<NoteItem> notes = datasource.findAll();    // notes contains all the notes from the persistent data storage
       
        NoteItem note = notes.get(0);   // 0th gets the first index value
        
        // Will show in the logcat section with tags as "NOTES" and the value as note.getKey()
        // Only when run in the DDMS perspective in the logcat section after entering inn the DEBUG mode
        note.setText("Updated!!!");
        datasource.update(note);
        
        notes = datasource.findAll();
        note = notes.get(0);
        
        
        // For effective logging in the logcat using the tag "NOTES" with value.
        Log.i("NOTES", note.getKey() + " : " + note.getText() + "we are at the last line"); 
        
        
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
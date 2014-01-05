package com.davidgassner.plainolnotes;

import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;

import com.davidgassner.plainolnotes.data.NoteItem;
import com.davidgassner.plainolnotes.data.NotesDataSource;


public class MainActivity extends ListActivity {  // ListActivity inherits from Activity class

	private NotesDataSource datasource;   // instantiating the NoteDataSource class
	List<NoteItem> notesList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	// ??
    	super.onCreate(savedInstanceState);
        
        // attaches the main activity from resources to the current layout
        setContentView(R.layout.activity_main);
        
        // data store is instantiated from next line 
        // Context is the superclass of Activity class and thus Main Activity class
        datasource = new NotesDataSource(this);  // this for passing in the context of the main activity
        
        refreshDisplay();  // this will refresh the notesList a very frequently used method
        
        
        /*  // this commented out code is for testing purpose
        Log.i("NOTES", "datasource has been created");
        datasource.findAll();    // notes contains all the notes from the persistent data storage
       
        NoteItem note = notes.get(0);   // 0th gets the first index value
        
        // Will show in the logcat section with tags as "NOTES" and the value as note.getKey()
        // Only when run in the DDMS perspective in the logcat section after entering inn the DEBUG mode
        note.setText("Updated!!!");
        datasource.update(note);
        
        notes = datasource.findAll();
        note = notes.get(0);
        
         
        // For effective logging in the logcat using the tag "NOTES" with value.
        Log.i("NOTES", note.getKey() + " : " + note.getText() + "we are at the last line"); 
        */
        
        
        
    }

    
    // on call we retrieve all data from persistent data store
    private void refreshDisplay() {
		
    	notesList = datasource.findAll();  // fetch all data
		// create an adapter that will tell android what & how to display
    	ArrayAdapter<NoteItem> adapter = 
				new ArrayAdapter<NoteItem>(this, R.layout.list_item_layout, notesList); 
		// attach this adapter to current ListActivity
    	setListAdapter(adapter);
		
	}


	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
	
	
	// this method is triggered when user clicks or touches anything on the phone screen
	// in this case when user selects on the + icon in the top action bar
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// item is the menu item which was selected or touched
		// we can make sure which menuitem was selected by comparing the item ids
		if (item.getItemId() == R.id.action_create) {
			createNote();
		}
		return super.onOptionsItemSelected(item);
	}

	// create a new note and pass it to the NoteEditorActivity
	private void createNote() {
		NoteItem note = NoteItem.getNew();  // get a new noteItem
		Intent intent = new Intent(this, NoteEditorActivity.class);  // class name is passed android instantiates the activity itself
		intent.putExtra("key", note.getKey());  // these are sent to the next activity
		intent.putExtra("text", note.getText());
		startActivityForResult(intent, 1001);   // requestcode is a const which is used to recgnize how the communication happens
		
		
	}
    
}

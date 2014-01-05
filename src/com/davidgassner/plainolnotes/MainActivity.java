package com.davidgassner.plainolnotes;

import java.lang.ref.Reference;
import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.davidgassner.plainolnotes.data.NoteItem;
import com.davidgassner.plainolnotes.data.NotesDataSource;


public class MainActivity extends ListActivity {  // ListActivity inherits from Activity class

	private static final int EDITOR_ACTIVITY_REQUEST = 1001;
	private static final int MENU_DELETE_ID = 1002;
	private int currentNoteId;
	private NotesDataSource datasource;   // instantiating the NoteDataSource class
	List<NoteItem> notesList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	// ??
    	super.onCreate(savedInstanceState);
        
        // attaches the main activity from resources to the current layout
        setContentView(R.layout.activity_main);
        // context menu enables the hold and pop up menu functionality
        registerForContextMenu(getListView());          // registering context menu for ListView
        
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
		
    	notesList = datasource.findAll();  										// fetch all data
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
		// When user clicks on the plus icon a new note should be made 
		NoteItem note = NoteItem.getNew();  							// get a new noteItem
		Intent intent = new Intent(this, NoteEditorActivity.class); 	// class name is passed android instantiates the activity itself
		intent.putExtra("key", note.getKey());  						// these are sent to the next activity
		intent.putExtra("text", note.getText());
		startActivityForResult(intent, EDITOR_ACTIVITY_REQUEST);   						// requestcode is a const which is used to recgnize how the communication happens
		
		
	}
    // When an item in the list is clicked this method gets called
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// When user clicks existing note it should load the text.
		NoteItem note = notesList.get(position);         // position is the index of the item clicked
		// Debug info
		Log.i("NOTES", "The position clicked " + position);
		
		Intent intent = new Intent(this, NoteEditorActivity.class);
		intent.putExtra("key", note.getKey());
		intent.putExtra("text", note.getText());
		startActivityForResult(intent, EDITOR_ACTIVITY_REQUEST);
	}
	
	
	// When EditorActivity returns to the main activity this method is triggered
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// check if we are back from right activity 
		if (requestCode == EDITOR_ACTIVITY_REQUEST && resultCode == RESULT_OK ) {
			// Log.i("NOTES", "Trying to save the sent data");
			NoteItem note = new NoteItem();
			Log.i("NOTES", "Value of Key  in Main Activity "+data.getStringExtra("key") + " Value " + data.getStringExtra("text"));
			
			note.setKey(data.getStringExtra("key"));
			note.setText(data.getStringExtra("text"));          // there was a typo error i wrote setKey
			datasource.update(note);							// so key value was overwritten by Text value
			refreshDisplay();
		}
	}
	
	@Override
		public void onCreateContextMenu(ContextMenu menu, View v,
				ContextMenuInfo menuInfo) {
			AdapterContextMenuInfo info = (AdapterContextMenuInfo) menuInfo;  // ??
			currentNoteId = (int) info.id;         
			menu.add(0, MENU_DELETE_ID, 0, "Delete");   // see the method definition for more info
			// menu.add(0, MENU, arg2, arg3)           // add another menu item
		}
	
	
	@Override
		public boolean onContextItemSelected(MenuItem item) {
			
			if (item.getItemId() == MENU_DELETE_ID) {
				NoteItem note = notesList.get(currentNoteId);
				datasource.remove(note);
				refreshDisplay();
			}
			return super.onContextItemSelected(item);
		}
	
	
}

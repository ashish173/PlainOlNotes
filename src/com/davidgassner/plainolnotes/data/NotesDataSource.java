package com.davidgassner.plainolnotes.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

//  This class is going to hide all the functionality that is used to put, get and remove the dataitems
//  from the shared preferences data storage
//  It is also going to expose that functionality through a set of methods.
public class NotesDataSource {
	
	private static final String PREFKEY = "notes";    //  naming the shared preferences key
	private SharedPreferences notePrefs;   // declaring the shared preferences
	
	// sets the shared preferences name and mode
	public NotesDataSource(Context context) {
		notePrefs = context.getSharedPreferences(PREFKEY, Context.MODE_PRIVATE);
	}
	
	// gets all the notes saved 
	public List<NoteItem> findAll() {
		
		// the getAll() method knows that the keys will be strings but
		// is does not know about the values so keep the data type of value as ?
		
		Map<String, ?> notesMap = notePrefs.getAll();  
		
		// KeySet returns the keys of all the nodes in the notesMap but in random order
		// TreeSet sorts them in the order we want it to "by keys" 
		// So by the time we have keys(SortedSet object) we have a set of sorted keys
		SortedSet<String> keys = new TreeSet<String>(notesMap.keySet());
		
		List<NoteItem> noteList = new ArrayList<NoteItem>();
		
		for (String key : keys) {
			NoteItem note = new NoteItem();
			note.setKey(key);
			note.setText((String) notesMap.get(key)); // get does not know of the return type so casting to string
			noteList.add(note);
		}
		
		return noteList;
	}
	
	public boolean update(NoteItem note) {
		
		
		Log.i("NOTES", "In update method");
		SharedPreferences.Editor editor = notePrefs.edit();  // getting the editor from shared Prefs.
		Log.i("NOTES", "Value of Key  in Notes DataSource class "+ note.getKey() + " Text " + note.getText());
		editor.putString(note.getKey(), note.getText());
		
		editor.commit();
		return true;
	}
	
	public boolean remove(NoteItem note) {

		if (notePrefs.contains(note.getKey())) {
			
			SharedPreferences.Editor editor = notePrefs.edit();  // getting the editor from shared Prefs.
			editor.remove(note.getKey());
			editor.commit();
			// Log.i("NOTES", "The requested object was deleted holla!!!");
		}
		// else
			// Log.i("NOTES", "There was no object to be deleted Check your Datastore!!");
		return true;
	}
}

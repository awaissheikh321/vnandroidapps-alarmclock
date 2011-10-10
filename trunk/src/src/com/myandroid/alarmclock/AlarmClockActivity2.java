package com.myandroid.alarmclock;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Browser;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class AlarmClockActivity2 extends ListActivity {
	String[] listItems = { "exploring", "android", "list", "activities" };

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
//        String[] projection = new String[] {Browser.BookmarkColumns._ID, 
//                                    Browser.BookmarkColumns.TITLE, 
//                                    Browser.BookmarkColumns.URL,
//                                    Browser.BookmarkColumns.VISITS};
//        String[] displayFields = new String[] {Browser.BookmarkColumns.VISITS,
//                                    Browser.BookmarkColumns.TITLE, 
//                                    Browser.BookmarkColumns.URL};
//        int[] displayViews = new int[] { R.id.bmark_visits,
//                                    R.id.bmark_title,
//                                    R.id.bmark_url};
//
//        Cursor cur = managedQuery(android.provider.Browser.BOOKMARKS_URI, 
//                       projection, null, null, null);
//
//        setListAdapter(new ImageCursorAdapter(this, 
//                        R.layout.image_list, cur, 
//                        displayFields, displayViews
//        ));

        String[] projection = new String[] {Browser.BookmarkColumns._ID, 
                                    Browser.BookmarkColumns.TITLE};
        String[] displayFields = new String[] {Browser.BookmarkColumns.TITLE};
        int[] displayViews = new int[] {R.id.btitle};
 
        Cursor cur = managedQuery(android.provider.Browser.BOOKMARKS_URI, 
                       projection, null, null, null);
 
        setListAdapter(new CheckboxCursorAdapter(this, 
        		R.layout.check_list, cur, 
			displayFields, displayViews
        ));


	}
}
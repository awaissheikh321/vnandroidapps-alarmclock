package com.vnandroidapp.alarmclock.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DatabaseAdapter {

	// Database fields
	public static final String KEY_ROWID = "_id";
	public static final String KEY_TITLE = "title";
	public static final String KEY_TIME = "time";
	public static final String KEY_STATUS = "status";
	private static final String DATABASE_TABLE = "alarm";
	private Context context;
	private SQLiteDatabase database;
	private DatabaseHelper dbHelper;

	public DatabaseAdapter(Context context) {
		this.context = context;
	}

	public DatabaseAdapter open() throws SQLException {
		dbHelper = new DatabaseHelper(context);
		database = dbHelper.getWritableDatabase();
		return this;
	}

	public void close() {
		dbHelper.close();
	}

	
/** * Create a new alarm If the alarm is successfully created return the new * rowId for that note, otherwise return a -1 to indicate failure. */

	public long createAlarm(String title, String time, String status) {
		ContentValues initialValues = createContentValues(title, time, status);
		return database.insert(DATABASE_TABLE, null, initialValues);
	}

	
/** * Update the alarm */

	public boolean updateAlarm(long rowId, String title, String time, String status) {
		ContentValues updateValues = createContentValues(title, time, status);
		return database.update(DATABASE_TABLE, updateValues, KEY_ROWID + "="
				+ rowId, null) > 0;
	}

	
/** * Deletes alarm */

	public boolean deleteAlarm(long rowId) {
		return database.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
	}

	
/** * Return a Cursor over the list of all alarm in the database * * @return Cursor over all notes */

	public Cursor fetchAllAlarms() {
		return database.query(DATABASE_TABLE, new String[] { KEY_ROWID,
				KEY_TITLE, KEY_TIME, KEY_STATUS }, null, null, null,
				null, null);
	}

	
/** * Return a Cursor positioned at the defined alarm */

	public Cursor fetchAlarm(long rowId) throws SQLException {
		Cursor mCursor = database.query(true, DATABASE_TABLE, new String[] {
				KEY_ROWID, KEY_TITLE, KEY_TIME, KEY_STATUS },
				KEY_ROWID + "=" + rowId, null, null, null, null, null);
		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		return mCursor;
	}

	private ContentValues createContentValues(String title, String time, String status) {
		ContentValues values = new ContentValues();
		values.put(KEY_TITLE, title);
		values.put(KEY_TIME, time);
		values.put(KEY_STATUS, status);
		return values;
	}
}
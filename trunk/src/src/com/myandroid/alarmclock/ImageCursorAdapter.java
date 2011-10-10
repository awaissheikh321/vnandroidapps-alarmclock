package com.myandroid.alarmclock;

import android.content.Context;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.provider.Browser;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class ImageCursorAdapter extends SimpleCursorAdapter {

	private Cursor c;
	private Context context;

	public ImageCursorAdapter(Context context, int layout, Cursor c,
			String[] from, int[] to) {
		super(context, layout, c, from, to);
		this.c = c;
		this.context = context;
	}

	public View getView(int pos, View inView, ViewGroup parent) {
		View v = inView;
		if (v == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = inflater.inflate(R.layout.image_list, null);
		}
		this.c.moveToPosition(pos);
		String bookmark = this.c.getString(this.c
				.getColumnIndex(Browser.BookmarkColumns.TITLE));
		byte[] favicon = this.c.getBlob(this.c
				.getColumnIndex(Browser.BookmarkColumns.FAVICON));
		if (favicon != null) {
			ImageView iv = (ImageView) v.findViewById(R.id.bimage);
			iv.setImageBitmap(BitmapFactory.decodeByteArray(favicon, 0,
					favicon.length));
		}
		TextView bTitle = (TextView) v.findViewById(R.id.btitle);
		bTitle.setText(bookmark);
		return (v);
	}

}

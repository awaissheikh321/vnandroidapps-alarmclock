package vnandroidapps.android.clock;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import vnandroidapps.android.clock.R;
import vnandroidapps.android.clock.utils.Background;

import android.app.ListActivity;
import android.content.ContentValues;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

public class FileChooser extends ListActivity {
	private static final String LOCATION = "sdcard";

	private String folder;
	private String file_size;
	private String folder_parent;
	private String message_success;
	private String message_duplicated;
	private File currentDir;
	private FileArrayAdapter adapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Background.setDefaultBackground(getWindow());
		
		currentDir = new File(File.separator + LOCATION + File.separator);
		folder = getResources().getString(R.string.file_type_folder_text);
		file_size = getResources().getString(R.string.file_size_text);
		folder_parent = getResources().getString(R.string.file_type_parent_text);
		message_success = getResources().getString(R.string.file_add_success);
		message_duplicated = getResources().getString(R.string.file_add_duplicated);
		fill(currentDir);
	}

	private void fill(File file) {
		File[] dirs = file.listFiles();
		this.setTitle(file.getAbsolutePath());
		
		List<Option> dirList = new ArrayList<Option>();
		List<Option> fileList = new ArrayList<Option>();
		String[] fileExts = getResources().getStringArray(R.array.file_extension);
		
		for (File ff : dirs) {
			if(ff.getName().startsWith(".")) continue;
			if (ff.isDirectory() && !ff.getName().equals(".android_secure")) {
				dirList.add(new Option(ff.getName(), folder, ff.getAbsolutePath()));
			} else {
				String fName = ff.getName();
				//check extension
				boolean allowAdd = false;
				for(String ext : fileExts) {
					if(fName.toLowerCase().endsWith(ext.toLowerCase())) {
						allowAdd = true;
						break;
					}
				}
				if(allowAdd)
					fileList.add(new Option(fName, file_size + ff.length(), ff.getAbsolutePath()));
			}
		}
		Collections.sort(dirList);
		Collections.sort(fileList);
		dirList.addAll(fileList);
		if (!file.getName().equalsIgnoreCase(LOCATION))
			dirList.add(0, new Option("..", folder_parent, file.getParent()));
		adapter = new FileArrayAdapter(FileChooser.this, R.layout.file_view,
				dirList);
		this.setListAdapter(adapter);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Option option = adapter.getItem(position);
		if (option.getData().equals(folder) || option.getData().equals(folder_parent)) {
			currentDir = new File(option.getPath());
			fill(currentDir);
		} else {
			onFileClick(option);
		}
	}

	private void onFileClick(Option option) {
		File k = new File(option.getPath());
		try {
			ContentValues values = new ContentValues();
			values.put(MediaStore.MediaColumns.DATA, k.getAbsolutePath());
			values.put(MediaStore.MediaColumns.TITLE, k.getName());
			values.put(MediaStore.MediaColumns.MIME_TYPE, "audio/mp3");
			values.put(MediaStore.MediaColumns.DISPLAY_NAME, k.getName());
			values.put(MediaStore.Audio.Media.ARTIST, k.getName());
			values.put(MediaStore.Audio.Media.IS_RINGTONE, "1");
			values.put(MediaStore.Audio.Media.IS_ALARM, "1");
			values.put(MediaStore.Audio.Media.IS_NOTIFICATION, true);
			values.put(MediaStore.Audio.Media.IS_MUSIC, false);

			Uri uri = MediaStore.Audio.Media.getContentUriForPath(k
					.getAbsolutePath());
			Uri newUri = FileChooser.this.getContentResolver().insert(uri,
					values);
			RingtoneManager.setActualDefaultRingtoneUri(
					getApplicationContext(), RingtoneManager.TYPE_RINGTONE,
					newUri);
			Toast.makeText(this, k.getName() + message_success, Toast.LENGTH_SHORT).show();
			finish();
		} catch (Exception e) {
			Toast.makeText(this, message_duplicated, Toast.LENGTH_SHORT).show();
		}
	}

}
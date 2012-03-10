package vnandroidapps.android.clock;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class FileArrayAdapter extends ArrayAdapter<Option> {
	private Context context;
	private int id;
	private List<Option> items;

	public FileArrayAdapter(Context context, int textViewResourceId,
			List<Option> objects) {
		super(context, textViewResourceId, objects);
		this.context = context;
		this.id = textViewResourceId;
		this.items = objects;
	}

	public Option getItem(int index) {
		return items.get(index);
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		if (view == null) {
			LayoutInflater vi = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = vi.inflate(id, null);
		}
		final Option o = items.get(position);
		if (o != null) {
			TextView file_name = (TextView) view.findViewById(R.id.file_name);
			TextView file_size = (TextView) view.findViewById(R.id.file_size);

			if (file_name != null)
				file_name.setText(o.getName());
			if (file_size != null)
				file_size.setText(o.getData());

		}
		return view;
	}

}
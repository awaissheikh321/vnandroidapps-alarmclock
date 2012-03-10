package vnandroidapps.android.clock;

public class Option implements Comparable<Option> {
	private String name;
	private String data;
	private String path;

	public Option(String name, String data, String path) {
		this.name = name;
		this.data = data;
		this.path = path;
	}

	public String getName() {
		return name;
	}

	public String getData() {
		return data;
	}

	public String getPath() {
		return path;
	}

	public int compareTo(Option that) {
		if(that instanceof Option && that != null) {
			return this.name.toLowerCase().compareTo(that.getName().toLowerCase());
		}
		return 0;
	}
}

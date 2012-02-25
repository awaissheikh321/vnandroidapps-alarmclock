package vnandroidapps.android.clock;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

public class WebHome extends Activity {

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.web);
		WebView page = (WebView) findViewById(R.id.page);
		page.loadUrl("http://diendandroid.com");
	}
}

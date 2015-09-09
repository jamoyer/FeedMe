package rainmanproductions.feedme;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;

import rainmanproductions.feedme.dominos.DominosWebViewClient;

public class FeedMeButton extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        final WebView webview = new WebView(this);
        setContentView(webview);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.clearHistory();
        webview.clearFormData();
        webview.clearCache(true);
        webview.setWebViewClient(new DominosWebViewClient());
        webview.loadUrl(DominosWebViewClient.getStartingURL());
        System.out.println("Leaving MainActivity on create.");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_feed_me_button, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

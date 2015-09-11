package rainmanproductions.feedme;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

import rainmanproductions.feedme.dominos.DominosPageFlow;
import rainmanproductions.feedme.dominos.DominosWebViewClient;

public class BrowserActivity extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        System.out.println("Entering BrowserActivity onCreate()");

        final WebView webview = new WebView(this);
        AssetReader.setAssetManager(this);
        setContentView(webview);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.clearHistory();
        webview.clearFormData();
        webview.clearCache(true);
        webview.setWebViewClient(new DominosWebViewClient());
        webview.loadUrl(DominosPageFlow.getStartingURL());

        System.out.println("Leaving BrowserActivity onCreate()");
    }
}
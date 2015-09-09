package rainmanproductions.feedme.dominos;

import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.IOException;

import rainmanproductions.feedme.AssetReader;

public class DominosWebViewClient extends WebViewClient
{
    @Override
    public void onPageFinished(final WebView view, final String url)
    {
        System.out.println("Entering Dominos on page finished. url=" + url);
        DominosPageFlow page = DominosPageFlow.getPageFromURL(url);
        try
        {
            doPage(view, page);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        System.out.println("Leaving Dominos on page finished.");
    }

    private void doPage(final WebView view, final DominosPageFlow page) throws IOException
    {
        System.out.println("Entering doPage(page=" + page + ")");
        String js = AssetReader.readTextAsset(page.getJsFilepath());
        view.loadUrl(js);
        System.out.println("Leaving doPage(page=" + page + ")");
    }
}
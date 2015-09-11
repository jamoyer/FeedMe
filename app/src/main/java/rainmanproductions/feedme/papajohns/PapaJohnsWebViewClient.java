package rainmanproductions.feedme.papajohns;

import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.IOException;

import rainmanproductions.feedme.AssetReader;


public class PapaJohnsWebViewClient extends WebViewClient
{
    @Override
    public void onPageFinished(final WebView view, final String url)
    {
        System.out.println("Entering PapaJohns on page finished. url=" + url);
        PapaJohnsPageFlow page = PapaJohnsPageFlow.getPageFromURL(url);
        try
        {
            doPage(view, page);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        System.out.println("Leaving PapaJohns on page finished.");
    }

    private void doPage(final WebView view, final PapaJohnsPageFlow page) throws IOException
    {
        System.out.println("Entering doPage(page=" + page + ")");
        String js = AssetReader.readTextAsset(page.getJsFilepath());
        view.loadUrl(js);
        System.out.println("Leaving doPage(page=" + page + ")");
    }
}
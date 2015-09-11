package rainmanproductions.feedme;


import android.content.Context;
import android.content.res.AssetManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class AssetReader
{
    private static final String PREPEND_JS_FILENAME = "Prepend.js";
    private static String prependJs = null;
    private static final String APPEND_JS_FILENAME = "Append.js";
    private static String appendJs = null;
    private static AssetManager assetManager;

    public static void setAssetManager(final AssetManager manager)
    {
        assetManager = manager;
    }

    public static void setAssetManager(final Context context)
    {
        setAssetManager(context.getAssets());
    }

    /**
     * Reads a JavaScript file and prepends the text from Prepend.js and appends the text from Append.js to it.
     *
     * @param filename
     * @return
     * @throws IOException
     */
    public static String readJsFile(final String filename) throws IOException
    {
        if (prependJs == null)
        {
            prependJs = readTextAsset(PREPEND_JS_FILENAME);
        }

        if (appendJs == null)
        {
            appendJs = readTextAsset(APPEND_JS_FILENAME);
        }

        return prependJs + readTextAsset(filename) + appendJs;
    }

    /**
     * Reads a text file from the assets folder.
     *
     * @param filename
     * @return
     * @throws IOException
     */
    public static String readTextAsset(final String filename) throws IOException
    {
        // check filename
        if (filename == null || filename.isEmpty())
        {
            throw new IllegalArgumentException("Filename must not be empty.");
        }

        // check asset manager is initialized
        if (assetManager == null)
        {
            throw new IllegalStateException("Asset Manager is not initialized.");
        }

        // open the file to be read
        BufferedReader reader = new BufferedReader(new InputStreamReader(assetManager.open(filename)));

        // continually read the file, appending each line to a string and then return the string
        String line;
        String toReturn = "";
        while ((line = reader.readLine()) != null)
        {
            toReturn += line + "\n";
        }
        reader.close();
        return toReturn;
    }
}

package rainmanproductions.feedme;


import android.content.Context;
import android.content.res.AssetManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class AssetReader
{
    private static AssetManager assetManager;

    public static void setAssetManager(final AssetManager manager)
    {
        assetManager = manager;
    }

    public static void setAssetManager(final Context context)
    {
        setAssetManager(context.getAssets());
    }

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

package rainmanproductions.feedme.restaurants;

import android.content.Context;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import rainmanproductions.feedme.controllers.OrderPreferencesActivity;

public class WebAppInterface {
    Context mContext;

    private static List<String> TOPPINGS = new LinkedList<>();

    /** Instantiate the interface and set the context */
    public WebAppInterface(Context c) {
        mContext = c;
    }

    /** Show a toast from the web page */
    @JavascriptInterface
    public void showToast(String toast) {
        Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();
    }

    /** Store value in keystore */
    @JavascriptInterface
    public void putInfo(final String key, final String val) {
        TemporaryKeyStore.putVal(key, val);
    }

    /** Retrieve value from keystore */
    @JavascriptInterface
    public String getInfo(final String key)
    {
        return TemporaryKeyStore.getVal(key);
    }

    /** Retrieve number of toppings */
    @JavascriptInterface
    public String getNumToppings()
    {
        //TODO: Fix possible bug with mix and matches
        String numOneTopping = getInfo("numOneTopping");
        if (numOneTopping != null && !numOneTopping.equals("0"))
        {
            putInfo("numOneTopping",(Integer.parseInt(numOneTopping)-1)+"");
            return "1";
        }
        String numTwoTopping = getInfo("numTwoTopping");
        if (numTwoTopping != null && !numTwoTopping.equals("0"))
        {
            putInfo("numTwoTopping",(Integer.parseInt(numTwoTopping)-1)+"");
            return "2";
        }
        String numThreeTopping = getInfo("numThreeTopping");
        if (numThreeTopping != null && !numThreeTopping.equals("0"))
        {
            putInfo("numThreeTopping",(Integer.parseInt(numThreeTopping)-1)+"");
            return "3";
        }
        String numFourTopping = getInfo("numFourTopping");
        if (numFourTopping != null && !numFourTopping.equals("0"))
        {
            putInfo("numFourTopping",(Integer.parseInt(numFourTopping)-1)+"");
            return "4";
        }
        String numFiveTopping = getInfo("numFiveTopping");
        if (numFiveTopping != null && !numFiveTopping.equals("0"))
        {
            putInfo("numFiveTopping",(Integer.parseInt(numFiveTopping)-1)+"");
            return "5";
        }
        return "0";
    }

    /** generate toppings */
    @JavascriptInterface
    public void generateToppings()
    {
        //TODO: Fix possible bug with mix and matches
        int numberOfToppings = Integer.parseInt(getNumToppings());
        Log.i("WebAppInterface", "number of toppings = " + numberOfToppings);
        List<String> selected = new LinkedList<>();
        List<String> selectableIngreds = OrderPreferencesActivity.getIngredients();
        Random random = new Random();
        while (selected.size()<=numberOfToppings)
        {
            int index = random.nextInt(selectableIngreds.size());
            String nextSelected = selectableIngreds.get(index);
            if(!selected.contains(nextSelected)) {
                selected.add(nextSelected);
            }
        }
        TOPPINGS = selected;
        Log.i("WebAppInterface", "Toppings set to " + TOPPINGS.toString());
    }

    /** gets next topping to add */
    @JavascriptInterface
    public String getNextTopping()
    {
        if(TOPPINGS != null && TOPPINGS.size()>0)
        {
            Log.i("WebAppInterface", "Applying Topping " + TOPPINGS.get(0));
            return TOPPINGS.remove(0);
        }
        return null;
    }

}

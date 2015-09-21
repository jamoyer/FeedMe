package rainmanproductions.feedme.controllers;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Toast;

import rainmanproductions.feedme.R;
import rainmanproductions.feedme.gps.GPSHandler;
import rainmanproductions.feedme.restaurants.FoodPicker;
import rainmanproductions.feedme.restaurants.Restaurant;
import rainmanproductions.feedme.userinformation.InfoType;
import rainmanproductions.feedme.userinformation.UserInformationAccessor;
import rainmanproductions.feedme.util.FileReader;

public class FeedMeButtonActivity extends AppCompatActivity
{
    private static final String LOG_PREFIX = "FeedMeButtonActivity";
    public static final int MAXIMUM_TIME_TO_WAIT_FOR_LOCATION = 2000; // 2 seconds
    private final FeedMeButtonActivity self = this;
    private Restaurant selectedRestaurant = Restaurant.DOMINOS;
    private static boolean isFindingLocation = false;
    private static boolean applicationHasStarted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_me_button);

        if (!applicationHasStarted)
        {
            applicationHasStarted = true;
            UserInformationAccessor.init(this);
            FileReader.setAssetManager(this);
            try
            {
                checkAndPromptLocationEnableDialog();
                GPSHandler.init(this);
            }
            catch (SecurityException e)
            {
                Log.e(LOG_PREFIX, "Insufficient Permissions to add gps listener: " + e.getMessage());
            }
            OrderPreferencesActivity.setDefaultPreferencesIfNull();
        }

        createRandomOrderButton();
    }

    public void partySizeButtonClicked(final View view)
    {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        int partySize = 1;
        switch (view.getId())
        {
            case R.id.partySize5:
                if (checked)
                {
                    partySize = 5;
                    break;
                }
            case R.id.partySize4:
                if (checked)
                {
                    partySize = 4;
                }
                break;
            case R.id.partySize3:
                if (checked)
                {
                    partySize = 3;
                }
                break;
            case R.id.partySize2:
                if (checked)
                {
                    partySize = 2;
                }
                break;
            default:
                if (checked)
                {
                    partySize = 1;
                }
                break;
        }
        Log.i(LOG_PREFIX, "Number of people selected: " + partySize);
        UserInformationAccessor.getInstance().putInfo(InfoType.PARTY_SIZE, partySize + "");
    }


    private void createRandomOrderButton()
    {
        ImageButton btnSubmit = (ImageButton) findViewById(R.id.mainActivityRandomOrderBtn);
        btnSubmit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Log.i(LOG_PREFIX, "Random order button pressed.");
                selectedRestaurant = FoodPicker.getUniformRandomRestaurant();
                doOrder();
            }
        });
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

        Class<?> cls = null;
        switch (id)
        {
            case R.id.mainActivityTopMenuUserInformation:
            {
                Log.i(LOG_PREFIX, "User Information pressed.");
                cls = UserInformationActivity.class;
                break;
            }
            case R.id.mainActivityTopMenuOrderPreferences:
            {
                Log.i(LOG_PREFIX, "Order Preferences pressed.");
                cls = OrderPreferencesActivity.class;
                break;
            }
            case R.id.mainActivityTopMenuDeliveryAddress:
            {
                Log.i(LOG_PREFIX, "Delivery Address pressed.");
                cls = DeliveryAddressActivity.class;
                break;
            }
        }
        final Intent intent = new Intent(this, cls);
        startActivity(intent);

        return super.onOptionsItemSelected(item);
    }

    private void doOrder()
    {
        final FeedMeButtonActivity feedMeButtonActivity = this;
        final GPSHandler gpsHandler = GPSHandler.getInstance();

        // use gps or network to get location if ...
        if (gpsHandler != null &&   // the handler is available
                GPSHandler.isAnyLocationEnabled(feedMeButtonActivity) &&    // we can get location
                !isFindingLocation &&   // we aren't already finding location
                !DeliveryAddressDialog.deliveryAddressIsOutdated()) // the current address is too old
        {
            isFindingLocation = true;
            Log.i(LOG_PREFIX, "Beginning to gather location from network or gps.");

            Toast.makeText(getApplicationContext(),
                    "Getting Location...",
                    MAXIMUM_TIME_TO_WAIT_FOR_LOCATION)   // android studio is wrong, any number works here
                    .show();
            gpsHandler.startGettingLocation();
            // make a thread to wait and not take up the UI thread
            Thread findLocationThread = new Thread()
            {
                @Override
                public void run()
                {
                    try
                    {
                        Thread.sleep(MAXIMUM_TIME_TO_WAIT_FOR_LOCATION);
                    }
                    catch (InterruptedException e)
                    {
                        Log.i(LOG_PREFIX, "Thread interrupted while waiting for location.");
                    }
                    // once the waiting is over, start the location dialog
                    feedMeButtonActivity.runOnUiThread(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            gpsHandler.stopGettingLocation();
                            isFindingLocation = false;
                            Dialog confirmAddressDialog = new DeliveryAddressDialog(feedMeButtonActivity);
                            confirmAddressDialog.show();
                        }
                    });
                }
            };
            findLocationThread.start();
        }
        else
        {
            Dialog confirmAddressDialog = new DeliveryAddressDialog(feedMeButtonActivity);
            confirmAddressDialog.show();
        }
    }

    public void onAddressConfirmation(final DeliveryAddressDialog dialog)
    {
        dialog.dismiss();
        final Intent intent = new Intent(self, BrowserActivity.class);
        intent.putExtra("restaurant", selectedRestaurant);
        startActivity(intent);
    }

    private void checkAndPromptLocationEnableDialog()
    {
        final Context self = this;
        if (!GPSHandler.isAnyLocationEnabled(self))
        {
            Log.i(LOG_PREFIX, "Prompting user for location enabling.");
            // notify user
            final AlertDialog.Builder dialog = new AlertDialog.Builder(self);
            dialog.setMessage("Enable location so I may suggest a delivery address?");
            dialog.setPositiveButton("YES", new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt)
                {
                    Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    self.startActivity(myIntent);
                }
            });
            dialog.setNegativeButton("NO", new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt)
                {
                }
            });
            dialog.show();
        }
    }
}
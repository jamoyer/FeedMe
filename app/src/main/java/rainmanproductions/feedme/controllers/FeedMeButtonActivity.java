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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import rainmanproductions.feedme.R;
import rainmanproductions.feedme.gps.GPSHandler;
import rainmanproductions.feedme.restaurants.FoodPicker;
import rainmanproductions.feedme.restaurants.Restaurant;
import rainmanproductions.feedme.userinformation.InfoType;
import rainmanproductions.feedme.userinformation.UserInformationAccessor;

public class FeedMeButtonActivity extends AppCompatActivity
{
    private static final String LOG_PREFIX = "FeedMeButtonActivity";
    public static final int MAXIMUM_TIME_TO_WAIT_FOR_LOCATION = 5000; // 5 seconds
    private final FeedMeButtonActivity self = this;
    private Restaurant selectedRestaurant = Restaurant.DOMINOS;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_me_button);

        UserInformationAccessor.init(this);
        try
        {
            checkAndPromptLocationEnableDialog();
            GPSHandler.init(this);
        }
        catch (SecurityException e)
        {
            Log.e(LOG_PREFIX, "Insufficient Permissions to add gps listener: " + e.getMessage());
        }

        createRestaurantSpinner();
        createNumberOfPeopleSpinner();
        createOrderButton();
        createRandomOrderButton();
        OrderPreferencesActivity.setDefaultPreferencesIfNull();
    }

    private void createNumberOfPeopleSpinner()
    {

        Spinner partySizeSpinner = (Spinner) findViewById(R.id.mainActivityPartySizeSpinner);
        Integer[] partySizeOptions = {1, 2, 3, 4, 5};
        ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, partySizeOptions);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        partySizeSpinner.setAdapter(arrayAdapter);
        partySizeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                Integer partySize = (Integer) parent.getItemAtPosition(position);
                Log.i(LOG_PREFIX, "Number of people selected: " + partySize);
                UserInformationAccessor.getInstance().putInfo(InfoType.PARTY_SIZE, partySize + "");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {
            }
        });
    }

    private void createRestaurantSpinner()
    {
        Spinner restaurantSpinner = (Spinner) findViewById(R.id.mainActivityRestaurantSpinner);
        ArrayAdapter<Restaurant> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Restaurant.values());
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        restaurantSpinner.setAdapter(arrayAdapter);
        restaurantSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                selectedRestaurant = (Restaurant) parent.getItemAtPosition(position);
                Log.i(LOG_PREFIX, "Restaurant selected: " + selectedRestaurant);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {
            }
        });
    }

    private void createOrderButton()
    {
        Button btnSubmit = (Button) findViewById(R.id.mainActivityOrderBtn);
        btnSubmit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Log.i(LOG_PREFIX, "Order button pressed.");
                doOrder();
            }
        });
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
        final FeedMeButtonActivity self = this;
        final GPSHandler gpsHandler = GPSHandler.getInstance();
        if (gpsHandler != null && GPSHandler.isAnyLocationEnabled(self))
        {
            Log.i(LOG_PREFIX, "Beginning to gather location from network or gps.");
            Toast.makeText(getApplicationContext(), "Getting Location...", Toast.LENGTH_SHORT).show();
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
                    self.runOnUiThread(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            gpsHandler.stopGettingLocation();
                            Dialog confirmAddressDialog = new DeliveryAddressDialog(self);
                            confirmAddressDialog.show();
                        }
                    });
                }
            };
            findLocationThread.start();
        }
        else
        {
            Dialog confirmAddressDialog = new DeliveryAddressDialog(self);
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
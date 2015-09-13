package rainmanproductions.feedme;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import rainmanproductions.feedme.restaurants.Restaurant;
import rainmanproductions.feedme.userinformation.DeliveryAddress;
import rainmanproductions.feedme.userinformation.UserInformationAccessor;
import rainmanproductions.feedme.userinformation.UserInformationActivity;

public class FeedMeButton extends AppCompatActivity
{
    private static final String LOG_PREFIX = "FeedMeButton";
    private FeedMeButton self = this;
    private Restaurant selectedRestaurant = Restaurant.Dominos;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_me_button);

        UserInformationAccessor.init(this);

        createRestaurantSpinner();
        createOrderButton();
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
                System.out.println("Restaurant selected: " + selectedRestaurant);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {
            }
        });
    }

    private void createOrderButton()
    {
        Button btnSubmit = (Button) findViewById(R.id.mainActivitySubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                System.out.println("Order button pressed.");
                Intent intent = new Intent(self, BrowserActivity.class);
                intent.putExtra("restaurant", selectedRestaurant);
                startActivity(intent);
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

        switch (id)
        {
            case R.id.mainActivityTopMenuUserInformation:
            {
                Log.i(LOG_PREFIX, "User Information pressed.");
                Intent intent = new Intent(self, UserInformationActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.mainActivityTopMenuOrderPreferences:
            {
                Log.i(LOG_PREFIX, "Order Preferences pressed.");
                //TODO
                break;
            }
            case R.id.mainActivityTopMenuDeliveryAddress:
            {
                Log.i(LOG_PREFIX, "Delivery Address pressed.");
                Intent intent = new Intent(self, DeliveryAddress.class);
                startActivity(intent);
                break;
            }
        }

        return super.onOptionsItemSelected(item);
    }
}
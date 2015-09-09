package rainmanproductions.feedme;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class FeedMeButton extends AppCompatActivity
{

    private FeedMeButton self = this;
    private Restaurant selectedRestaurant = Restaurant.Dominos;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_me_button);

        Spinner restaurantSpinner = (Spinner) findViewById(R.id.restaurantSpinner);
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

        Button btnSubmit = (Button) findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                System.out.println("Order button pressed.");
                switch (selectedRestaurant)
                {
                    case Dominos:
                    {
                        Intent intent = new Intent(self, BrowserActivity.class);
                        startActivity(intent);
                        break;
                    }
                }
            }
        });
    }
}
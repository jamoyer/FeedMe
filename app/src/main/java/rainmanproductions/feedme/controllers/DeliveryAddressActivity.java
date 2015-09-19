package rainmanproductions.feedme.controllers;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup.LayoutParams;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import rainmanproductions.feedme.R;
import rainmanproductions.feedme.gps.AddressInfo;
import rainmanproductions.feedme.gps.GPSHandler;
import rainmanproductions.feedme.gps.StoredAddressesAccessor;
import rainmanproductions.feedme.userinformation.StateCodes;

public class DeliveryAddressActivity extends AppCompatActivity
{
    private static final int ID_MULTIPLE = 100;
    private static int numEntries = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_delivery_addresses);

        // get the root layout element of the view
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.savedDeliveryRoot);

        // get all the stored addresses and add them to the layout
        List<AddressInfo> addressInfos = StoredAddressesAccessor.getSavedAddressInfos();
        for (numEntries = 0; numEntries < addressInfos.size(); numEntries++)
        {
            AddressInfo addressInfo = addressInfos.get(numEntries);
            Map<String, String> viewMap = new LinkedHashMap<>();
            viewMap.put("Street Address", addressInfo.getStreetAddress());
            viewMap.put("Unit Number", addressInfo.getUnitNumber());
            viewMap.put("City", addressInfo.getCity());
            viewMap.put("Zip Code", addressInfo.getZipCode());
            viewMap.put("State", addressInfo.getStateName());
            viewMap.put("Country", addressInfo.getCountry());

            // ids of each view will be as even=label,odd=edit, every AddressInfo will start on multiples of ID_MULTIPLE
            int idCounter = numEntries * ID_MULTIPLE;
            for (Map.Entry<String, String> entry : viewMap.entrySet())
            {
                TextView label = new TextView(this);
                label.setText(entry.getKey());
                label.setId(idCounter++);
                label.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
                linearLayout.addView(label);

                EditText edit = new EditText(this);
                edit.setText(entry.getValue());
                edit.setId(idCounter++);
                edit.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
                linearLayout.addView(edit);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_saved_delivery_addresses, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.savedDeliverySave)
        {
            saveFields();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Overwrites everything from all fields to the the info store.
     */
    private void saveFields()
    {
        List<AddressInfo> addressInfoList = new LinkedList<>();
        for (int i = 0; i < numEntries; i++)
        {
            int streetAddrId = i * ID_MULTIPLE;    //0
            streetAddrId++;    //1
            String streetAddress = ((EditText) findViewById(streetAddrId)).getText().toString();   //1
            int unitId = 2 + streetAddrId; //3
            String unitNumber = ((EditText) findViewById(unitId)).getText().toString();  //3
            int cityId = 2 + unitId; //5
            String city = ((EditText) findViewById(cityId)).getText().toString();    //5
            int zipId = 2 + cityId;  //7
            String zipCode = ((EditText) findViewById(zipId)).getText().toString();    //7
            int stateId = 2 + zipId;  //9
            String stateName = ((EditText) findViewById(stateId)).getText().toString();    //9
            int countryId = 2 + stateId;  //11
            String country = ((EditText) findViewById(countryId)).getText().toString();    //11

            // build an AddressInfo
            AddressInfo addressInfo = new AddressInfo();
            addressInfo.setStreetAddress(streetAddress);
            addressInfo.setUnitNumber(unitNumber);
            addressInfo.setCity(city);
            addressInfo.setZipCode(zipCode);
            addressInfo.setStateName(stateName);
            addressInfo.setCountry(country);
            addressInfo.setStateCode(StateCodes.getCode(stateName));

            // try to look up this addresses coordinates
            GPSHandler gpsHandler = GPSHandler.getInstance();
            if (gpsHandler != null)
            {
                addressInfo.setLatLon(gpsHandler.lookupLatLon(addressInfo));
            }

            // store the address at store index i
            StoredAddressesAccessor.setAddressInfoAtIndex(i, addressInfo);
        }
    }
}
package rainmanproductions.feedme.userinformation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import rainmanproductions.feedme.R;

public class DeliveryAddress extends AppCompatActivity
{

    private static final InfoType[] INFO_TYPES =
            {
                    InfoType.DELIVERY_STREET_ADDRESS,
                    InfoType.DELIVERY_UNIT_NUMBER,
                    InfoType.DELIVERY_CITY,
                    InfoType.DELIVERY_COUNTRY,
                    InfoType.DELIVERY_STATE,
                    InfoType.DELIVERY_ZIP_CODE
            };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_address);
        fillFields();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_delivery_address, menu);
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
        if (id == R.id.deliveryAddressSave)
        {
            saveFields();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Fills all the fields on the form with information that is already stored.
     */
    private void fillFields()
    {
        UserInformationAccessor accessor = UserInformationAccessor.getInstance();

        /*
         * Sets all info types that have a form id
         */

        for (InfoType infoType : INFO_TYPES)
        {
            // unimplemented fields or special fields will be null
            if (infoType.getFormId() != null)
            {
                String saved = accessor.getInfo(infoType);
                EditText field = (EditText) findViewById(infoType.getFormId());
                field.setText(saved);
            }
        }
        String state = accessor.getInfo(InfoType.DELIVERY_STATE);
        String code = StateCodes.getCode(state);
        accessor.putInfo(InfoType.DELIVERY_STATE_CODE, code);
    }

    /**
     * Overwrites everything from all fields to the the info store.
     */
    private void saveFields()
    {
        UserInformationAccessor accessor = UserInformationAccessor.getInstance();
        for (InfoType infoType : INFO_TYPES)
        {
            // unimplemented fields or special fields will be null
            if (infoType.getFormId() != null)
            {
                EditText field = (EditText) findViewById(infoType.getFormId());
                String text = field.getText().toString();
                accessor.putInfo(infoType, text);
            }
        }
    }
}

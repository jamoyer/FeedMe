package rainmanproductions.feedme.controllers;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.IOException;

import rainmanproductions.feedme.R;
import rainmanproductions.feedme.gps.AddressInfo;
import rainmanproductions.feedme.gps.GPSAccessor;
import rainmanproductions.feedme.userinformation.InfoType;
import rainmanproductions.feedme.userinformation.StateCodes;
import rainmanproductions.feedme.userinformation.UserInformationAccessor;

public class DeliveryAddressDialog extends Dialog
{
    private static final String LOG_PREFIX = "DeliveryAddressDialog";
    private static final InfoType[] INFO_TYPES =
            {
                    InfoType.DELIVERY_STREET_ADDRESS,
                    InfoType.DELIVERY_UNIT_NUMBER,
                    InfoType.DELIVERY_CITY,
                    InfoType.DELIVERY_COUNTRY,
                    InfoType.DELIVERY_STATE_NAME,
                    InfoType.DELIVERY_ZIP_CODE
            };

    private final FeedMeButtonActivity parent;

    public DeliveryAddressDialog(final Context context)
    {
        super(context);
        this.parent = (FeedMeButtonActivity) context;
        setContentView(R.layout.activity_delivery_address);

        suggestLocation();
        createConfirmationButton();
    }

    private void createConfirmationButton()
    {
        final DeliveryAddressDialog self = this;
        Button btnSubmit = (Button) findViewById(R.id.deliveryAddressConfirmBtn);
        btnSubmit.setText("CONFIRM");
        btnSubmit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Log.i(LOG_PREFIX, "Confirm button pressed.");
                if (checkAllFieldsNonNull())
                {
                    saveFields();
                    parent.onAddressConfirmation(self);

                    // the parent should dismiss but just in case they don't
                    dismiss();
                }
            }
        });
    }

    /**
     * Fills all the fields on the form with information that is already stored.
     */
    private void suggestLocation()
    {
        UserInformationAccessor accessor = UserInformationAccessor.getInstance();

        /*
         * Sets all info types that have a form id
         */


        AddressInfo addressInfo = null;
        try
        {
            addressInfo = GPSAccessor.getInstance().getSuggestedAddress();
        }
        catch (IOException e)
        {
            Log.e(LOG_PREFIX, "Unable to get suggested address: " + e.getMessage());
        }
        if (addressInfo != null)
        {
            ((EditText) findViewById(InfoType.DELIVERY_STREET_ADDRESS.getFormId())).setText(addressInfo.getStreetAddress());
            ((EditText) findViewById(InfoType.DELIVERY_UNIT_NUMBER.getFormId())).setText(addressInfo.getUnitNumber());
            ((EditText) findViewById(InfoType.DELIVERY_ZIP_CODE.getFormId())).setText(addressInfo.getZipCode());
            ((EditText) findViewById(InfoType.DELIVERY_CITY.getFormId())).setText(addressInfo.getCity());
            ((EditText) findViewById(InfoType.DELIVERY_STATE_NAME.getFormId())).setText(addressInfo.getState());
            ((EditText) findViewById(InfoType.DELIVERY_COUNTRY.getFormId())).setText(addressInfo.getCountry());
        }
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

        String state = accessor.getInfo(InfoType.DELIVERY_STATE_NAME);
        String code = StateCodes.getCode(state);
        accessor.putInfo(InfoType.DELIVERY_STATE_CODE, code);

        AddressInfo addressInfo = new AddressInfo();
        addressInfo.setZipCode(accessor.getInfo(InfoType.DELIVERY_ZIP_CODE));
        addressInfo.setCountry(accessor.getInfo(InfoType.DELIVERY_COUNTRY));
        addressInfo.setCity(accessor.getInfo(InfoType.DELIVERY_CITY));
        addressInfo.setUnitNumber(accessor.getInfo(InfoType.DELIVERY_UNIT_NUMBER));
        addressInfo.setStreetAddress(accessor.getInfo(InfoType.DELIVERY_STREET_ADDRESS));
        addressInfo.setState(accessor.getInfo(InfoType.DELIVERY_STATE_NAME));
    }

    private boolean checkAllFieldsNonNull()
    {
        // TODO check all fields to be not null
        return true;
    }
}

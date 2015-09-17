package rainmanproductions.feedme.controllers;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import rainmanproductions.feedme.R;
import rainmanproductions.feedme.gps.AddressInfo;
import rainmanproductions.feedme.gps.DeliveryAddressHandler;
import rainmanproductions.feedme.userinformation.InfoType;
import rainmanproductions.feedme.userinformation.StateCodes;
import rainmanproductions.feedme.userinformation.UserInformationAccessor;

public class DeliveryAddressDialog extends Dialog
{
    private static final String LOG_PREFIX = "DeliveryAddressDialog";
    private static final InfoType[] DELIVERY_ADDRESS_TYPES =
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

        fillFields();
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
    private void fillFields()
    {
        AddressInfo addressInfo = DeliveryAddressHandler.getAddress();
        setText(InfoType.DELIVERY_STREET_ADDRESS, addressInfo.getStreetAddress());
        setText(InfoType.DELIVERY_UNIT_NUMBER, addressInfo.getUnitNumber());
        setText(InfoType.DELIVERY_ZIP_CODE, addressInfo.getZipCode());
        setText(InfoType.DELIVERY_CITY, addressInfo.getCity());
        setText(InfoType.DELIVERY_STATE_NAME, addressInfo.getStateName());
        setText(InfoType.DELIVERY_COUNTRY, addressInfo.getCountry());
    }

    /**
     * Overwrites everything from all fields to the the info store.
     */
    private void saveFields()
    {
        // save address to store for javascript access
        UserInformationAccessor accessor = UserInformationAccessor.getInstance();
        for (InfoType infoType : DELIVERY_ADDRESS_TYPES)
        {
            // unimplemented fields or special fields will be null
            if (infoType.getFormId() != null)
            {
                accessor.putInfo(infoType, getText(infoType));
            }
        }

        String stateName = accessor.getInfo(InfoType.DELIVERY_STATE_NAME);
        String stateCode = StateCodes.getCode(stateName);
        accessor.putInfo(InfoType.DELIVERY_STATE_CODE, stateCode);

        AddressInfo addressInfo = new AddressInfo();
        addressInfo.setZipCode(accessor.getInfo(InfoType.DELIVERY_ZIP_CODE));
        addressInfo.setCountry(accessor.getInfo(InfoType.DELIVERY_COUNTRY));
        addressInfo.setCity(accessor.getInfo(InfoType.DELIVERY_CITY));
        addressInfo.setUnitNumber(accessor.getInfo(InfoType.DELIVERY_UNIT_NUMBER));
        addressInfo.setStreetAddress(accessor.getInfo(InfoType.DELIVERY_STREET_ADDRESS));
        addressInfo.setStateName(accessor.getInfo(InfoType.DELIVERY_STATE_NAME));
        addressInfo.setStateCode(stateCode);

        // save address for gps purposes
        DeliveryAddressHandler.saveAddress(addressInfo);
    }

    /**
     * Checks if all the important fields on the dialog are set and if not it sets them red.
     *
     * @return
     */
    private boolean checkAllFieldsNonNull()
    {
        InfoType[] types =
                {
                        InfoType.DELIVERY_STREET_ADDRESS,
                        InfoType.DELIVERY_ZIP_CODE,
                        InfoType.DELIVERY_CITY,
                        InfoType.DELIVERY_STATE_NAME,
                        InfoType.DELIVERY_COUNTRY
                };

        boolean allFieldsNonNull = true;
        for (InfoType type : types)
        {
            String field = getText(type);
            if (field.isEmpty())
            {
                allFieldsNonNull = false;
                setColor(type, Color.RED);
            }
        }
        return allFieldsNonNull;
    }

    private void setColor(final InfoType type, final int color)
    {
        ((EditText) findViewById(type.getFormId())).setHintTextColor(color);
    }

    private String getText(final InfoType type)
    {
        return ((EditText) findViewById(type.getFormId())).getText().toString();
    }

    private void setText(final InfoType type, final String text)
    {
        ((EditText) findViewById(type.getFormId())).setText(text);
    }
}
package rainmanproductions.feedme.controllers;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

import rainmanproductions.feedme.R;
import rainmanproductions.feedme.userinformation.InfoType;
import rainmanproductions.feedme.userinformation.StateCodes;
import rainmanproductions.feedme.userinformation.UserInformationAccessor;

public class UserInformationActivity extends AppCompatActivity
{
    private static final String[] MONTHS = new DateFormatSymbols().getMonths();
    private static final String LOG_PREFIX = UserInformationActivity.class.getSimpleName();
    private static final InfoType[] INFO_TYPES =
            {
                    InfoType.FIRST_NAME,
                    InfoType.MIDDLE_NAME,
                    InfoType.LAST_NAME,
                    InfoType.EMAIL,
                    InfoType.PHONE_NUMBER,
                    InfoType.CREDIT_CARD_NUMBER,
                    InfoType.CREDIT_CARD_CSV_NUMBER,
                    InfoType.CREDIT_CARD_EXP_YEAR,
                    InfoType.BILLING_STREET_ADDRESS,
                    InfoType.BILLING_UNIT_NUMBER,
                    InfoType.BILLING_ZIP_CODE,
                    InfoType.BILLING_CITY,
                    InfoType.BILLING_STATE_NAME,
                    InfoType.BILLING_COUNTRY
            };
    private static Calendar birthday;
    private String creditCardExp;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_information);

        fillFields();
        createMonthSpinner();

    }

    private void createMonthSpinner()
    {
        Spinner monthSpinner = (Spinner) findViewById(R.id.userInformationCreditCardExpMonth);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, MONTHS);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        monthSpinner.setAdapter(arrayAdapter);
        /*
         * Sets credit card exp month
         */
        try
        {
            int creditCardMonthIndex = Integer.parseInt(UserInformationAccessor.getInstance().getInfo(InfoType.CREDIT_CARD_EXP_MONTH_NUM)) - 1;
            monthSpinner.setSelection(creditCardMonthIndex);
            Log.i(LOG_PREFIX, "Set month spinner to " + creditCardMonthIndex);
        }
        catch (NumberFormatException e)
        {
            Log.i(LOG_PREFIX, "Could not set month spinner: " + e.getMessage());
        }
        monthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                creditCardExp = (String) parent.getItemAtPosition(position);
                System.out.println("Credit Card Exp Month selected: " + creditCardExp);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {
            }
        });
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
                // save and star out the credit card number
                if (saved != null && saved.length() == 16 && infoType == InfoType.CREDIT_CARD_NUMBER)
                {
                    // star out the first 12 numbers and show the last 4
                    saved = "************" + saved.substring(saved.length() - 4);
                }
                else if ((saved == null || saved.isEmpty()) && infoType == InfoType.PHONE_NUMBER)
                {
                    // suggest this phone's number if the stored phone number is null or empty
                    saved = ((TelephonyManager)this.getSystemService(Context.TELEPHONY_SERVICE)).getLine1Number();
                }

                EditText field = (EditText) findViewById(infoType.getFormId());
                field.setText(saved);
            }
        }

        /*
         * Sets birthday
         */
        String dayOfMonthString = accessor.getInfo(InfoType.BIRTH_DAY_OF_MONTH);
        String monthNumString = accessor.getInfo(InfoType.BIRTH_MONTH_NUMBER);
        String yearString = accessor.getInfo(InfoType.BIRTH_YEAR);
        try
        {
            int dayOfMonthInt = Integer.parseInt(dayOfMonthString);
            int yearInt = Integer.parseInt(yearString);
            int monthNumInt = Integer.parseInt(monthNumString);
            birthday = new GregorianCalendar(yearInt, monthNumInt, dayOfMonthInt);
        }
        catch (NumberFormatException e)
        {
            Log.i(LOG_PREFIX, e.getMessage());
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
                // do not save the credit card information if it has *'s, or is not cleared or full length
                if (infoType == InfoType.CREDIT_CARD_NUMBER && (text.contains("*") || (text.length() != 16 && text.length() != 0)))
                {
                    continue;
                }
                accessor.putInfo(infoType, text);
            }
        }

        if (birthday != null)
        {
            int dayOfMonth = birthday.get(Calendar.DAY_OF_MONTH);
            int monthNum = birthday.get(Calendar.MONTH);
            String month = birthday.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
            int year = birthday.get(Calendar.YEAR);

            accessor.putInfo(InfoType.BIRTH_DAY_OF_MONTH, dayOfMonth + "");
            accessor.putInfo(InfoType.BIRTH_MONTH_NUMBER, monthNum + "");
            accessor.putInfo(InfoType.BIRTH_MONTH, month);
            accessor.putInfo(InfoType.BIRTH_YEAR, year + "");
        }

        String middleName = accessor.getInfo(InfoType.MIDDLE_NAME);
        if (middleName != null && !middleName.isEmpty())
        {
            accessor.putInfo(InfoType.MIDDLE_INITIAL, middleName.charAt(0) + "");
        }

        if (creditCardExp != null)
        {
            for (int i = 0; i < MONTHS.length; i++)
            {
                if (MONTHS[i].equals(creditCardExp))
                {
                    int currentMonthNum = i + 1;
                    accessor.putInfo(InfoType.CREDIT_CARD_EXP_MONTH_NUM, currentMonthNum + "");
                    break;
                }
            }
        }

        String state = accessor.getInfo(InfoType.BILLING_STATE_NAME);
        String code = StateCodes.getCode(state);
        accessor.putInfo(InfoType.BILLING_STATE_CODE, code);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_user_information, menu);
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
        if (id == R.id.userInformationSave)
        {
            saveFields();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void showDatePickerDialog(View view)
    {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener
    {

        @Override
        @NonNull
        public Dialog onCreateDialog(Bundle savedInstanceState)
        {
            // Use the current date as the default date in the picker
            final Calendar c = birthday != null ? birthday : Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day)
        {
            birthday = new GregorianCalendar(year, month, day);
            System.out.println("Birthday chosen: " + birthday.toString());
        }
    }
}
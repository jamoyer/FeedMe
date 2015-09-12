package rainmanproductions.feedme.userinformation;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.Calendar;
import java.util.GregorianCalendar;

import rainmanproductions.feedme.R;

public class UserInformationActivity extends AppCompatActivity
{

    private static Calendar birthday;
    private Month creditCardExp;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_information);

        createMonthSpinner();
        fillFields();
    }

    private void createMonthSpinner()
    {
        Spinner monthSpinner = (Spinner) findViewById(R.id.userInformationCreditCardExpMonth);
        ArrayAdapter<Month> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Month.values());
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        monthSpinner.setAdapter(arrayAdapter);
        monthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                creditCardExp = (Month) parent.getItemAtPosition(position);
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
        for (InfoType infoType : InfoType.values())
        {
            // unimplemented fields or special fields will be null
            if (infoType.getFormId() != null)
            {
                String saved = accessor.getInfo(infoType);
                EditText field = (EditText) findViewById(infoType.getFormId());
                field.setText(saved);
            }
        }
        //TODO more fields and special fields
    }

    /**
     * Overwrites everything from all fields to the the info store.
     */
    private void saveFields()
    {
        UserInformationAccessor accessor = UserInformationAccessor.getInstance();
        for (InfoType infoType : InfoType.values())
        {
            // unimplemented fields or special fields will be null
            if (infoType.getFormId() != null)
            {
                EditText field = (EditText) findViewById(infoType.getFormId());
                String text = field.getText().toString();
                accessor.putInfo(infoType, text);
            }
        }
        //TODO more fields and special fields
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
            // TODO c = birthday, get calendar from saved preferences
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
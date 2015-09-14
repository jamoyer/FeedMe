package rainmanproductions.feedme.userinformation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import rainmanproductions.feedme.R;

public class OrderPreferencesActivity extends AppCompatActivity {

    private static final InfoType[] INFO_TYPES =
            {
                    InfoType.PREFERENCE_COST_PER_PERSON
            };
    private static final InfoType[] CHECK_BOXES =
            {
                    InfoType.PREFERENCE_CONFIRM_ORDERS,
                    InfoType.PREFERENCE_DISPLAY_BROWSER,
                    InfoType.PREFERENCE_ALERT_SOUND,
                    InfoType.PREFERENCE_PAPA_JOHNS,
                    InfoType.PREFERENCE_JIMMY_JOHNS,
                    InfoType.PREFERENCE_DOMINOS,
                    InfoType.PREFERENCE_888_CHINESE,
                    InfoType.PREFERENCE_PEPPERONI,
                    InfoType.PREFFERENCE_GRILLED_CHICKEN,
                    InfoType.PREFERENCE_BEEF,
                    InfoType.PREFERENCE_SPICY_ITALIAN_SAUSAGE,
                    InfoType.PREFERENCE_BACON,
                    InfoType.PREFERENCE_SAUSAGE,
                    InfoType.PREFERENCE_CANDADIAN_BACON,
                    InfoType.PREFERENCE_ANCHOVIES,
                    InfoType.PREFERENCE_PINEAPPLE,
                    InfoType.PREFERENCE_ROMA_TOMATOES,
                    InfoType.PREFERENCE_GREEN_OLIVES,
                    InfoType.PREFERENCE_MUSHROOMS,
                    InfoType.PREFERENCE_SAUERKRAUT,
                    InfoType.PREFERENCE_ONIONS,
                    InfoType.PREFERENCE_BLACK_OLIVES,
                    InfoType.PREFERENCE_JALAPENO_PEPPERS,
                    InfoType.PREFERENCE_EXTRA_CHEESE,
                    InfoType.PREFERENCE_THREE_CHEESE_BLEND,
                    InfoType.PREFERENCE_PARMESAN,
                    InfoType.PREFERENCE_ORIGINAL_CRUST,
                    InfoType.PREFERENCE_THIN_CRUST,
                    InfoType.PREFERENCE_NORMAL_CUT,
                    InfoType.PREFERENCE_SQUARE_CUT,
                    InfoType.PREFERENCE_NORMAL_BAKE,
                    InfoType.PREFERENCE_WELL_DONE_BAKE,
                    InfoType.PREFERENCE_NORMAL_SAUCE,
                    InfoType.PREFERENCE_ORIGINAL_SAUCE,
                    InfoType.PREFERENCE_BBQ_SAUCE,
                    InfoType.PREFERENCE_RANCH_SAUCE,
                    InfoType.PREFERENCE_LIGHT_SAUCE,
                    InfoType.PREFERENCE_EXTRA_SAUCE,
                    InfoType.PREFERENCE_NO_SAUCE,
                    InfoType.PREFERENCE_NORMAL_CHEESE,
                    InfoType.PREFERENCE_LIGHT_CHEESE,
                    InfoType.PREFERENCE_NO_CHEESE

            };
    private static final InfoType[] DEFAULT_CHECKED =
            {
                    InfoType.PREFERENCE_NO_SAUCE,
                    InfoType.PREFERENCE_NO_CHEESE,
                    InfoType.PREFERENCE_BBQ_SAUCE,
                    InfoType.PREFERENCE_RANCH_SAUCE,
                    InfoType.PREFERENCE_ANCHOVIES
            };

    public static void setDefaultPreferencesIfNull()
    {
        UserInformationAccessor accessor = UserInformationAccessor.getInstance();
        for(InfoType default_checked : DEFAULT_CHECKED)
        {
            //Only is null by default, so if it is null set it to true
            if (accessor.getInfo(default_checked) == null)
            {
                accessor.putInfo(default_checked, "true");
            }
        }
    }

    public void resetDefaultPreferences(View view)
    {
        UserInformationAccessor accessor = UserInformationAccessor.getInstance();
        for(InfoType checked : CHECK_BOXES)
        {
            accessor.putInfo(checked, null);
        }
        setDefaultPreferencesIfNull();
        fillFields();
    }

    public void clearPreferences(View view)
    {
        UserInformationAccessor accessor = UserInformationAccessor.getInstance();
        for(InfoType checkboxes : CHECK_BOXES)
        {
            accessor.putInfo(checkboxes, "false");
        }
        for(InfoType textfield :INFO_TYPES)
        {
            accessor.putInfo(textfield, null);
        }
        fillFields();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_preferences);
        fillFields();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_order_preferences, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.preferenceSave) {
            saveFields();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Fills all the fields on the form with information that is already stored.
     */
    private void fillFields() {
        UserInformationAccessor accessor = UserInformationAccessor.getInstance();

        /*
         * Sets all info types that have a form id
         */
        for (InfoType infoType : INFO_TYPES) {
            // unimplemented fields or special fields will be null
            if (infoType.getFormId() != null) {
                String saved = accessor.getInfo(infoType);
                EditText field = (EditText) findViewById(infoType.getFormId());
                field.setText(saved);
            }
        }

        for (InfoType checkBox : CHECK_BOXES) {
            if (checkBox.getFormId() != null){
                CheckBox field = (CheckBox) findViewById(checkBox.getFormId());
                Boolean isChecked = Boolean.valueOf(accessor.getInfo(checkBox));
                if (isChecked != null)
                {
                    field.setChecked(isChecked);
                }
            }
        }
    }

    /**
     * Overwrites everything from all fields to the the info store.
     */
    private void saveFields()
    {
        UserInformationAccessor accessor = UserInformationAccessor.getInstance();
        for (InfoType infoType : INFO_TYPES) {
            // unimplemented fields or special fields will be null
            if (infoType.getFormId() != null) {
                EditText field = (EditText) findViewById(infoType.getFormId());
                String text = field.getText().toString();
                accessor.putInfo(infoType, text);
            }
        }

        for (InfoType checkBox : CHECK_BOXES) {
            // unimplemented fields or special fields will be null
            if (checkBox.getFormId() != null) {
                CheckBox field = (CheckBox) findViewById(checkBox.getFormId());
                String text = Boolean.toString(field.isChecked());
                accessor.putInfo(checkBox, text);
            }
        }
    }


}

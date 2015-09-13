package rainmanproductions.feedme.userinformation;

import java.util.HashMap;
import java.util.Map;

public class StateCodes
{
    private static final Map<String, String> states = new HashMap<>();

    static
    {
        states.put("alabama", "AL");
        states.put("alaska", "AK");
        states.put("alberta", "AB");
        states.put("arizona", "AZ");
        states.put("arkansas", "AR");
        states.put("california", "CA");
        states.put("colorado", "CO");
        states.put("connecticut", "CT");
        states.put("delaware", "DE");
        states.put("districtofcolumbia", "DC");
        states.put("florida", "FL");
        states.put("georgia", "GA");
        states.put("hawaii", "HI");
        states.put("idaho", "ID");
        states.put("illinois", "IL");
        states.put("indiana", "IN");
        states.put("iowa", "IA");
        states.put("kansas", "KS");
        states.put("kentucky", "KY");
        states.put("louisiana", "LA");
        states.put("maine", "ME");
        states.put("maryland", "MD");
        states.put("massachusetts", "MA");
        states.put("michigan", "MI");
        states.put("minnesota", "MN");
        states.put("mississippi", "MS");
        states.put("missouri", "MO");
        states.put("montana", "MT");
        states.put("nebraska", "NE");
        states.put("nevada", "NV");
        states.put("newhampshire", "NH");
        states.put("newjersey", "NJ");
        states.put("newmexico", "NM");
        states.put("newyork", "NY");
        states.put("northcarolina", "NC");
        states.put("northdakota", "ND");
        states.put("ohio", "OH");
        states.put("oklahoma", "OK");
        states.put("oregon", "OR");
        states.put("pennsylvania", "PA");
        states.put("puertorico", "PR");
        states.put("rhodeisland", "RI");
        states.put("southcarolina", "SC");
        states.put("southdakota", "SD");
        states.put("tennessee", "TN");
        states.put("texas", "TX");
        states.put("utah", "UT");
        states.put("vermont", "VT");
        states.put("virginislands", "VI");
        states.put("virginia", "VA");
        states.put("washington", "WA");
        states.put("westvirginia", "WV");
        states.put("wisconsin", "WI");
        states.put("wyoming", "WY");
    }

    public static String getCode(final String state)
    {
        final String key = state.toLowerCase().replaceAll(" ", "");
        return states.get(key);
    }
}

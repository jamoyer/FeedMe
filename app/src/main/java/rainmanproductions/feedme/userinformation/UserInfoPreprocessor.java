package rainmanproductions.feedme.userinformation;

public class UserInfoPreprocessor
{
    /**
     * Parses the input string and replaces all instances of InfoType strings and replaces them with
     * information from the App Preferences.
     *
     * @param input A string that is to be processed.
     * @return A new String where all InfoTypes are replaced with real information.
     */
    public static String process(final String input)
    {
        // check for invalid input
        if (input == null)
        {
            throw new IllegalArgumentException("Input was null");
        }

        // get the info accessor
        UserInformationAccessor accessor = UserInformationAccessor.getInstance();

        // replace all InfoTypes with real data
        String output = input;
        for (InfoType infoType : InfoType.values())
        {
            // only try to replace data if the input contains the specific InfoType
            if (output.contains(infoType.toString()))
            {
                String returnedInfo = accessor.getInfo(infoType);
                // retrieve information if necessary
                if (returnedInfo == null)
                {
                    returnedInfo = "";
                }
                output = output.replaceAll(infoType.toString(), returnedInfo);
            }
        }
        return output;
    }
}
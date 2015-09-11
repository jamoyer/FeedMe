package rainmanproductions.feedme;

import java.util.LinkedList;
import java.util.List;

import rainmanproductions.feedme.userinformation.InfoType;
import rainmanproductions.feedme.userinformation.UserInformationAccessor;

/**
 * Created by Jamoyer on 9/10/2015.
 */
public class Preprocessor
{
    public static String preprocess(String input)
    {
        if (input == null)
        {
            throw new IllegalArgumentException("Input was null");
        }
        UserInformationAccessor accessor = UserInformationAccessor.getInstance();
        List<InfoType> neededTypes = new LinkedList<>();
        for(InfoType infoType: InfoType.values())
        {
            if (input.contains(infoType.toString()))
            {
                neededTypes.add(infoType);
            }
        }
        for(InfoType infoType: neededTypes)
        {
            String returnedInfo = accessor.getInfo(infoType);
            if(returnedInfo == null)
            {
                returnedInfo = promptUser(infoType);
            }
            input = input.replaceAll(infoType.toString(), returnedInfo);
        }
        return input;
    }

    public static String promptUser(InfoType infoType)
    {
        //todo
        return null;
    }
}

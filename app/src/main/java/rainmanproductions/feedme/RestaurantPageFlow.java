package rainmanproductions.feedme;

/**
 * Created by Matt C on 9/10/2015.
 */
public interface RestaurantPageFlow {

    RestaurantPageFlow getPageFromURL(String url);
    String getJsFilepath();
    Restaurant getRestaurantType();
    String getStartingURL();
}

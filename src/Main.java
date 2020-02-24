
import Components.Authentication;
import Components.UserInfo;
import Database.DBConnection;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        System.out.println("Start");
//        Authentication.register("olaftest", "passord");
	    System.out.println(Authentication.logIn("olaftest", "passord"));
	    System.out.println(UserInfo.getUserID());
//	    DBConnection.registerPhoto("Superbilde", "https://nrk.no");
	    ArrayList<String> photos = DBConnection.getPhotos();
	    photos.stream().forEach(x -> System.out.println(x));
	    System.out.println(DBConnection.getPhoto(5));
    }
}

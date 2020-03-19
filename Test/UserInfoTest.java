import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import Components.Encryptor;
import Components.UserInfo;
import Database.HibernateClasses.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class UserInfoTest {
  static User user;

  @BeforeAll
  static void setup(){
    String encrypted = Encryptor.Encryptor("password", null);
    user = new User();
    user.setUsername("test");
    user.setEmail("test@email.com");
    user.setHash(Encryptor.getHash(encrypted));
    user.setSalt(Encryptor.getSalt(encrypted));
  }

  @Test
  void initializeUserAndGetUser_LoggedIn_True(){
    UserInfo.initializeUser(user);
    assertEquals(UserInfo.getUser(), user);
  }

  @Test
  void logOut_userLoggedIn_True(){
    UserInfo.logOut();
    assertNull(UserInfo.getUser());
  }
}

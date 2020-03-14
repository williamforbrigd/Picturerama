import Components.Authentication;
import Database.DBConnection;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AuthenticationTest {

    @Test
    void registerFalse() {
        assertFalse(Authentication.register(null, null, null));
    }

    @Test
    void registerTrue() {
        assertTrue(Authentication.register("jUnitTest", "test", "test"));
    }

    @Test
    void registerDuplicateFalse() {
        assertFalse(Authentication.register("jUnitTest", "test", "test"));
    }

    @Test
    void logInFalse() {
        assertFalse(Authentication.logIn("DoNotMakeThisUsername", "DoNotMakeThisPassword"));
    }

    @Test
    void logInTrue() {
        assertTrue(Authentication.logIn("test", "test"));
    }

    @AfterAll
    static void clearUp() {
        DBConnection.deleteUser("jUnitTest");
    }
}
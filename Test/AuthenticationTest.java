import Components.Authentication;
import Database.Hibernate;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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
        Hibernate.deleteUser("jUnitTest");
    }
}
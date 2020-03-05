import Components.Authentication;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuthenticationTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void registerFalse() {
        assertFalse(Authentication.register(null, null));
    }

    @Test
    void registerTrue() {
        assertTrue(Authentication.register("test", "test"));
    }

    @Test
    void logInFalse() {
        assertFalse(Authentication.logIn("DoNotMakeThisUsername", "DoNotMakeThisPassword"));
    }

    @Test
    void logInTrue() {
        assertTrue(Authentication.logIn("test", "test"));
    }
}
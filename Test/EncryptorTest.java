import Components.Encryptor;
import org.junit.jupiter.api.Test;
import static org.junit.Assert.*;

public class EncryptorTest {

  @Test
  void getHash_EqualPasswords_ReturnDifferentHashes() {
    String password = "test";
    String hash1 = Encryptor.getHash(Encryptor.Encryptor(password, null));
    String hash2 = Encryptor.getHash(Encryptor.Encryptor(password, null));

    assertNotEquals(hash1, hash2);
  }

  @Test
  void getSalt_Random_ReturnTwoDifferentSalts() {
    String password = "test";
    String salt1 = Encryptor.getSalt(Encryptor.Encryptor(password, null));
    String salt2 = Encryptor.getSalt(Encryptor.Encryptor(password, null));

    assertNotEquals(salt1, salt2);
  }

  @Test
  void getHash_EqualPasswordAndSalt_ReturnEqualHashes() {
    String password = "test";
    String hash1 = Encryptor.getHash(Encryptor.Encryptor(password, "salt"));
    String hash2 = Encryptor.getHash(Encryptor.Encryptor(password, "salt"));

    assertEquals(hash1, hash2);
  }
}

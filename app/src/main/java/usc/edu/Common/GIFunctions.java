package usc.edu.Common;

import android.util.Base64;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/**
 * Common functions reusable in the app
 */
public class GIFunctions {
   // using PBKDF2 from Sun, an alternative is https://github.com/wg/scrypt
    // cf. http://www.unlimitednovelty.com/2012/03/dont-use-bcrypt.html
    public static String hash(String password, byte[] salt) throws Exception {
        if (password == null || password.length() == 0)
            throw new IllegalArgumentException("Empty passwords are not supported.");
        SecretKeyFactory f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        SecretKey key = f.generateSecret(new PBEKeySpec(
                        password.toCharArray(), salt, GIConstants.ITERATIONS, GIConstants.DESIREDKEYLEN)
        );
        return Base64.encodeToString(key.getEncoded(), GIConstants.DEFAULT);
    }
    /** Checks whether given plaintext password corresponds
     to a stored salted hash of the password. */
    public static boolean check(String password, String stored) throws Exception{
        String[] saltAndPass = stored.split("\\$");
        if (saltAndPass.length != 2) {
            throw new IllegalStateException(
                    "The stored password have the form 'salt$hash'");
        }
        String hashOfInput = GIFunctions.hash(password, Base64.decode(saltAndPass[0], GIConstants.DEFAULT));
        return hashOfInput.equals(saltAndPass[1]);
    }
}

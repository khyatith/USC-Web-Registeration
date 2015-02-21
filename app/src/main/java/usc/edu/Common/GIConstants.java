package usc.edu.Common;

/**
 * For declaring any kinds of constants in the app that are re-usable
 */
public class GIConstants {
    public static final int DEFAULT = 0;

    public static final int SALTLEN = 32;
    // The higher the number of iterations the more
    // expensive computing the hash is for us and
    // also for an attacker.
    public static final int ITERATIONS = 20*1000;
    public static final int DESIREDKEYLEN = 256;
    public static final int PRIVATE_MODE=0;
    //Shared preference file name
    public static final String PREF_NAME = "UserWebPref";

    //all shared preference keys
    public static final String IS_LOGIN = "IsLoggedIn";

    // User name (make variable public to access from outside)
    public static final String KEY_NAME = "username";
    public static final String KEY_USCID="password";
}

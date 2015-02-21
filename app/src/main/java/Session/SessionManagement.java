package Session;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import java.util.HashMap;
import usc.edu.Common.GIConstants;
import usc.edu.uscwebapp.MainActivity;

/**
 * This is for storing some variables in shared pref
 */
public class SessionManagement {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;

    // Constructor
    public SessionManagement(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(GIConstants.PREF_NAME, GIConstants.PRIVATE_MODE);
        editor = pref.edit();
        editor.commit();

    }

    public void createLoginSession(String username,String USCID,String firstname,String lastname){
        // Storing login value as TRUE
        editor.putBoolean(GIConstants.IS_LOGIN, true);

        // Storing name in pref
        editor.putString(GIConstants.KEY_NAME, username);

        //Storing USCID in pref
        editor.putString(GIConstants.KEY_USCID, USCID);

        //storing firstname and lastname in pref
        editor.putString(GIConstants.KEY_FIRSTNAME,firstname);
        editor.putString(GIConstants.KEY_LASTNAME,lastname);


        editor.commit();
    }
    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();
        // user name
        user.put(GIConstants.KEY_NAME, pref.getString(GIConstants.KEY_NAME, null));
        user.put(GIConstants.KEY_USCID, pref.getString(GIConstants.KEY_USCID, null));
        user.put(GIConstants.KEY_FIRSTNAME, pref.getString(GIConstants.KEY_FIRSTNAME, null));
        user.put(GIConstants.KEY_LASTNAME, pref.getString(GIConstants.KEY_LASTNAME, null));
        // return user
        return user;
    }

    public boolean isLoggedIn(){
        return pref.getBoolean(GIConstants.IS_LOGIN, false);
    }
    public void checkLogin(){
        // Check login status
        if(!this.isLoggedIn()){
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(_context, MainActivity.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Starting Login Activity
            _context.startActivity(i);
        }
    }


    public void logoutUser()
    {
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();

        // TODO Add extras or a data URI to this intent as appropriate.


        // After logout redirect user to Loing Activity
        //Intent i = new Intent(_context, LoginActivity.class);
        // Closing all the Activities
        //i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        //i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        //_context.startActivity(i);
    }

}

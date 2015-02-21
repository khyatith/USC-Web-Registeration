package usc.edu.uscwebapp;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.security.SecureRandom;
import java.util.List;
import Session.SessionManagement;
import usc.edu.Common.GIConstants;
import usc.edu.Common.GIFunctions;
import usc.edu.dbHelper.DBHelper;
import usc.edu.dbHelper.User;
import android.view.View.OnClickListener;

public class MainActivity extends ActionBarActivity implements OnClickListener {
    private EditText tv_username;
    private EditText tv_password;
    private Button bt_signin;
    private DBHelper db;
    SessionManagement sm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db=new DBHelper(getApplicationContext());
        sm=new SessionManagement(getApplicationContext());

        //get the username and password ids
        tv_username=(EditText)findViewById(R.id.tv_username);
        tv_password=(EditText)findViewById(R.id.tv_password);
        bt_signin=(Button)findViewById(R.id.bt_signin);
        bt_signin.setOnClickListener(this);

        User newUser=new User("5042265928","Khyati","Thakur","2707 Portland street, apt 201,LA,CA,90007","2135097049",0,"Spring 2014",1,1,"khyati","gagan");
        Long id=db.createUsers(newUser);

    }
    @Override
    public void onClick(View v) {
            String enteredUserName=tv_username.getText().toString();
            String enteredpassword=tv_password.getText().toString();
        Log.d("username",enteredUserName);
            //Validating user input
            if (enteredUserName.matches(" ")) {
                Toast.makeText(getApplicationContext(), "UserName is blank!", Toast.LENGTH_SHORT);
            } else if (enteredpassword.matches(" ")) {
                Toast.makeText(getApplicationContext(), "Password is blank!", Toast.LENGTH_SHORT);
            } else {
                List<User> user=db.findUser(enteredUserName, enteredpassword);

                 if (user.isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Wrong Credentials!", Toast.LENGTH_SHORT);
                    } else {
                     for (User tag : user) {
                         sm.createLoginSession(tag.getUserName(),tag.getUserUSCId());
                     }
                     Intent intent = new Intent(this, ShowSchoolListActivity.class);
                     startActivity(intent);
                    }
                }


            }

    public static String getSaltedHash(String password) throws Exception {
        byte[] salt = SecureRandom.getInstance("SHA1PRNG").generateSeed(GIConstants.SALTLEN);
        // store the salt with the password
        return Base64.encodeToString(salt, GIConstants.DEFAULT) + "$" + GIFunctions.hash(password, salt);
    }
}

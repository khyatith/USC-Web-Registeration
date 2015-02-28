package usc.edu.uscwebapp;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import  android.util.Log;;
import android.view.View.OnClickListener;
import android.content.Intent;
/**
 * Created by Ashwin on 2/22/2015.
 */
public class ConfirmDclear  extends Activity {
    private Button backtomain;
    private Button backtocourse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.confirmation_dclearance);
        Log.e("Cleck","Click");
        addListenerOnButton();
    }

    public void addListenerOnButton() {
        Log.e("Clock","Click");
        backtomain = (Button) findViewById(R.id.bt_becktomain);

        backtomain.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                startActivity(intent);

            }

        });
        backtocourse = (Button) findViewById(R.id.bt_becktonextcourse);

        backtocourse.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent intent = new Intent(getApplicationContext(), DClearanceActivity.class);
                startActivity(intent);

            }

        });

    }




}

package usc.edu.uscwebapp;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import Session.SessionManagement;
import usc.edu.Common.CircularImageView;
import usc.edu.Common.GIConstants;


public class ProfileActivity extends ActionBarActivity implements OnClickListener {
    Button bt_register;
    Button bt_clearance;
    Button bt_advisor;
    Button bt_profile;
    TextView tv_profile_welcome;
    SessionManagement sm;
    HashMap<String, String>get_pref;
    String logged_in_first_name;
    String logged_in_last_name;
    CircularImageView iv_circular;
    TextView tv_USCID;
    TextView tv_degreename;
    TextView tv_degreetitle;
    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    List expandableListTitle;
    HashMap expandableListDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        sm = new SessionManagement(getApplicationContext());
        tv_profile_welcome = (TextView) findViewById(R.id.tv_profile_welcome);
        tv_profile_welcome.setOnClickListener(this);
        bt_register = (Button) findViewById(R.id.bt_register);
        bt_register.setOnClickListener(this);
        bt_clearance = (Button) findViewById(R.id.bt_clearance);
        bt_clearance.setOnClickListener(this);
        bt_advisor = (Button) findViewById(R.id.bt_advisor);
        bt_advisor.setOnClickListener(this);
        bt_profile = (Button) findViewById(R.id.bt_profile);
        bt_profile.setOnClickListener(this);
        expandableListView = (ExpandableListView) findViewById(R.id.tv_profilelistview);

        if (sm.isLoggedIn()) {
            get_pref = sm.getUserDetails();
            logged_in_first_name = get_pref.get(GIConstants.KEY_FIRSTNAME);
            logged_in_last_name = get_pref.get(GIConstants.KEY_LASTNAME);
            tv_profile_welcome.setText("Hi," + " " + logged_in_first_name + " " + logged_in_last_name);

        }
        else{
            Intent intent=new Intent(this,MainActivity.class);
            startActivity(intent);
        }

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_clearance:
                Intent intent=new Intent(this,DClearanceActivity.class);
                startActivity(intent);
                break;
            case R.id.bt_profile:
                //go to profile activity
                break;
            case R.id.bt_register:
                //go to register activity
                break;
            case R.id.bt_advisor:
                Intent structureintent=new Intent(this,AdvisorInfoActivity.class);
                startActivity(structureintent);
                break;
        }
    }
}

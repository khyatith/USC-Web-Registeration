package usc.edu.uscwebapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import Session.SessionManagement;
import usc.edu.Common.CircularImageView;
import usc.edu.Common.GIConstants;
import usc.edu.adapter.ProfileExpandableListAdapter;
import android.app.ExpandableListActivity;

public class ProfileActivity extends ActionBarActivity implements OnClickListener, ExpandableListView.OnChildClickListener {
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
    private ArrayList<String> parentItems = new ArrayList<String>();
    private ArrayList<Object> childItems = new ArrayList<Object>();
    private Context context=this;
    private RatingBar mrating;

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
        ExpandableListView expandableList = (ExpandableListView) findViewById(R.id.list);
        mrating=(RatingBar)findViewById(R.id.rating);

        if (sm.isLoggedIn()) {
            get_pref = sm.getUserDetails();
            logged_in_first_name = get_pref.get(GIConstants.KEY_FIRSTNAME);
            logged_in_last_name = get_pref.get(GIConstants.KEY_LASTNAME);
            tv_profile_welcome.setText("Hi," + " " + logged_in_first_name + " " + logged_in_last_name);
            mrating.setRating(3);
            setGroupParents();
            setChildData();
            /*ProfileExpandableListAdapter adapter = new ProfileExpandableListAdapter(parentItems, childItems);
            adapter.setInflater((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE), this);
            expandableList.setAdapter(adapter);
            expandableList.setOnChildClickListener(this);*/

        } else {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }
        public void setGroupParents() {
        parentItems.add("Android");
        parentItems.add("Core Java");
        parentItems.add("Desktop Java");
        parentItems.add("Enterprise Java");
    }
        public void setChildData() {
            ArrayList<String> child = new ArrayList<String>();
            child.add("Core");
            child.add("Games");
            childItems.add(child);
            child = new ArrayList<String>();
            child.add("Apache");
            child.add("Applet");
            child.add("AspectJ");
            child.add("Beans");
            child.add("Crypto");
            childItems.add(child);
            child = new ArrayList<String>();
            child.add("Accessibility");
            child.add("AWT");
            child.add("ImageIO");
            child.add("Print");

             childItems.add(child);
            child = new ArrayList<String>();
            child.add("EJB3");
            child.add("GWT");
            child.add("Hibernate");
            child.add("JSP");
            childItems.add(child);
        }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_clearance:
                Intent intent=new Intent(this,DClearanceActivity.class);
                startActivity(intent);
                break;
            case R.id.bt_profile:
                Intent intent1=new Intent(this,ProfileActivity.class);
                startActivity(intent1);
                break;
            case R.id.bt_register:
                Intent registerIntent=new Intent(this,chooseSemester.class);
                startActivity(registerIntent);
                break;
            case R.id.bt_advisor:
                Intent structureintent=new Intent(this,AdvisorInfoActivity.class);
                startActivity(structureintent);
                break;
        }
    }

    @Override
    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
        return false;
    }
}

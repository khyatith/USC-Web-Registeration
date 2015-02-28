package usc.edu.uscwebapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Parcelable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import usc.edu.Common.courseInformation;
import usc.edu.Common.courseList;
import usc.edu.adapter.ExpandableListAdapter;
import usc.edu.adapter.courseBinListAdapter;

public class CourseBinActivity extends ActionBarActivity implements View.OnClickListener {

    private List<String> expandableListTitle;
    private HashMap<String, courseInformation> expandableListDetail;
    String selectedCourse = "";
    ExpandableListView expandableListView;
    ImageButton calender;
    Button bt_register;
    Button bt_clearance;
    Button bt_advisor;
    Button bt_profile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_bin);
       // Bundle b = getIntent().getExtras();
        //courseList c = b.getParcelable("Subjects");
        //Log.d("course List", c.get(0).courseName);
        expandableListDetail = new HashMap<>();
        expandableListTitle = new ArrayList<>();
        /*for (int i = 0; i < c.size(); i++) {

            ci = c.get(i);
            expandableListDetail.put(c.get(i).courseName, ci);
            expandableListTitle.add(c.get(i).courseName);
            Log.d("fdg", c.get(i).courseName);
        }*/
       // ArrayList<courseInformation> c = new ArrayList<courseInformation>();
        courseInformation ci = new courseInformation("(CSCI-104  L) Data Structures and Object Oriented Design","30204R","Lecture","","02:30pm-04:45pm TTh");
        expandableListDetail.put(ci.courseName, ci);
        expandableListTitle.add(ci.courseName);
        courseInformation ci1 = new courseInformation("(CSCI-170) Discrete Methods in Computer Science","30044R","Lecture","Cote","12:30pm-01:50pm MTWTh");
        expandableListDetail.put(ci1.courseName, ci1);
        expandableListTitle.add(ci1.courseName);
        courseInformation ci2 = new courseInformation("CSCI-201) Principles of Software Development","30201R","Lecture","Miller","2:00pm-04:50pm MTWTh");
        expandableListDetail.put(ci2.courseName, ci2);
        expandableListTitle.add(ci2.courseName);
        bt_register = (Button) findViewById(R.id.bt_register);
        bt_register.setOnClickListener(this);
        bt_clearance = (Button) findViewById(R.id.bt_clearance);
        bt_clearance.setOnClickListener(this);
        bt_advisor = (Button) findViewById(R.id.bt_advisor);
        bt_advisor.setOnClickListener(this);
        bt_profile = (Button) findViewById(R.id.bt_profile);
        bt_profile.setOnClickListener(this);
       calender =   (ImageButton) findViewById(R.id.ib_course_calendar);
       calender.setOnClickListener(new View.OnClickListener(){

           @Override
           public void onClick(View v) {
               Intent calenderIntent=new Intent(CourseBinActivity.this,myCourseCalenderActivity.class);

               startActivity(calenderIntent);
           }
       });

        expandableListView = (ExpandableListView) findViewById(R.id.expandableListView2);
        expandableListView = (ExpandableListView) findViewById(R.id.expandableListView2);
        courseBinListAdapter expandableListAdapter = new courseBinListAdapter(getBaseContext(), expandableListTitle, expandableListDetail);
        expandableListView.setAdapter(expandableListAdapter);
        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {

                v.setBackgroundResource(R.drawable.listitemclickedborder);
                return false;
            }
        });
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {

                selectedCourse = expandableListTitle.get(groupPosition);
            }
        });

        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        expandableListTitle.get(groupPosition) + " List Collapsed.",
                        Toast.LENGTH_SHORT).show();

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_course_bin, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will;
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.bt_clearance:
                Intent intent = new Intent(this, DClearanceActivity.class);
                startActivity(intent);
                break;
            case R.id.bt_profile:
                Intent profileintent = new Intent(this, ProfileActivity.class);
                startActivity(profileintent);
                break;
            case R.id.bt_register:
                Intent registerintent = new Intent(this, chooseSemester.class);
                startActivity(registerintent);

            case R.id.bt_advisor:
                Intent structureintent = new Intent(this, AdvisorInfoActivity.class);
                startActivity(structureintent);
                break;
        }

    }


}

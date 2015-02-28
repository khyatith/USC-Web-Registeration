package usc.edu.uscwebapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.Spinner;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

import usc.edu.Common.CourseInformation;
import usc.edu.Common.DepartmentInformation;
import usc.edu.Common.JSONfunctions;
import usc.edu.adapter.RegisterExpandableListAdapter;


public class RegisterActivity extends ActionBarActivity implements OnClickListener {
    Button bt_register;
    Button bt_clearance;
    Button bt_advisor;
    Button bt_profile;
    String selectedsem;
    String selecteddept;
    String selectedyear;
    String DepartmentURL;
    String Course_id;
    String sis_course_id;
    String course_title;
    String min_units;
    String max_units;
    String total_max_units;
    String course_description;
    String diversity_flag;
    String effective_term_code;
    String v_soc_section;
    ArrayList<String> details= new ArrayList<String>();
    ArrayList<String> deptinfo= new ArrayList<String>();
    ArrayList<CourseInformation> courseinfolist= new ArrayList<CourseInformation>();
    ArrayList<CourseInformation> courseinforesult=new ArrayList<CourseInformation>();
    ArrayList<String> res;
    CourseInformation courseinfo;
    Spinner sp_courses;
    String CourseURL;
    CourseInformation course_details;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        bt_register = (Button) findViewById(R.id.bt_register);
        bt_register.setOnClickListener(this);
        bt_clearance = (Button) findViewById(R.id.bt_clearance);
        bt_clearance.setOnClickListener(this);
        bt_advisor = (Button) findViewById(R.id.bt_advisor);
        bt_advisor.setOnClickListener(this);
        bt_profile = (Button) findViewById(R.id.bt_profile);
        bt_profile.setOnClickListener(this);
       // expandableListView = (ExpandableListView) findViewById(R.id.expandableListView);

        //if(!selectedsem.equals(" ") && !selecteddept.equals(" ") && !selectedyear.equals(" ")) {
            selectedsem = getIntent().getStringExtra("semester");
            selecteddept = getIntent().getStringExtra("dept");
            selectedyear = getIntent().getStringExtra("semesteryear");
            DepartmentURL = "http://petri.esd.usc.edu/socAPI/Courses/" + selectedyear + selectedsem + "/" +selecteddept;
            Log.d("department URL", DepartmentURL);
            new HttpAsyncTask().execute(DepartmentURL);
       // }

    }
    private class HttpAsyncTask extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... params) {
            InputStream inputStream = null;
            String result = "";
            //DepartmentURL = "http://petri.esd.usc.edu/socAPI/Courses/"+selectedyear+selectedsem+"/"+selecteddept;
            try {

                // create HttpClient
                HttpClient httpclient = new DefaultHttpClient();

                // make GET request to the given URL
                HttpResponse httpResponse = httpclient.execute(new HttpGet(params[0]));

                // receive response as inputStream
                inputStream = httpResponse.getEntity().getContent();

                // convert inputstream to string
                if(inputStream != null)
                    result = convertInputStreamToString(inputStream);
                else
                    result = "Did not work!";

            } catch (Exception e) {
                Log.d("InputStream", e.getLocalizedMessage());
            }
            return result;
        }
        protected void onPostExecute(String result) {
            try {
                details=parseJSON(result);
                PopulateSpinner(details);
                deptinfo=parseJSONforCourseId(result);
                Log.d("dept info", String.valueOf(deptinfo));
                new GetCourses().execute(String.valueOf(deptinfo));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        private String convertInputStreamToString(InputStream inputStream) throws IOException {
            BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
            String line = "";
            String result = "";
            while((line = bufferedReader.readLine()) != null)
                result += line;

            inputStream.close();
            return result;

        }
        public ArrayList<String> parseJSON(String result) throws JSONException {
            JSONArray arr = new JSONArray(result);
            res = new ArrayList<String>();
            StringBuilder s = new StringBuilder();
            for(int i = 0;i<arr.length();i++)
            {
                Course_id= arr.getJSONObject(i).getString("COURSE_ID");
                course_title= arr.getJSONObject(i).getString("TITLE");
                res.add(Course_id+" "+course_title);
               /* deptinfo =new DepartmentInformation();
                deptinfo.setCourse_id(arr.getJSONObject(i).getString("COURSE_ID"));
                deptinfo.setCourse_title(arr.getJSONObject(i).getString("TITLE"));*/
            }

            // Toast.makeText(getBaseContext(), res.toString(), Toast.LENGTH_LONG).show();
            return res;
        }
    }
        public ArrayList<String> parseJSONforCourseId(String result) throws JSONException{
            JSONArray arr = new JSONArray(result);
            res=new ArrayList<String>();
            for(int i=0;i<arr.length();i++){
                Course_id= arr.getJSONObject(i).getString("COURSE_ID");
                res.add(Course_id);
            }
            return res;
        }
    private void PopulateSpinner(ArrayList<String> result) throws JSONException{
        sp_courses=(Spinner)findViewById(R.id.sp_courses);
        sp_courses.setAdapter(new ArrayAdapter<String>(RegisterActivity.this,android.R.layout.simple_spinner_dropdown_item,result));

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
                break;
            case R.id.bt_advisor:
                Intent structureintent = new Intent(this, AdvisorInfoActivity.class);
                startActivity(structureintent);
                break;
        }

    }
    private class GetCourses extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... params) {
            InputStream inputStream = null;
            String result = " ";

            //DepartmentURL = "http://petri.esd.usc.edu/socAPI/Courses/"+selectedyear+selectedsem+"/"+selecteddept;
           // for (int i = 0; i < deptinfo.size(); i++) {
                CourseURL = "http://petri.esd.usc.edu/socAPI/Courses/" + selectedyear + selectedsem + "/2814" ;
                Log.d("course", CourseURL);
                JSONArray data=null;
                return result;
        }
        protected void onPostExecute(String result) {
            try {

                courseinforesult= parseJSON(result);
                Log.d("courseinforesult",""+courseinforesult);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        private String convertInputStreamToString(InputStream inputStream) throws IOException {
            BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
            String line = "";
            String result = "";
            while((line = bufferedReader.readLine()) != null)
                result += line;

            inputStream.close();
            return result;

        }
        public ArrayList<CourseInformation> parseJSON(String result) throws JSONException {
            JSONArray arr = new JSONArray(result);
            for(int i = 0;i<arr.length();i++)
            {
                course_details=new CourseInformation();
                course_details.setMax_units(arr.getJSONObject(i).getString("MAX_UNITS"));
                courseinfolist.add(course_details);
               /* deptinfo =new DepartmentInformation();
                deptinfo.setCourse_id(arr.getJSONObject(i).getString("COURSE_ID"));
                deptinfo.setCourse_title(arr.getJSONObject(i).getString("TITLE"));*/
            }
            return courseinfolist;
            // Toast.makeText(getBaseContext(), res.toString(), Toast.LENGTH_LONG).show();

        }
    }

}

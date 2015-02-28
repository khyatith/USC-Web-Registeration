package usc.edu.uscwebapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



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
    ArrayList<String> deptinfo = new ArrayList<String>();
    public Context context = this;
    Spinner sp_courses;
    ImageButton ib_course_bin;
    ArrayList<String> details = new ArrayList<String>();
    RatingBar rating;
    RatingBar rating1;


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
        ib_course_bin=(ImageButton)findViewById(R.id.ib_course_bin);
        ib_course_bin.setOnClickListener(this);
        rating=(RatingBar)findViewById(R.id.rating);
        rating1=(RatingBar)findViewById(R.id.rating1);
        rating.setRating(3);
        rating1.setRating(4);

        // expandableListView = (ExpandableListView) findViewById(R.id.expandableListView);

        //if(!selectedsem.equals(" ") && !selecteddept.equals(" ") && !selectedyear.equals(" ")) {
        selectedsem = getIntent().getStringExtra("semester");
        selecteddept = getIntent().getStringExtra("dept");
        selectedyear = getIntent().getStringExtra("semesteryear");
        DepartmentURL = "http://petri.esd.usc.edu/socAPI/Courses/" + selectedyear + selectedsem + "/" + selecteddept;
        Log.d("department URL", DepartmentURL);

        new HttpAsyncTask().execute(DepartmentURL);
        // }

        // new HttpAsyncTask().execute(DepartmentURL);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ib_course_bin:
                Intent courseListIntent = new Intent(RegisterActivity.this, CourseBinActivity.class);
                startActivity(courseListIntent);
                break;
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

    private class HttpAsyncTask extends AsyncTask<String, Void, String> {

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
                if (inputStream != null)
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
                //deptinfo=parseJSONforCourseId(result);

                //Log.d("dept info", String.valueOf(deptinfo));
                //new Getjsonvalues().execute(deptinfo);


                class Getjsonvalues extends AsyncTask<String, String, Void> {

                    class sectioninfo {
                        String secid;
                        String termcode;
                        String courseid;
                        String maxunit;
                        String minunit;
                        String type;
                        String begintime;
                        String endtime;
                        String day;
                        String location;
                        String instructor;
                        String seats;
                        String adddate;
                        String canceldate;
                    }

                    class courseinfo {
                        String courseid;
                        String title;
                        String minunit;
                        String maxunit;
                        String desc;
                        List<sectioninfo> si = new ArrayList<sectioninfo>();
                    }

                    /*LayoutInflater inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
                    View layout = inflater.inflate(R.layout.register_activity_group, null);
                    LinearLayout ll=(LinearLayout)layout.findViewById(R.id.samplelayout);
                    TextView listTitle=(TextView)layout.findViewById(R.id.actgroup);*/
                    //HashMap <String,HashMap<String,String>> hm=new HashMap <String,HashMap<String,String>>();
                    List<courseinfo> displaylist = new ArrayList<courseinfo>();

                    @Override
                    protected Void doInBackground(String... params) {
                        try {

                            for (int i = 0; i < deptinfo.size(); i++) {
                                String CourseURL = "http://petri.esd.usc.edu/socAPI/Courses/" + selectedyear + selectedsem + "/" + deptinfo.get(i);
                                JSONObject jobj = getValues(CourseURL);
                                JSONArray jarr = jobj.getJSONArray("V_SOC_SECTION");
                                Log.d("section id", jobj.getString("TITLE"));
                                courseinfo cou = new courseinfo();
                                cou.courseid = jobj.getString("COURSE_ID");
                                cou.desc = jobj.getString("DESCRIPTION");
                                cou.maxunit = jobj.getString("MIN_UNITS");
                                cou.minunit = jobj.getString("MAX_UNITS");
                                cou.title = jobj.getString("TITLE");

                                List<sectioninfo> ll = new ArrayList<sectioninfo>();
                                for (int j = 0; j < jarr.length(); j++) {
                                    sectioninfo s = new sectioninfo();
                                    s.adddate = jarr.getJSONObject(j).getString("ADD_DATE");
                                    s.begintime = jarr.getJSONObject(j).getString("BEGIN_TIME");
                                    s.canceldate = jarr.getJSONObject(j).getString("CANCEL_DATE");
                                    s.courseid = jarr.getJSONObject(j).getString("COURSE_ID");
                                    s.day = jarr.getJSONObject(j).getString("DAY");
                                    s.endtime = jarr.getJSONObject(j).getString("END_TIME");
                                    s.location = jarr.getJSONObject(j).getString("LOCATION");
                                    s.maxunit = jarr.getJSONObject(j).getString("MAX_UNITS");
                                    s.minunit = jarr.getJSONObject(j).getString("MIN_UNITS");
                                    s.type = jarr.getJSONObject(j).getString("TYPE");
                                    s.termcode = jarr.getJSONObject(j).getString("TERM_CODE");
                                    s.seats = jarr.getJSONObject(j).getString("SEATS");
                                    s.instructor = jarr.getJSONObject(j).getString("INSTRUCTOR");
                                    s.secid = jarr.getJSONObject(j).getString("SECTION_ID");
                                    ll.add(s);
                                }

                                cou.si = ll;
                                displaylist.add(cou);
                                //      Log.d("Ashwin",displaylist.toString());
                            /*    HashMap<String,String> innermap=new HashMap<String,String>();
                                innermap.put("COURSE_ID","");
                                innermap.put("SIS_COURSE_ID","");
                                innermap.put("TITLE","");
                                innermap.put("MIN_UNITS","");
                                innermap.put("MAX_UNITS","");
                                innermap.put("TOTAL_MAX_UNITS","");
                                innermap.put("DESCRIPTION","");
                                innermap.put("EFFECTIVE_TERM_CODE","");
                                innermap.put("SECTION_ID","");
                                innermap.put("SECTION","");
                                innermap.put("TYPE","");
                                innermap.put("BEGIN_TIME","");
                                innermap.put("END_TIME","");
                                innermap.put("LOCATION","");
                                innermap.put("INSTRUCTOR","");
                                innermap.put("SEATS","");
                                innermap.put("ADD_DATE","");
                                innermap.put("CANCEL_DATE","");
                                innermap.put("COURSE_ID","");
                                innermap.put("DAY","");
*/
                                //TextView tv = null;
                                //  tv = new TextView();
                                // tv.setId("Course"+i);

                            }

                        } catch (Exception e) {
                            Log.d("excep id", e.toString());
                        }

                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void aVoid) {
                        super.onPostExecute(aVoid);
                        try {
                            LayoutInflater inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
                            View layout = inflater.inflate(R.layout.register_activity_group, null);
                            LinearLayout ll = (LinearLayout) layout.findViewById(R.id.samplelayout);
                            TextView listTitle = (TextView) layout.findViewById(R.id.actgroup);
                            String s = "App" + displaylist.size();
                            Log.d("Entered", s);
                            //listTitle=(TextView)findViewById(R.id.actgroup);

                            for (int i = 0; i < displaylist.size(); i++) {
                                listTitle.setText("Apple");
                            }
                        } catch (Exception e) {
                            Log.d("DX", e.toString());
                        }
                    }

                }
                ;
                Log.d("hello", "hello");
                Getjsonvalues jj = new Getjsonvalues();
                jj.execute(String.valueOf(deptinfo));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public JSONObject getValues(String urlvalue) {
            JSONObject schoolnamearray = null;
            // errorstring="";
            try {
                String url = Uri
                        .parse(urlvalue)
                        .toString();
                URI uri = new URI(url);

                JSONObject json = null;
                String str = "";
                HttpResponse response;
                HttpClient myClient = new DefaultHttpClient();
                HttpPost myConnection = new HttpPost(uri);
                myConnection.setHeader("Content-type", "application/json");


                response = myClient.execute(myConnection);
                str = EntityUtils.toString(response.getEntity(),
                        "UTF-8");
                //	mEdit1.append(str);


                schoolnamearray = new JSONObject(str);

                //  TextView txtrank = (TextView) findViewById(R.id.rank);
                //  txtrank.setText("Rank : "+str);
            } catch (Exception e) {
                //TextView txtrank = (TextView) findViewById(R.id.rank);
                //txtrank.setText("Rank : "+e.toString());
            }
            return schoolnamearray;
        }

        private String convertInputStreamToString(InputStream inputStream) throws IOException {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            String result = "";
            while ((line = bufferedReader.readLine()) != null)
                result += line;

            inputStream.close();
            return result;

        }

        public HashMap parseJSONforCourseURL(String URL) throws JSONException {
            JSONArray arr = new JSONArray(URL);
            HashMap<String, String> CourseMap = new HashMap<String, String>();
            for (int i = 0; i < arr.length(); i++) {
                String ctitle = arr.getJSONObject(i).getString("TITLE");
                CourseMap.put("title", ctitle);
            }
            Log.d("map value", CourseMap.toString());
            return CourseMap;
        }

        public ArrayList<String> parseJSON(String result) throws JSONException {
            JSONArray arr = new JSONArray(result);
            ArrayList<String> res = new ArrayList<String>();
            StringBuilder s = new StringBuilder();
            for (int i = 0; i < arr.length(); i++) {
                Course_id = arr.getJSONObject(i).getString("COURSE_ID");
                course_title = arr.getJSONObject(i).getString("TITLE");
                res.add(Course_id + " " + course_title);
               /* deptinfo =new DepartmentInformation();
                deptinfo.setCourse_id(arr.getJSONObject(i).getString("COURSE_ID"));
                deptinfo.setCourse_title(arr.getJSONObject(i).getString("TITLE"));*/
            }

            // Toast.makeText(getBaseContext(), res.toString(), Toast.LENGTH_LONG).show();
            return res;
        }

        public ArrayList<String> parseJSONforCourseId(String result) throws JSONException {
            JSONArray arr = new JSONArray(result);
            ArrayList<String> res = new ArrayList<String>();
            for (int i = 0; i < arr.length(); i++) {
                Course_id = arr.getJSONObject(i).getString("COURSE_ID");
                res.add(Course_id);
            }
            return res;
        }

        private void PopulateSpinner(ArrayList<String> result) throws JSONException {
            sp_courses = (Spinner) findViewById(R.id.sp_courses);
            sp_courses.setAdapter(new ArrayAdapter<String>(RegisterActivity.this, android.R.layout.simple_spinner_dropdown_item, result));

        }
    /*private class GetCourses extends AsyncTask<String, Void, String>{

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
                deptinfo.setCourse_title(arr.getJSONObject(i).getString("TITLE"));
            }
            return courseinfolist;
            // Toast.makeText(getBaseContext(), res.toString(), Toast.LENGTH_LONG).show();

        }
    }*/

    }
}

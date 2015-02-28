package usc.edu.uscwebapp;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import java.util.ArrayList;
import org.json.JSONArray;
import java.io.Console;
import android.widget.TextView;

import android.widget.Button;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.text.Html;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.net.Uri;
import android.widget.*;

import org.json.JSONObject;
import android.os.AsyncTask;
import java.util.Hashtable;
import java.util.Set;
import java.util.Enumeration;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import java.util.Date;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import 	java.text.SimpleDateFormat;
public class DClearanceActivity extends Activity implements OnClickListener {

    final Context context = this;
    JSONArray jsonarrayforschool;
    Hashtable<String, String>  schoollist;
    Hashtable<String,String> deptlist;
    ArrayList<String> schoolarraylist;
    private Button bt_signin;
    String promptdisp="";
  //  public JSONParser jsonParser = new JSONParser();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dclearance);
        schoollist= new Hashtable<String,String>();
        deptlist=new Hashtable<String,String>();
        GetJsonValues jvalues=new GetJsonValues();
        jvalues.execute();
        bt_signin=(Button)findViewById(R.id.bt_submit);
        bt_signin.setOnClickListener(this);
        Button bt_clearance, bt_profile,bt_register, bt_structure;
        bt_clearance=(Button)findViewById(R.id.bt_clearance);
        bt_profile=(Button)findViewById(R.id.bt_profile);
        bt_register=(Button)findViewById(R.id.bt_register);
        bt_structure=(Button)findViewById(R.id.bt_structure);
        bt_clearance.setOnClickListener(this);
        bt_profile.setOnClickListener(this);
        bt_register.setOnClickListener(this);
        bt_structure.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {

            switch (v.getId()){
                case R.id.bt_clearance:
                    Intent intent=new Intent(this,DClearanceActivity.class);
                    startActivity(intent);
                    break;
                case R.id.bt_profile:
                    Intent intent1=new Intent(this, ProfileActivity.class);
                    startActivity(intent1);

                    break;
                case R.id.bt_register:
                    Intent registerintent = new Intent(this, chooseSemester.class);
                    startActivity(registerintent);
                    break;
                case R.id.bt_structure:
                    //go to structure activity
                    Intent structureintent = new Intent(this, AdvisorInfoActivity.class);
                    startActivity(structureintent);
                    break;
            }

        
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);

        // set title
        alertDialogBuilder.setTitle("Confirm");

        // set dialog message
        alertDialogBuilder
                .setMessage("Are you sure you wanna register for "+promptdisp)
                .setCancelable(false)
                .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // if this button is clicked, close
                        // current activity
                        Intent intent = new Intent(getApplicationContext(), ConfirmDclear.class);
                        startActivity(intent);

                      Log.e("Here","Reached");
                    }
                })
                .setNegativeButton("No",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // if this button is clicked, just close
                        // the dialog box and do nothing
                        dialog.cancel();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();

}



    private ProgressDialog progressBar;
    private class GetJsonValues extends AsyncTask<Void, Void, Void> {
        ArrayList<String> schoolnamearraylist= new ArrayList<String>();
        JSONArray schoolnames=null;
        JSONArray termnames=null;
        JSONArray deptcode=null;
        JSONArray coursecode=null;
        JSONObject sectioncode=null;
        ArrayList<String> deptnamearraylist= new ArrayList<String>();
        ArrayList<String> TermList=new ArrayList<String>();
        String selectedschoolvalue="";
        String termid="";
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar = new ProgressDialog(DClearanceActivity.this);
            progressBar.setMessage("Connecting.Please Wait.");
            progressBar.setCancelable(false);
            progressBar.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            // TODO Auto-generated method stub
            schoolnames=getValues("http://petri.esd.usc.edu/socAPI/Schools");
            //get all terms;
            termnames=getValues("http://petri.esd.usc.edu/socAPI/Terms");
            try {
              //  SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-ddTHH:MM:SS");
              //  s.ge
                JSONObject jsonobjectforschool;
                for (int i = 0; i < schoolnames.length(); i++) {
                    JSONObject object = schoolnames.getJSONObject(i);
              //      Log.d("Ashwin",object.get("SOC_SCHOOL_DESCRIPTION").toString());
                  //  Log.d("Ashwin",object.get("SOC_SCHOOL_CODE").toString());
                    schoollist.put(object.get("SOC_SCHOOL_DESCRIPTION").toString(),object.get("SOC_SCHOOL_CODE").toString());
                                       schoolnamearraylist.add(object.get("SOC_SCHOOL_DESCRIPTION").toString());

                }

                Log.e("Term name",termnames.length()+termnames.toString());
                for(int j=0;j<termnames.length();j++)
                {
                    JSONObject object = termnames.getJSONObject(j);
                   // TermList.add(object.get("PRE_REG_START_DATE").toString());
                    String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
                    Log.e("CurrentDate",date);
                    if(date.compareTo(object.get("PRE_REG_START_DATE").toString())>0 &&date.compareTo(object.get("COMMENCEMENT_DATE").toString())<=0 )
                    {
                        termid=object.get("TERM_CODE").toString();
                    }
                    Log.e("CurrentTerm is ",termid);
                }

            }
            catch(Exception e)
            {

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // hide the progress dialog

            if (progressBar.isShowing())
                progressBar.dismiss();
            Spinner mySpinner = (Spinner) findViewById(R.id.spinner_school_name);

            // Spinner adapter
            mySpinner
                    .setAdapter(new ArrayAdapter<String>(DClearanceActivity.this,
                            android.R.layout.simple_spinner_dropdown_item,
                            schoolnamearraylist));

            // Spinner on item click listener
            mySpinner
                    .setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                        @Override
                        public void onItemSelected(AdapterView<?> arg0,
                                                   View arg1, int position, long arg3) {
                            // TODO Auto-generated method stub
                            // Locate the textviews in activity_main.xml
                            //TextView txtrank = (TextView) findViewById(R.id);

                             selectedschoolvalue= schoolnamearraylist.get(position);
                            // Set the text followed by the position
                            deptlist.clear();
                            deptnamearraylist.clear();
                            Log.e ("Ranker",schoollist.get(selectedschoolvalue));

                             class GetJsonValuesfordept extends AsyncTask<Void, Void, Void> {

                             /*    @Override
                                 protected void onPreExecute() {
                                     super.onPreExecute();
                                     progressBar = new ProgressDialog(DClearanceActivity.this);
                                 //    progressBar.setMessage("Connecting.Please Wait");
                                     progressBar.setCancelable(false);
                                     progressBar.show();
                                 }*/
                                 @Override
                                 protected Void doInBackground(Void... params) {

                                     deptcode=getValues("http://petri.esd.usc.edu/socAPI/schools/"+schoollist.get(selectedschoolvalue));
                                     try {
                                         //  SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-ddTHH:MM:SS");
                                         //  s.ge
                                         Log.e ("Gotdept val",deptcode.toString()+deptcode.length());
                                         JSONObject jsonobjectforschool;
                                         for (int i = 0; i < deptcode.length(); i++) {
                                             JSONObject object = deptcode.getJSONObject(i);
                                             //      Log.d("Ashwin",object.get("SOC_SCHOOL_DESCRIPTION").toString());
                                             //  Log.d("Ashwin",object.get("SOC_SCHOOL_CODE").toString());
                                            // schoollist.put(object.get("SOC_SCHOOL_DESCRIPTION").toString(),object.get("SOC_SCHOOL_CODE").toString());
                                          //   deptnamearraylist.add(object.get("SOC_DEPARTMENT_CODE").toString());
                                             JSONArray codearray=new JSONArray();
                                             codearray=object.getJSONArray("SOC_DEPARTMENT_CODE");
                                             Log.e("Array Length:",codearray.length()+codearray.toString());
                                             for(int j=0;j<codearray.length();j++)
                                             {
                                                 JSONObject depobj=new JSONObject();
                                                 depobj=codearray.getJSONObject(j);
                                                 deptnamearraylist.add(depobj.get("SOC_DEPARTMENT_DESCRIPTION").toString());
                                                 deptlist.put(depobj.get("SOC_DEPARTMENT_DESCRIPTION").toString(),depobj.get("SOC_DEPARTMENT_CODE").toString());
                                             }
                                         }
                                    Log.e ("GotdeptRam",deptnamearraylist.toString());

                                     }
                                     catch(Exception e)
                                     {
                                         Log.e ("Gotdept",e.toString());
                                     }

                                     return null;
                                 }
                                 @Override
                                 protected void onPostExecute(Void result) {
                                     super.onPostExecute(result);
                                    // if (progressBar.isShowing())
                                      //   progressBar.dismiss();
                                     Spinner mySpinner = (Spinner) findViewById(R.id.deptspinner);

                                     // Spinner adapter
                                     mySpinner
                                             .setAdapter(new ArrayAdapter<String>(DClearanceActivity.this,
                                                     android.R.layout.simple_spinner_dropdown_item,
                                                     deptnamearraylist));

                                     // Spinner on item click listener
                                     mySpinner
                                             .setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


                                                 @Override
                                                 public void onItemSelected(AdapterView<?> arg0,
                                                                            View arg1, int position1, long arg3) {
                                                    final String selecteddeptart=deptlist.get(deptnamearraylist.get(position1));
                                                    Log.e("Selected Dept is",selecteddeptart);

                                                     class GetJsonValuesforCourse extends AsyncTask<Void, Void, Void> {
                                                         ArrayList<String> courselistarray=new ArrayList<String>();
                                                         Hashtable<String,String> courselist=new Hashtable<String, String>();
                                                         @Override
                                                         protected void onPreExecute() {
                                                             super.onPreExecute();
                                                             courselistarray.clear();
                                                             courselist.clear();
                                                      /*       progressBar = new ProgressDialog(DClearanceActivity.this);
                                                             progressBar.setMessage("Connecting.Please Wait");
                                                             progressBar.setCancelable(false);
                                                             progressBar.show();*/
                                                         }
                                                         @Override

                                                         protected Void doInBackground(Void... params) {
                                                             try {
                                                                 coursecode = getValues("http://petri.esd.usc.edu/socAPI/Courses/" + termid + "/" + selecteddeptart);
                                                                 Log.e("Course code", coursecode.toString());
                                                                 for (int i = 0; i < coursecode.length(); i++) {
                                                                     JSONObject depobj = new JSONObject();
                                                                     depobj = coursecode.getJSONObject(i);
                                                                     courselistarray.add(depobj.get("SIS_COURSE_ID").toString()+"-"+depobj.get("TITLE").toString());
                                                                     courselist.put(depobj.get("SIS_COURSE_ID").toString()+"-"+depobj.get("TITLE").toString(),depobj.get("COURSE_ID").toString());
                                                                 }

                                                             }
                                                             catch (Exception e)
                                                             {

                                                             }
                                                             return null;
                                                         }
                                                         @Override
                                                         protected void onPostExecute(Void result) {
                                                             super.onPostExecute(result);
                                                             // hide the progress dialog

                                                       //      if (progressBar.isShowing())
                                                         //        progressBar.dismiss();
                                                             Spinner mySpinner = (Spinner) findViewById(R.id.coursespinner);

                                                             // Spinner adapter
                                                             mySpinner
                                                                     .setAdapter(new ArrayAdapter<String>(DClearanceActivity.this,
                                                                             android.R.layout.simple_spinner_dropdown_item,
                                                                             courselistarray));

                                                             // Spinner on item click listener
                                                             mySpinner
                                                                     .setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                                         @Override
                                                                         public void onItemSelected(AdapterView<?> arg0,
                                                                                                    View arg1, int position2, long arg3) {
                                                                             final String selectedcourse=courselist.get(courselistarray.get(position2));
                                                                             Log.e("Selected Course is",selectedcourse);
                                                                             promptdisp="";
                                                                             promptdisp+=courselistarray.get(position2)+" in ";
                                                                             class GetJsonValuesforsection extends AsyncTask<Void, Void, Void> {
                                                                                 ArrayList<String> Sectionlist=new ArrayList<String>();
                                                                                 Hashtable<String,String> Sectiontable=new Hashtable<String, String>();
                                                                                 @Override
                                                                                 protected void onPreExecute() {
                                                                                     super.onPreExecute();
                                                                                     Sectiontable.clear();
                                                                                     Sectionlist.clear();
                                                                                     progressBar = new ProgressDialog(DClearanceActivity.this);
                                                                                     progressBar.setMessage("Connecting.Please Wait");
                                                                                     progressBar.setCancelable(false);
                                                                                     progressBar.show();
                                                                                 }
                                                                                 @Override

                                                                                 protected Void doInBackground(Void... params) {
                                                                                     try {
                                                                                         sectioncode=getObjectValues("http://petri.esd.usc.edu/socAPI/Courses/" + termid + "/" + selectedcourse);
                                                                                         JSONArray jarray=new JSONArray();
                                                                                         jarray=sectioncode.getJSONArray("V_SOC_SECTION");
                                                                                         for (int i = 0; i < jarray.length(); i++) {
                                                                                             JSONObject depobj = new JSONObject();
                                                                                             depobj = jarray.getJSONObject(i);
                                                                                             Sectionlist.add(depobj.get("SECTION_ID").toString()+","+depobj.get("BEGIN_TIME").toString()+"-"+depobj.get("END_TIME").toString()+","+depobj.get("DAY").toString());
                                                                                             Sectiontable.put(depobj.get("SECTION_ID").toString()+","+depobj.get("BEGIN_TIME").toString()+"-"+depobj.get("END_TIME").toString()+","+depobj.get("DAY").toString(),depobj.get("SECTION_ID").toString());
                                                                                         }
                                                                                     }
                                                                                     catch(Exception e)
                                                                                     {

                                                                                     }
                                                                                     return null;
                                                                                 }
                                                                                 @Override
                                                                                 protected void onPostExecute(Void result) {
                                                                                     super.onPostExecute(result);
                                                                                     // hide the progress dialog

                                                                                     if (progressBar.isShowing())
                                                                                         progressBar.dismiss();
                                                                                     Spinner mySpinner = (Spinner) findViewById(R.id.sectionspinner);

                                                                                     // Spinner adapter
                                                                                     mySpinner
                                                                                             .setAdapter(new ArrayAdapter<String>(DClearanceActivity.this,
                                                                                                     android.R.layout.simple_spinner_dropdown_item,
                                                                                                     Sectionlist));

                                                                                     // Spinner on item click listener
                                                                                     mySpinner
                                                                                             .setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                                                                 @Override
                                                                                                 public void onItemSelected(AdapterView<?> arg0,
                                                                                                                            View arg1, int position3, long arg3) {
                                                                                                     final String selectedsection = Sectiontable.get(Sectionlist.get(position3));
                                                                                                     promptdisp+=Sectionlist.get(position3);
                                                                                                     Log.e("Selected section is", selectedsection);
                                                                                                 }
                                                                                                 @Override
                                                                                                 public void onNothingSelected(AdapterView<?> arg0) {
                                                                                                     // TODO Auto-generated method stub
                                                                                                 }

                                                                                             });
                                                                                 }//End
                                                                             };
                                                                             GetJsonValuesforsection gjs=new GetJsonValuesforsection();
                                                                             gjs.execute();

                                                                         }
                                                                         @Override
                                                                         public void onNothingSelected(AdapterView<?> arg0) {
                                                                             // TODO Auto-generated method stub
                                                                         }

                                                                     });
                                                         }//ash

                                                     };
                                                     GetJsonValuesforCourse jjc=new GetJsonValuesforCourse();
                                                     jjc.execute();
                                                 }



                                                 @Override
                                                 public void onNothingSelected(AdapterView<?> arg0) {
                                                     // TODO Auto-generated method stub
                                                 }
                                             });
                                 }

                            };
                            GetJsonValuesfordept d=new GetJsonValuesfordept();
                            d.execute();

                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> arg0) {
                            // TODO Auto-generated method stub
                        }
                    });
        }
    };
    public JSONArray getValues(String urlvalue)
    {
        JSONArray schoolnamearray=null;
       // errorstring="";
        try
        {
            String url = Uri
                    .parse(urlvalue)
                    .toString();
            URI uri = new URI(url);

            JSONObject json = null;
            String str = "";
            HttpResponse response;
            HttpClient myClient = new DefaultHttpClient();
            HttpPost myConnection = new HttpPost(uri);
            myConnection.setHeader("Content-type","application/json");


            response = myClient.execute(myConnection);
            str = EntityUtils.toString(response.getEntity(),
                    "UTF-8");
            //	mEdit1.append(str);


                schoolnamearray=new JSONArray(str);

              //  TextView txtrank = (TextView) findViewById(R.id.rank);
              //  txtrank.setText("Rank : "+str);




        }
        catch(Exception e)
        {
            //TextView txtrank = (TextView) findViewById(R.id.rank);
            //txtrank.setText("Rank : "+e.toString());
        }
        	return schoolnamearray;
    }
    public JSONObject getObjectValues(String urlvalue)
    {
        JSONObject nameobject=null;
        // errorstring="";
        try
        {
            String url = Uri
                    .parse(urlvalue)
                    .toString();
            URI uri = new URI(url);

            JSONObject json = null;
            String str = "";
            HttpResponse response;
            HttpClient myClient = new DefaultHttpClient();
            HttpPost myConnection = new HttpPost(uri);
            myConnection.setHeader("Content-type","application/json");


            response = myClient.execute(myConnection);
            str = EntityUtils.toString(response.getEntity(),
                    "UTF-8");
            //	mEdit1.append(str);


            nameobject=new JSONObject(str);

            //  TextView txtrank = (TextView) findViewById(R.id.rank);
            //  txtrank.setText("Rank : "+str);




        }
        catch(Exception e)
        {
            //TextView txtrank = (TextView) findViewById(R.id.rank);
            //txtrank.setText("Rank : "+e.toString());
        }
        return nameobject;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_dclearance, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

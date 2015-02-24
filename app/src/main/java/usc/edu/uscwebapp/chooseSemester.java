package usc.edu.uscwebapp;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.PopupWindow;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import usc.edu.adapter.ExpandableListAdapter;


public class chooseSemester extends ActionBarActivity {

    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    Boolean DenStudent;
    HashMap<String,String> schoolCodes;
    boolean getSchools;
    boolean getDepartments;
    ArrayList<String> details= new ArrayList<String>();
    final HashMap<String, ArrayList<String>> expandableListDetail = new  HashMap<String,ArrayList<String>>();
    final ArrayList<String> expandableListTitle = new ArrayList<String>();
    private PopupWindow chooseDepartment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_semester);
        expandableListView = (ExpandableListView) findViewById(R.id.expandableListView);
        String apiURL = "http://petri.esd.usc.edu/socAPI/Schools/";
        new HttpAsyncTask().execute(apiURL,"getSchools");

        //apapi call 1 made here follow the code here on
        //all defined in different modules




    }


    //ASYNC TASK TO CALL API TO GET THE SCHOOL LIST FOR A PARTICUALR SEMESTER
    private class HttpAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            InputStream inputStream = null;
            String result = "";
            if(params[1].equals("getSchools")) getSchools=true;
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
        /*
        * tHE activity needs to make 2 async api calls both manages by the same code.
        * if parameter 2 = schoolList getSchools gets set to true and onPostExe calls the UI thread associated
        * with expandable list
        * else it makes a call to populate the popup box
        * */
        protected void onPostExecute(String result) {

            if(getSchools==true)
                populateExpandableList(result);
            else
            {
                try {
                    populatePopupbox(result);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            implementAdapter();



        }



        //Result from doinback obtained as an inputStream , needs to be parsed
        private String convertInputStreamToString(InputStream inputStream) throws IOException {
            BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
            String line = "";
            String result = "";
            while((line = bufferedReader.readLine()) != null)
                result += line;

            inputStream.close();
            return result;

        }

    }

    private void populatePopupbox(String result) throws JSONException {
        JSONArray arr = new JSONArray(result);
        JSONArray deptList = arr.getJSONObject(0).getJSONArray("SOC_DEPARTMENT_CODE");
        ArrayList<String> res = new ArrayList<String>();
        schoolCodes = new HashMap<>();
        StringBuilder s = new StringBuilder();
        for(int i = 0;i<deptList.length();i++)
        {
            String department = deptList.getJSONObject(i).getString("SOC_DEPARTMENT_DESCRIPTION");
            String deptCode = deptList.getJSONObject(i).getString("SOC_DEPARTMENT_CODE");
            schoolCodes.put(department,deptCode);
            res.add(department);
        }
        LayoutInflater inflater = (LayoutInflater) chooseSemester.this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.activity_temp_pop_up,
                (ViewGroup) findViewById(R.id.abc));


        chooseDepartment = new PopupWindow(layout, 600, 750, true);

        chooseDepartment.showAtLocation(layout, Gravity.CENTER, 0, 0);




    }
    //ASYNC TASK TO CALL API TO GET THE SCHOOL LIST FOR A PARTICUALR SEMESTER ENDS here
    /*****************************************************************************************/

    private void populateExpandableList(String result) {
        final HashMap<String, ArrayList<String>> temp;


        try {
            details= parseJSON(result);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        temp=getCourses(details);
        expandableListDetail.putAll(temp);
        expandableListTitle.addAll(expandableListDetail.keySet());

        expandableListAdapter = new ExpandableListAdapter(getBaseContext(),expandableListTitle , expandableListDetail);
        expandableListView.setAdapter(expandableListAdapter);

    }



    //JSON PARSING
    public ArrayList<String> parseJSON(String result) throws JSONException {
        JSONArray arr = new JSONArray(result);
        ArrayList<String> res = new ArrayList<String>();
        schoolCodes = new HashMap<>();
        StringBuilder s = new StringBuilder();
        for(int i = 0;i<arr.length();i++)
        {
            String school = arr.getJSONObject(i).getString("SOC_SCHOOL_DESCRIPTION");
            String schoolCode = arr.getJSONObject(i).getString("SOC_SCHOOL_CODE");
            schoolCodes.put(school,schoolCode);
            res.add(school);
        }
        // Toast.makeText(getBaseContext(), res.toString(), Toast.LENGTH_LONG).show();
        return res;
    }

    //TO FILL THE ADAPTER FR THE EXPANDABLE LIST
    //
    public HashMap<String,ArrayList<String>> getCourses(ArrayList<String> details)
    {
        HashMap<String, ArrayList<String>> hm = new HashMap<String,ArrayList<String>>();
        Time today = new Time(Time.getCurrentTimezone());
        String sem = "";
        today.setToNow();
        ArrayList<String> temp = new ArrayList<>();
        temp = details;
        ArrayList<String> temp1 = new ArrayList<>();
        ArrayList<String> temp2 = new ArrayList<>();
        temp1=temp2=details;
        if(today.month<=4 || (today.month==5 && today.monthDay<20))
        {
            temp2 = details;
            sem = "Summer "+(today.year-1);
            hm.put(sem,temp2);
            temp1 = details;
            sem = "Fall "+(today.year-1);
            hm.put(sem,temp1);

            sem = "Spring "+today.year;
            hm.put(sem,temp);
        }
        else if(today.month>=8)
        {
            temp1 = details;
            sem = "Spring "+(today.year);
            hm.put(sem,temp1);
            temp2 = details;
            sem = "Summer "+(today.year);
            hm.put(sem,temp2);
            sem = "Fall "+today.year;
            hm.put(sem,details);

        }
        else
        {
            temp1 = details;
            sem = "Fall "+(today.year-1);
            hm.put(sem,temp1);
            sem = "Spring "+(today.year);
            hm.put(sem,temp2);
            sem = "Summer "+today.year;
            hm.put(sem,details);

        }



        return hm;
    }


    public void implementAdapter()
    {
        getSchools = false;
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

                expandableListTitle.get(groupPosition);

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


        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                // TODO Auto-generated method stub
                Toast.makeText(
                        getApplicationContext(),

                        schoolCodes.get(expandableListDetail.get(
                                expandableListTitle.get(groupPosition)).get(
                                childPosition)), Toast.LENGTH_SHORT)
                        .show();
                String apiURL = "http://petri.esd.usc.edu/socAPI/Schools/"+schoolCodes.get(expandableListDetail.get(
                        expandableListTitle.get(groupPosition)).get(
                        childPosition));
                new HttpAsyncTask().execute(apiURL,"getDepts");
                return false;
            }
        });
    }




































































































































    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_choose_semester, menu);
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

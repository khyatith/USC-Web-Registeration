package usc.edu.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import usc.edu.Common.CourseInformation;
import usc.edu.Common.DepartmentInformation;
import usc.edu.uscwebapp.R;

/**
 * Created by Khyati on 2/24/2015.
 */
public class RegisterExpandableListAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<String> expandableListTitle;
    private HashMap<String, ArrayList<String>> expandableListDetail;
    ArrayList<DepartmentInformation>deptinfo;
    DepartmentInformation deptinfo1=new DepartmentInformation();
    String CourseURL;
    public RegisterExpandableListAdapter(Context context,ArrayList<DepartmentInformation>deptinfo){
        this.context=context;
        this.deptinfo=deptinfo;
    }
    @Override
    public int getChildrenCount(int listPosition) {
        return this.deptinfo.size();
        // return this.expandableListDetail.get(this.expandableListTitle.get(0))
        //     .size();
    }

    @Override
    public Object getGroup(int listPosition) {
        return this.deptinfo.get(listPosition);
    }

    @Override
    public int getGroupCount() {
        return this.deptinfo.size();
    }

    @Override
    public long getGroupId(int listPosition) {
        return listPosition;
    }


    @Override
    public Object getChild(int groupPosition, int childPosition) {
       return deptinfo.get(groupPosition).getCourse_id();
    }

    @Override
    public long getChildId(int listPosition, int expandedListPosition) {
        return expandedListPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        //String listTitle = (String) getGroup(listPosition);
        DepartmentInformation departmentInfo = (DepartmentInformation) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.register_activity_group, null);
        }
        TextView listTitleTextView = (TextView) convertView
                .findViewById(R.id.listTitle);
        listTitleTextView.setText(departmentInfo.getSis_course_id().trim()+" "+departmentInfo.getCourse_title().trim()+" "+"("+departmentInfo.getMax_units().trim()+" "+"Units)");
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        String childText = (String) getChild(groupPosition, childPosition);
        Log.d("child text",""+childText);
        CourseURL = "http://petri.esd.usc.edu/socAPI/Courses/20151"+"/"+childText;
        new HttpAsyncTask().execute(CourseURL);
        //final String childText = "jdsbhjsbdjfs";
        if (convertView == null) {
            LayoutInflater Inflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = Inflater.inflate(R.layout.register_activity_list_item, null);
        }

        TextView tv_type = (TextView) convertView
                .findViewById(R.id.tv_type);
        //String selectedcourseid=childText.getCourse_id().trim();
        //Log.d("selected course id",selectedcourseid);
        //tv_type.setText(childText);
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int listPosition, int expandedListPosition) {
        return true;
    }

}
class HttpAsyncTask extends AsyncTask<String, Void, String> {

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

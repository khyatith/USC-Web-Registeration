package usc.edu.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import usc.edu.uscwebapp.R;

/**
 * Adapter for setting data in profile activity
 */
public class ProfileExpandableListAdapter extends BaseExpandableListAdapter {
    private Activity activity;
    private ArrayList<Object> childtems;
    private LayoutInflater inflater;
    private ArrayList<String> parentItems, child;

    public ProfileExpandableListAdapter(ArrayList<String> parents, ArrayList<Object> childern) {
        this.parentItems = parents;
        this.childtems = childern;
    }
    public void setInflater(LayoutInflater inflater, Activity activity) {
        this.inflater = inflater;
        this.activity = activity;
    }

    public static HashMap getData() {
        HashMap expandableListDetail = new HashMap();

        List technology = new ArrayList();
        technology.add("Beats sued for noise-cancelling tech");
        technology.add("Wikipedia blocks US Congress edits");
        technology.add("Google quizzed over deleted links");
        technology.add("Nasa seeks aid with Earth-Mars links");
        technology.add("The Good, the Bad and the Ugly");

        List entertainment = new ArrayList();
        entertainment.add("Goldfinch novel set for big screen");
        entertainment.add("Anderson stellar in Streetcar");
        entertainment.add("Ronstadt receives White House honour");
        entertainment.add("Toronto to open with The Judge");
        entertainment.add("British dancer return from Russia");

        expandableListDetail.put("TECHNOLOGY NEWS", technology);
        expandableListDetail.put("ENTERTAINMENT NEWS", entertainment);
        return expandableListDetail;
    }

    @Override
    public int getGroupCount() {
        return 0;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 0;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return null;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        return null;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        child = (ArrayList<String>) childtems.get(groupPosition);
        TextView textView = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_group, null);
        }
        textView = (TextView) convertView.findViewById(R.id.listTitle);
        textView.setText(child.get(childPosition));
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(activity, child.get(childPosition),

                        Toast.LENGTH_SHORT).show();
            }
        });
        return convertView;

    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}

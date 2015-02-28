package usc.edu.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import usc.edu.Common.courseInformation;
import usc.edu.uscwebapp.R;

/**
 * Created by Aditi on 2/26/2015.
 */
public class courseBinListAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<String> expandableListTitle;
    private HashMap<String, courseInformation> expandableListDetail;

    public courseBinListAdapter(Context context, List<String> expandableListTitle,
                                 HashMap<String, courseInformation> expandableListDetail) {
        this.context = context;
        this.expandableListTitle = expandableListTitle;
        this.expandableListDetail = expandableListDetail;
    }



    @Override
    public int getChildrenCount(int listPosition) {
        return 1;
        // return this.expandableListDetail.get(this.expandableListTitle.get(0))
        //     .size();
    }

    @Override
    public Object getGroup(int listPosition) {
        return this.expandableListTitle.get(listPosition);
    }

    @Override
    public int getGroupCount() {
        return this.expandableListTitle.size();
    }

    @Override
    public long getGroupId(int listPosition) {
        return listPosition;
    }


    @Override
    public Object getChild(int listPosition, int expandedListPosition) {
        return this.expandableListDetail.get(this.expandableListTitle.get(listPosition));

    }



    @Override
    public long getChildId(int listPosition, int expandedListPosition) {
        return expandedListPosition;
    }

    @Override
    public View getGroupView(int listPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String listTitle = (String) getGroup(listPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_group, null);
        }
        TextView listTitleTextView = (TextView) convertView
                .findViewById(R.id.listTitle);
        listTitleTextView.setTypeface(null, Typeface.BOLD);
        listTitleTextView.setText(listTitle);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final courseInformation childText = (courseInformation) getChild(groupPosition, childPosition);
        final int groupPos = groupPosition;
        //final String childText = "jdsbhjsbdjfs";
        if (convertView == null) {
            LayoutInflater Inflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = Inflater.inflate(R.layout.coursebin_listitem, null);
        }
        final Button schedule = (Button) convertView.findViewById(R.id.button2);
        Button unSchedule = (Button) convertView.findViewById(R.id.button3);
        Button Delete = (Button) convertView.findViewById(R.id.button4);
        schedule.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                schedule.setText("Scheduled");
                schedule.setTextColor(Color.parseColor("#009100"));
                
            }
        });

        unSchedule.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                schedule.setText("Schedule");
                schedule.setTextColor(Color.parseColor("#ff121aff"));
            }
        });
        Delete.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                removeGroup(groupPos);
            }
        } );




        TextView courseCode = (TextView) convertView
                .findViewById(R.id.textView);
        TextView courseType = (TextView) convertView
                .findViewById(R.id.textView2);
        TextView courseTime = (TextView) convertView
                .findViewById(R.id.textView4);
        TextView profName = (TextView) convertView
                .findViewById(R.id.textView5);
        courseCode.setText(childText.courseCode);
        courseType.setText(childText.courseType);
        courseTime.setText(childText.startTime);
        profName.setText(childText.profName);
        return convertView;
    }

    private void removeGroup(int groupPosition) {
        notifyDataSetChanged();
    }
    public void removeChild(int group, int child) {
        //TODO: Remove the according child
        notifyDataSetChanged();
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


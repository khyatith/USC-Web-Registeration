package usc.edu.Common;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Aditi on 2/26/2015.*/

public class courseList extends ArrayList<courseInformation> implements Parcelable {

   String courseName;
    String courseCode;
    String courseType;  //Lecture / lab make it boolean if yu want
    String profName;
    String startTime;   // change the data type for convenience if u want

   /* public courseInfo(String courseName, String courseCode, String courseType, String profName, String startTime) {

        this.courseName = courseName;
        this.courseCode = courseCode;
        this.courseType = courseType;
        this.profName = profName;
        this.startTime = startTime;
    }
*/
    public courseList(Parcel in) {
        readFromParcel(in);
    }

    public courseList() {

    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel in, int flags) {
        int size = this.size();
        in.writeInt(size);
        for(int i = 0;i<size;i++) {
            courseInformation c= this.get(i);
            in.writeString(c.courseCode);
            in.writeString(c.courseName);
            in.writeString(c.courseType);
            in.writeString(c.profName);
            in.writeString(c.startTime);

        }

    }
    private void readFromParcel(Parcel in)
    {
        this.clear();
        int size = in.readInt();
        for(int i =0;i<size;i++)
        {
            courseInformation c = new courseInformation();
            c.courseCode = in.readString();
            c.courseName = in.readString();
            c.courseType = in.readString();
            c.profName = in.readString();
            c.startTime = in.readString();
            this.add(c);
        }

    }

    public static final Creator CREATOR = new Creator(){

        @Override
        public courseList createFromParcel(Parcel in) {
            return new courseList(in);
        }

        @Override
        public Object[] newArray(int size) {
            return null;
        }
    };
}
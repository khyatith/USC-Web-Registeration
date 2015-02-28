package usc.edu.Common;

/**
 * Created by Aditi on 2/26/2015.
 */
public class courseInformation {

    public String courseName;
    public String courseCode;
    public String courseType;  //Lecture / lab make it boolean if yu want
    public String profName;
    public String startTime;

    public courseInformation(){}
    public courseInformation(String courseName, String courseCode, String courseType, String profName, String startTime) {
        this.courseName = courseName;
        this.courseCode = courseCode;
        this.courseType = courseType;
        this.profName = profName;
        this.startTime = startTime;
    }
}

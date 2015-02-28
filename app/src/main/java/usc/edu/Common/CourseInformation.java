package usc.edu.Common;

/**
 * Created by Khyati on 2/25/2015.
 */
public class CourseInformation {
    String section_id;
    String term_code;
    String course_id;
    String sis_course_id;
    String min_units;
    String max_units;
    String name;
    String section;
    String session;
    String type;
    String begin_time;
    String end_time;
    String day;
    String location;
    String registered;
    String instructor;
    String seats;
    String add_date;
    String cancel_date;
    String publish_flag;
    String publish_section_flag;

    public CourseInformation(){}
    public CourseInformation(String section_id, String term_code, String course_id, String sis_course_id, String min_units, String max_units, String name, String section, String session, String type, String begin_time, String end_time, String day, String location, String registered, String instructor, String seats, String add_date, String cancel_date, String publish_flag, String publish_section_flag) {
        this.section_id = section_id;
        this.term_code = term_code;
        this.course_id = course_id;
        this.sis_course_id = sis_course_id;
        this.min_units = min_units;
        this.max_units = max_units;
        this.name = name;
        this.section = section;
        this.session = session;
        this.type = type;
        this.begin_time = begin_time;
        this.end_time = end_time;
        this.day = day;
        this.location = location;
        this.registered = registered;
        this.instructor = instructor;
        this.seats = seats;
        this.add_date = add_date;
        this.cancel_date = cancel_date;
        this.publish_flag = publish_flag;
        this.publish_section_flag = publish_section_flag;
    }

    public String getSection_id() {
        return section_id;
    }

    public void setSection_id(String section_id) {
        this.section_id = section_id;
    }

    public String getTerm_code() {
        return term_code;
    }

    public void setTerm_code(String term_code) {
        this.term_code = term_code;
    }

    public String getCourse_id() {
        return course_id;
    }

    public void setCourse_id(String course_id) {
        this.course_id = course_id;
    }

    public String getSis_course_id() {
        return sis_course_id;
    }

    public void setSis_course_id(String sis_course_id) {
        this.sis_course_id = sis_course_id;
    }

    public String getMin_units() {
        return min_units;
    }

    public void setMin_units(String min_units) {
        this.min_units = min_units;
    }

    public String getMax_units() {
        return max_units;
    }

    public void setMax_units(String max_units) {
        this.max_units = max_units;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBegin_time() {
        return begin_time;
    }

    public void setBegin_time(String begin_time) {
        this.begin_time = begin_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getRegistered() {
        return registered;
    }

    public void setRegistered(String registered) {
        this.registered = registered;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public String getSeats() {
        return seats;
    }

    public void setSeats(String seats) {
        this.seats = seats;
    }

    public String getAdd_date() {
        return add_date;
    }

    public void setAdd_date(String add_date) {
        this.add_date = add_date;
    }

    public String getCancel_date() {
        return cancel_date;
    }

    public void setCancel_date(String cancel_date) {
        this.cancel_date = cancel_date;
    }

    public String getPublish_flag() {
        return publish_flag;
    }

    public void setPublish_flag(String publish_flag) {
        this.publish_flag = publish_flag;
    }

    public String getPublish_section_flag() {
        return publish_section_flag;
    }

    public void setPublish_section_flag(String publish_section_flag) {
        this.publish_section_flag = publish_section_flag;
    }
}

package usc.edu.Common;

import java.util.ArrayList;

/**
 * Created by Khyati on 2/23/2015.
 */
public class DepartmentInformation {
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

    public DepartmentInformation(){

    }
    public DepartmentInformation(String course_id, String sis_course_id, String course_title, String min_units, String max_units, String total_max_units, String course_description, String diversity_flag, String effective_term_code, String v_soc_section) {
        Course_id = course_id;
        this.sis_course_id = sis_course_id;
        this.course_title = course_title;
        this.min_units = min_units;
        this.max_units = max_units;
        this.total_max_units = total_max_units;
        this.course_description = course_description;
        this.diversity_flag = diversity_flag;
        this.effective_term_code = effective_term_code;
        this.v_soc_section = v_soc_section;
    }
    public String getCourse_id() {
        return Course_id;
    }

    public void setCourse_id(String course_id) {
        Course_id = course_id;
    }

    public String getSis_course_id() {
        return sis_course_id;
    }

    public void setSis_course_id(String sis_course_id) {
        this.sis_course_id = sis_course_id;
    }

    public String getCourse_title() {
        return course_title;
    }

    public void setCourse_title(String course_title) {
        this.course_title = course_title;
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

    public String getTotal_max_units() {
        return total_max_units;
    }

    public void setTotal_max_units(String total_max_units) {
        this.total_max_units = total_max_units;
    }

    public String getCourse_description() {
        return course_description;
    }

    public void setCourse_description(String course_description) {
        this.course_description = course_description;
    }

    public String getDiversity_flag() {
        return diversity_flag;
    }

    public void setDiversity_flag(String diversity_flag) {
        this.diversity_flag = diversity_flag;
    }

    public String getEffective_term_code() {
        return effective_term_code;
    }

    public void setEffective_term_code(String effective_term_code) {
        this.effective_term_code = effective_term_code;
    }

    public String getV_soc_section() {
        return v_soc_section;
    }

    public void setV_soc_section(String v_soc_section) {
        this.v_soc_section = v_soc_section;
    }
}

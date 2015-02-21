package usc.edu.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Adapter for setting data in profile activity
 */
public class ProfileExpandableListAdapter {
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
}

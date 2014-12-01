package com.project.parth.closestone;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Parth on 11/5/14.
 */
public class Cheeses {
    public static List<String> sCheeseStrings = new ArrayList<String>();

    static void populateList(){

        sCheeseStrings.add("Priority 1");
        sCheeseStrings.add("Priority 2");
        sCheeseStrings.add("Priority 3");
    }

    static void removeList(){
        sCheeseStrings.clear();
    }

}




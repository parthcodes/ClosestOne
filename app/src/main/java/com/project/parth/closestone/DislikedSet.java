package com.project.parth.closestone;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Parth on 11/7/14.
 */
public class DislikedSet {
    private String name;

    public DislikedSet(String n){

        name =n;


    }

    public static List<DislikedSet> getResults(){

        List<DislikedSet> results = new ArrayList<DislikedSet>();

        results.add(new DislikedSet("Restaurant1"));
        results.add(new DislikedSet("Restaurant2"));
        results.add(new DislikedSet("Restaurant3"));

        return results;
    }

    public String toString(){
        return name;
    }
}

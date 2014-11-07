package com.project.parth.closestone;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Parth on 11/6/14.
 */
public class ResultSet {

    private String name;
    public ResultSet(String n){

        name =n;

    }

    public static List<ResultSet> getResults(){

        List<ResultSet> results = new ArrayList<ResultSet>();

        results.add(new ResultSet("Restaurant1"));
        results.add(new ResultSet("Restaurant2"));
        results.add(new ResultSet("Restaurant3"));

        return results;
    }

    public String toString(){
        return name;
    }
}

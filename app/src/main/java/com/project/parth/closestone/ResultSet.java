package com.project.parth.closestone;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Parth on 11/6/14.
 */
public class ResultSet {

    private String name;
    private String address;
    private float distance;
    public ResultSet(String n,String addr, float dis){

        name =n;
        address=addr;
       distance = dis;

    }

    public static List<ResultSet> getResults(String[] names, float[] dis, String[] address){

        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);

        List<ResultSet> results = new ArrayList<ResultSet>();

        for(int i=0;i<3;i++) {
            results.add(new ResultSet(names[i], address[i], Float.valueOf(df.format(dis[i]))));

        }

        return results;
    }

    public String toString(){
        return name+"   "+address+"        "+distance;
    }
}

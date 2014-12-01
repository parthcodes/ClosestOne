package com.project.parth.closestone;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import android.location.Location;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.DecimalFormat;

import static android.R.layout.simple_list_item_1;
import static android.R.layout.simple_list_item_2;


public class ResultsActivity extends ListActivity implements LocationListener{

    private GoogleMap googleMap;
    public static final String YELPDATATAG= "yelp.data.tag";

    protected LocationManager locationManager;
    public float[] disArray = new float[3];
    private SharedPreferences sharedpreferences;
    public String[] ratings=new String[3];
    public String[] address=new String[3];
    public String[] restaurantNames=new String[3];
    public String[] locationLat = new String[3];
    public String[] locationLon = new String[3];
    public String type="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        Intent i = getIntent();
        type = i.getStringExtra("type");

        sharedpreferences = getSharedPreferences(HomeActivity.MyPREFERENCES, Context.MODE_PRIVATE);
       // Toast.makeText(ResultsActivity.this,sharedpreferences.getString("priority1",""), Toast.LENGTH_LONG).show();

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,this);





        //Toast.makeText(ResultsActivity.this, String.valueOf(response.length()),Toast.LENGTH_LONG).show();



    }

    void createList(String[] names, float[] distance, String[] address){

        ArrayAdapter<ResultSet> adapter = new ArrayAdapter<ResultSet>(this, R.layout.results_text_view, ResultSet.getResults(names, distance, address));
        setListAdapter(adapter);

        //Toast.makeText(ResultsActivity.this, String.valueOf(disArray[2]),Toast.LENGTH_SHORT).show();

        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("http://maps.google.com/maps?daddr="+String.valueOf(locationLat[position])+","+String.valueOf(locationLon[position])));
                intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                startActivity(intent);

            }
        });
    }
//Yelp API
 //create an Async Task and get data.



    private class GetYelpData extends AsyncTask<Double, String, String>{

        String response_1="";
        String response_2="";


        String consumerKey = "42IsZffVx9KHCsMYauw75w";
        String consumerSecret = "NOqogfTZFyQYr8rnC0Ttc4wMvV4";
        String token = "dod6fXABio4IMoxuG-smBqPPPBXIRugl";
        String tokenSecret = "qQuJV7WlfM2mYDAnoCW1lDSz2qQ";

        @Override
        protected String doInBackground(Double... coordinates) {


            try {

                Yelp yelp = new Yelp(consumerKey, consumerSecret, token, tokenSecret);
                if(coordinates[2]==0.00) {
                    response_1 = yelp.search(sharedpreferences.getString("priority1", ""), coordinates[0], coordinates[1]); // get top priority item.
                    response_2 = yelp.search(sharedpreferences.getString("priority2", ""), coordinates[0], coordinates[1]);
                    return (response_1 + ",,," + coordinates[0] + ",,," + coordinates[1] + ",,," + response_2);
                }
                else if(coordinates[2]==1.00){
                        response_1 = yelp.search("Gas Station", coordinates[0], coordinates[1]);
                    return (response_1 + ",,," + coordinates[0] + ",,," + coordinates[1]) ;
                }
                else{
                    response_1 = yelp.search("Repair shop", coordinates[0], coordinates[1]);
                    return (response_1 + ",,," + coordinates[0] + ",,," + coordinates[1]);
                }
            }catch (Exception e){
                 //Toast.makeText(ResultsActivity.this, "Error in getting Data!",Toast.LENGTH_SHORT).show();

            }

            return null;
        }
        @Override
        protected void onPostExecute(String result) {
        //  Toast.makeText(ResultsActivity.this, String.valueOf(result.length()),Toast.LENGTH_SHORT).show();
           //Log.v(YELPDATATAG, result);

            JSONArray businesses;
            JSONArray businesses2;
            int counter;
            double clat;
            double clon;
            try {

                // Toast.makeText(ResultsActivity.this, String.valueOf(jsonData.length()),Toast.LENGTH_SHORT).show();



                if (type.equals("restaurants")) {
                    String[] values = result.split(",,,");
                    String jsonData = values[0];
                    String jsonData_2 = values[3];
                    JSONObject json = new JSONObject(jsonData);
                    JSONObject json2 = new JSONObject(jsonData_2);
                    //Toast.makeText(ResultsActivity.this, String.valueOf(),Toast.LENGTH_SHORT).show();
                    businesses = json.getJSONArray("businesses");
                    businesses2 = json2.getJSONArray("businesses");
                    clat = Double.valueOf(values[1]);
                    clon = Double.valueOf(values[2]);
                    counter=0;


                    for (counter = 0; counter < 2; counter++) {

                        ratings[counter] = businesses.getJSONObject(counter).getString("rating");
                        //Toast.makeText(ResultsActivity.this, ratings, Toast.LENGTH_SHORT).show();
                        address[counter] = businesses.getJSONObject(counter).getJSONObject("location").getJSONArray("display_address").getString(0);
                        locationLat[counter] = businesses.getJSONObject(counter).getJSONObject("location").getJSONObject("coordinate").getString("latitude");
                        locationLon[counter] = businesses.getJSONObject(counter).getJSONObject("location").getJSONObject("coordinate").getString("longitude");

                        restaurantNames[counter] = businesses.getJSONObject(counter).getString("name");
                    }
                    //counter++;

                    ratings[counter] = businesses2.getJSONObject(0).getString("rating");
                    //Toast.makeText(ResultsActivity.this, ratings, Toast.LENGTH_SHORT).show();
                    address[counter] = businesses2.getJSONObject(0).getJSONObject("location").getJSONArray("display_address").getString(0);
                    locationLat[counter] = businesses2.getJSONObject(0).getJSONObject("location").getJSONObject("coordinate").getString("latitude");
                    locationLon[counter] = businesses2.getJSONObject(0).getJSONObject("location").getJSONObject("coordinate").getString("longitude");

                    restaurantNames[counter] = businesses2.getJSONObject(0).getString("name");


                    try {
                        initilizeMap(locationLat, locationLon, clat, clon, restaurantNames);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    //Toast.makeText(ResultsActivity.this, String.valueOf(ratings.length), Toast.LENGTH_SHORT).show();
                    //Toast.makeText(ResultsActivity.this, address[2], Toast.LENGTH_SHORT).show();
                    createList(restaurantNames, disArray, address);
                }



                else {


                    String[] values = result.split(",,,");
                    String jsonData = values[0];

                    JSONObject json = new JSONObject(jsonData);

                    //Toast.makeText(ResultsActivity.this, String.valueOf(),Toast.LENGTH_SHORT).show();
                    businesses = json.getJSONArray("businesses");

                    clat = Double.valueOf(values[1]);
                    clon = Double.valueOf(values[2]);

                    for (counter = 0; counter < 3; counter++) {

                        ratings[counter] = businesses.getJSONObject(counter).getString("rating");
                        //Toast.makeText(ResultsActivity.this, ratings, Toast.LENGTH_SHORT).show();
                        address[counter] = businesses.getJSONObject(counter).getJSONObject("location").getJSONArray("display_address").getString(0);
                        locationLat[counter] = businesses.getJSONObject(counter).getJSONObject("location").getJSONObject("coordinate").getString("latitude");
                        locationLon[counter] = businesses.getJSONObject(counter).getJSONObject("location").getJSONObject("coordinate").getString("longitude");

                        restaurantNames[counter] = businesses.getJSONObject(counter).getString("name");
                    }
                    //counter++;


                    try {
                        initilizeMap(locationLat, locationLon, clat, clon, restaurantNames);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    //Toast.makeText(ResultsActivity.this, String.valueOf(ratings.length), Toast.LENGTH_SHORT).show();
                    //Toast.makeText(ResultsActivity.this, address[2], Toast.LENGTH_SHORT).show();
                    createList(restaurantNames, disArray, address);

                }
            }


            catch (Exception e){
                e.printStackTrace();
            }






        }
    }






    private void initilizeMap(String[] latitude, String[] longitude, double clat, double clon, String[] names) {
        if (googleMap == null) {
            googleMap = ((MapFragment) getFragmentManager().findFragmentById(
                    R.id.map)).getMap();

            // check if map is created successfully or not
            if (googleMap == null) {
                Toast.makeText(getApplicationContext(),
                        "Sorry! unable to create maps", Toast.LENGTH_SHORT)
                        .show();
            }
            else{
                googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);



                // latitude and longitude
               // double[] latitude = {38.8969280,38.897892,38.893288};
               // double[] longitude = {-77.0514120,-77.046369,-77.041128};



                for (int i=0;i<latitude.length;i++) {
// create marker
                    double lat = Double.valueOf(latitude[i]);
                    double lon = Double.valueOf(longitude[i]);


                    MarkerOptions marker = new MarkerOptions().position(new LatLng(lat, lon)).title(names[i]);
// adding marker

                    googleMap.addMarker(marker);


                    float[] shortestDistance = new float[2];
                    Location.distanceBetween(clat,clon,lat,lon, shortestDistance);
                    try{
                    disArray[i] = shortestDistance[0]/1609.34f;
                    }
                    catch (Exception e){
                        disArray[i] = 0;
                    }
                }

                MarkerOptions markerCurrent = new MarkerOptions().position(new LatLng(clat,clon)).title("Current Location");
                markerCurrent.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                googleMap.addMarker(markerCurrent);

                CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(clat,clon)).zoom(12).build();
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

                //Toast.makeText(ResultsActivity.this, String.valueOf(disArray[0]),Toast.LENGTH_SHORT).show();


            }

        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.results, menu);
        return true;
    }
@Override
    protected void onResume() {
        super.onResume();
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,this);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onLocationChanged(Location location) {

        //Toast.makeText(this,String.valueOf(clongitude),Toast.LENGTH_LONG).show();
        //Add current location marker

        //intialize map.


        //Checking Json data.
        GetYelpData gy = new GetYelpData();
        if(type.equals("restaurants")) {
            gy.execute((Double) location.getLatitude(), (Double) location.getLongitude(), 0.00); // for restaurants
        }
        else if(type.equals("Gas Station")){
            gy.execute((Double) location.getLatitude(), (Double) location.getLongitude(), 1.00); // for gas
        }
        else{
            gy.execute((Double) location.getLatitude(), (Double) location.getLongitude(), 2.00); // for repair

        }



        //Toast.makeText(ResultsActivity.this, "in Location change", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {
        Log.d("Latitude", "status");
    }

    @Override
    public void onProviderEnabled(String s) {
        Log.d("Latitude", "enabled");
    }

    @Override
    public void onProviderDisabled(String s) {
        Log.d("Latitude", "disable");
    }
}

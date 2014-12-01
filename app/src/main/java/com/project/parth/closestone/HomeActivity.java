package com.project.parth.closestone;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Map;


public class HomeActivity extends Activity {

    private Button restaurantSearch;
    private Button gasStationSearch;
    private Button repairSearch;
    public static final String MyPREFERENCES = "myPreferences";

    private SharedPreferences sharedpreferences;

    private DrawerLayout drawerLayout;
    private ListView drawerList;
    private String[] planets;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        planets = getResources().getStringArray(R.array.planets);
        restaurantSearch = (Button)findViewById(R.id.button_restaurant);
        gasStationSearch = (Button)findViewById(R.id.button_gasstation);
        repairSearch = (Button)findViewById(R.id.button_repairs);
        drawerLayout = (DrawerLayout)findViewById(R.id.drawerLayout);
        drawerList = (ListView)findViewById(R.id.drawerList);


        drawerList.setAdapter(new ArrayAdapter<>(HomeActivity.this, android.R.layout.simple_list_item_1, planets));

        sharedpreferences = getSharedPreferences(HomeActivity.MyPREFERENCES, Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedpreferences.edit();
        final Map<String,?> initialSizeCheckMap = sharedpreferences.getAll();


        //editor.clear().commit();

        if(!(initialSizeCheckMap.size()>0)){

            editor.clear().commit();
            editor.putString("priority1", "burritos");
            editor.putString("priority2", "sandwiches");
            editor.putString("priority3", "pizza");
            editor.commit();

        }

        restaurantSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent results = new Intent(HomeActivity.this,ResultsActivity.class);
                results.putExtra("type","restaurants");
                startActivity(results);
            }
        });

        gasStationSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent results = new Intent(HomeActivity.this,ResultsActivity.class);
                results.putExtra("type","Gas Station");
                startActivity(results);
            }
        });

        repairSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent results = new Intent(HomeActivity.this,ResultsActivity.class);
                results.putExtra("type","Repair shop");
                startActivity(results);
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.app_settings) {
            Toast.makeText(HomeActivity.this, "Under development",Toast.LENGTH_LONG).show();
        }
        else if(id == R.id.favorites){
            Intent intent = new Intent(this, SetPreferences.class);
            startActivity(intent);
        }
        else if(id == R.id.about_us){
            Toast.makeText(HomeActivity.this, "Under development",Toast.LENGTH_LONG).show();

        }
        else if(id == R.id.dislike){
            Intent intent = new Intent(this, DislikedPlaces.class);
            startActivity(intent);

        }
        else if(id==R.id.share){
            Intent intent_share = new Intent(this, ShareActivity.class);
            startActivity(intent_share);
        }
        return super.onOptionsItemSelected(item);
    }
}

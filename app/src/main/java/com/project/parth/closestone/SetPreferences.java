package com.project.parth.closestone;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class SetPreferences extends Activity {


    private Button mButton;
    private TextView mTextView;

    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_set_preferences);

        sharedpreferences = getSharedPreferences(HomeActivity.MyPREFERENCES, Context.MODE_PRIVATE);
       final SharedPreferences.Editor editor = sharedpreferences.edit();
        //final Map<String,?> initialSizeCheckMap = sharedpreferences.getAll();






        mButton = (Button)findViewById(R.id.add);
        mTextView = (TextView)findViewById(R.id.new_item);

         mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String s = mTextView.getText().toString();
                if(s.equals("")){
                    Toast.makeText(SetPreferences.this, "Write a food name to add!",Toast.LENGTH_LONG).show();
                    return;
                }
                final Map<String,?> insertNewValueMap = sharedpreferences.getAll();

                editor.putString("priority"+String.valueOf(insertNewValueMap.size()+1),s);
                 editor.commit();//adding new value to the shared preference.
                Bundle tempBundle = new Bundle();
                onCreate(tempBundle);
            }
        });

        /*if(Cheeses.sCheeseStrings.size()>0){
            Cheeses.removeList();
        }*/

        //Cheeses.populateList();

        final Map<String,?> iterateMap = sharedpreferences.getAll();

        //populating list
        ArrayList<String> mCheeseList = new ArrayList<String>();
        for(int i=1;i<=iterateMap.size();i++){
            mCheeseList.add(sharedpreferences.getString("priority"+String.valueOf(i),""));
        }

        final StableArrayAdapter adapter = new StableArrayAdapter(this, R.layout.text_view, mCheeseList);
        DynamicListView listView = (DynamicListView) findViewById(R.id.listview);

        listView.setCheeseList(mCheeseList);
        listView.setAdapter(adapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.set_preferences, menu);
        return true;
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
}

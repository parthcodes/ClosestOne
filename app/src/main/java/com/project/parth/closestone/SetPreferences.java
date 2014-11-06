package com.project.parth.closestone;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;


public class SetPreferences extends Activity {


    private Button mButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_preferences);

        mButton = (Button)findViewById(R.id.add);

        ArrayList<String> mCheeseList = new ArrayList<String>();
        for (int i = 0; i < Cheeses.sCheeseStrings.length; ++i) {
            mCheeseList.add(Cheeses.sCheeseStrings[i]);
        }

        final StableArrayAdapter adapter = new StableArrayAdapter(this, R.layout.text_view, mCheeseList);
        DynamicListView listView = (DynamicListView) findViewById(R.id.listview);

        listView.setCheeseList(mCheeseList);
        listView.setAdapter(adapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cheeses.sCheeseStrings[Cheeses.sCheeseStrings.length - 1] = "sample";
                Bundle tempBundle = new Bundle();
                onCreate(tempBundle);

            }
        });
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

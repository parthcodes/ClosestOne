package com.project.parth.closestone;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static android.R.layout.simple_list_item_1;

public class DislikedPlaces extends ListActivity {

    private Button mButton;
    private EditText mEditText;
    List<String> value = new ArrayList<String>();
    SharedPreferences sharedpref;
    SharedPreferences mSharedPreferences;
    public static final String DISLIKEPREFERENCES="dislikepreferences";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_disliked_places);


        sharedpref = getSharedPreferences(HomeActivity.MyPREFERENCES, Context.MODE_PRIVATE);

        mSharedPreferences = getSharedPreferences(DislikedPlaces.DISLIKEPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mSharedPreferences.edit();
       // editor.clear().commit();

        final Map<String,?> initialSizeCheckMap = mSharedPreferences.getAll();

        if(!(initialSizeCheckMap.size()>0)){

            editor.putString("dislike1", "Thai food");
            editor.putString("dislike2", "Sea food");
            editor.commit();
            loadList();

        }
        else{
            loadList();
        }


        mButton = (Button)findViewById(R.id.add_dislike);
        mEditText = (EditText) findViewById(R.id.new_item_dislike);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Map<String,?> checkMap = sharedpref.getAll();
                    if(!(mEditText.getText().toString().equals(""))) {
                    if (!(checkMap.containsKey(mEditText.getText().toString()))) {

                        addItem(mEditText.getText().toString());
                    }
                        else{
                        Toast.makeText(DislikedPlaces.this, "Please enter some type of food!", Toast.LENGTH_LONG).show();
                    }
                }
                else
                    {
                        Toast.makeText(DislikedPlaces.this, "You can not add it, as it is in favorites.", Toast.LENGTH_LONG).show();

                    }

            }
        });
    }

    public void addItem(String item){
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        final Map<String,?> addMap = mSharedPreferences.getAll();
        editor.putString("dislike"+String.valueOf(addMap.size()+1),item);
        editor.commit();
        loadList();
    }

    public void loadList(){
        value.clear();
        final Map<String,?> traverseMap = mSharedPreferences.getAll();

        for(int i=1;i<=traverseMap.size();i++){
            value.add(mSharedPreferences.getString("dislike"+String.valueOf(i),""));
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(DislikedPlaces.this, simple_list_item_1, value);
        setListAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.disliked_places, menu);
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

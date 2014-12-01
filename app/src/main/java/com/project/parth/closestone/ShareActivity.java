package com.project.parth.closestone;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.File;


public class ShareActivity extends Activity {

    private static final int CAPTURE_IMAGE_REQUEST_CODE = 448; //this can be any int
    private static final int GALLARY_REQUEST_CODE = 440; //this can be any int

    private Uri mFileUri=null;
   // private  Uri gallaryUri=null;
    private ImageView mImageView;
    private Button mButton;
    private Button shareButton;
    private EditText review;
    private Button gallaryButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);

        mImageView = (ImageView) findViewById(R.id.imageView3);
        mButton = (Button)findViewById(R.id.image_button);
        shareButton = (Button)findViewById(R.id.share_button);
        review = (EditText)findViewById(R.id.review);
        gallaryButton = (Button)findViewById(R.id.open_gallary_button);


        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takePicture(view);
            }
        });

        shareButton.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                share(view);
            }
        }));

        gallaryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                i.setType("image/*");
               // intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"xyz@gmail.com"});
                //i.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(i, GALLARY_REQUEST_CODE);
            }
        });


    }

    public void share(View v){
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);

        shareIntent.putExtra(Intent.EXTRA_STREAM,mFileUri);
        shareIntent.putExtra(Intent.EXTRA_TEXT, review.getText() );
        shareIntent.setType("image/*");
        startActivity(shareIntent);
    }


    public void takePicture(View v){
        //create folder to store image
        File imagesFolder = new File(Environment.getExternalStorageDirectory(),"Camera");
        imagesFolder.mkdirs();

        //create image file reference
        File imageFile = new File(imagesFolder,"image1.jpg");

        mFileUri = Uri.fromFile(imageFile);
        Log.d("cameraActivity", mFileUri.getPath());

        //create intent and launch camera
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT,mFileUri);

        startActivityForResult(intent,CAPTURE_IMAGE_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode, Intent data){
        if(requestCode == CAPTURE_IMAGE_REQUEST_CODE && resultCode == RESULT_OK){

           Bitmap bitmap = BitmapFactory.decodeFile(mFileUri.getPath());

           mImageView.setImageBitmap(bitmap);

        }
        else if(requestCode == GALLARY_REQUEST_CODE && resultCode == RESULT_OK){

            mFileUri = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
            Cursor cursor = getContentResolver().query(mFileUri,filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();


            Bitmap bitmap = BitmapFactory.decodeFile(picturePath);

            mImageView.setImageBitmap(bitmap);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.share, menu);
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

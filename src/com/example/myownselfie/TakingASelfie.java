package com.example.myownselfie;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class TakingASelfie extends Activity {
	private Intent takePictureIntent;
	static final int REQUEST_IMAGE_CAPTURE = 1;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.camera_layout);
		
		dispatchTakePictureIntent();
	//	public void dispatchTakePictureIntent() {
	//	//private void dispatchTakePictureIntent() {
	//	    takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
	//	    if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
	//	        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
	//	    }
	//	}
	//	
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void dispatchTakePictureIntent() {
	//private void dispatchTakePictureIntent() {
	    takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
	    if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
	        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
	    }
	}
	//@Override
	//public boolean onOptionsItemSelected(MenuItem item) {
	//	// Handle action bar item clicks here. The action bar will
	//	// automatically handle clicks on the Home/Up button, so long
	//	// as you specify a parent activity in AndroidManifest.xml.
	//	int id = item.getItemId();
	//	if (id == R.id.action_settings) {
	//		return true;
	//	}
	//	return super.onOptionsItemSelected(item);
	//}
}
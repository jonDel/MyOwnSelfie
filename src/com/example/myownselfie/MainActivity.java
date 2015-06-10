package com.example.myownselfie;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.view.ViewGroup;
import android.support.v7.app.ActionBarActivity;
import android.app.AlarmManager;
import android.app.ListActivity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class MainActivity extends ListActivity {
	private AlarmManager mAlarmManager;
	private Intent mNotificationReceiverIntent;
	private PendingIntent mNotificationReceiverPendingIntent;
	private static final long INITIAL_ALARM_DELAY = 2 * 60 * 1000L;
	private static final long INTERVAL_TWO_MINUTES = 2 * 60 * 1000;
	protected static final long JITTER = 5000L;
	private Intent takePictureIntent;
	static final int REQUEST_IMAGE_CAPTURE = 1;
	static final int REQUEST_TAKE_PHOTO = 1;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Calendar calendar = Calendar.getInstance();
		// Get the AlarmManager Service
		mAlarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

		// Create an Intent to broadcast to the AlarmNotificationReceiver
		mNotificationReceiverIntent = new Intent(MainActivity.this,
				AlarmNotificationReceiver.class);

		// Create an PendingIntent that holds the NotificationReceiverIntent
		mNotificationReceiverPendingIntent = PendingIntent.getBroadcast(
				MainActivity.this, 0, mNotificationReceiverIntent, 0);

		// Set inexact repeating alarm
		mAlarmManager.setInexactRepeating(
				AlarmManager.ELAPSED_REALTIME,
				SystemClock.elapsedRealtime(),
				//0 + INITIAL_ALARM_DELAY,
				//SystemClock.elapsedRealtime() + INITIAL_ALARM_DELAY,
				INTERVAL_TWO_MINUTES,
				mNotificationReceiverPendingIntent);
		String st1[]=new String[]{"x", "y", "z", "sdf", "sdfsd", "sdfsd"};
		String st2[]=new String[]{"0", "1", "2", "3", "4", "5"};
		String st[];
		Bundle extras = getIntent().getExtras();
		//st = st1;
		if (extras == null) {
			st = st1;
		} else {
			st = st2;
			st[0] = extras.getString("SendCode");
			dispatchTakePictureIntent();
		}
		
		
		// Create a new Adapter containing a list of colors
		// Set the adapter on this ListActivity's built-in ListView
		
		
		//setListAdapter(new ArrayAdapter<String>(this, R.layout.list_item,
		//		st));
		
		
		ArrayList<Bitmap> pics = new ArrayList<Bitmap>();
	    String DIRECTORY_PATH = "/sdcard/Directory/";
	    File file = new File(DIRECTORY_PATH);
	    String[] s = file.list();       

	    for(int i = 0; i<s.length; i++){
	        Bitmap b = BitmapFactory.decodeFile(DIRECTORY_PATH + s[i]);
	        pics.add(b);
	    }

		
		
		setListAdapter(new ArrayAdapter<Bitmap>(this, R.layout.imageview_item,
				pics));
		
		setListAdapter(new ArrayAdapter<Bitmap>(this, R.layout.imageview_item, pics) {
	        @Override
	        public View getView(int position, View convertView, ViewGroup parent) {
	        View row;
	        LayoutInflater mInflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);

	        if (null == convertView) {
	        	row = mInflater.inflate(R.layout.imageview_item, null);
	        } else {
	        	row = convertView;
	        }

	        ImageView iv = (ImageView) row.findViewById(R.id.imageview_item);
	        iv.setImageBitmap(getItem(position));
	        return row;
	        }
	       });
		
		
		

		ListView lv = getListView();

		// Enable filtering when the user types in the virtual keyboard
		lv.setTextFilterEnabled(true);

		// Set an setOnItemClickListener on the ListView
		lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				if (position == 0) {
					
				}
				// Display a Toast message indicting the selected item
				Toast.makeText(getApplicationContext(),
						((TextView) view).getText(), Toast.LENGTH_SHORT).show();
				Toast.makeText(getApplicationContext(),
						Integer.toString(position), Toast.LENGTH_SHORT).show();
			}
		});
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
		// Ensure that there's a camera activity to handle the intent
		if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
			// Create the File where the photo should go
			File photoFile = null;
			try {
				photoFile = createImageFile();
			} catch (IOException ex) {
				// Error occurred while creating the File
				photoFile = null;
			}
			// Continue only if the File was successfully created
			if (photoFile != null) {
				takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
				Uri.fromFile(photoFile));
				startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
			}
		}

	}

	private File createImageFile() throws IOException {
		// Create an image file name
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		String imageFileName = "JPEG_" + timeStamp + "_";
		File storageDir = Environment.getExternalStoragePublicDirectory(
			Environment.DIRECTORY_PICTURES);
		File image = File.createTempFile(
			imageFileName,  /* prefix */
			".jpg",         /* suffix */
			storageDir      /* directory */
		);

	    // Save a file: path for use with ACTION_VIEW intents
	  //  mCurrentPhotoPath = "file:" + image.getAbsolutePath();
	    return image;
	}
	
	private void setPic(ImageView mImageView, String photoPath) {
	    // Get the dimensions of the View
	    int targetW = mImageView.getWidth();
	    int targetH = mImageView.getHeight();

	    // Get the dimensions of the bitmap
	    BitmapFactory.Options bmOptions = new BitmapFactory.Options();
	    bmOptions.inJustDecodeBounds = true;
	    BitmapFactory.decodeFile(photoPath, bmOptions);
	    int photoW = bmOptions.outWidth;
	    int photoH = bmOptions.outHeight;

	    // Determine how much to scale down the image
	    int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

	    // Decode the image file into a Bitmap sized to fill the View
	    bmOptions.inJustDecodeBounds = false;
	    bmOptions.inSampleSize = scaleFactor;
	    bmOptions.inPurgeable = true;

	    Bitmap bitmap = BitmapFactory.decodeFile(photoPath, bmOptions);
	    mImageView.setImageBitmap(bitmap);
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
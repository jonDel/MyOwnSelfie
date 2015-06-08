package com.example.myownselfie;

import java.util.Calendar;

import android.support.v7.app.ActionBarActivity;
import android.app.AlarmManager;
import android.app.ListActivity;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
		mAlarmManager.setRepeating(
				AlarmManager.ELAPSED_REALTIME,
				//SystemClock.elapsedRealtime() + INITIAL_ALARM_DELAY,
				//0 + INITIAL_ALARM_DELAY,
				calendar.getTimeInMillis()+1000,
				INTERVAL_TWO_MINUTES,
				mNotificationReceiverPendingIntent);

		String st3[]=new String[]{"x", "y", "z", "sdf", "sdfsd", "sdfsd"};
		
		// Create a new Adapter containing a list of colors
		// Set the adapter on this ListActivity's built-in ListView
		
		
		setListAdapter(new ArrayAdapter<String>(this, R.layout.list_item,
				st3));

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
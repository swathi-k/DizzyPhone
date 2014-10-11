package cs175.skotturu.dizphone;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class SettingsActivity extends Activity {
	Button returnButton;
	SeekBar seek;
	TextView playerName;
	final Context context = this;
	
	@Override 
	public void onCreate(Bundle savedInstanceState) { 
		super.onCreate(savedInstanceState); 
		setContentView(R.layout.settings);
		setPlayerName();
		addReturnButtonListener();
		addSeekListener();
		
		seek.setProgress(getSeekProgress());
		} 
	
	private int getSeekProgress() {
		SharedPreferences sharedPref = getSharedPreferences("MyPREFERENCES", Context.MODE_PRIVATE);
		float defaultValue = sharedPref.getFloat(getString(R.string.SettingsSeekBarMidWay), (float) 1.0);
		int val = (int)(defaultValue*10);
		if(val >= 1)
			val--;
		return val;
	}

	private void setPlayerName() {
		TextView current = (TextView) findViewById(R.id.edit_player_name);
		SharedPreferences sharedPref = getSharedPreferences("MyPREFERENCES", Context.MODE_PRIVATE);
		String name = sharedPref.getString(getString(R.string.Player1), "Player 1");
		current.setText(name);
	}

	private void addReturnButtonListener() {

		
		returnButton = (Button) findViewById(R.id.return_button);
		playerName = (TextView) findViewById(R.id.edit_player_name);
		
		returnButton.setOnClickListener(new OnClickListener() {

			//@Override
			public void onClick(View arg0) {
				AlertDialog.Builder builder = new AlertDialog.Builder(context);
				// Add the buttons
				builder.setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
				           public void onClick(DialogInterface dialog, int id) {
				               	// User clicked OK button
				        	   	//save username and gamespeed

				        	   	SharedPreferences sharedPref = getSharedPreferences("MyPREFERENCES", Context.MODE_PRIVATE);
				        	   	String pName = playerName.getText() + "";
				        	   	float speed = (float) ((seek.getProgress() + 1.0)/10.0) ;
				        	   	SharedPreferences.Editor editor = sharedPref.edit();
				        	   	editor.putString(getString(R.string.Player1), pName);
				        	   	editor.putFloat(getString(R.string.SettingsSeekBarMidWay), speed);
				        	   	editor.commit();
				        	   	dialog.cancel();
				        	   	
				   				Intent intent = new Intent(context, MainActivity.class);
				   				startActivity(intent);
				   				finish();
				           }
				       });
				builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
				           public void onClick(DialogInterface dialog, int id) {
				        	   	// if this button is clicked, just close
				        	   	// the dialog box and do nothing
				        	   	dialog.cancel();
				   				Intent intent = new Intent(context, MainActivity.class);
				   				startActivity(intent);
				   				finish();
				           }
				       });
				// Set other dialog properties
				builder.setMessage(R.string.savesettings)
			       .setTitle(R.string.saveq);

				// Create the AlertDialog
				AlertDialog dialog = builder.create();
				dialog.show();
			}

		});

		
	}

	public void addSeekListener() { 
		
		seek = (SeekBar) findViewById(R.id.game_speed_slider);
		TextView current = (TextView) findViewById(R.id.SeekBarMidWay);
		SharedPreferences sharedPref = getSharedPreferences("MyPREFERENCES", Context.MODE_PRIVATE);
		float defaultValue = sharedPref.getFloat(getString(R.string.SettingsSeekBarMidWay), (float) 1.0);
		current.setText(defaultValue + " secs");
		seek.setProgress(9);
		seek.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onProgressChanged(SeekBar arg0, int progress, boolean fromUser) {
				TextView current = (TextView) findViewById(R.id.SeekBarMidWay);

				double currentvalue = (progress + 1)/10.0;
				current.setText(currentvalue + "");
				
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}

			
		});

		} 
	
}
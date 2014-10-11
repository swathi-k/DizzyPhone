package cs175.skotturu.dizphone;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.view.Display;
import android.view.Surface;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class StartGameActivity extends Activity {
	SharedPreferences sharedPref;
	Display displ;
	final Context context = this;
	private Handler h = new Handler();
	Button imthere;
	private Runnable run = new Runnable(){
	    public void run(){
	        //do something
	    	guess();
	        
	    }
	};
	
	@Override 
	public void onCreate(Bundle savedInstanceState) { 
		super.onCreate(savedInstanceState); 
		displ = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
		int rotation = displ.getRotation();
		sharedPref = sharedPref = getSharedPreferences("MyPREFERENCES", Context.MODE_PRIVATE);
		
		if(rotation == Surface.ROTATION_0 || rotation == Surface.ROTATION_180)
			setContentView(R.layout.start_game_portrait);
		else
			setContentView(R.layout.start_game_landscape);
		start();
		
		//h.postDelayed(run, getGameSpeed());
	
	}
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
	    super.onConfigurationChanged(newConfig);
		int rotation = displ.getRotation();

		if(rotation == Surface.ROTATION_0 || rotation == Surface.ROTATION_180)
			setContentView(R.layout.start_game_portrait);
		else
			setContentView(R.layout.start_game_landscape);
		
		display();
		
	}

	public void addListenerOnButton() {

		imthere = (Button) findViewById(R.id.ImThere);

		imthere.setOnClickListener(new OnClickListener() {

			//@Override
			public void onClick(View arg0) {
				guess();
				h.postDelayed(run, getGameSpeed());
			}
		});
	}
	
	private void start() {
		reset();
		
		display();
		changeGameLabel();
		h.postDelayed(run, getGameSpeed());

	}
	
	private void guess() {
		checkcorrect(getOrientationGuess());
		if(keepgoing())
		{
			display();
			changeGameLabel();
//			h.postDelayed(run, getGameSpeed()*1000);
		}
		else
		{
			savescores();
			//start intent GameOver
			Intent intent = new Intent(context, GameOverActivity.class);
			startActivity(intent);
			finish();
		}
	}

	private void display() {
		//display HighScore
		TextView highScore = (TextView) findViewById(R.id.HighScore);
		highScore.setText(getString(R.string.HighScore) + getHighScore());
		
		//display CurrentScore
		TextView currentScore = (TextView) findViewById(R.id.CurrentScore);
		currentScore.setText(getString(R.string.CurrentScore) + getCurrentScore());
		
		//display lives left
		TextView lives = (TextView) findViewById(R.id.LivesLeft);
		lives.setText(getString(R.string.LivesLeft) + getLives());
		
		//display game label
		TextView game = (TextView) findViewById(R.id.GameLabel);
   		game.setText(getGameLabel());
	}
	
	private void reset() {
		setCurrentScore(0);
		setLives(3);
	}
	
	private boolean getOrientationGuess() {
		//@TODO change "Portrait" to enum
		if(getGameLabel().equals("Portrait"))
			return true;
		else
			return false;
	}

	private void checkcorrect(boolean portrait) {
		int rotation = displ.getRotation();
		if(rotation == Surface.ROTATION_0 || rotation == Surface.ROTATION_180)
		{
			if(portrait)
				incCurrentScore();
			else
				decLives();
		}
		else
		{
			if(!portrait)
				incCurrentScore();
			else
				decLives();
		}
	}
	
	private boolean keepgoing() {
		if(getLives() < 1)
			return false;
		else
			return true;
	}
	
	private void savescores() {
		int current = getCurrentScore();
		int high = getHighScore();
		
		if(current > high)
    	   	setHighScore(current);
	}
	
	private int getHighScore() {
		return sharedPref.getInt(getString(R.string.HighScore), 0);
	}
	
	private void setHighScore(int score) {
		SharedPreferences.Editor editor = sharedPref.edit();
	   	editor.putInt(getString(R.string.HighScore), score);
   		editor.commit();
	}
	
	private void incCurrentScore() {
		int current = getCurrentScore();
		current++;
		setCurrentScore(current);
	}


	private int getCurrentScore() {
		return sharedPref.getInt(getString(R.string.CurrentScore), 0);
	}
	
	private void setCurrentScore(int score) {
		SharedPreferences.Editor editor = sharedPref.edit();
	   	editor.putInt(getString(R.string.CurrentScore), score);
   		editor.commit();
	}
	
	private void decLives() {
		int live = getLives();
		live--;
		setLives(live);
	}


	private int getLives() {
		return sharedPref.getInt(getString(R.string.LivesLeft), 3);
	}
	
	private void setLives(int live) {
		SharedPreferences.Editor editor = sharedPref.edit();
	   	editor.putInt(getString(R.string.LivesLeft), live);
   		editor.commit();
	}
	
	private long getGameSpeed() {
		return (long) sharedPref.getFloat(getString(R.string.SettingsSeekBarMidWay), (float) 1.0) * 1000;
	}
	
	private String getGameLabel() {
		return sharedPref.getString(getString(R.string.GameLabel1), "Portrait");
	}
	
	private void changeGameLabel() {
		if( Math.random() < 0.5)
			setGameLabel("Portrait");
		else
			setGameLabel("Landscape");
	}
	
	private void setGameLabel(String orien) {
		SharedPreferences.Editor editor = sharedPref.edit();
	   	editor.putString(getString(R.string.GameLabel1), orien);
   		editor.commit();
   		TextView game = (TextView) findViewById(R.id.GameLabel);
   		game.setText(orien);
	}
}

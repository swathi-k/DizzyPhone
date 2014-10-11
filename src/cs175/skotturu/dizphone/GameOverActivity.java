package cs175.skotturu.dizphone;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class GameOverActivity extends Activity {

	@Override 
	public void onCreate(Bundle savedInstanceState) { 
		super.onCreate(savedInstanceState); 
		setContentView(R.layout.game_over);
		final Context context = this;
		
		Handler handler = new Handler(); //used to set up a delayed callback

		//Runnable object called after delay via Handler
		Runnable timeUp = new Runnable() {
			public void run() {
				//do after times up
				Intent intent = new Intent(context, MainActivity.class);
				startActivity(intent);
				finish();
			}
		};
		
		handler.postDelayed(timeUp, 10*1000);
	}
}

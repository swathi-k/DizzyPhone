package cs175.skotturu.dizphone;

import android.support.v7.app.ActionBarActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


public class MainActivity extends ActionBarActivity {
	Button startgbutton;
    Button settingsbutton;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addListenerOnStartGameButton();
        addListenerOnSettingsButton();
    }
    
	public void addListenerOnStartGameButton() {

		final Context context = this;

		startgbutton = (Button) findViewById(R.id.start_game_button);

		startgbutton.setOnClickListener(new OnClickListener() {

			//@Override
			public void onClick(View arg0) {

				Intent intent = new Intent(context, StartGameActivity.class);
				startActivity(intent);
				finish();
			}

		});

	}
	
	public void addListenerOnSettingsButton() {

		final Context context = this;

		settingsbutton = (Button) findViewById(R.id.settings_button);

		settingsbutton.setOnClickListener(new OnClickListener() {

			//@Override
			public void onClick(View arg0) {

				Intent intent = new Intent(context, SettingsActivity.class);
				startActivity(intent);
				finish();
			}

		});

	}



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

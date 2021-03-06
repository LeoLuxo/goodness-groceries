package lu.uni.bicslab.greenbot.android.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;


import com.android.volley.NoConnectionError;
import com.android.volley.TimeoutError;

import lu.uni.bicslab.greenbot.android.MainActivity;
import lu.uni.bicslab.greenbot.android.R;
import lu.uni.bicslab.greenbot.android.other.ServerConnection;
import lu.uni.bicslab.greenbot.android.other.UserData;
import lu.uni.bicslab.greenbot.android.other.Utils;
import lu.uni.bicslab.greenbot.android.ui.activity.welcome.SignInFragment;
import lu.uni.bicslab.greenbot.android.ui.activity.welcome.WelcomeActivity;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class StartActivity extends AppCompatActivity {
	
	private boolean refreshStatus = false;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// Always reset the app locale when it is restarted
		String lang = UserData.getLanguage(this);
		if (lang != null)
			Utils.setLocale(this, lang);
		
		// Redirect to correct activities or show correct layout based on the user status
		String userStatus = UserData.getStatus(this);
		
		if (userStatus.equals(UserData.USER_VALID)) {
			if (UserData.isFirstTimeVisit(getApplicationContext())) {
				// Show first-time screen 1 or 2
				
				int firstTimePage = getIntent().getIntExtra("first_time_page", 1);
				
				if (firstTimePage == 1) {
					setContentView(R.layout.activity_firsttime1);
					
					Button continue_button = findViewById(R.id.button_continue);
					continue_button.setOnClickListener(v -> {
						Intent i = new Intent(this, StartActivity.class);
						i.putExtra("first_time_page", 2);
						startActivity(i);
					});
					
				} else if (firstTimePage == 2) {
					setContentView(R.layout.activity_firsttime2);
					
					Button continue_button = findViewById(R.id.button_continue);
					continue_button.setOnClickListener(v -> {
						UserData.setFirstTimeVisit(this, false);
						startActivity(new Intent(this, StartActivity.class));
					});
				}
				
			} else {
				// Go to main view
				
				Intent i = new Intent(this, MainActivity.class);
				i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
				startActivity(i);
			}
			
		} else if (userStatus.equals(UserData.USER_REQUESTED) || userStatus.equals(UserData.USER_ARCHIVED)) {
			// Show requested/survey screen
			setContentView(R.layout.activity_waitingpage_layout);
			refreshStatus = true;
			
		} else {
			// User invalid, show welcome+login screen
			startActivity(new Intent(StartActivity.this, WelcomeActivity.class));
			finish();
		}
		
		
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
		if (refreshStatus) {
			ServerConnection.fetchUserStatus(this, UserData.getID(this), status -> {
				UserData.setStatus(this, status);
				
				// Restart/redirect to the right screen if user isn't requested anymore
				if (!(status.equals(UserData.USER_REQUESTED) || status.equals(UserData.USER_ARCHIVED))) {
					startActivity(new Intent(this, StartActivity.class));
					finish();
				}
			}, error -> {
				if (error instanceof TimeoutError || error instanceof NoConnectionError) {
					Toast.makeText(this, R.string.network_error, Toast.LENGTH_SHORT).show();
				}
			});
		}
	}
}

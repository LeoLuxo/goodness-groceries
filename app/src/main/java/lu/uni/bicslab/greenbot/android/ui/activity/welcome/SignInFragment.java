package lu.uni.bicslab.greenbot.android.ui.activity.welcome;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import lu.uni.bicslab.greenbot.android.R;

public class SignInFragment extends Fragment {
	
	public static final int RC_BARCODE_CAPTURE = 9001;
	
	private EditText signin_id;
	
	private String id;
	
	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return inflater.inflate(R.layout.onbording_signin, container);
	}
	
	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		signin_id = getView().findViewById(R.id.signin_id);
		signin_id.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// May not be good design as it keeps this Fragment and the WelcomeActivity tightly coupled, but it simplifies this horrible mess of a welcome screen a lot
				WelcomeActivity activity = ((WelcomeActivity) getActivity());
				
				if (s.length() != 13) {
					signin_id.setError("id must be 13 digits long");
					activity.setIDValid(false);
				} else {
					signin_id.setError(null);
					activity.setIDValid(true);
				}
				
				if (s.length() == 0) {
					signin_id.setError(null);
				}
				
				activity.id = s.toString();
			}
			
			@Override public void afterTextChanged(Editable s) {}
			@Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
		});
	}
	
//	@Override
//	public void onCreate(Bundle icicle) {
//		super.onCreate(icicle);
//		requestWindowFeature(Window.FEATURE_NO_TITLE);
////		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//		setContentView(R.layout.activity_siginin);
//		login = findViewById(R.id.tx_login);
//		signin_id = findViewById(R.id.signin_id);
//		jsonObject = new JsonObject();
//		
//		
//		login.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				try {
//					id = signin_id.getText().toString();
//					
//				} catch (Exception e) {
//					id = "";
//				}
//				if (id.isEmpty() || id.length() == 0) {
//					Intent intent = new Intent(SignInFragment.this, SigninSelectActivity.class);
//					startActivityForResult(intent, RC_BARCODE_CAPTURE);
//					
//				} else {
//					
////					Profile profile = new Profile();
////					profile.setSerialscanner(id);
////					profile.setLogedin(Utils.user_notloggedin);
////					Utils.saveProfile(getApplicationContext(), profile);
//					Intent intent = new Intent(SignInFragment.this, WelcomeActivity.class);
//					intent.putExtra("logintype", false);//
//					intent.putExtra("data", onJsonObjectSet(signin_id.getText().toString()).toString());//
//					// startActivityForResult(intent, RC_BARCODE_CAPTURE);
//					startActivity(intent);
//				}
//			}
//		});
//		
//	}
	
	
//	@Override
//	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//		Log.e("TAG", " irequestCode" + requestCode + " resultCode" + resultCode);
//		if (requestCode == RC_BARCODE_CAPTURE) {
//			if (data != null) {
//				String requiredValue = data.getStringExtra("barcode");
//				signin_id.setText("" + requiredValue);
//				Log.e("TAG", "Barcode read:final " + requiredValue);
//				
//			} else {
//				Log.e("TAG", "No barcode captured, intent data is null");
//			}
//			
//		} else {
//			super.onActivityResult(requestCode, resultCode, data);
//		}
//	}
//	
//	private JSONObject onJsonObjectSet(String participant_id) {
//		JSONObject object = new JSONObject();
//		try {
//			//input your API parameters
//			object.put("participant_id", participant_id);
//			object.put("product_category_1", "null");
//			object.put("product_category_2", "null");
//			object.put("product_category_3", "null");
//			object.put("product_category_4", "null");
//			object.put("indicator_category_1", "null");
//			object.put("indicator_category_2", "null");
//			object.put("indicator_category_3", "null");
//			object.put("indicator_category_4", "null");
//			
//		} catch (JSONException e) {
//			e.printStackTrace();
//		}
//		return object;
//	}
}

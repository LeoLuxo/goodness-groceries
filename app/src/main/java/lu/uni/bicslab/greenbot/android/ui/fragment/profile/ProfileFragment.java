package lu.uni.bicslab.greenbot.android.ui.fragment.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lu.uni.bicslab.greenbot.android.R;
import lu.uni.bicslab.greenbot.android.other.ServerConnection;
import lu.uni.bicslab.greenbot.android.other.UserData;
import lu.uni.bicslab.greenbot.android.ui.activity.feedback.FeedbackMainActivity;

public class ProfileFragment extends Fragment {
	
	private Button review_products;
	
	private List<String> productsToReview;
	
	private static final int FEEDBACK_RESULT_CODE = 1;
	
	public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View root = inflater.inflate(R.layout.fragment_profile, container, false);
		
		TextView profile_id = root.findViewById(R.id.profile_id);
		review_products = root.findViewById(R.id.review_products);
		
		String[] arg = getArguments().getStringArray("products_to_review");
		if (arg == null)
			arg = new String[]{};
		productsToReview = new ArrayList<>(Arrays.asList(arg));
		
		profile_id.setText(UserData.getID(getContext()));
		
		review_products.setOnClickListener(v -> {
			reviewNextProduct();
		});
		
		// TODO: Add badge with number of products to the review_products button
		
		return root;
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		if (requestCode == FEEDBACK_RESULT_CODE) {
			productsToReview.remove(0);
		}
		
		reviewNextProduct();
	}
	
	public void reviewNextProduct() {
		if (productsToReview.size() > 0) {
			Intent i = new Intent(getActivity(), FeedbackMainActivity.class);
			i.putExtra("product_id", productsToReview.get(0));
			startActivityForResult(i, FEEDBACK_RESULT_CODE);
		}
	}
	
	
	@Override
	public void onResume() {
		super.onResume();
		// Will be called after onActivityResult(), so productsToReview shouldn't be changed earlier than expected
		
		// Get list of bought products from the API
		ServerConnection.fetchProductsBought(getContext(), UserData.getID(getContext()), products -> {
			if (products == null)
				products = new String[]{};
			productsToReview = new ArrayList<>(Arrays.asList(products));
		}, error -> {});
		
		review_products.setEnabled(productsToReview.size() > 0);
	}
}

package lu.uni.bicslab.greenbot.android.ui.fragment.indicator_category;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import lu.uni.bicslab.greenbot.android.R;
import lu.uni.bicslab.greenbot.android.datamodel.IndicatorCategoryModel;

public class ItemAdapter extends RecyclerView.Adapter<ItemHolder> {
	
	private final List<IndicatorCategoryModel> celebrityList;
	
	public ItemAdapter(List<IndicatorCategoryModel> celebrityList) {
		this.celebrityList = celebrityList;
	}
	
	
	@Override
	public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View itemView = LayoutInflater.from(parent.getContext())
				.inflate(R.layout.indicator_category_item_layout, parent, false);
		
		return new ItemHolder(itemView);
	}
	
	@Override
	public void onBindViewHolder(ItemHolder holder, int position) {
		IndicatorCategoryModel item = celebrityList.get(position);
		holder.txtName.setText(item.getName());
		holder.txtDoc.setText(item.getName());
	}
	
	@Override
	public int getItemCount() {
		return celebrityList.size();
	}
}
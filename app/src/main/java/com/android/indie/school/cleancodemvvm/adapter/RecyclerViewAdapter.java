package com.android.indie.school.cleancodemvvm.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.indie.school.cleancodemvvm.R;
import com.android.indie.school.cleancodemvvm.activity.home.ItemViewModel;
import com.android.indie.school.cleancodemvvm.databinding.ItemHomeBinding;
import com.android.indie.school.cleancodemvvm.models.CityListData;
import com.android.indie.school.cleancodemvvm.models.CityListResponse;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

/**
 * Created by herisulistiyanto on 12/6/16.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.HomeViewHolder> {

    private List<CityListData> datas;
    private OnItemClickListener onItemClickListener;
    private Context context;

    public RecyclerViewAdapter(Context context, CityListResponse response, OnItemClickListener onItemClickListener) {
        this.datas = response.getData();
        this.onItemClickListener = onItemClickListener;
        this.context = context;
    }

    @Override
    public HomeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemHomeBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_home, parent, false);

        return new HomeViewHolder(binding, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(HomeViewHolder holder, int position) {
        holder.binding.setViewModel(new ItemViewModel(datas.get(position)));
        holder.binding.city.setText(datas.get(position).getName());
        holder.binding.hotel.setText(datas.get(position).getDescription());

        String imgUrl = datas.get(position).getBackground();

        Glide.with(context)
                .load(imgUrl)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .skipMemoryCache(true)
                .into(holder.binding.image);
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public static class HomeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ItemHomeBinding binding;
        private OnItemClickListener onItemClickListener;

        public HomeViewHolder(ItemHomeBinding binding, OnItemClickListener listener) {
            super(binding.getRoot());
            this.binding = binding;
            this.onItemClickListener = listener;
            binding.getRoot().setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onItemClickListener.onItemClick(getAdapterPosition());
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}

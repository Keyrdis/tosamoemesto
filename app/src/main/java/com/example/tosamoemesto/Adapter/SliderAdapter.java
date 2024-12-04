package com.example.tosamoemesto.Adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.tosamoemesto.Domain.Slideritems;
import com.example.tosamoemesto.R;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

public class SliderAdapter extends RecyclerView.Adapter<SliderAdapter.SliderViewHolder> {
    private final List<Slideritems> sliderItems;
    private Context context;
    private final ViewPager2 viewPager2; // Declare ViewPager2

    public SliderAdapter(List<Slideritems> sliderItems, ViewPager2 viewPager2) {
        this.sliderItems = sliderItems;
        this.viewPager2 = viewPager2; // Initialize ViewPager2

        // Start auto-scrolling if needed
        startAutoScroll();
    }

    @NonNull
    @Override
    public SliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.slider_viewholer, parent, false);
        return new SliderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SliderViewHolder holder, int position) {
        RequestOptions requestOptions = new RequestOptions()
                .transform(new CenterCrop(), new RoundedCorners(60));

        Glide.with(context)
                .load(sliderItems.get(position).getImage())
                .apply(requestOptions)
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return sliderItems.size();
    }

    class SliderViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;

        public SliderViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageSlide);
        }
    }

    private void startAutoScroll() {
        final Handler handler = new Handler(Looper.getMainLooper()); // Use Looper for main thread
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (viewPager2.getCurrentItem() == sliderItems.size() - 1) {
                    viewPager2.setCurrentItem(0);
                } else {
                    viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1);
                }
                handler.postDelayed(this, 3000); // Repeat every 3 seconds
            }
        };
        handler.postDelayed(runnable, 3000); // Start the first delay
    }
}


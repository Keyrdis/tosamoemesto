package com.example.tosamoemesto.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;

import com.example.tosamoemesto.Adapter.BestFoodsAdapter;
import com.example.tosamoemesto.Adapter.CategoryAdapter;
import com.example.tosamoemesto.Adapter.SliderAdapter;
import com.example.tosamoemesto.Domain.Foods;
import com.example.tosamoemesto.Domain.Slideritems;
import com.example.tosamoemesto.Domain.Category; // Import your custom category class
import com.example.tosamoemesto.R;
import com.example.tosamoemesto.databinding.ActivityMainBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import java.util.ArrayList;

public class MainActivity extends BaseActivity {
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initCategory();
        initBanner();
        initBestFood();
        setVariable();
    }

    private void initBestFood() {
        DatabaseReference myRef = database.getReference("Foods");
        binding.progressBarBestFood.setVisibility(View.VISIBLE);
        ArrayList<Foods> list = new ArrayList<>();
        Query query = myRef.orderByChild("BestFood").equalTo(true);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    for (DataSnapshot issue: snapshot.getChildren()){
                        list.add(issue.getValue(Foods.class));
                    }
                    if (list.size()>0){
                        binding.BestFoodView.setLayoutManager(new LinearLayoutManager(MainActivity.this,LinearLayoutManager.HORIZONTAL, false));
                        RecyclerView.Adapter adapter= new BestFoodsAdapter(list);
                        binding.BestFoodView.setAdapter(adapter);
                    }
                    binding.progressBarBestFood.setVisibility(View.GONE);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void initBanner() {
        DatabaseReference myRef = database.getReference("Banners");
        binding.progressBarbanner.setVisibility(View.VISIBLE);
        ArrayList<Slideritems> items = new ArrayList<>();

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot issue : snapshot.getChildren()) {
                        Slideritems sliderItem = issue.getValue(Slideritems.class);
                        if (sliderItem != null) {
                            items.add(sliderItem);
                        }
                    }
                    setupBanner(items);
                }
                binding.progressBarbanner.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                handleDatabaseError(error);
            }
        });
    }

    private void setupBanner(ArrayList<Slideritems> items) {
        SliderAdapter sliderAdapter = new SliderAdapter(items, binding.viewpager2);
        binding.viewpager2.setAdapter(sliderAdapter);
        binding.viewpager2.setClipChildren(false);
        binding.viewpager2.setClipToPadding(false);
        binding.viewpager2.setOffscreenPageLimit(3);
        binding.viewpager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        // Add your transformers here if needed
        binding.viewpager2.setPageTransformer(compositePageTransformer);
    }

    private void setVariable() {
        binding.bottomMenu.setItemSelected(R.id.home, true);
        binding.bottomMenu.setOnItemSelectedListener(i -> {
            if (i == R.id.cart) {
                startActivity(new Intent(MainActivity.this, CartActivity.class));
            }
            if (i == R.id.favorites) {
                startActivity(new Intent(MainActivity.this, FavoriteActivity.class));
            }
        });

    }

    private void initCategory() {
        DatabaseReference myRef = database.getReference("Category");
        binding.progressBarCategory.setVisibility(View.VISIBLE);
        ArrayList<Category> list = new ArrayList<>();

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot issue : snapshot.getChildren()) {
                        Category cat = issue.getValue(Category.class);
                        if (cat != null) {
                            list.add(cat);
                        }
                    }
                    setupCategoryRecyclerView(list);
                }
                binding.progressBarCategory.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                handleDatabaseError(error);
            }
        });
    }

    private void setupCategoryRecyclerView(ArrayList<Category> list) {
        if (!list.isEmpty()) {
            binding.categoryView.setLayoutManager(new GridLayoutManager(MainActivity.this, 3));
            binding.categoryView.setAdapter(new CategoryAdapter(list));
        }
    }

    private void handleDatabaseError(@NonNull DatabaseError error) {
        binding.progressBarCategory.setVisibility(View.GONE);
        // Show an error message to the user
        Toast.makeText(MainActivity.this, "Ошибка при загрузке данных: " + error.getMessage(), Toast.LENGTH_SHORT).show();
    }
}


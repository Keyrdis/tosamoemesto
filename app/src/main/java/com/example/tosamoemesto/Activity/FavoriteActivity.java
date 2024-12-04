package com.example.tosamoemesto.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.tosamoemesto.Adapter.FavoriteAdapter;
import com.example.tosamoemesto.Helper.ManagmentFavorites;
import com.example.tosamoemesto.R;
import com.example.tosamoemesto.databinding.ActivityFavoriteBinding;

public class FavoriteActivity extends BaseActivity {
    ActivityFavoriteBinding binding;
    private ManagmentFavorites managmentFavorites;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityFavoriteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        managmentFavorites=new ManagmentFavorites(this);

        initFavoriteList();
        setVariable();

    }

    private void  initFavoriteList() {
        if (managmentFavorites.getListFavorites().isEmpty()){
            binding.emptyTxt.setVisibility(View.VISIBLE);
            binding.scrollViewFavorite.setVisibility(View.GONE);
        }else {
            binding.emptyTxt.setVisibility(View.GONE);
            binding.scrollViewFavorite.setVisibility(View.VISIBLE);
        }
        binding.favoriteView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
    }


    private void setVariable() {
        binding.backBtn.setOnClickListener(v -> startActivity(new Intent( FavoriteActivity.this,MainActivity.class)));
        binding.bottomMenu.setItemSelected(R.id.favorites,true);
        binding.bottomMenu.setOnItemSelectedListener(i -> {
            if (i==R.id.home){
                startActivity(new Intent(FavoriteActivity.this,MainActivity.class));
            }
        });

    }
}
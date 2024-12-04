package com.example.tosamoemesto.Activity;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.tosamoemesto.Domain.Foods;
import com.example.tosamoemesto.Helper.ManagmentCart;
import com.example.tosamoemesto.Helper.ManagmentFavorites;
import com.example.tosamoemesto.databinding.ActivityDetailBinding;

public class DetailActivity extends BaseActivity {
    ActivityDetailBinding binding;

    private Foods object;
    private int num = 1;
    private ManagmentCart managmentCart;
    private ManagmentFavorites managmentFavorites;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getIntentExtra();
        setVariable();
    }

    private void setVariable() {
        managmentFavorites = new ManagmentFavorites(this);
        managmentCart = new ManagmentCart(this);

        binding.backBtn.setOnClickListener(v -> finish());

        if (object != null) {
            Glide.with(this)
                    .load(object.getImagePath())
                    .transform(new CenterCrop(), new RoundedCorners(60))
                    .into(binding.pic);

            binding.priceTxt.setText("₽" + object.getPrice());
            binding.titleTxt.setText(object.getTitle());
            binding.detailTxt.setText(object.getTitle());
            binding.timeTxt.setText(object.getTimeValue() + " мин");
            binding.descritionTxt.setText(object.getDescription());
            binding.ratingTxt.setText(object.getStar() + " рейтинг");
            binding.ratingBar.setRating((float) object.getStar());
            binding.totalTxt.setText("₽" + (num * object.getPrice()));

            binding.plusBtn.setOnClickListener(v -> updateQuantity(1));
            binding.minusBtn.setOnClickListener(v -> updateQuantity(-1));
            binding.addBtn.setOnClickListener(v -> addToCart());
            binding.favBtn.setOnClickListener(v -> addToFavorites());
        } else {
            Toast.makeText(this, "Ошибка: товар не найден", Toast.LENGTH_SHORT).show();
            finish(); // Закрываем активность, если объект не найден
        }
    }

    private void updateQuantity(int change) {
        num += change;
        if (num < 1) num = 1; // Убедимся, что количество не меньше 1
        binding.numTxt.setText(String.valueOf(num));
        binding.totalTxt.setText("₽" + (num * object.getPrice()));
    }

    private void addToCart() {
        object.setNumberInCart(num);
        managmentCart.insertFood(object);
        Toast.makeText(this, "Товар добавлен в корзину", Toast.LENGTH_SHORT).show();
    }

    private void addToFavorites() {
        object.setNumberInFavorites(num);
        managmentFavorites.insertFood(object);
        Toast.makeText(this, "Товар добавлен в любимое", Toast.LENGTH_SHORT).show();
    }

    private void getIntentExtra() {
        object = (Foods) getIntent().getSerializableExtra("object");
    }
}

package com.example.tosamoemesto.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.tosamoemesto.Domain.Foods;
import com.example.tosamoemesto.Helper.ChangeNumberItemsListener;
import com.example.tosamoemesto.Helper.ManagmentFavorites;
import com.example.tosamoemesto.R;

import java.util.ArrayList;
import java.util.Objects;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.viewholder> {

    private Context context;

    ArrayList<Foods> list;
    private ManagmentFavorites managmentFavorites;
    ChangeNumberItemsListener changeNumberItemsListener;

    public FavoriteAdapter(ArrayList<Foods> list, ManagmentFavorites managmentFavorites) {
        this.list = list;
        this.managmentFavorites = managmentFavorites;
        this.changeNumberItemsListener = changeNumberItemsListener;
    }


    @NonNull
    @Override
    public FavoriteAdapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new viewholder(LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_list_favorite,parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteAdapter.viewholder holder, int position) {
        holder.title.setText(list.get(position).getTitle());
        holder.feeEachItem.setText("₽"+(list.get(position).getNumberInCart()*list.get(position).getPrice()));
        holder.num.setText(list.get(position).getNumberInCart()+"");

        Glide.with(holder.itemView.getContext())
                .load(list.get(position).getImagePath())
                .transform(new CenterCrop(),new RoundedCorners(30))
                .into(holder.pic);
        Objects.requireNonNull(holder).plusItem.setOnClickListener(view -> managmentFavorites.plusNumberItem(list, position, () -> {
            notifyDataSetChanged();
            changeNumberItemsListener.change();
        }));
        holder.minusItem.setOnClickListener(view -> managmentFavorites.minusNumberItem(list, position, () -> {
            notifyDataSetChanged();
            changeNumberItemsListener.change();
        }));

        holder.trashBtn.setOnClickListener(view -> managmentFavorites.removeItem(list, position, () -> {
            notifyDataSetChanged();
            changeNumberItemsListener.change();
        }));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {
        TextView title,feeEachItem,plusItem,minusItem;
        ImageView pic;
        TextView num;

        ConstraintLayout trashBtn;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            title= itemView.findViewById(R.id.titleTxt);
            pic= itemView.findViewById(R.id.pic);
            feeEachItem=itemView.findViewById(R.id.feeEachitem);
            plusItem=itemView.findViewById(R.id.plusCartBtn);
            minusItem=itemView.findViewById(R.id.minusCartBtn);
            num=itemView.findViewById(R.id.numberItemTxt);
            trashBtn=itemView.findViewById(R.id.trashBtn);


        }
    }
}

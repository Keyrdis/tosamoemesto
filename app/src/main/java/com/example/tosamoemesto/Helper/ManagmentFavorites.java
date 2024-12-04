package com.example.tosamoemesto.Helper;

import android.content.Context;
import android.widget.Toast;
import com.example.tosamoemesto.Domain.Foods;

import java.util.ArrayList;


public class ManagmentFavorites {
    private Context context;
    private TinyDB tinyDB;

    public ManagmentFavorites(Context context) {
        this.context = context;
        this.tinyDB=new TinyDB(context);
    }

    public void insertFood(Foods item) {
        ArrayList<Foods> listpop = getListFavorites();
        boolean existAlready = false;
        int n = 0;
        for (int i = 0; i < listpop.size(); i++) {
            if (listpop.get(i).getTitle().equals(item.getTitle())) {
                existAlready = true;
                n = i;
                break;
            }
        }
        if(existAlready){
            listpop.get(n).setNumberInFavorites(item.getNumberInFavorites());
        }else{
            listpop.add(item);
        }
        tinyDB.putListObject("FavoriteList",listpop);
        Toast.makeText(context, "Added to your Cart", Toast.LENGTH_SHORT).show();
    }

    public ArrayList<Foods> getListFavorites() {
        return tinyDB.getListObject("FavoriteList");
    }

    public Double getTotalFee(){
        ArrayList<Foods> listItem=getListFavorites();
        double fee=0;
        for (int i = 0; i < listItem.size(); i++) {
            fee=fee+(listItem.get(i).getPrice()*listItem.get(i).getNumberInCart());
        }
        return fee;
    }
    public void minusNumberItem(ArrayList<Foods> listItem,int position,ChangeNumberItemsListener changeNumberItemsListener){
        if(listItem.get(position).getNumberInFavorites()==1){
            listItem.remove(position);
        }else{
            listItem.get(position).setNumberInFavorites(listItem.get(position).getNumberInFavorites()-1);
        }
        tinyDB.putListObject("CartList",listItem);
        changeNumberItemsListener.change();
    }
    public  void plusNumberItem(ArrayList<Foods> listItem,int position,ChangeNumberItemsListener changeNumberItemsListener){
        listItem.get(position).setNumberInFavorites(listItem.get(position).getNumberInFavorites()+1);
        tinyDB.putListObject("FavoriteList",listItem);
        changeNumberItemsListener.change();
    }
    public  void removeItem(ArrayList<Foods> listItem,int position,ChangeNumberItemsListener changeNumberItemsListener){
        listItem.remove(position);
        tinyDB.putListObject("FavoriteList",listItem);
        changeNumberItemsListener.change();
    }
}

package com.gyk.muzeyen.ulkeler;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdaptor extends BaseAdapter
{
    private  Context context;
    private ArrayList<Ulke> ulkeList;
    public CustomAdaptor(Context context, ArrayList<Ulke> ulkeList) {
        this.context= context;
        this.ulkeList= ulkeList;
    }
    @Override
    public int getCount() {
        return ulkeList.size();
    }
    @Override
    public Object getItem(int position) {
        return ulkeList.get(position);
    }
    @Override
    public long getItemId(int position) {
        return 0;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final View v = View.inflate(context,R.layout.row,null);   //kimse değiştirmesin diye final yaptık

        ImageView bayrak = v.findViewById(R.id.bayrak);
        TextView isim = v.findViewById(R.id.isim);
        TextView telefon= v.findViewById(R.id.telefon);

        bayrak.setImageResource(ulkeList.get(position).bayrak);  //bayrak positiona göre gelicek,atanacak
        isim.setText(ulkeList.get(position).isim);   //text düzenleme
        telefon.setText(ulkeList.get(position).telefon);
        return v;
    }
}

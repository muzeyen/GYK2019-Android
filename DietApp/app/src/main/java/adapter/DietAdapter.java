package adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gyk.muzeyen.dietapp.R;

import java.util.ArrayList;

import models.Diet;

public class DietAdapter extends BaseAdapter {

    private ArrayList<Diet> yemekListesi;
    private LayoutInflater layoutInflater;

    public DietAdapter(LayoutInflater layoutInflater, ArrayList<Diet> yemekListesi) {
        this.layoutInflater = layoutInflater;
        this.yemekListesi = yemekListesi;
    }

    @Override
    public int getCount() {
        return yemekListesi.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View dietView = layoutInflater.inflate(R.layout.row,null);

        ImageView yemekResim = dietView.findViewById(R.id.yemekFotoL);
        TextView yemekText = dietView.findViewById(R.id.yemekText);
        TextView yemekKalori = dietView.findViewById(R.id.yemekKaloriT);

        yemekResim.setImageResource(yemekListesi.get(position).yemekFoto);
        yemekText.setText(yemekListesi.get(position).yemekAd);
        yemekKalori.setText(yemekListesi.get(position).yemekKalori);

        return dietView;
    }
}

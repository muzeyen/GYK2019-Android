package fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.gyk.muzeyen.dietapp.R;

import java.util.ArrayList;

import adapter.DietAdapter;
import models.Diet;

public class DietFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    final ArrayList<Diet>  yemekListesi = new ArrayList<>();

    public DietFragment() {
        // Required empty public constructor
    }

    public static DietFragment newInstance(String param1, String param2) {
        DietFragment fragment = new DietFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_diet, container, false);
        ListView liste = v.findViewById(R.id.dietList);

        yemekListesi.add(new Diet(R.drawable.menu_3,"yumurta,peynir,zeytin","500C"));
        yemekListesi.add(new Diet(R.drawable.menu_1,"Mevsim Yeşillikleri salata","750C"));
        yemekListesi.add(new Diet(R.drawable.menu_2,"Badem,fındık,ceviz","1000C"));
        yemekListesi.add(new Diet(R.drawable.menu_4,"Kivi,çilek,muz","480C"));

        DietAdapter adapter = new DietAdapter(getLayoutInflater(),yemekListesi);
        liste.setAdapter(adapter);

        return v;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}

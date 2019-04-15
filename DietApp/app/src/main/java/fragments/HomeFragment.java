package fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.gyk.muzeyen.dietapp.R;

public class HomeFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private EditText kiloText;
    private EditText boyText;
    private Button hesaplaButton;
    private TextView oran;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        kiloText = v.findViewById(R.id.kiloText);
        boyText = v.findViewById(R.id.boyText);
        hesaplaButton = v.findViewById(R.id.hesaplaButton);
        oran = v.findViewById(R.id.oran);

        hesaplaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hesapla();
            }
        });

        return v;
    }

    public void hesapla() {

        if(kiloText.getText().toString().length() > 0 && boyText.getText().toString().length() > 0){
            float kilo = Float.parseFloat(kiloText.getText().toString());
            float boy = Float.parseFloat(boyText.getText().toString());

            boy = boy / 100;
            float result = kilo / (boy*boy);
            String indexResult = "";

            if(result<=15){
                indexResult = "Aşırı Zayıf";
            }else if(result>15 && result<=30){
                indexResult = "Zayıf";
            }else if(result>30 && result<=40){
                indexResult = "Normal";
            }else if(result>40){
                indexResult = "Aşırı Şişman";
            }

            oran.setText("Vücut Kitle Endeksiniz: " + result + "\n" + indexResult);
        }else{
            AlertDialog.Builder hata = new AlertDialog.Builder(getContext());
            hata.setTitle("Hata");
            hata.setMessage("Kilo ya da Boy Boş Bırakılamaz !");
            hata.setNegativeButton("Tamam", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });

            hata.show();
        }
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

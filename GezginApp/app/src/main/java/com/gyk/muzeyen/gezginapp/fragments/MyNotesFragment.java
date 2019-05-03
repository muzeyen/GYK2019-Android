package com.gyk.muzeyen.gezginapp.fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.gyk.muzeyen.gezginapp.R;
import com.gyk.muzeyen.gezginapp.activities.AddNoteActivity;

import java.util.ArrayList;

public class MyNotesFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    ArrayAdapter<String> adapter;
    ArrayList<String> noteArrayList = new ArrayList<>();
    ArrayList<String> noteIDArrayList = new ArrayList<>();
    ProgressDialog progress;
    FirebaseAuth mAuth;

    private OnFragmentInteractionListener mListener;

    public MyNotesFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static MyNotesFragment newInstance(String param1, String param2) {
        MyNotesFragment fragment = new MyNotesFragment();
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
        View noteView= inflater.inflate(R.layout.fragment_my_notes, container, false);
        //çekme işlemlerini burda yapıyoruz
        Button addButton= noteView.findViewById(R.id.addNote_button);
        ListView notListView = noteView.findViewById(R.id.note_listview);
        mAuth= FirebaseAuth.getInstance(); //mAuthu doldur
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addNoteIntent = new Intent(getContext(),AddNoteActivity.class);
                startActivity(addNoteIntent);
            }
        });

        notListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {  //kısa basınca düzenleme için
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                alertGoster(noteIDArrayList.get(position));

            }
        });

        notListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {   //uzun basıldığında silme
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                AlertDialog.Builder alert= new AlertDialog.Builder((getContext()));
                alert.setTitle("Uyarı");
                alert.setMessage("Notu silmek istediğinizden emin misiniz?");
                alert.setNegativeButton("Vazgeç",null);
                alert.setPositiveButton("Sil", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        notSil(noteIDArrayList.get(position)); //idnin positionunu alıp notsile git
                        noteIDArrayList.remove(position); //notu sildikten sonra idsini de sildik
                        adapter.notifyDataSetChanged(); //sildiğimiz için adapteri yeniledik
                    }
                }).show();

                return true;
            }
        });

        noteArrayList =notlariGetir(); //getirilen notları buraya atadım

        adapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1, android.R.id.text1,noteArrayList);
        notListView.setAdapter(adapter);
        return noteView;
    }
    private void alertGoster(final String id){
        //alertin içince edittext yazmak için dittext tasarladık
        final EditText guncelNot= new EditText(getContext());
        guncelNot.setHeight(150);
        guncelNot.setWidth(300);
        guncelNot.setGravity(Gravity.LEFT); //imleç soldan başlaması için bunu yazdım

        AlertDialog.Builder alert=new AlertDialog.Builder(getContext());
        alert.setTitle("Not Güncelleme");
        guncelNot.setImeOptions(EditorInfo.IME_ACTION_DONE);
        alert.setView(guncelNot); //edittext üzerinde göster
        alert.setNegativeButton("Vazgeç",null);
        alert.setPositiveButton("Güncelle", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                notGuncelle(id,guncelNot.getText().toString());
            }
        }).show();

    }
    private void notGuncelle(String id, String yeniNot){
        FirebaseDatabase fd = FirebaseDatabase.getInstance(); //databaseden verilere ulaşmak için
        DatabaseReference dr= fd.getReference().child(mAuth.getUid()).child("Notlarim").child(id).child("Notlarim"); //silinecek idyi referans olarak al
        dr.setValue(yeniNot); //yeninot eklenmiş oldu, databaseden guncellenmiş oldu
        Toast.makeText(getContext(),"Not güncellendi",Toast.LENGTH_SHORT).show();
    }
    private void notSil(String id){
        FirebaseDatabase fd = FirebaseDatabase.getInstance(); //databaseden verilere ulaşmak için
        DatabaseReference dr= fd.getReference().child(mAuth.getUid()).child("Notlarim").child(id); //silinecek idyi referans olarak al
        dr.removeValue(); //databaseden silinmiş oldu
        Toast.makeText(getContext(),"Not başarıyla silindi",Toast.LENGTH_SHORT).show();
    }
    private ArrayList<String> notlariGetir(){
        yukleniyorGoster(); //progressDialog burda yüklenmesi uzun sürdüğünde takıldı sanılmaması için kullanıldı

        final ArrayList<String> yeniNotlar = new ArrayList<>();
        FirebaseDatabase noteDatabase =FirebaseDatabase.getInstance(); //güncel veri şuan geldi
        //getUid kişiye özel olmasını sağladı
        DatabaseReference notRef= noteDatabase.getReference().child(mAuth.getUid()).child("Notlarim"); //notlara ulaşabilmek için bir referans oluşturuyorum
        //tüm notları çektiğim için idye gerek yok
        final String[] notum = {""};
        notRef.addValueEventListener(new ValueEventListener() {
            //önemli olan on datachange olduğu için buraya kadar görülmesi gerek
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {  //bir hata olmadığı sürece burası çalışacak
                yeniNotlar.clear();   //yeni bir datasnapshot çekmeden önce listeyi yenilemek için kullanıyoruz
                noteIDArrayList.clear(); //tekrar çıkmasın diye her girdiğinde temizleyip girsin
                progress.dismiss();

                for(DataSnapshot ds : dataSnapshot.getChildren()){  //benim datalarımın çocuklarını al ve datasnapshot ds'e koy
                    String id= ds.getKey(); //notların keylerini alırken hep id ye atıyoruz bu silme işleminde kolaylaştırmak için yapıyoruz
                    //bana gelen boyut kadar içerde dönecek ve verileri alacak
                    notum[0] = ds.child("Notlarim").getValue().toString(); //Notlarimin çocuğuna girdi
                    yeniNotlar.add(notum[0]);
                    noteIDArrayList.add(id); //aldığım idleri liste şeklinde burda tutuyorum
                }
                //refresh etmeden listview kendini yenileyebilmesi için kendini sürekli set etmesini istiyorum
                adapter.notifyDataSetChanged(); //yeni data geldiğinde adaptere söyle ve kendini yenilesin
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { //hata olursa buraya düşecek

            }
        });  //dataları okumak için

       return yeniNotlar;
    }

    private void yukleniyorGoster(){
        progress = new ProgressDialog(getContext());
        progress.setMessage("Notlar yükleniyor..");
        progress.setCancelable(false); //onun dışında herhangi bir yere bastıpında kaybolmamasını istiyorum
        progress.show();
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }
/*
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }
*/
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

package com.gyk.muzeyen.gezginapp.fragments;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.gyk.muzeyen.gezginapp.R;
import com.gyk.muzeyen.gezginapp.activities.AddPhotoActivity;
import com.squareup.picasso.Picasso;

import javax.microedition.khronos.opengles.GL;

public class ProfileFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private ImageView profileFoto,instaFoto,profilDegis;
    private TextView profilename,bioText,placeText;
    FirebaseAuth mAuth;
    ProgressDialog progress;

    public ProfileFragment() {

    }
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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
        View v =inflater.inflate(R.layout.fragment_profile, container, false);

        profileFoto=v.findViewById(R.id.profile_image);
        instaFoto=v.findViewById(R.id.profile_user_insta);
        profilename=v.findViewById(R.id.profile_name);
        bioText=v.findViewById(R.id.profile_bio);
        placeText=v.findViewById(R.id.profile_user_place);
        profilDegis=v.findViewById(R.id.profileDegis);
        mAuth= FirebaseAuth.getInstance();
        profilGuncelle();
        instaFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openInstagram();
            }
        });

        profilDegis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent fotoSayfa=new Intent(getContext(),AddPhotoActivity.class);
                startActivity(fotoSayfa);
            }
        });


        return v;
    }
    private void profilGuncelle(){
        yukleniyorGoster();
        FirebaseStorage fs= FirebaseStorage.getInstance();
        StorageReference sr=fs.getReference().child(mAuth.getUid());

        sr.child("Foto").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                progress.dismiss();
                Glide.with(getContext()).load(uri).asBitmap().centerCrop().into(new SimpleTarget<Bitmap>(200,200) {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        profileFoto.setImageBitmap(resource);
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progress.dismiss();
                Toast.makeText(getContext(), "Profil fotoğrafı yüklenemedi",Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void yukleniyorGoster(){
        progress = new ProgressDialog(getContext());
        progress.setMessage("Notlar yükleniyor..");
        progress.setCancelable(false); //onun dışında herhangi bir yere bastıpında kaybolmamasını istiyorum
        progress.show();
    }

    public void openInstagram(){
        Uri instagramUri =Uri.parse("https://www.instagram.com");
        Intent instagramIntent =new Intent(Intent.ACTION_VIEW,instagramUri);

        instagramIntent.setPackage("com.instagram.android");

        try{
            startActivity(instagramIntent);
        }catch (ActivityNotFoundException e){
            startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("https://www.instagram.com")));
        }
    }


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

        void onFragmentInteraction(Uri uri);
    }
}


package com.gyk.muzeyen.gezginapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gyk.muzeyen.gezginapp.R;
import com.gyk.muzeyen.gezginapp.models.PostModel;

import java.util.List;

public class CustomPostAdapter extends BaseAdapter {

    List<PostModel> postModelList;
    LayoutInflater layoutInflater;  //xmldeki tasarımı kod kısmına çekmek için

    public CustomPostAdapter (LayoutInflater layoutInflater,List<PostModel> postModelList){  //constructor

        this.layoutInflater=layoutInflater;
        this.postModelList=postModelList;

    }
    @Override
    public int getCount() {   //kaç tane var
        return postModelList.size();
    }

    @Override
    public Object getItem(int position) {
        return postModelList.get(position);
    }

    @Override
    public long getItemId(int position) {   //hangisine tıkladıysam ona gitmesini istediğim için return position yaptım
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //veriyi çekme işlemini yaptık
        View postView = layoutInflater.inflate(R.layout.post_list,null);
        ImageView postPicture = postView.findViewById(R.id.post_picture);
        TextView postTitle= postView.findViewById(R.id.post_title);
        TextView postDescription= postView.findViewById(R.id.post_desc);

        PostModel postModel = postModelList.get(position);
        postPicture.setImageResource(postModel.getPostPicture());
        postTitle.setText(postModel.getPostName());
        postDescription.setText(postModel.getPostDescription());

        return postView;
    }
}
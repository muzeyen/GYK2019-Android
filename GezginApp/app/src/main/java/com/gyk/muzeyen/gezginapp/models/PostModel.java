package com.gyk.muzeyen.gezginapp.models;

public class PostModel{

    int postPicture;  //resmin olup olmamasını değer olarak tutacağız
    String postName;
    String postDescription;

    public PostModel(){ }

    public PostModel(int postPicture,String postName,String postDescription){
        this.postPicture=postPicture;
        this.postName=postName;
        this.postDescription=postDescription;

    }
    public String getPostDescription() {
        return postDescription;
    }

    public void setPostDescription(String postDescription) {
        this.postDescription = postDescription;
    }

    public int getPostPicture() {
        return postPicture;
    }

    public void setPostPicture(int postPicture) {
        this.postPicture = postPicture;
    }

    public String getPostName() {
        return postName;
    }

    public void setPostName(String postName) {
        this.postName = postName;
    }
}



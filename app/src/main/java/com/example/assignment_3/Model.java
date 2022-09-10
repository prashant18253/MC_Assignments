package com.example.assignment_3;

public class Model{
    int news_no;
    String comment;
    float rating;
    Model(int n){
        news_no = n;
        comment = "";
    }

    public int getNews(){
        return news_no;
    }

    public void setComment(String comment){
        this.comment = comment;
    }

    public void setRating(float rating){
        this.rating = rating;
    }

    public String getComment(){
        return comment;
    }

    public float getRating(){
        return rating;
    }
}

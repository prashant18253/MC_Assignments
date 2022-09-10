package com.example.assignment_3;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Rating;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class detailsFragment extends Fragment {
    private TextView title, body;
    private ImageView image;
    private EditText comments;
    private int count;
    Button edit, submit;
    RatingBar ratingBar;
    public detailsFragment(int count) {
        this.count = count;
    }

    public static detailsFragment newInstance(int count) {
        detailsFragment fragment = new detailsFragment(count);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_details, container, false);
        title = view.findViewById(R.id.title);
        body = view.findViewById(R.id.body);
        image = view.findViewById(R.id.image);
        comments = view.findViewById(R.id.comments);
        edit = view.findViewById(R.id.edit);
        submit = view.findViewById(R.id.submit);
        ratingBar = view.findViewById(R.id.ratingBar);

        File f = new File("/data/data/com.example.assignment_3/files/imageFile" +count);
        try {
            String string = "";
            StringBuilder stringBuilder = new StringBuilder();
            InputStream is = view.getContext().openFileInput("titleFile" + count);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            while (true) {
                try {
                    if ((string = reader.readLine()) == null) break;
                } catch (IOException e) {
                }
                stringBuilder.append(string).append("\n");
            }
            title.setText(stringBuilder);
        } catch (Exception e) {

        }
        try {
            String string = "";
            StringBuilder stringBuilder = new StringBuilder();
            InputStream is = view.getContext().openFileInput("bodyFile" + count);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            while (true) {
                try {
                    if ((string = reader.readLine()) == null) break;
                } catch (IOException e) {
                    e.printStackTrace();
                }
                stringBuilder.append(string).append("\n");
            }
            body.setText(stringBuilder);
        } catch (Exception e) {

        }
        Bitmap bitmap = null;
        try {
            FileInputStream buf = new FileInputStream(f);
            byte[] arr = new byte[(int) f.length()];
            buf.read(arr);
            bitmap = BitmapFactory.decodeByteArray(arr, 0, arr.length);
            buf.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        image.setImageBitmap(bitmap);

        comments.setText(activityFragment.modelList.get(count).getComment());
        ratingBar.setRating(activityFragment.modelList.get(count).getRating());

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = comments.getText().toString();
                activityFragment.modelList.get(count).setComment(text);
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                float x = ratingBar.getRating();
                activityFragment.modelList.get(count).setRating(x);
            }
        });

        return view;
    }
}
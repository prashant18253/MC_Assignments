package com.example.assignment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragmentActivity#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragmentActivity extends Fragment {

    TextView text, body;
    ImageView imageView;
    public fragmentActivity() {
        // Required empty public constructor
    }

    public static fragmentActivity newInstance() {
        fragmentActivity fragment = new fragmentActivity();
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
        View root = inflater.inflate(R.layout.fragment_activity, container, false);
        text = root.findViewById(R.id.text);
        body = root.findViewById(R.id.body);
        imageView = root.findViewById(R.id.image);
        return root;
    }
}
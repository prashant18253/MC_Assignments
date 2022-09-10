package com.example.assignment_3;

import android.app.ActivityManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class activityFragment extends Fragment{
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    public static List<Model> modelList;
    public static Adapter adapter;
    private Button start, stop;
    View view;
    public activityFragment() {
    }

    public static activityFragment newInstance() {
        activityFragment fragment = new activityFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //modelList = new ArrayList<>();
        loadData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_activity, container, false);
        start = view.findViewById(R.id.start);
        stop = view.findViewById(R.id.stop);

//        loadData();
        recyclerView = view.findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(dividerItemDecoration);
        adapter.notifyDataSetChanged();


        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityManager manager = (ActivityManager) view.getContext().getSystemService(view.getContext().ACTIVITY_SERVICE);
                for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
                    if (MyService.class.getName().equals(service.service.getClassName()))
                        start.setEnabled(false);
                }
                Intent intent = new Intent(view.getContext(), MyService.class);
                view.getContext().startService(intent);
                start.setEnabled(false);
            }
        });
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData();
                view.getContext().stopService(new Intent(view.getContext(), MyService.class));
                start.setEnabled(true);

            }
        });
        return view;
    }

    @Override
    public void onDestroy(){
        getActivity().stopService(new Intent(getActivity(), MyService.class));
        saveData();
        super.onDestroy();
        //Toast.makeText(getActivity(), "Fragment 1 destroyed", Toast.LENGTH_SHORT).show();
    }

    public void saveData(){
        SharedPreferences sharedPreferences = view.getContext().getSharedPreferences("sharedPreferences", view.getContext().MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(modelList);
        editor.putString("model", json);
        editor.apply();
    }

    public void loadData(){
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("sharedPreferences", this.getActivity().MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("model", null);
        Type type = new TypeToken<ArrayList<Model>>() {}.getType();
        modelList = gson.fromJson(json, type);
        if (modelList == null) {
            modelList = new ArrayList<>();
        }
        //Toast.makeText(this.getActivity(), "data loaded "+modelList.size(), Toast.LENGTH_SHORT).show();
        adapter = new Adapter(modelList);
    }
}
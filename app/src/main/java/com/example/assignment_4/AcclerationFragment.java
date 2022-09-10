package com.example.assignment_4;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.List;


public class AcclerationFragment extends Fragment {

    LineChart chart;
    MyDatabase database;
    public AcclerationFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static AcclerationFragment newInstance(String param1, String param2) {
        AcclerationFragment fragment = new AcclerationFragment();
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
        View view = inflater.inflate(R.layout.fragment_accleration, container, false);
        chart = view.findViewById(R.id.chart1);
        database = MyDatabase.getInstance(view.getContext());
        List<ModelAcceleration> values = database.acceleration_dao().getListItems();
        List<Entry> data = new ArrayList<Entry>();

        for(int i =0;i<values.size();i++){
            float x = (values.get(i).getValue1()+values.get(i).getValue2()+values.get(i).getValue3())/3;
            data.add(new Entry(values.get(i).getTimestamp()-values.get(0).getTimestamp(), x));
            Log.d("Aaf", ""+(values.get(i).getTimestamp()-values.get(0).getTimestamp())+" "+ x);
        }
        Log.d("abfkfhjslf", ""+data.size());
        String label = "acceleration table";
        LineDataSet lineDataSet = new LineDataSet(data, label);
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(lineDataSet);
        LineData ld = new LineData(dataSets);
        chart.setData(ld);
        chart.invalidate();
        return view;
    }
}
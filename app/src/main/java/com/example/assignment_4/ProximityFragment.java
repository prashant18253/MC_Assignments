package com.example.assignment_4;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.LineRadarDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProximityFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProximityFragment extends Fragment {

    LineChart chart;
    MyDatabase database;
    public ProximityFragment() {
        // Required empty public constructor
    }

    public static ProximityFragment newInstance(String param1, String param2) {
        ProximityFragment fragment = new ProximityFragment();
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
        View view =  inflater.inflate(R.layout.fragment_proximity, container, false);
        chart = view.findViewById(R.id.chart2);
        database = MyDatabase.getInstance(view.getContext());
        List<Entry> data = new ArrayList<Entry>();
        List<ModelProximity> values = database.proximity_dao().getListItems();
        Log.d("abfkfhjslf", ""+values.size());
        for(int i =0;i<values.size();i++){
            data.add(new Entry(values.get(i).getTimestamp()-values.get(0).getTimestamp(), values.get(i).getValue1()));
        }

        String label = "proximity table";
        LineDataSet lineDataSet = new LineDataSet(data, label);
        List<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(lineDataSet);
        LineData ld = new LineData(dataSets);
        chart.setData(ld);
        //chart.setMinimumHeight(500);
        chart.invalidate();
        return view;
    }
}
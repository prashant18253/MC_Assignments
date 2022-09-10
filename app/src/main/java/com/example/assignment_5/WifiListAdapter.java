package com.example.assignment_5;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class WifiListAdapter extends BaseAdapter {
    LayoutInflater mLayoutInflater;
    Context mContext;
    List<ScanResult> av_wifi;
    ArrayList<Integer> level = new ArrayList<>();
    ArrayList<String> ssid = new ArrayList<>();

    public WifiListAdapter(ArrayList<String> SSID, ArrayList<Integer> Level, Context context){
        level = Level;
        mContext = context;
        ssid = SSID;
        mLayoutInflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return ssid.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Holder holder;

        if (view != null){
            holder = (Holder)view.getTag();
        }
        else {
            view = mLayoutInflater.inflate(R.layout.wifi_list,null);
            holder = new Holder();

            holder.txtView = (TextView)view.findViewById(R.id.available);

            view.setTag(holder);
        }
        holder.txtView.setText(ssid.get(i) + ("\n RSSI value : " + level.get(i)));
        return view;
    }

    class Holder{
        TextView txtView;
    }
}

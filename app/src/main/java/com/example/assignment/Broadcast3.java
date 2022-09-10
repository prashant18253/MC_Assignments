package com.example.assignment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class Broadcast3 extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        context.stopService(new Intent(context, MyService.class));
    }
}
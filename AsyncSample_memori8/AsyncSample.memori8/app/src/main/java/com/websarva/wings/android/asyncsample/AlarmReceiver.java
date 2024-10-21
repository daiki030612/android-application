package com.websarva.wings.android.asyncsample;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // アラームが発動したときの処理を記述
        Toast.makeText(context, "アラームが鳴りました", Toast.LENGTH_SHORT).show();
    }
}
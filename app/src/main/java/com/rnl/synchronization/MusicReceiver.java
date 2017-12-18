package com.rnl.synchronization;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.widget.Toast;

public class MusicReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        CharSequence intentData =
                intent.getCharSequenceExtra("Trevor Novak");
        Toast.makeText(context, "Trevor Novak", Toast.LENGTH_LONG).show();
        Vibrator vibrator = (Vibrator)
                context.getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(1000);
    }
}
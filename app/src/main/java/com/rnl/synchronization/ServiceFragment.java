package com.rnl.synchronization;

import android.app.Fragment;
import android.util.Log;

import com.instacart.library.truetime.TrueTime;
import com.peak.salut.Callbacks.SalutCallback;

import java.util.Timer;
import java.util.TimerTask;

import static com.rnl.synchronization.MainActivity.network;
import static com.rnl.synchronization.WiFiServiceDiscoveryActivity.TAG;


abstract class ServiceFragment extends Fragment {
    public String serviceName;

    public boolean play(String data, final SalutCallback callback) {
        MyMessage message = new MyMessage();
        message.description = "beep test";
        message.serviceName = serviceName;
        message.timeToTrigger = TrueTime.now().getTime() + 1000;
        network.sendToAllDevices(message, new SalutCallback() {
            @Override
            public void call() {
                Log.e(TAG, "Failed to send play message for " + serviceName);
            }
        });
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                MusicFragment.doAction(getActivity().getApplicationContext());
                callback.call();
            }
        }, message.timeToTrigger - TrueTime.now().getTime());

        return true;
    }

    public boolean pause() {
        return false;
    }
}

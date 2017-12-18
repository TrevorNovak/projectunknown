package com.rnl.synchronization;

import android.app.Fragment;
import android.util.Log;

import com.instacart.library.truetime.TrueTime;
import com.peak.salut.Callbacks.SalutCallback;

import java.util.Timer;
import java.util.TimerTask;

import static com.rnl.synchronization.MainActivity.currentService;
import static com.rnl.synchronization.MainActivity.host;
import static com.rnl.synchronization.MainActivity.network;
import static com.rnl.synchronization.MainActivity.serviceCallback;
import static com.rnl.synchronization.WiFiServiceDiscoveryActivity.TAG;


abstract class ServiceFragment extends Fragment {
    public String serviceName;
    public void register() {
        currentService = serviceName;
        serviceCallback = new SalutCallback() {
            @Override
            public void call() {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        doAction();
                    }
                });
            }
        };
    }
    public void deregister() {
        currentService = "";
        serviceCallback = null;
    }
    public boolean play(String data) {
        if(!host) {
            return false;
        }
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
                doAction();
            }
        }, message.timeToTrigger - TrueTime.now().getTime());

        return true;
    }
    public abstract void doAction();
    public boolean pause() {
        return false;
    }

     @Override
    public void onResume() {
        super.onResume();
        register();
    }
    @Override
    public void onStop() {
        super.onStop();
        deregister();
    }
}

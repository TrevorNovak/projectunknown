package com.rnl.synchronization;

/**
 * Created by L on 12/7/2017.
 */

import android.media.ToneGenerator;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.instacart.library.truetime.TrueTime;

import static com.rnl.synchronization.MainActivity.toneG;
import static com.rnl.synchronization.WiFiServiceDiscoveryActivity.TAG;


public class BeepFragment extends ServiceFragment {
    private View parentView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        parentView = inflater.inflate(R.layout.music, container, false);
        serviceName = "beep";
        register();

        // Play Music Button
        final Button button = (Button) parentView.findViewById(R.id.BeepButton);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                play("");
            }
        });


        return parentView;
    }

    public void doAction() {
        Log.d(TAG, "trueTime says the time is: " + TrueTime.now().getTime());
        toneG.startTone(ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD, 200);
        Log.d(TAG, "trueTime says the time is: " + TrueTime.now().getTime());
    }
}


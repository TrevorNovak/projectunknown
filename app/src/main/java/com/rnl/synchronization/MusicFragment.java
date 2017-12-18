package com.rnl.synchronization;

/**
 * Created by L on 12/7/2017.
 */

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;


public class MusicFragment extends ServiceFragment {
    private View parentView;
    private ListView listView;
    private MediaPlayer player;
    final MyBroadcastReceiver r2 = new MyBroadcastReceiver();
    private boolean isServiceRunning = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        parentView = inflater.inflate(R.layout.music, container, false);
        listView = (ListView) parentView.findViewById(R.id.musicListView);
        serviceName = "music";
        register();
        player = MediaPlayer.create(getActivity().getApplicationContext(),
                R.raw.braincandy);
        player.setLooping(false);

        // Play Music Button
        final Button button = (Button) parentView.findViewById(R.id.select);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String title = button.getText().toString();
                if (title.equals("Play")) {
                    play("");
                    button.setText("Pause");
                } else {
                    button.setText("Play");
                    play("");
                }
            }
        });


        return parentView;
    }

    public void doAction() {
        playMusic();
    }

    public void broadcastIntent()    // static
    {
        Intent intent = new Intent("MyCustomIntent");
        // add data to the Intent
        //intent.putExtra("message", (CharSequence)et.getText().toString());
        intent.setAction("com.rnl.synchronization.A_CUSTOM_INTENT1");
        getActivity().sendBroadcast(intent);
    }

    public void playMusic() {
        if(!isServiceRunning) {
            getActivity().startService(new Intent(getActivity(),
                    com.rnl.synchronization.MusicService.class));
            broadcastIntent();
            isServiceRunning = true;
        }
        else {
                getActivity().stopService(new Intent(getActivity(), MusicService.class));
            isServiceRunning = false;
        }
    }
}

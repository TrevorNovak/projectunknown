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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


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
        initView();
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
//    public void doAction() {
//        if(player.isPlaying()) {
//            player.stop();
//            player = MediaPlayer.create(getActivity().getApplicationContext(),
//                R.raw.braincandy);
//            player.setLooping(false);
//        } else {
//            player.start();
//        }
//    }

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

    private void initView() {
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                getActivity(),
                R.layout.text_view,
                getMusicData());
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        Toast.makeText(getActivity(), "1 is clicked", Toast.LENGTH_LONG).show();
                    case 1:
                        Toast.makeText(getActivity(), "2 is clicked", Toast.LENGTH_LONG).show();
                        break;
                    case 2:
                        Toast.makeText(getActivity(), "3 is clicked", Toast.LENGTH_LONG).show();
                        break;
                    case 3:
                        Toast.makeText(getActivity(), "4 is clicked", Toast.LENGTH_LONG).show();
                        break;
                    case 4:
                        Toast.makeText(getActivity(), "5 is clicked", Toast.LENGTH_LONG).show();
                        break;
                }
            }
        });
    }

    private ArrayList<String> getMusicData() {
        ArrayList<String> musicList = new ArrayList<String>();
        musicList.add("Song 1");
        musicList.add("Song 2");
        musicList.add("Song 3");
        musicList.add("Song 4");
        musicList.add("Song 5");
        musicList.add("Song 6");
        musicList.add("Song 7");
        musicList.add("Song 8");
        musicList.add("Song 9");
        musicList.add("Song 10");
        musicList.add("Song 11");
        musicList.add("Song 12");
        /*calendarList.add("Song 13");
        calendarList.add("Song 14");
        calendarList.add("Song 15");
        calendarList.add("Song 16");*/
        return musicList;
    }

}

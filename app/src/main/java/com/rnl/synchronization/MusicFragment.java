package com.rnl.synchronization;

/**
 * Created by L on 12/7/2017.
 */

import android.app.Fragment;
import android.content.Context;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.instacart.library.truetime.TrueTime;
import com.peak.salut.Callbacks.SalutCallback;

import java.util.ArrayList;

import static com.rnl.synchronization.MainActivity.toneG;
import static com.rnl.synchronization.WiFiServiceDiscoveryActivity.TAG;


public class MusicFragment extends ServiceFragment {
    private View parentView;
    private ListView listView;
    Fragment someFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        parentView = inflater.inflate(R.layout.music, container, false);
        listView = (ListView) parentView.findViewById(R.id.musicListView);
        serviceName = "music";
        initView();


        // Play Music Button
        final Button button = (Button) parentView.findViewById(R.id.select);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String title = button.getText().toString();
                if (title.equals("Play")) {
                    play("", new SalutCallback() {
                        @Override
                        public void call() {

                        }
                    });
                    button.setText("Pause");
                } else {
                    button.setText("Play");
                    pause();
                }
            }
        });


        return parentView;
    }
    public static void doAction(Context context) {
        Log.d(TAG, "trueTime says the time is: " + TrueTime.now().getTime());
        toneG.startTone(ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD, 200);
        Log.d(TAG, "trueTime says the time is: " + TrueTime.now().getTime());
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

package com.rnl.synchronization;

/**
 * Created by L on 12/7/2017.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import java.util.ArrayList;


public class MusicFragment extends Fragment {
    private View parentView;
    private ListView listView;
    Fragment someFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        parentView = inflater.inflate(R.layout.music, container, false);
        listView = (ListView) parentView.findViewById(R.id.musicListView);
        initView();


        // Play Music Button
        final Button button = (Button) parentView.findViewById(R.id.select);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String title = button.getText().toString();
                if (title.equals("Play")) {
                    button.setText("Pause");
                } else {
                    button.setText("Play");
                }
            }
        });


        return parentView;
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

package com.rnl.synchronization;

/**
 * Created by L on 12/7/2017.
 */


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class UserFragment extends Fragment {
    private View parentView;
    private ListView listView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        parentView = inflater.inflate(R.layout.user, container, false);
        listView = (ListView) parentView.findViewById(R.id.usersListView);

        // Search Devices Button
        final Button button = (Button) parentView.findViewById(R.id.search);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSearchingView();
            }
        });
        return parentView;
    }

    private void startSearchingView() {
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                getActivity(),
                R.layout.text_view,
                getDevicesData());
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        Toast.makeText(getActivity(), "Trevor's device is clicked", Toast.LENGTH_LONG).show();
                    case 1:
                        Toast.makeText(getActivity(), "Jake's device is clicked", Toast.LENGTH_LONG).show();
                        break;
                    case 2:
                        Toast.makeText(getActivity(), "Lu's device is clicked", Toast.LENGTH_LONG).show();
                        break;
                }
            }
        });
    }

    private ArrayList<String> getDevicesData() {
        ArrayList<String> usersList = new ArrayList<String>();
        usersList.add("Trevor's Device");
        usersList.add("Jake's Device");
        usersList.add("Lu's Device");
        return usersList;
    }
}

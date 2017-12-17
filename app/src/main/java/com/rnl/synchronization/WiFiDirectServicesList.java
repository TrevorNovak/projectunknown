package com.rnl.synchronization;

import android.app.ListFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.peak.salut.Callbacks.SalutCallback;
import com.peak.salut.SalutDevice;

import static com.rnl.synchronization.MainActivity.network;
import static com.rnl.synchronization.WiFiServiceDiscoveryActivity.TAG;

public class WiFiDirectServicesList extends ListFragment {
    ArrayAdapter<SalutDevice> listAdapter = null;

    interface DeviceClickListener {
        public void connectP2p(WiFiP2pService wifiP2pService);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_device_list, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        listAdapter = new ArrayAdapter<SalutDevice>(this.getActivity(), android.R.layout.simple_list_item_2, android.R.id.text1);
        setListAdapter(listAdapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        // TODO Auto-generated method stub
        ((TextView) v.findViewById(android.R.id.text2)).setText("Connecting");

        network.registerWithHost((SalutDevice) l.getAdapter().getItem(position), new SalutCallback() {
            @Override
            public void call() {
                Log.d(TAG, "Connected!!!");
                MyMessage myMessage = new MyMessage();
                myMessage.description = "See you on the other side!";
                network.sendToHost(myMessage, new SalutCallback() {
                    @Override
                    public void call() {
                        Log.d(TAG, "data send failed");
                    }
                });
            }
        }, new SalutCallback() {
            @Override
            public void call() {

                Log.d(TAG, "failed :(");
            }
        });
    }

    public void addDevice(SalutDevice device) {
        listAdapter.add(device);
        listAdapter.notifyDataSetChanged();
    }
}

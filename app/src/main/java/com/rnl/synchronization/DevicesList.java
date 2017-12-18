package com.rnl.synchronization;

import android.app.ListFragment;
import android.content.Context;
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

import java.util.ArrayList;
import java.util.List;

import static com.rnl.synchronization.MainActivity.network;
import static com.rnl.synchronization.WiFiServiceDiscoveryActivity.TAG;

public class DevicesList extends ListFragment {
    ArrayAdapter<SalutDevice> listAdapter = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_device_list, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        listAdapter = new DevicesAdapter(this.getActivity(),
                android.R.layout.simple_list_item_2,
                android.R.id.text1,
                new ArrayList<SalutDevice>());
        setListAdapter(listAdapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        // TODO Auto-generated method stub
        ((TextView) v.findViewById(android.R.id.text2)).setText("Connecting");
        final View w = v;
        network.registerWithHost((SalutDevice) l.getAdapter().getItem(position), new SalutCallback() {
            @Override
            public void call() {
                Log.d(TAG, "Connected!!!");
                ((TextView) w.findViewById(android.R.id.text2)).setText("Connected");

            }
        }, new SalutCallback() {
            @Override
            public void call() {

                Log.d(TAG, "failed :(");
            }
        });
    }

    public class DevicesAdapter extends ArrayAdapter<SalutDevice> {
        private List<SalutDevice> items;

        public DevicesAdapter(Context context, int resource,
                              int textViewResourceId, List<SalutDevice> items) {
            super(context, resource, textViewResourceId, items);
            this.items = items;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            if (v == null) {
                LayoutInflater vi = (LayoutInflater) getActivity()
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(android.R.layout.simple_list_item_2, null);
            }
            SalutDevice service = items.get(position);
            if (service != null) {
                TextView nameText = (TextView) v
                        .findViewById(android.R.id.text1);
                if (nameText != null) {
                    nameText.setText(service.deviceName);
                }
            }
            return v;
        }
    }

    public void addDevice(SalutDevice device) {
        Log.d(TAG, "adding device " + device.deviceName);
        listAdapter.add(device);
        listAdapter.notifyDataSetChanged();
    }
}

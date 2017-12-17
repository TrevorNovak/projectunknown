package com.rnl.synchronization;

import android.app.Activity;
import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.net.wifi.p2p.WifiP2pManager;
import android.net.wifi.p2p.WifiP2pManager.ActionListener;
import android.net.wifi.p2p.WifiP2pManager.Channel;
import android.net.wifi.p2p.nsd.WifiP2pDnsSdServiceRequest;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.peak.salut.Callbacks.SalutDeviceCallback;
import com.peak.salut.SalutDevice;

import static com.rnl.synchronization.MainActivity.network;

public class WiFiServiceDiscoveryActivity extends Activity  {
    public static final String TAG = "synchronizationdemo";
    // TXT RECORD properties
    public static final String TXTRECORD_PROP_AVAILABLE = "available";
    public static final String SERVICE_INSTANCE = "_serviceinstance";
    public static final String SERVICE_REG_TYPE = "_presence._tcp";
    private WifiP2pManager manager;
    private final IntentFilter intentFilter = new IntentFilter();
    private Channel channel;
    private BroadcastReceiver receiver = null;
    private WifiP2pDnsSdServiceRequest serviceRequest;
    private WiFiDirectServicesList servicesList;
    private TextView statusTxtView;
    private boolean host;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wifidirect);
        statusTxtView = (TextView) findViewById(R.id.status_text);
        servicesList = new WiFiDirectServicesList();
        getFragmentManager().beginTransaction()
                .add(R.id.container_root, servicesList, "services").commit();
        network.discoverNetworkServices(new SalutDeviceCallback() {
                @Override
                public void call(SalutDevice device) {
                    Log.d(TAG, "A device has connected with the name " + device.deviceName);
                    servicesList.addDevice(device);
                }
            }, true);
        }

    @Override
    protected void onRestart() {
        Fragment frag = getFragmentManager().findFragmentByTag("services");
        if (frag != null) {
            getFragmentManager().beginTransaction().remove(frag).commit();
        }
        super.onRestart();
    }
    @Override
    protected void onStop() {
        network.stopNetworkService(false);
        if (manager != null && channel != null) {
            manager.removeGroup(channel, new ActionListener() {
                @Override
                public void onFailure(int reasonCode) {
                    Log.d(TAG, "Disconnect failed. Reason :" + reasonCode);
                }
                @Override
                public void onSuccess() {
                }
            });
        }
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        network.stopNetworkService(false);
    }
    /**
     * Registers a local service and then initiates a service discovery
     */

    @Override
    public void onResume() {
        super.onResume();
        receiver = new WiFiDirectBroadcastReceiver(manager, channel, this);
        registerReceiver(receiver, intentFilter);
    }
    @Override
    public void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }
}
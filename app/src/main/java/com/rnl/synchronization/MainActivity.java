package com.rnl.synchronization;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.bluelinelabs.logansquare.LoganSquare;
import com.instacart.library.truetime.TrueTime;
import com.peak.salut.Callbacks.SalutCallback;
import com.peak.salut.Callbacks.SalutDataCallback;
import com.peak.salut.Callbacks.SalutDeviceCallback;
import com.peak.salut.Salut;
import com.peak.salut.SalutDataReceiver;
import com.peak.salut.SalutDevice;
import com.peak.salut.SalutServiceData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends FragmentActivity implements View.OnClickListener, SalutDataCallback {
    private static String TAG = "synchMain";
    private ResideMenu resideMenu;
    private MainActivity mContext;
    private ResideMenuItem itemHome;
    private ResideMenuItem itemUser;
    private ResideMenuItem itemMusic;
    private ResideMenuItem itemCamera;
    private ResideMenuItem itemCanvas;
    private ResideMenuItem itemBeep;
    private ResideMenuItem itemList;
    public static boolean host;
    static public Salut network;
    public static String deviceName;
    public static ToneGenerator toneG;
    public static List<ServiceFragment> serviceFragments;
    public static String currentService = null;
    public static SalutCallback serviceCallback;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        setUpMenu();
        if (savedInstanceState == null)
            changeFragment(new HomeFragment(), null, null);
        new getTimeTask().execute("asdf");
        serviceFragments = new ArrayList<>();
        hostPrompt();
        toneG = new ToneGenerator(AudioManager.STREAM_ALARM, 100);
        SalutDataReceiver dataReceiver = new SalutDataReceiver(this, this);
        SalutServiceData serviceData = new SalutServiceData("sas", 50489, "SynchDemo");
        MainActivity.network = new Salut(dataReceiver, serviceData, new SalutCallback() {
            @Override
            public void call() {
                Log.e("Salut", "Wifi direct not supported");
            }
        });
    }
    class getTimeTask extends AsyncTask<String, Void, Boolean> {

        private Exception exception;

        protected Boolean doInBackground(String... strings) {
            try {
                TrueTime.build().initialize();
            } catch (Exception e) {
                this.exception = e;

                return Boolean.FALSE;
            } finally {
            }
            return Boolean.TRUE;
        }
    }
    public void hostPrompt() {
         AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                host = true;
                startService();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                host = false;
                startService();
            }
        });

        builder.setMessage("Do you want to be the host?");
        builder.create().show();
    }
    public void startService() {
        if (host) {
            Log.d(TAG, "Host, starting network service");
            network.startNetworkService(new SalutDeviceCallback() {
                @Override
                public void call(SalutDevice device) {
                    Log.d(TAG, device.readableName + " has connected!");
                }
            });
        } else {
//            Log.d(TAG, "client, discvoering network services");
//            network.discoverNetworkServices(new SalutDeviceCallback() {
//                @Override
//                public void call(SalutDevice device) {
//                    Log.d(TAG, "A device has connected with the name " + device.deviceName);
//                    network.registerWithHost(device, new SalutCallback() {
//                        @Override
//                        public void call() {
//                            Log.d(TAG, "successfully connected");
//                        }
//                    }, new SalutCallback() {
//                        @Override
//                        public void call() {
//
//                        }
//                    });
//                }
//            }, true);
        }
    }

    @Override
    public void onDataReceived(Object data) {
        Log.d(TAG, "Received network data.");
        try {
            MyMessage newMessage = LoganSquare.parse((String) data, MyMessage.class);
            Log.d(TAG, newMessage.description);  //See you on the other side!
            if (newMessage.serviceName.equals(currentService)) {
                try {
                    new Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {
                            serviceCallback.call();
                        }
                    }, newMessage.timeToTrigger - TrueTime.now().getTime());
                } catch (IllegalArgumentException e) {
                    Log.d(TAG, "negative delay: " + TrueTime.now().getTime() + " message says: " + newMessage.timeToTrigger);
                }
            }
        } catch (IOException ex) {
            Log.e(TAG, "Failed to parse network data.");

        }
    }


    private void setUpMenu() {

        // attach to current activity;
        resideMenu = new ResideMenu(this);
        resideMenu.setUse3D(true);
        //resideMenu.setBackground(R.drawable.);
        resideMenu.attachToActivity(this);
        resideMenu.setMenuListener(menuListener);
        //valid scale factor is between 0.0f and 1.0f. leftmenu'width is 150dip.
        resideMenu.setScaleValue(0.6f);

        // create menu items;
        itemHome = new ResideMenuItem(this, R.drawable.home_icon, "Home");
        itemUser = new ResideMenuItem(this, R.drawable.profile_icon, "Users");
        itemMusic = new ResideMenuItem(this, R.drawable.music_icon, "Music");
        itemCamera = new ResideMenuItem(this, R.drawable.camera_icon, "Camera");
        itemBeep = new ResideMenuItem(this, android.R.drawable.stat_sys_speakerphone, "Beep");
        itemList = new ResideMenuItem(this, R.drawable.ic_cached_white, "Device List");
        itemCanvas = new ResideMenuItem(this, R.drawable.ic_create_white, "Canvas");

        itemHome.setOnClickListener(this);
        itemUser.setOnClickListener(this);
        itemMusic.setOnClickListener(this);
        itemCamera.setOnClickListener(this);
        itemBeep.setOnClickListener(this);
        itemList.setOnClickListener(this);
        itemCanvas.setOnClickListener(this);


        resideMenu.addMenuItem(itemHome, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemUser, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemList, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemMusic, ResideMenu.DIRECTION_RIGHT);
        resideMenu.addMenuItem(itemCamera, ResideMenu.DIRECTION_RIGHT);
        resideMenu.addMenuItem(itemCanvas, ResideMenu.DIRECTION_RIGHT);
        resideMenu.addMenuItem(itemBeep, ResideMenu.DIRECTION_RIGHT);


        // You can disable a direction by setting ->
        // resideMenu.setSwipeDirectionDisable(ResideMenu.DIRECTION_RIGHT);

        findViewById(R.id.title_bar_left_menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resideMenu.openMenu(ResideMenu.DIRECTION_LEFT);
            }
        });
        findViewById(R.id.title_bar_right_menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resideMenu.openMenu(ResideMenu.DIRECTION_RIGHT);
            }
        });
    }


    // Active Touch Event
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return resideMenu.dispatchTouchEvent(ev);
    }

    @Override
    public void onClick(View view) {

        if (view == itemHome) {
            changeFragment(new HomeFragment(), null, null);
        } else if (view == itemUser) {
            changeFragment(new UserFragment(), null, null);
        } else if (view == itemMusic) {
            changeFragment(null, new MusicFragment(), null);
        } else if (view == itemCamera) {
            changeFragment(null, new CameraFragment(), null);
        } else if (view == itemCanvas) {
            changeFragment(null, new CanvasFragment(), null);
        } else if (view == itemBeep) {
            changeFragment(null, new BeepFragment(), null);
        } else if (view == itemList) {
            try {
                Intent i = new Intent(this, WiFiServiceDiscoveryActivity.class);
                startActivity(i);
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Error: " + e, Toast.LENGTH_LONG);
            }
        }


        resideMenu.closeMenu();
    }

    private ResideMenu.OnMenuListener menuListener = new ResideMenu.OnMenuListener() {
        @Override
        public void openMenu() {
        }

        @Override
        public void closeMenu() {
        }
    };

    private void changeFragment(Fragment targetFragment, ServiceFragment serviceFragment, ListFragment secondaryFragment) {
        resideMenu.clearIgnoredViewList();

        if (targetFragment != null) {
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_fragment, targetFragment, "fragment")
                    .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .commit();
        }

        if (serviceFragment != null) {
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_fragment, serviceFragment, "fragment")
                    .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .commit();
        }

        if (secondaryFragment != null) {
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_fragment, secondaryFragment, "secfragment")
                    .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .commit();

        }
    }

    // What good method is to access resideMenu？
    public ResideMenu getResideMenu() {
        return resideMenu;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        network.stopNetworkService(false);

    }
    @Override
    public void onStop() {
        super.onStop();
        network.stopNetworkService(false);
    }
}

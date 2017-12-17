package com.rnl.synchronization;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends FragmentActivity implements View.OnClickListener {

    private ResideMenu resideMenu;
    private MainActivity mContext;
    private ResideMenuItem itemHome;
    private ResideMenuItem itemUser;
    private ResideMenuItem itemMusic;
    private ResideMenuItem itemCamera;
    private ResideMenuItem itemList;

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
            changeFragment(new HomeFragment(), null);
    }

    private void setUpMenu() {

        // attach to current activity;
        resideMenu = new ResideMenu(this);
        resideMenu.setUse3D(true);
        resideMenu.setBackground(R.drawable.menu_background);
        resideMenu.attachToActivity(this);
        resideMenu.setMenuListener(menuListener);
        //valid scale factor is between 0.0f and 1.0f. leftmenu'width is 150dip.
        resideMenu.setScaleValue(0.6f);

        // create menu items;
        itemHome = new ResideMenuItem(this, R.drawable.home_icon, "Home");
        itemUser = new ResideMenuItem(this, R.drawable.profile_icon, "Users");
        itemMusic = new ResideMenuItem(this, R.drawable.music_icon, "Music");
        itemCamera = new ResideMenuItem(this, R.drawable.camera_icon, "Camera");
        itemList = new ResideMenuItem(this, R.drawable.ic_cached_white, "Device List");

        itemHome.setOnClickListener(this);
        itemUser.setOnClickListener(this);
        itemMusic.setOnClickListener(this);
        itemCamera.setOnClickListener(this);
        itemList.setOnClickListener(this);

        resideMenu.addMenuItem(itemHome, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemUser, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemMusic, ResideMenu.DIRECTION_RIGHT);
        resideMenu.addMenuItem(itemCamera, ResideMenu.DIRECTION_RIGHT);
        resideMenu.addMenuItem(itemList, ResideMenu.DIRECTION_RIGHT);

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
            changeFragment(new HomeFragment(), null);
        } else if (view == itemUser) {
            changeFragment(new UserFragment(), null);
        } else if (view == itemMusic) {
            changeFragment(new MusicFragment(), null);
        } else if (view == itemCamera) {
            changeFragment(new CameraFragment(), null);
        } else if (view == itemList) {
            try {
                Intent i = new Intent(this, WiFiServiceDiscoveryActivity.class);
                startActivity(i);
            } catch(Exception e) {
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

    private void changeFragment(Fragment targetFragment, ListFragment secondaryFragment) {
        resideMenu.clearIgnoredViewList();

        if(targetFragment != null) {
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_fragment, targetFragment, "fragment")
                    .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .commit();
        }

        if(secondaryFragment != null)
        {
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
}

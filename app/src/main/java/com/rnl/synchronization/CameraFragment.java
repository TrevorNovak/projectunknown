package com.rnl.synchronization;

/**
 * Created by L on 12/7/2017.
 */


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
public class CameraFragment extends ServiceFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.camera, container, false);
    }

    @Override
    public void doAction() {

    }
}

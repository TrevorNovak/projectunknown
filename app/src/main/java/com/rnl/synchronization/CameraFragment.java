package com.rnl.synchronization;

/**
 * Created by L on 12/7/2017.
 */


import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
public class CameraFragment extends ServiceFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Camera cam = Camera.open();

        Camera.Parameters pon = cam.getParameters(), poff = cam.getParameters();

        pon.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
        poff.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);

        return inflater.inflate(R.layout.camera, container, false);
    }

    /** Check if this device has a camera */
    private boolean checkCameraHardware(Context context) {
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
            // this device has a camera
            return true;
        } else {
            // no camera on this device
            return false;
        }
    }

    public static Camera getCameraInstance(){
        Camera c = null;
        try {
            c = Camera.open(); // attempt to get a Camera instance
        }
        catch (Exception e){
            // Camera is not available (in use or does not exist)
        }
        return c; // returns null if camera is unavailable
    }
}

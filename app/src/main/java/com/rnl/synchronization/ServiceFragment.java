package com.rnl.synchronization;

import android.support.v4.app.Fragment;


abstract class ServiceFragment extends Fragment {
    public String serviceName;

    public boolean play(String data) {
        return false;
    }
    public boolean pause() {
        return false;
    }
}

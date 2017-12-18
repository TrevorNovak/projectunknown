package com.rnl.synchronization;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class CanvasFragment extends ServiceFragment {
    Bitmap bitmap;
    Canvas canvas;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        bitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);




        return inflater.inflate(R.layout.canvas, container, false);
    }



    @Override
    public void doAction() {

    }
}

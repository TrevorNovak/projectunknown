package com.rnl.synchronization;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class CanvasFragment extends ServiceFragment {
    Bitmap bitmap;
    Canvas canvas;
    View parentView;
    boolean color = false;
    Button button;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        serviceName = "canvas";
        parentView = inflater.inflate(R.layout.canvas, container, false);
        button = (Button) parentView.findViewById(R.id.blub);
        button.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_bright));
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                play("");
            }
        });


        return parentView;
    }


    @Override
    public void doAction() {
        mHandler.obtainMessage().sendToTarget();
    }
    @SuppressLint("HandlerLeak")
    public Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
        int colorInt;
        if (color) {
            colorInt = android.R.color.holo_blue_bright;
        } else {
            colorInt = android.R.color.holo_blue_dark;
        }
        color = !color;
        button.setBackgroundColor(getResources().getColor(colorInt));
        }
    };
}

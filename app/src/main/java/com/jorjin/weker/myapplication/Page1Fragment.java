package com.jorjin.weker.myapplication;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * Created by weker on 8/24/2016.
 */
public class Page1Fragment extends Fragment {
    public Page1Fragment() {
    }

    public static Page1Fragment newInstance() {
        Page1Fragment fragment = new Page1Fragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        int i;
        View rootView = inflater.inflate(R.layout.page1, container, false);
        return rootView;
    }
}

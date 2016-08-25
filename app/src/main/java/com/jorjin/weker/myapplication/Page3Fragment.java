package com.jorjin.weker.myapplication;

import android.support.v4.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * Created by weker on 8/24/2016.
 */
public class Page3Fragment extends Fragment{
    private LinearLayout llBarChart;
    private View vRRChart;

    private FFTManager m_FTManager;

    private ArrayList<ArrayList<Integer>> RR;
    private Random rand;

    public Page3Fragment() {
    }

    public static Page3Fragment newInstance() {
        Page3Fragment fragment = new Page3Fragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        int i;
        View rootView = inflater.inflate(R.layout.page3, container, false);
        RR = new ArrayList<ArrayList<Integer>>();
        ArrayList<Integer> A = new ArrayList<Integer>();
        for(i=0;i<30;i++){
            A.add(0);
        }
        ArrayList<Integer> B = new ArrayList<Integer>();
        for(i=0;i<30;i++){
            B.add(0);
        }
        RR.add(A);
        RR.add(B);

        m_FTManager = new FFTManager();

        llBarChart = (LinearLayout) rootView.findViewById(R.id.llBarChart);
        rand = new Random();
        try{
            vRRChart = getLineChart(RR);
            llBarChart.removeAllViews();
            llBarChart.addView(vRRChart, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, 500));
        }catch(Exception e){

        }
        ScheduledExecutorService scheduleTaskExecutor = Executors.newScheduledThreadPool(5);
        final ScheduledFuture drawHandle =
                scheduleTaskExecutor.scheduleAtFixedRate(drawThread, 1, 1, TimeUnit.SECONDS);

        return rootView;

    }

    private Runnable drawThread = new Runnable() {
        public void run() {
            int i = rand.nextInt(1000 - 700) + 700;
            Log.d(MainActivity.APP_TAG, "add: " + Integer.toString(i));
            if(RR.get(0).size()==30) {
                RR.get(0).remove(0);
            }
            RR.get(0).add(i);

            int j = rand.nextInt(1000);
            if(RR.get(1).size()==30) {
                RR.get(1).remove(0);
            }
            RR.get(1).add(j);

            try{
                Log.d(MainActivity.APP_TAG, "getBarChart");
            }catch(Exception e){

            }
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    vRRChart = getLineChart(RR);
                    llBarChart.removeAllViews();
                    llBarChart.addView(vRRChart, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, 500));
                }
            });
        }
    };

    protected XYMultipleSeriesRenderer buildLineRenderer(int[] colors) {
        XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
        renderer.setAxisTitleTextSize(16);
        renderer.setChartTitleTextSize(20);
        renderer.setLabelsTextSize(15);
        renderer.setLegendTextSize(15);
        int length = colors.length;
        for (int i = 0; i < length; i++) {
            XYSeriesRenderer r = new XYSeriesRenderer();
            r.setColor(colors[i]);
            r.setLineWidth(4f);
            renderer.addSeriesRenderer(r);
        }
        return renderer;
    }

    protected void setChartSettings(XYMultipleSeriesRenderer renderer, String title, String xTitle,
                                    String yTitle, double xMin, double xMax, double yMin, double yMax, int axesColor,
                                    int labelsColor) {
        renderer.setChartTitle(title);
        renderer.setXTitle(xTitle);
        renderer.setYTitle(yTitle);
        renderer.setXAxisMin(xMin);
        renderer.setXAxisMax(xMax);
        renderer.setYAxisMin(yMin);
        renderer.setYAxisMax(yMax);
        renderer.setAxesColor(axesColor);
        renderer.setLabelsColor(labelsColor);
    }

    public XYMultipleSeriesDataset buildLineDataset(String[] titles, ArrayList<ArrayList<Integer>> values) {
        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
        for(int i=0;i<titles.length;i++) {
            CategorySeries series = new CategorySeries(titles[i]);
            int seriesLength = values.get(i).size();
            //Log.d(MainActivity.APP_TAG, "length: " + Integer.toString(values.size()));
            for (int k = 0; k < seriesLength; k++) {
                series.add(values.get(i).get(k));
            }
            dataset.addSeries(series.toXYSeries());
        }
        return dataset;
    }

    private GraphicalView getLineChart(ArrayList<ArrayList<Integer>> RR){
        String[] titles = {"A","B"};
        int[] lineColors = new int[] { Color.CYAN, Color.GREEN};

        XYMultipleSeriesRenderer renderer = buildLineRenderer(lineColors);
        renderer.setOrientation(XYMultipleSeriesRenderer.Orientation.HORIZONTAL);

        setChartSettings(renderer, "", "", "", 0.5,
                30.5, 0, 1000, Color.GRAY, Color.RED);
        renderer.setXLabels(1);
        renderer.setYLabels(10);
        XYSeriesRenderer seriesRenderer = (XYSeriesRenderer) renderer.getSeriesRendererAt(0);
        seriesRenderer.setDisplayChartValues(true);
        return ChartFactory.getLineChartView(this.getActivity(), buildLineDataset(titles, RR), renderer);
    }
}

package com.example.cyrinehammmi.androidchart;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.RelativeLayout;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.Legend.LegendForm;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

public class MainActivity extends Activity {

    private RelativeLayout chartActivity;
    private LineChart mchart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        chartActivity = (RelativeLayout) findViewById(R.id.mainLayout);
/*
* code in comment can be used in diffrent version  .09*/
        //create line charte
        mchart = new LineChart(this);

        //add to main layout
        chartActivity.addView(mchart);
        //Description d = new Description() ;
        //d.setText("Description Here !");
        //customize line Chart
        mchart.setDescription("Description Here !");
        mchart.setNoDataTextDescription("No data for the moment ");

        //Enable value highlighting

        mchart.setMinimumHeight(1100);
        mchart.setMinimumWidth(1090);
        //mchart.setHighLightEnabled(true);
        mchart.setTouchEnabled(true);
        mchart.setDragEnabled(true);
        mchart.setScaleEnabled(true);
        mchart.setDrawGridBackground(false);

        //enable pinch zoom to avoid scaling x and y axis separately
        mchart.setPinchZoom(true);

        //alternative background color
        mchart.setBackgroundColor(Color.LTGRAY);


        // now we work with data
        LineData data = new LineData();
        data.setValueTextColor(Color.BLACK);

        //ADD DATA TO LINE CHART
        mchart.setData(data);

        //get Legend Object
        Legend l =mchart.getLegend();
        //customize legend
        l.setForm(LegendForm.LINE);
        l.setTextColor(Color.WHITE);

        //CUSTOMIZE L'AXE DES ABSCISSE
        XAxis x1 =mchart.getXAxis();
        x1.setTextColor(Color.BLUE);
        x1.setDrawGridLines(false);
        //x1.setAxisMaximum(120f);
        //x1.setAxisMinimum(0f);
        x1.setAvoidFirstLastClipping(true);

        //CUSTOMIZE L'AXE DES CORDONNES
        YAxis y1 =mchart.getAxisLeft();
        y1.setTextColor(Color.WHITE);
        y1.setDrawGridLines(true);
        y1.setAxisMaxValue(120f);
        y1.setAxisMinValue(0f);

        //pour Ne pas fermer le rectangle puisique les donnes sont infinie  ...
        YAxis y12 =mchart.getAxisRight();
        y12.setEnabled(false);

    }

    @Override
    protected void onResume() {
        super.onResume();

        // now we're going t simulate real time data addition
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i =0 ; i < 100 ; i++ )
                {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            AddEntry();
                        }
                    });

                    //pausse between adds
                    try {
                        Thread.sleep(400);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    //method to add entry to the Line chart

    private void AddEntry()
    {
        LineData data = mchart.getData();

        if(data != null)
        {
            LineDataSet set = (LineDataSet) data.getDataSetByIndex(0);
            if (set == null )
            {
                set = createSet();
                data.addDataSet(set);
            }

            //create a new random value
            data.addXValue("");
            data.addEntry(new Entry((float) (Math.random()*120) + 5f ,set.getEntryCount()),0);


            // if data changed
            mchart.notifyDataSetChanged();

            //limit number of visible entries

            mchart.setVisibleXRange(6f);

            //scroll to the last entry
            mchart.moveViewToX(data.getXValCount() - 7);

        }
    }
    //method to create set
    private LineDataSet createSet()
    {
        LineDataSet set = new LineDataSet(null,"Description of the Line");

        set.setDrawCubic(true);
        set.setCubicIntensity(0.2f);
        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        set.setColor((ColorTemplate.getHoloBlue()));
        set.setCircleColor(ColorTemplate.getHoloBlue());
        set.setLineWidth(2f);
        set.setCircleSize(4f);
        set.setFillAlpha(65);
        set.setFillColor(ColorTemplate.getHoloBlue());
        set.setHighLightColor(Color.rgb(244,117,177));
        set.setValueTextColor(Color.WHITE);
        set.setValueTextSize(10f);

        return set ;
    }

}

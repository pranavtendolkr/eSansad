package com.persistent.esansad.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.interfaces.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.Legend;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.koushikdutta.ion.Ion;
import com.persistent.esansad.EsansadTabActivity;
import com.persistent.esansad.R;

import com.persistent.esansad.helper.APIHelper;
import com.persistent.esansad.helper.MemberofParliament;
import com.persistent.esansad.helper.Proposal;

public class DashboardFragment extends Fragment {

    protected TextView mName;
    protected TextView mConstituency;
    protected TextView mAmtAllocated;
    protected TextView mAmtUsed;
    protected TextView mAmtRemaining;
    protected HashMap<Integer,String> mProjects;
    protected HashMap<Integer,String> mProposals;

    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dashboard,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        createBarChart();
        createMpInfoData();
        createPieChartProjects();
        createPieChartProposals();
    }

    private void createMpInfoData() {

        mName = (TextView) getActivity().findViewById(R.id.tvMpName);
        mConstituency = (TextView) getActivity().findViewById(R.id.tvConstituency);
        mAmtAllocated = (TextView) getActivity().findViewById(R.id.tvAmountAllocated);
        mAmtUsed = (TextView) getActivity().findViewById(R.id.tvExpenditureIncurred);
        mAmtRemaining = (TextView) getActivity().findViewById(R.id.tvAmountUnspent);
        ImageView mpImage = (ImageView) getActivity().findViewById(R.id.ivMpPhoto);


        //Populate MP's data
        MemberofParliament mp=APIHelper.getMP(getActivity(), "Goa", "North Goa");
        String name=mp.getFirst_name()+" "+mp.getLast_name();
        String cons=mp.getConstituency();
        String total= String.valueOf(mp.getTotal());
        String incurred=String.valueOf(mp.getExpenditure_incurred());
        String unspent=String.valueOf(mp.getUnspent_balance());
        //String url=mp.getImageUrl();
//        List<Proposal> p=APIHelper.getProposalsByCategory(getActivity(),"Goa","North Goa","Sports");
//        for (Proposal p1 :p)
//            Log.d("API GET ALL PROJECTS",p1.getCategory());




        //mName.setText("Narendra Sawaiker");
//        mConstituency.setText("South Goa");
//        mAmtAllocated.setText("5 Cr");
//        mAmtUsed.setText("1.1 Cr");
//        mAmtRemaining.setText("3.9 Cr");
        mName.setText(name);
        mConstituency.setText(cons);
        mAmtAllocated.setText(total+" Cr");
        mAmtUsed.setText(incurred+" Cr");
        mAmtRemaining.setText(unspent+" Cr");
        mpImage.setImageResource(R.drawable.north_goa);

//        Ion.with(mpImage)
//                .placeholder(R.drawable.north_goa)
//                .error(R.drawable.north_goa)
//               // .animateLoad(spinAnimation)
//               // .animateIn(fadeInAnimation)
//                .load(url);

    }

    private void createPieChartProposals() {
        PieChart mChart = (PieChart) getActivity().findViewById(R.id.piechartProposals);

        mChart.setHoleRadius(50f);
        mChart.setDescription("");
        mChart.setDrawYValues(true);
        mChart.setDrawCenterText(true);
        mChart.setDrawHoleEnabled(true);
        mChart.setRotationAngle(0);
        mChart.setDrawXValues(true);
        mChart.setRotationEnabled(false);
        mChart.setUsePercentValues(true);
        mChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry entry, int i) {
                Intent intent=new Intent(getActivity().getApplicationContext(),EsansadTabActivity.class);
                intent.putExtra("category",mProposals.get(entry.getXIndex()));
                Toast.makeText(getActivity().getApplicationContext(),"ALL PROPOSALS",Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }

            @Override
            public void onNothingSelected() {

            }
        });
        mChart.setCenterText("Proposals");
        //Names
        Log.d("In Proposals PieChart","");
        List<Proposal> proposals=APIHelper.getAllProposals(getActivity(), "Goa", "North Goa");

        for (Proposal p:proposals)
        {

            if(p.getCategory().equals("Chocolates")){
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                proposals=APIHelper.getAllProposals(getActivity(), "Goa", "North Goa");
                break;
            }
        }
        for( Proposal p:proposals)
        {
            Log.d("Proposals ALL", p.getCategory());
        }
        HashMap<String,Double> categories=new HashMap<>();
        //Log.d("Original Data",projects.toString() );
        for(Proposal ppp:proposals)
        {
            Log.d("Original data",ppp.getCategory()+ppp.getCost());
        }
        List<Double> cost=new ArrayList<>();
        int i=0;
        for(Proposal finalP:proposals){
            if (i>2)
                break;
            double sum = 0.0;
            if (categories.get(finalP.getCategory()) != null)
                sum = categories.get(finalP.getCategory()) + finalP.getCost();
            else
                sum = finalP.getCost();
            categories.put(finalP.getCategory(), sum);
            //i++;

        }
        Log.d("HashMap",categories.toString());
        //ArrayList<String> xVals = new ArrayList<String>();
        //ArrayList<Entry> yVals1 = new ArrayList<Entry>();
        i=0;
        double sum=0.0;
        for(double val:categories.values())
        {
            sum=val+sum;
        }
        ArrayList<String> xVals = new ArrayList<String>();
        ArrayList<Entry> yVals1 = new ArrayList<Entry>();
        mProposals=new HashMap<>();
        for(String key :categories.keySet())
        {
            xVals.add(key);
            mProposals.put(i,key);
            float f=Float.parseFloat(String.valueOf(categories.get(key)));
            float percentage=(float)(f/sum)*100;
            yVals1.add(new Entry(percentage,i));
            i++;

        }





//       ArrayList<String> xVals = new ArrayList<String>();
//        xVals.add("Education");
//        xVals.add("Health");
//        xVals.add("Irrigation");
//        xVals.add("Sports");
//        xVals.add("Infrastrcuture");

        //Values
//         ArrayList<Entry> yVals1 = new ArrayList<Entry>();
//        yVals1.add(new Entry(25.0f,0));
//        yVals1.add(new Entry(15.0f,1));
//        yVals1.add(new Entry(22.0f,2));
//        yVals1.add(new Entry(20.0f,3));
//        yVals1.add(new Entry(18.0f,4));

        PieDataSet set1 = new PieDataSet(yVals1, "Proposals Data");
        int[] colors=new int[]{Color.rgb(54, 169, 225),Color.rgb(189, 234, 116),Color.rgb(255, 84, 84),Color.rgb(250, 187, 61),Color.rgb(103, 194, 239)};

        set1.setColors(colors);

        PieData data = new PieData(xVals, set1);
        mChart.setData(data);
        mChart.animateXY(1500, 1500);
        Legend l = mChart.getLegend();
        l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(5f);
        //mChart.

    }

    private void createPieChartProjects() {

        PieChart mChart = (PieChart) getActivity().findViewById(R.id.piechartProjects);

        mChart.setHoleRadius(50f);
        mChart.setDescription("");
        mChart.setDrawYValues(true);
        mChart.setDrawCenterText(true);
        mChart.setDrawHoleEnabled(true);
        mChart.setRotationAngle(0);
        mChart.setRotationEnabled(false);
        mChart.setDrawLegend(true);
        mChart.setUnit("₹");
        // draws the corresponding description value into the slice
        mChart.setDrawXValues(true);
        // enable rotation of the chart by touch
        mChart.setRotationEnabled(true);
        // display percentage values
        mChart.setUsePercentValues(true);
        // mChart.setUnit(" €");
        // mChart.setDrawUnitsInChart(true);
        // add a selection listener
        mChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry entry, int i) {

                Intent intent=new Intent(getActivity().getApplicationContext(),EsansadTabActivity.class);
                intent.putExtra("category",mProjects.get(entry.getXIndex()));
//                Toast.makeText(getActivity().getApplicationContext(),"ALL PROJECTS",Toast.LENGTH_SHORT).show();
                startActivity(intent);
                Toast.makeText(getActivity().getApplicationContext(),"ALL PROJECTS",Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected() {

            }
        });
        // mChart.setTouchEnabled(false);

        mChart.setCenterText("Projects");

        //Names
        List<Proposal> projects=APIHelper.getAllProjects(getActivity(),"Goa","North Goa");


        for (Proposal p:projects)
        {
            if(p.getCategory().equals("Chocolates")){
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                projects=APIHelper.getAllProjects(getActivity(),"Goa","North Goa");
                break;
            }
        }
        for(Proposal ppp:projects)
        {
            Log.d("Original data",ppp.getCategory()+ppp.getCost());
        }



        HashMap<String,Double> categories=new HashMap<>();
        //Log.d("Original Data",projects.toString() );
        for(Proposal ppp:projects)
        {
            Log.d("Original data",ppp.getCategory()+ppp.getCost());
        }
        List<Double> cost=new ArrayList<>();
        int i=0;
        for(Proposal finalP:projects){
                if (i>2)
                    break;
                double sum = 0.0;
                if (categories.get(finalP.getCategory()) != null)
                    sum = categories.get(finalP.getCategory()) + finalP.getCost();
                else
                    sum = finalP.getCost();
                categories.put(finalP.getCategory(), sum);
            //i++;

        }
        Log.d("HashMap",categories.toString());
        ArrayList<String> xVals = new ArrayList<String>();
        ArrayList<Entry> yVals1 = new ArrayList<Entry>();
        i=0;
        double sum=0.0;
        for(double val:categories.values())
        {
            sum=val+sum;
        }
        mProjects=new HashMap<>();
        for(String key :categories.keySet())
        {
            xVals.add(key);
            mProjects.put(i,key);
            float f=Float.parseFloat(String.valueOf(categories.get(key)));
            float percentage=(float)(f/sum)*100;
            yVals1.add(new Entry(percentage,i));
            i++;
        }

//



//        xVals.add("Health");
//        xVals.add("Irrigation");
//        xVals.add("Sports");
//        xVals.add("Infrastrcuture");

        //Values

//        yVals1.add(new Entry(25.0f,0));
//        yVals1.add(new Entry(15.0f,1));
//        yVals1.add(new Entry(19.0f,2));
//        yVals1.add(new Entry(21.0f,3));
//        yVals1.add(new Entry(20.0f,4));

        PieDataSet set1 = new PieDataSet(yVals1, "Projects Data");
        int[] colors=new int[]{Color.rgb(54, 169, 225),Color.rgb(189, 234, 116),Color.rgb(255, 84, 84),Color.rgb(250, 187, 61),Color.rgb(103, 194, 239),Color.rgb(239,153,239)};

        set1.setColors(colors);

        PieData data = new PieData(xVals, set1);
        mChart.setData(data);
        mChart.animateXY(1500, 1500);
        Legend l = mChart.getLegend();
        l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(5f);


    }

    private void createBarChart()
    {
        BarChart barChart=(BarChart)getActivity().findViewById(R.id.barchart);
        ArrayList<String> xVals = new ArrayList<String>();
        xVals.add("2014");
        xVals.add("2013");
        xVals.add("2012");
        xVals.add("2011");

        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();
        yVals1.add(new BarEntry(new float[] {1.0f,4.0f}, 0));
        yVals1.add(new BarEntry(new float[] {0.6f, 4.4f}, 1));
        yVals1.add(new BarEntry(new float[] {2.2f, 2.8f}, 2));
        yVals1.add(new BarEntry(new float[] {1.2f, 3.8f}, 3));

        BarDataSet set1 = new BarDataSet(yVals1, "");
        int[] colors=new int[]{Color.rgb(255, 84, 840),Color.rgb(121, 196, 71),};


        set1.setBarSpacePercent(45);
        set1.setColors(colors);
        set1.setStackLabels(new String[] {
                "Unspent",  "Spent     Note: All amounts in Crores"
        });
        ArrayList<BarDataSet> dataSets = new ArrayList<BarDataSet>();
        dataSets.add(set1);
        barChart.setDrawYValues(true);
        barChart.set3DEnabled(true);
        barChart.setDrawBarShadow(true);
        barChart.setDescription("");
        barChart.setMaxVisibleValueCount(500);
        barChart.setUnit("₹");
        barChart.setBorderColor(Color.BLACK);
        barChart.setDrawYLabels(true);
        barChart.setHighlightIndicatorEnabled(false);
        barChart.setHighlightEnabled(false);
        barChart.setPinchZoom(false);


        // if false values are only drawn for the stack sum, else each value is drawn
        // disable 3D
        barChart.set3DEnabled(false);
        // scaling can now only be done on x- and y-axis separately
        barChart.setPinchZoom(false);
        barChart.setDrawBarShadow(false);
        BarData data = new BarData(xVals, dataSets);
        Log.d("Data ", data.toString());
        barChart.setData(data);
    }

}

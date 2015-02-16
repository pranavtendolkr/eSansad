package com.persistent.esansad.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;

import com.persistent.esansad.NewProposal;
import com.persistent.esansad.R;
import com.persistent.esansad.adapter.ProposalCollectionPagerAdapter;


public class EsansadTab extends Fragment {


    ViewPager mViewPager;

    public static EsansadTab newInstance(String type) {
        EsansadTab myFragment = new EsansadTab();
        Bundle args = new Bundle();
        args.putString("type", type);
        myFragment.setArguments(args);
        return myFragment;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_esansad_tab, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ViewPager pager = (ViewPager) getActivity().findViewById(R.id.pager);
        pager.setAdapter(new ProposalCollectionPagerAdapter(getActivity().getSupportFragmentManager(),getArguments().getString("type")));

        PagerSlidingTabStrip tabs = (PagerSlidingTabStrip) getActivity().findViewById(R.id.tabs);
        tabs.setViewPager(pager);

        getActivity().findViewById(R.id.addProposalButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getActivity(), NewProposal.class);
                startActivity(i);
            }
        });


    }

    @Override
    public void onResume() {
        super.onResume();
    }


}

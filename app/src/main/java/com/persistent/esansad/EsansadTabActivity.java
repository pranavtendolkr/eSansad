package com.persistent.esansad;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.astuetz.PagerSlidingTabStrip;
import com.persistent.esansad.adapter.ProposalCollectionPagerAdapter;
import com.persistent.esansad.constants.Constants;


public class EsansadTabActivity extends FragmentActivity {

    // When requested, this adapter returns a DemoObjectFragment,
    // representing an object in the collection.

   // DemoCollectionPagerAdapter mDemoCollectionPagerAdapter;
    ViewPager mViewPager;
    String fragCat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_esansad_tab);


        // ViewPager and its adapters use support library
        // fragments, so use getSupportFragmentManager.

        ViewPager pager = (ViewPager) findViewById(R.id.pager);

        pager.setAdapter(new ProposalCollectionPagerAdapter(getSupportFragmentManager(), Constants.IS_PROJECT,fragCat));

        PagerSlidingTabStrip tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        tabs.setViewPager(pager);

        findViewById(R.id.addProposalButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplication(), NewProposal.class);
                startActivity(i);
            }
        });
    }
}

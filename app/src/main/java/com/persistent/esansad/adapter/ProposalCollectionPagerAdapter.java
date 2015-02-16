package com.persistent.esansad.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.persistent.esansad.MpCardsView;
import com.persistent.esansad.MyCardsView;
import com.persistent.esansad.PeopleCardsView;

/**
 * Created by jeetendra_nayak on 2/7/2015.
 */
public class ProposalCollectionPagerAdapter extends FragmentStatePagerAdapter {

    private final int PAGES = 3;
    private String[] titles={"People", "MP", "My"};
    private String proposalType;

    public ProposalCollectionPagerAdapter(android.support.v4.app.FragmentManager fm,String type) {
        super(fm);
        proposalType=type;
    }

    public ProposalCollectionPagerAdapter(android.support.v4.app.FragmentManager fm,String type,String cat) {
        super(fm);
        proposalType=type;
    }





    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:

                return PeopleCardsView.newInstance(proposalType);
            case 1:

                return  MyCardsView.newInstance(proposalType);
            case 2:

                return MpCardsView.newInstance(proposalType);
            default:
                throw new IllegalArgumentException("The item position should be less or equal to:" + PAGES);
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

    @Override
    public int getCount() {
        return PAGES;
    }
}

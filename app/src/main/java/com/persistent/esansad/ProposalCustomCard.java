package com.persistent.esansad;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.persistent.esansad.helper.Proposal;

import it.gmariotti.cardslib.library.internal.Card;

/**
 * Created by ashwin_valento on 2/7/2015.
 */
public class ProposalCustomCard extends Card {

    protected TextView mTitle;
    protected TextView mLocation;
    protected TextView mBudget;
    protected TextView mLikes;
    protected TextView mDislikes;

    public Proposal proposal;

    public ProposalCustomCard(Context context,Proposal proposal) {
        this(context, R.layout.layout_proposal_card);
        this.proposal = proposal;
    }

    /**
     * Constructor with a custom inner layout
     * @param context
     */
    public ProposalCustomCard(Context context) {
        this(context, R.layout.layout_proposal_card);
    }

    /**
     *
     * @param context
     * @param innerLayout
     */
    public ProposalCustomCard(Context context, int innerLayout) {
        super(context, innerLayout);
        init();
    }

    /**
     * Init
     */
    private void init(){

        //No Header

        //Set a OnClickListener listener
        setOnClickListener(new OnCardClickListener() {
            @Override
            public void onClick(Card card, View view) {
                //Toast.makeText(getContext(), "Click Listener card=", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getContext(), ProposalDetailsActivity.class);
               intent.putExtra("proposal",proposal);
               getContext().startActivity(intent);
               //Log.d("tag",proposal.getTitle());
            }
        });
    }

    @Override
    public void setupInnerViewElements(ViewGroup parent, View view) {

        //Retrieve elements
        mTitle = (TextView) parent.findViewById(R.id.tvProposalTitle);
        mLocation= (TextView) parent.findViewById(R.id.tvProposalLocation);
        mBudget= (TextView) parent.findViewById(R.id.tvProposalBudget);
        mLikes= (TextView) parent.findViewById(R.id.tvProposalLikes);
        mDislikes= (TextView) parent.findViewById(R.id.tvProposalDislikes);



        if (mTitle!=null)
            mTitle.setText(proposal.getTitle());

        if (mLocation!=null)
            mLocation.setText(proposal.getConstituency());

        if (mBudget!=null)
            mBudget.setText(String.valueOf(proposal.getCost()));

        if (mLikes!=null)
            mLikes.setText(String.valueOf(proposal.getUpvotes()));

        if (mDislikes!=null)
            mDislikes.setText(String.valueOf(proposal.getDownvotes()));



    }
}

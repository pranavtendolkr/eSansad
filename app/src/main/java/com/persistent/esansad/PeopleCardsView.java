package com.persistent.esansad;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.persistent.esansad.constants.Constants;
import com.persistent.esansad.helper.APIHelper;
import com.persistent.esansad.helper.Proposal;

import java.util.ArrayList;
import java.util.List;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardArrayAdapter;
import it.gmariotti.cardslib.library.view.CardListView;

/**
 * Created by ashwin_valento on 2/7/2015.
 */
public class PeopleCardsView extends Fragment {

    public static PeopleCardsView newInstance(String type) {
        PeopleCardsView myFragment = new PeopleCardsView();
        Bundle args = new Bundle();
        args.putString("type", type);
        myFragment.setArguments(args);
        return myFragment;
    }

    public static PeopleCardsView newInstance(String type,String cat) {
        PeopleCardsView myFragment = new PeopleCardsView();
        Bundle args = new Bundle();
        args.putString("type", type);
        args.putString("cat", cat);
        myFragment.setArguments(args);
        return myFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_peopleview_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        List<Proposal> proposalList=new ArrayList<Proposal>();
       if(getArguments().getString("type").equals(Constants.IS_PROJECT)){
           proposalList = APIHelper.getAllProjects(this.getActivity(), "goa", "North Goa");
       }else{
           proposalList = APIHelper.getAllProposals(this.getActivity(), "goa", "North Goa");
       }


        ArrayList<Card> cards = new ArrayList<Card>();
        for (Proposal proposal : proposalList){
       // for (int i=0;i<10;i++) {
            //Create a Card
           /* Proposal proposal = new Proposal();
            proposal.setTitle("title of Proposal"+ i);
            proposal.setConstituency("location of " + i);
            proposal.setCost(i * 2000);
            proposal.setUpvotes(10 + i);
            proposal.setDownvotes(20 + i);*/

            ProposalCustomCard card = new ProposalCustomCard(getActivity(),proposal);
           // Card card=new Card(getActivity());
            cards.add(card);
        }

        CardArrayAdapter mCardArrayAdapter = new CardArrayAdapter(getActivity(),cards);

       CardListView listView = (CardListView) getActivity().findViewById(R.id.myList);
        if (listView!=null){
            listView.setAdapter(mCardArrayAdapter);
        }

    }
}

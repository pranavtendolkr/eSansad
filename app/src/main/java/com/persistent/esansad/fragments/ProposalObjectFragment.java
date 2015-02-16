package com.persistent.esansad.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.persistent.esansad.R;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DemoObjectFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DemoObjectFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProposalObjectFragment extends Fragment {

    public static final String ARG_OBJECT = "object";





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_proposal_object, container, false);
       /* Bundle args = getArguments();
        ((TextView) rootView.findViewById(android.R.id.text1)).setText(
                Integer.toString(args.getInt(ARG_OBJECT)));*/
        return rootView;
    }





}

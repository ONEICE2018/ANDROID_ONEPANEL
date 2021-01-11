package com.oneice.onepanel.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.oneice.onepanel.R;
import com.oneice.onepanel.activity_communication.RefrashFregments;

public class FragmentWarning extends OneLifeFragment implements RefrashFregments {
    public View onCreateView(LayoutInflater inflater , ViewGroup container, Bundle savedInstanceState){
        View view=inflater .inflate(R.layout .fragment_warring ,container,false) ;
        refrashinit(view);
        return view;
    }

    @Override
    public void refrashinterface(String refname) {

    }

    @Override
    public void refrashinit(View view) {

    }

}

package com.oneice.onepanel.Fragments;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

public class OneLifeFragment extends Fragment {
    public boolean isFragmentLife() {
        return fragmentLife;
    }

    public void setFragmentLife(boolean fragmentLife) {
        this.fragmentLife = fragmentLife;
    }
    public boolean fragmentLife=false;



}

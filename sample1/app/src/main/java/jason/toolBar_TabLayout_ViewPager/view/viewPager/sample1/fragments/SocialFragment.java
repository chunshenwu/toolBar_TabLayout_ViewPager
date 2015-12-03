package jason.toolBar_TabLayout_ViewPager.view.viewPager.sample1.fragments;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import jason.toolBar_TabLayout_ViewPager.R;
import jason.toolBar_TabLayout_ViewPager.view.viewPager.base.uiFragment.BaseFragment;

/**
 * Created by justin on 11/8/15.
 */
public class SocialFragment extends BaseFragment  {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(getTAG(), this.getClass().getSimpleName() + " onCreateView");
        return inflater.inflate(R.layout.fragment_4, container, false);
    }

    @Override
    public void onTabChangedResume() {
        Log.d(getTAG(), "onTabChangedResume ");
    }

    @Override
    public void onTabChangedPause() {
        Log.d(getTAG(), "onTabChangedPause ");
    }

    @Override
    protected String getTAG() {
        return SocialFragment.class.getSimpleName();
    }
}

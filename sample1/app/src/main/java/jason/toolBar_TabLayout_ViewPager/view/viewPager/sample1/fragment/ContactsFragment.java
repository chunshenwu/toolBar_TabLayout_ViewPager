package jason.toolBar_TabLayout_ViewPager.view.viewPager.sample1.fragment;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import jason.toolBar_TabLayout_ViewPager.R;
import jason.toolBar_TabLayout_ViewPager.view.viewPager.base.fragment.BaseFragment;

/**
 * Created by justin on 11/8/15.
 */
public class ContactsFragment extends BaseFragment  {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(getTAG(), this.getClass().getSimpleName() + " onCreateView");
        return inflater.inflate(R.layout.fragment_1, container, false);
    }

    @Override
    protected void onTabChangedResume() {
        Log.d(getTAG(), "onTabChangedResume ");
    }

    @Override
    public void onTabChangedPause() {
        Log.d(getTAG(), "onTabChangedPause ");
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_1;
    }

    @Override
    protected String getTAG() {
        return ContactsFragment.class.getSimpleName();
    }
}

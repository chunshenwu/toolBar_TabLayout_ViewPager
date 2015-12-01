package jason.toolBar_TabLayout_ViewPager.view.viewPager.sample1.fragment;


import android.util.Log;

import jason.toolBar_TabLayout_ViewPager.R;
import jason.toolBar_TabLayout_ViewPager.view.viewPager.base.fragment.BaseFragment;

/**
 * Created by justin on 11/8/15.
 */
public class BulletinFragment extends BaseFragment  {

    @Override
    public void onTabChangedResume() {
        Log.d(getTAG(), "onTabChangedResume ");
    }

    @Override
    public void onTabChangedPause() {
        Log.d(getTAG(), "onTabChangedPause ");
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_3;
    }

    @Override
    protected String getTAG() {
        return BulletinFragment.class.getSimpleName();
    }
}

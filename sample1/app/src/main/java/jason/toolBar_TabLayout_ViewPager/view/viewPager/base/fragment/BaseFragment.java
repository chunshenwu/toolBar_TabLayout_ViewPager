package jason.toolBar_TabLayout_ViewPager.view.viewPager.base.fragment;

import android.support.v4.app.Fragment;
import android.util.Log;

/**
 * Created by justin on 11/16/15.
 */
public abstract class BaseFragment extends Fragment{

    protected abstract String getTAG();

    //FragmentLifeCycle
    protected abstract void onTabChangedResume();
    protected abstract void onTabChangedPause();

    private boolean mIsOnTabChangedResume = false;

    @Override
    public void onResume() {
        Log.i(getTAG(), "onResume >");
        super.onResume();
        if(getUserVisibleHint()){
            performTabResume();
        }
        Log.i(getTAG(), "onResume <");
    }


    public void performTabResume() {
        if (!mIsOnTabChangedResume) {
            mIsOnTabChangedResume = true;
            onTabChangedResume();
        }
    }

    public void performTabPause() {
        if (mIsOnTabChangedResume) {
            mIsOnTabChangedResume = false;
            onTabChangedPause();
        }
    }

    @Override
    public void onPause() {
        Log.i(getTAG(), "onPause >");
        super.onPause();
        if(getUserVisibleHint()){
            performTabPause();
        }
        Log.i(getTAG(), "onPause <");
    }

    @Override
    public void onDestroy() {
        Log.d(getTAG(), "onDestroy ");
        super.onDestroy();
    }
}
package jason.toolBar_TabLayout_ViewPager.view.viewPager.base.uiFragment;

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
    final public void onResume() {
        Log.i(getTAG(), "onResume >");
        super.onResume();
        if(getUserVisibleHint()){
            performTabResume();
        }
        Log.i(getTAG(), "onResume <");
    }

    final public void performTabResume() {
        if (!mIsOnTabChangedResume) {
            mIsOnTabChangedResume = true;
            onTabChangedResume();
        }
    }

    final public void performTabPause() {
        if (mIsOnTabChangedResume) {
            mIsOnTabChangedResume = false;
            onTabChangedPause();
        }
    }

    @Override
    final public void onPause() {
        Log.i(getTAG(), "onPause >");
        super.onPause();
        if(getUserVisibleHint()){
            performTabPause();
        }
        Log.i(getTAG(), "onPause <");
    }

    @Override
    final public void onDestroy() {
        Log.d(getTAG(), "onDestroy ");
        super.onDestroy();
    }
}
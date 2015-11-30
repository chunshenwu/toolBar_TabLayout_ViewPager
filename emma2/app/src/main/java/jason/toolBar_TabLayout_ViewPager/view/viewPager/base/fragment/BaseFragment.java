package jason.toolBar_TabLayout_ViewPager.view.viewPager.base.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by justin on 11/16/15.
 */
public abstract class BaseFragment extends Fragment implements FragmentLifeCycle, LayoutProvide{

    protected abstract String getTAG();

    private boolean mIsOnTabChangedResume = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(getTAG(), this.getClass().getSimpleName() + " onCreateView");
        return inflater.inflate(getLayoutId(), container, false);
    }

    @Override
    public void onResume() {
        Log.d(getTAG(), "onResume ");
        super.onResume();
        if(getUserVisibleHint() && !mIsOnTabChangedResume){
            mIsOnTabChangedResume = true;
            Log.d(getTAG(), ">>onTabChangedResume ");
            onTabChangedResume();
        }
    }

    @Override
    public void onPause() {
        Log.d(getTAG(), "onPause ");
        super.onPause();
        if(getUserVisibleHint()){
            mIsOnTabChangedResume =false;
            Log.d(getTAG(), ">>onTabChangedPause ");
            onTabChangedPause();
        }
    }

    @Override
    public void onDestroy() {
        Log.d(getTAG(), "onDestroy ");
        super.onDestroy();
    }
}

interface FragmentLifeCycle {
    void onTabChangedResume();
    void onTabChangedPause();
}

interface LayoutProvide {
    int getLayoutId();
}

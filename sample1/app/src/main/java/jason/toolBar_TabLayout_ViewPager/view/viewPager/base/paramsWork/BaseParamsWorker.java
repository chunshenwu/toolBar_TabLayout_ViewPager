package jason.toolBar_TabLayout_ViewPager.view.viewPager.base.paramsWork;


import android.content.Context;

import jason.toolBar_TabLayout_ViewPager.view.viewPager.base.fragment.BaseFragment;

/**
 * Created by cs on 2015/11/29.
 */
public abstract class BaseParamsWorker implements IToolBarParamsWork, ITabLayoutParamsWork, IViewPagerParamsWork {

    private BaseFragment mBaseFragment = null;
    private final Context mContext;
    private int mPosition;

    public BaseParamsWorker(final Context context) {
        mContext = context;
    }

    public Context getContext() {
        return mContext;
    }

    public BaseFragment reAssigntFragment() {
        mBaseFragment = getNewFragment();
        return mBaseFragment;
    }

    public BaseFragment getFragment() {
        if (mBaseFragment == null) {
            mBaseFragment = getNewFragment();
        }
        return mBaseFragment;
    }

    public void setPosition(final int position) {
        mPosition = position;
    }

    public int getPosition() {
        return mPosition;
    }
}

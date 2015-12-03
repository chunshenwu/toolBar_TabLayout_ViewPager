package jason.toolBar_TabLayout_ViewPager.view.viewPager.base.miscKeeper;


import android.content.Context;

import jason.toolBar_TabLayout_ViewPager.view.viewPager.base.uiFragment.BaseFragment;

/**
 * Created by cs on 2015/11/29.
 */
public abstract class BaseViewMiscKeeper implements IToolBarMisc, ITabLayoutMisc, IViewPagerMisc {

    private final Context mContext;
    private BaseFragment mBaseFragment = null;
    private int mPosition;

    public BaseViewMiscKeeper (final Context context) {
        mContext = context;
    }

    final protected Context getContext() {
        return mContext;
    }

    final public BaseFragment reAssigntFragment() {
        mBaseFragment = getNewFragment();
        return mBaseFragment;
    }

    final public BaseFragment getFragment() {
        if (mBaseFragment == null) {
            mBaseFragment = getNewFragment();
        }
        return mBaseFragment;
    }

    final public void setPosition(final int position) {
        mPosition = position;
    }

    final public int getPosition() {
        return mPosition;
    }
}

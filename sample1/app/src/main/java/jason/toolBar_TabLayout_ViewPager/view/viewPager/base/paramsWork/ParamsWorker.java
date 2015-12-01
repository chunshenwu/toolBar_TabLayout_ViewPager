package jason.toolBar_TabLayout_ViewPager.view.viewPager.base.paramsWork;


import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;

import jason.toolBar_TabLayout_ViewPager.view.viewPager.base.fragment.BaseFragment;

/**
 * Created by cs on 2015/11/29.
 */
public abstract class ParamsWorker implements IToolBarParamsWork, ITabLayoutParamsWork, IViewPagerParamsWork {

    private BaseFragment mBaseFragment = null;
    private final Context mContext;

    public ParamsWorker(final Context context) {
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
        if (mBaseFragment != null) {
            mBaseFragment = getNewFragment();
        }
        return mBaseFragment;
    }


    //FIXME:
    @Override
    public int getTitleTypeface() {
        return Typeface.BOLD;
    }

    @Override
    public int getTitleTextSize() {
        return 20;
    }

    @Override
    public int getTitleTextColor() {
        return Color.BLACK;
    }

    @Override
    public int getBackgroundColor() {
        return Color.BLUE;
    }


}

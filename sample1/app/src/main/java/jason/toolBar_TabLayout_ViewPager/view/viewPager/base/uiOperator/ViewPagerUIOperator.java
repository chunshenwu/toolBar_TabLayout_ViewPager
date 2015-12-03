package jason.toolBar_TabLayout_ViewPager.view.viewPager.base.uiOperator;

import android.support.v4.view.ViewPager;

import java.util.HashMap;

import jason.toolBar_TabLayout_ViewPager.view.viewPager.base.miscKeeper.BaseViewMiscKeeper;

/**
 * Created by jason on 2015/12/1.
 */
public class ViewPagerUIOperator extends BaseUIOperator {

    private final HashMap<Integer, BaseViewMiscKeeper> mHashMap;
    private final ViewPager mViewPager;

    public ViewPagerUIOperator(final HashMap<Integer, BaseViewMiscKeeper> hashMap, final ViewPager viewPager) {
        mHashMap = hashMap;
        mViewPager = viewPager;
    }

    public void init() {

    }

    @Override
    public void setKeeper(BaseViewMiscKeeper currentParamsKeeper) {
        //ViewPager
        mViewPager.setCurrentItem(currentParamsKeeper.getPosition());
        //Fragment
        currentParamsKeeper.getFragment().performTabResume();
    }

    public void unSetKeeper(final BaseViewMiscKeeper paramsKeeper) {
        //ViewPager

        //Fragment;
        paramsKeeper.getFragment().performTabPause();
    }
}

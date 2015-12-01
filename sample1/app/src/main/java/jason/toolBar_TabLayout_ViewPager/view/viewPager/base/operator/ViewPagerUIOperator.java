package jason.toolBar_TabLayout_ViewPager.view.viewPager.base.operator;

import android.support.v4.view.ViewPager;

import java.util.HashMap;

import jason.toolBar_TabLayout_ViewPager.view.viewPager.base.paramsWork.BaseParamsWorker;

/**
 * Created by jason on 2015/12/1.
 */
public class ViewPagerUIOperator extends BaseUIOperator {

    private final HashMap<Integer, BaseParamsWorker> mHashMap;
    private final ViewPager mViewPager;

    public ViewPagerUIOperator(final HashMap<Integer, BaseParamsWorker> hashMap, final ViewPager viewPager) {
        mHashMap = hashMap;
        mViewPager = viewPager;
    }

    public void init() {

    }

    @Override
    public void setWorker(BaseParamsWorker currentParamsWorker) {
        //ViewPager
        mViewPager.setCurrentItem(currentParamsWorker.getPosition());
        //Fragment
        currentParamsWorker.getFragment().performTabPause();
    }

    public void cleanWorker(final BaseParamsWorker paramsWorker) {
        //ViewPager

        //Fragment;
        paramsWorker.getFragment().performTabPause();
    }
}

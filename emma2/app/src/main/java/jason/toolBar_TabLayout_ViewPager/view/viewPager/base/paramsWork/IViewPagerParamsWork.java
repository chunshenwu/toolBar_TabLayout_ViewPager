package jason.toolBar_TabLayout_ViewPager.view.viewPager.base.paramsWork;

import android.view.View;

import jason.toolBar_TabLayout_ViewPager.view.viewPager.base.fragment.BaseFragment;

/**
 * Created by cs on 2015/11/28.
 */


public interface IViewPagerParamsWork extends IBaseParamsWork {

    //For ViewPager
    View getOnSelectView();
    BaseFragment getNewFragment();

}

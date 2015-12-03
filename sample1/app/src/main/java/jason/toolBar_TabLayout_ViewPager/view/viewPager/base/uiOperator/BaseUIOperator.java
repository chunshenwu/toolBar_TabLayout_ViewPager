package jason.toolBar_TabLayout_ViewPager.view.viewPager.base.uiOperator;

import jason.toolBar_TabLayout_ViewPager.view.viewPager.base.miscKeeper.BaseViewMiscKeeper;

/**
 * Created by jason on 2015/12/1.
 */
public abstract class BaseUIOperator {

    public abstract void setKeeper(BaseViewMiscKeeper currentParamsKeeper);
    public abstract void unSetKeeper(BaseViewMiscKeeper oldParamsKeeper);
}

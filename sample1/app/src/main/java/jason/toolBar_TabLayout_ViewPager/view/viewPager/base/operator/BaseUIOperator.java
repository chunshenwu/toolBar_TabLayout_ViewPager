package jason.toolBar_TabLayout_ViewPager.view.viewPager.base.operator;

import jason.toolBar_TabLayout_ViewPager.view.viewPager.base.paramsWork.BaseParamsWorker;

/**
 * Created by jason on 2015/12/1.
 */
public abstract class BaseUIOperator {

    public abstract void setWorker(BaseParamsWorker currentParamsWorker);
    public abstract void cleanWorker(BaseParamsWorker oldParamsWorker);
}

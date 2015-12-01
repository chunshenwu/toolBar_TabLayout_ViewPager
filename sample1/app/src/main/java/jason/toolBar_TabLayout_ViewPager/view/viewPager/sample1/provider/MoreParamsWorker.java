package jason.toolBar_TabLayout_ViewPager.view.viewPager.sample1.provider;

import android.content.Context;
import android.support.v7.widget.Toolbar;
import android.view.View;

import jason.toolBar_TabLayout_ViewPager.R;
import jason.toolBar_TabLayout_ViewPager.view.viewPager.base.fragment.BaseFragment;
import jason.toolBar_TabLayout_ViewPager.view.viewPager.base.paramsWork.BaseParamsWorker;
import jason.toolBar_TabLayout_ViewPager.view.viewPager.sample1.fragment.MoreFragment;

/**
 * Created by cs on 15/11/28.
 */
public class MoreParamsWorker extends BaseParamsWorker {

    public MoreParamsWorker(Context conext) {
        super(conext);
    }

    @Override
    public String getOnSelectTittle() {
        return "More";
    }

    @Override
    public View.OnClickListener getNavigationIconOnClickListener() {
        return null;
    }

    @Override
    public Toolbar.OnMenuItemClickListener  getMenuOnMenuItemClickListener() {
        return null;
    }

    @Override
    public int getOnSelectNavigationIconResId() {
        return 0;
    }

    @Override
    public int getOnSelectMenuResId() {
        return 0;
    }

    @Override
    public int getOnSelectResId() {
        return R.drawable.in;
    }

    @Override
    public int getOnUnSelectResId() {
        return R.drawable.out;
    }

    @Override
    public View getOnSelectView() {
        return null;
    }

    @Override
    public BaseFragment getNewFragment() {
        return new MoreFragment();
    }

    @Override
    public String getTagName() {
        return this.getClass().getSimpleName();
    }

}

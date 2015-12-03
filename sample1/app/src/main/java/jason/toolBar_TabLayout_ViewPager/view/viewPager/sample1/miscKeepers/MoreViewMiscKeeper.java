package jason.toolBar_TabLayout_ViewPager.view.viewPager.sample1.miscKeepers;

import android.content.Context;
import android.view.MenuItem;
import android.view.View;

import jason.toolBar_TabLayout_ViewPager.R;
import jason.toolBar_TabLayout_ViewPager.view.viewPager.base.miscKeeper.BaseViewMiscKeeper;
import jason.toolBar_TabLayout_ViewPager.view.viewPager.base.uiFragment.BaseFragment;
import jason.toolBar_TabLayout_ViewPager.view.viewPager.sample1.fragments.MoreFragment;

/**
 * Created by cs on 15/11/28.
 */
public class MoreViewMiscKeeper extends BaseViewMiscKeeper {

    public MoreViewMiscKeeper(Context context) {
        super(context);
    }

    @Override
    public String getTittleText() {
        return "More";
    }

    @Override
    public int getNavigationIconResId() {
        return 0;
    }

    @Override
    public void onNavigationOnClick(View v) {

    }

    @Override
    public int getMenuResId() {
        return 0;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        return false;
    }

    @Override
    public int getBackgroundResIdSelected() {
        return R.drawable.in;
    }

    @Override
    public int getBackgroundResIdUnSelected() {
        return R.drawable.out;
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

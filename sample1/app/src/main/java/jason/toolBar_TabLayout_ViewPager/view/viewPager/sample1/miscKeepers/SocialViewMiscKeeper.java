package jason.toolBar_TabLayout_ViewPager.view.viewPager.sample1.miscKeepers;

import android.content.Context;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import jason.toolBar_TabLayout_ViewPager.R;
import jason.toolBar_TabLayout_ViewPager.view.viewPager.base.miscKeeper.BaseViewMiscKeeper;
import jason.toolBar_TabLayout_ViewPager.view.viewPager.base.uiFragment.BaseFragment;
import jason.toolBar_TabLayout_ViewPager.view.viewPager.sample1.fragments.SocialFragment;

/**
 * Created by cs on 15/11/28.
 */
public class SocialViewMiscKeeper extends BaseViewMiscKeeper {


    public SocialViewMiscKeeper(Context context) {
        super(context);
    }

    @Override
    public String getTittleText() {
        return "Social";
    }

    @Override
    public int getNavigationIconResId() {
        return R.drawable.ab_android;
    }

    @Override
    public void onNavigationOnClick(View v) {
        Toast.makeText(v.getContext(), "" + getTagName(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public int getMenuResId() {
        return R.menu.menu_social;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        Toast.makeText(getContext(), getTagName() + " " + item.getItemId(), Toast.LENGTH_SHORT).show();
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
        return new SocialFragment();
    }

    @Override
    public String getTagName() {
        return this.getClass().getSimpleName();
    }

}

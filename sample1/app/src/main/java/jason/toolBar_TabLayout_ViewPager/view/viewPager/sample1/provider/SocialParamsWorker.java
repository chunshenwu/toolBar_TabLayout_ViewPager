package jason.toolBar_TabLayout_ViewPager.view.viewPager.sample1.provider;

import android.content.Context;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import jason.toolBar_TabLayout_ViewPager.R;
import jason.toolBar_TabLayout_ViewPager.view.viewPager.base.fragment.BaseFragment;
import jason.toolBar_TabLayout_ViewPager.view.viewPager.base.paramsWork.BaseParamsWorker;
import jason.toolBar_TabLayout_ViewPager.view.viewPager.sample1.fragment.SocialFragment;

/**
 * Created by cs on 15/11/28.
 */
public class SocialParamsWorker extends BaseParamsWorker {


    public SocialParamsWorker(Context conext) {
        super(conext);
    }

    @Override
    public String getOnSelectTittle() {
        return "Social";
    }

    @Override
    public View.OnClickListener getNavigationIconOnClickListener() {
        return new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "" + getTagName(), Toast.LENGTH_SHORT).show();
            }
        };
    }

    @Override
    public Toolbar.OnMenuItemClickListener  getMenuOnMenuItemClickListener() {
        return new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Toast.makeText(getContext(), getTagName() + " " + item.getItemId(), Toast.LENGTH_SHORT).show();
                return false;
            }
        };
    }

    @Override
    public int getOnSelectNavigationIconResId() {
        return R.drawable.ab_android;
    }

    @Override
    public int getOnSelectMenuResId() {
        return R.menu.menu_social;
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
        return new SocialFragment();
    }

    @Override
    public String getTagName() {
        return this.getClass().getSimpleName();
    }

}

package jason.toolBar_TabLayout_ViewPager.view.viewPager.sample1.provider;

import android.content.Context;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import jason.toolBar_TabLayout_ViewPager.R;
import jason.toolBar_TabLayout_ViewPager.view.viewPager.base.fragment.BaseFragment;
import jason.toolBar_TabLayout_ViewPager.view.viewPager.base.paramsWork.ParamsWorker;
import jason.toolBar_TabLayout_ViewPager.view.viewPager.sample1.fragment.ChatsFragment;

/**
 * Created by cs on 15/11/28.
 */
public class ChatsParamsWorker extends ParamsWorker {

    public ChatsParamsWorker(Context conext) {
        super(conext);
    }

    @Override
    public String getOnSelectTittle() {
        return "Chats";
    }

    @Override
    public View.OnClickListener getNavigationIconOnClickListener() {
        return null;
    }
    @Override
    public int getOnSelectNavigationIconResId() {
        return 0;
    }

    @Override
    public Toolbar.OnMenuItemClickListener  getMenuOnMenuItemClickListener() {
        return new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Log.i(getTAGName(), "onMenuItemClick:");
                Toast.makeText(getContext(), getTAGName(), Toast.LENGTH_SHORT).show();
                return false;
            }
        };
    }

    @Override
    public int getOnSelectMenuResId() {
        return R.menu.menu_chats;
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
        return new ChatsFragment();
    }

    @Override
    public String getTAGName() {
        return this.getClass().getSimpleName();
    }

}

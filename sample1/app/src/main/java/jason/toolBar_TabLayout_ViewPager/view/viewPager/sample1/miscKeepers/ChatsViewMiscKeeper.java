package jason.toolBar_TabLayout_ViewPager.view.viewPager.sample1.miscKeepers;

import android.content.Context;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import jason.toolBar_TabLayout_ViewPager.R;
import jason.toolBar_TabLayout_ViewPager.view.viewPager.base.miscKeeper.BaseViewMiscKeeper;
import jason.toolBar_TabLayout_ViewPager.view.viewPager.base.uiFragment.BaseFragment;
import jason.toolBar_TabLayout_ViewPager.view.viewPager.sample1.fragments.ChatsFragment;

/**
 * Created by cs on 15/11/28.
 */
public class ChatsViewMiscKeeper extends BaseViewMiscKeeper {

    public ChatsViewMiscKeeper(Context context) {
        super(context);
    }

    @Override
    public String getTittleText() {
        return "Chats";
    }

    @Override
    public int getNavigationIconResId() {
        return 0;
    }

    @Override
    public void onNavigationOnClick(View v) {
        Toast.makeText(getContext(), getTagName(), Toast.LENGTH_SHORT).show();
    }

    public int getMenuResId() {
        return R.menu.menu_chats;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        Toast.makeText(getContext(), getTagName(), Toast.LENGTH_SHORT).show();
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
        return new ChatsFragment();
    }

    @Override
    public String getTagName() {
        return this.getClass().getSimpleName();
    }

}

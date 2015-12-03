package jason.toolBar_TabLayout_ViewPager.view.viewPager.base.uiOperator;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.HashMap;

import jason.toolBar_TabLayout_ViewPager.R;
import jason.toolBar_TabLayout_ViewPager.view.viewPager.base.miscKeeper.BaseViewMiscKeeper;

/**
 * Created by jason on 2015/12/1.
 */
public class ToolBarUIOperator extends BaseUIOperator {

    private final String TAG = ToolBarUIOperator.class.getSimpleName();

    private final HashMap<Integer, BaseViewMiscKeeper> mHashMap;
    private final AppCompatActivity mAppCompatActivity;
    private final Toolbar mToolbar;

    public ToolBarUIOperator(final HashMap<Integer, BaseViewMiscKeeper> hashMap, final AppCompatActivity appCompatActivity, final Toolbar toolbar) {
        mHashMap = hashMap;
        mAppCompatActivity = appCompatActivity;
        mToolbar = toolbar;
    }

    public void init() {
    }

    public void setKeeper(final BaseViewMiscKeeper currentParamsKeeper) {
        setOnSelectToolBarTitle(currentParamsKeeper);

        setOnSelectToolBarNavigationIconIfNeed(currentParamsKeeper);

        setOnSelectToolBarMenuIfNeed(currentParamsKeeper);
    }

    @Override
    public void unSetKeeper(BaseViewMiscKeeper oldParamsKeeper) {
        setOnUnSelectToolBarTitle();
        setOnUnSelectMenuIfNeed();
        setOnUnSelectNavigationIcon();
    }

    private void setOnSelectToolBarTitle(BaseViewMiscKeeper currentParamsKeeper) {
        mToolbar.setTitle("");
        TextView toolbarTitle = (TextView) mToolbar.findViewById(R.id.toolbar_title);
        Log.i(currentParamsKeeper.getTagName(), "setOnSelectToolBarTitle " + toolbarTitle);
        toolbarTitle.setText(currentParamsKeeper.getTittleText());
    }

    private void setOnSelectToolBarNavigationIconIfNeed(final BaseViewMiscKeeper currentParamsKeeper) {
        final int iconId = currentParamsKeeper.getNavigationIconResId();
        if (iconId != 0) {
            mToolbar.setNavigationIcon(iconId);
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    currentParamsKeeper.onNavigationOnClick(v);
                }
            });
        } else {
            Log.v(TAG, "setOnSelectToolBarNavigationIconIfNeed: resId = 0, at " + currentParamsKeeper.getTagName());
        }
    }

    private void setOnSelectToolBarMenuIfNeed(final BaseViewMiscKeeper currentParamsKeeper) {
        final int menuId = currentParamsKeeper.getMenuResId();
        if (menuId != 0) {
            Log.v(TAG, "setOnSelectToolBarMenuIfNeed: resId = " + menuId + " at " + currentParamsKeeper.getTagName());
            mToolbar.inflateMenu(menuId);
            mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    currentParamsKeeper.onMenuItemClick(item);
                    return false;
                }
            });
        } else {
            Log.v(TAG, "setOnSelectToolBarMenuIfNeed: resId = 0, at " + currentParamsKeeper.getTagName());
        }
    }

    private void setOnUnSelectToolBarTitle() {
        mToolbar.setTitle("");
        TextView toolbarTitle = (TextView) mToolbar.findViewById(R.id.toolbar_title);
        toolbarTitle.setText("");
    }

    private void setOnUnSelectMenuIfNeed() {
        Menu menu = mToolbar.getMenu();
        if (menu != null) {
            menu.clear();
        } else {
            Log.v(TAG, "setOnUnSelectMenuIfNeed: menu = null");
        }
    }

    private void setOnUnSelectNavigationIcon() {
        mToolbar.setNavigationIcon(null);
        mToolbar.setNavigationOnClickListener(null);
    }
}

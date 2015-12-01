package jason.toolBar_TabLayout_ViewPager.view.viewPager.base.operator;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import java.util.HashMap;

import jason.toolBar_TabLayout_ViewPager.R;
import jason.toolBar_TabLayout_ViewPager.view.viewPager.base.paramsWork.BaseParamsWorker;

/**
 * Created by jason on 2015/12/1.
 */
public class ToolBarUIOperator extends BaseUIOperator {

    private final String TAG = ToolBarUIOperator.class.getSimpleName();

    private final HashMap<Integer, BaseParamsWorker> mHashMap;
    private final AppCompatActivity mAppCompatActivity;
    private final Toolbar mToolbar;

    public ToolBarUIOperator(final HashMap<Integer, BaseParamsWorker> hashMap, final AppCompatActivity appCompatActivity, final Toolbar toolbar) {
        mHashMap = hashMap;
        mAppCompatActivity = appCompatActivity;
        mToolbar = toolbar;
    }

    public void init() {
//        mAppCompatActivity.setSupportActionBar(mToolbar);
    }

    public void setWorker(final BaseParamsWorker currentParamsWorker) {
        setOnSelectToolBarTitle(currentParamsWorker);

        setOnSelectToolBarNavigationIconIfNeed(currentParamsWorker);
        setOnSelectToolBarNavigationIconListenerIfNeed(currentParamsWorker);

        setOnSelectToolBarMenuIfNeed(currentParamsWorker);
        setOnSelectToolBarMenuOnMenuItemClickListenerIfNeed(currentParamsWorker);
    }

    @Override
    public void cleanWorker(BaseParamsWorker oldParamsWorker) {
        setOnUnSelectToolBarTitle();
        setOnUnSelectMenuIfNeed();
        setOnUnSelectNavigationIcon();
    }

    private void setOnSelectToolBarTitle(BaseParamsWorker currentParamsWorker) {
        mToolbar.setTitle("");
        TextView toolbarTitle = (TextView) mToolbar.findViewById(R.id.toolbar_title);
        Log.i(currentParamsWorker.getTagName(), "setOnSelectToolBarTitle " + toolbarTitle);
        toolbarTitle.setText(currentParamsWorker.getOnSelectTittle());
    }

    private void setOnSelectToolBarNavigationIconIfNeed(BaseParamsWorker currentParamsWorker) {
        final int iconId = currentParamsWorker.getOnSelectNavigationIconResId();
        if (iconId != 0) {
            mToolbar.setNavigationIcon(iconId);
        } else {
            Log.v(TAG, "setOnSelectToolBarNavigationIconIfNeed: resId = 0, at " + currentParamsWorker.getTagName());
        }
    }

    private void setOnSelectToolBarNavigationIconListenerIfNeed(BaseParamsWorker currentParamsWorker) {
        final View.OnClickListener listener = currentParamsWorker.getNavigationIconOnClickListener();
        if (listener != null) {
            mToolbar.setNavigationOnClickListener(listener);
        } else {
            Log.v(TAG, "setOnSelectToolBarNavigationIconListenerIfNeed: listener = null, at " + currentParamsWorker.getTagName());
        }
    }

    private void setOnSelectToolBarMenuIfNeed(BaseParamsWorker currentParamsWorker) {
        final int menuId = currentParamsWorker.getOnSelectMenuResId();
        if (menuId != 0) {
            Log.v(TAG, "setOnSelectToolBarMenuIfNeed: resId = " + menuId + " at " + currentParamsWorker.getTagName());
            mToolbar.inflateMenu(menuId);
        } else {
            Log.v(TAG, "setOnSelectToolBarMenuIfNeed: resId = 0, at " + currentParamsWorker.getTagName());
        }
    }

    private void setOnSelectToolBarMenuOnMenuItemClickListenerIfNeed(BaseParamsWorker currentParamsWorker) {
        final Toolbar.OnMenuItemClickListener listener = currentParamsWorker.getMenuOnMenuItemClickListener();
        if (listener != null) {
            mToolbar.setOnMenuItemClickListener(listener);
        } else {
            Log.v(TAG, "setOnSelectToolBarNavigationIconListenerIfNeed: listener = null, at " + currentParamsWorker.getTagName());
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

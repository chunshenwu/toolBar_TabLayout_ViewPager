package jason.toolBar_TabLayout_ViewPager.view.viewPager.base.uiOperator;

import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.util.HashMap;

import jason.toolBar_TabLayout_ViewPager.R;
import jason.toolBar_TabLayout_ViewPager.view.viewPager.base.miscKeeper.BaseViewMiscKeeper;

/**
 * Created by jason on 2015/12/1.
 */
public class TabLayoutUIOperator extends BaseUIOperator {

    private String TAG = TabLayoutUIOperator.class.getName();

    private final HashMap<Integer, BaseViewMiscKeeper> mHashMap;
    private final TabLayout mTabLayout;

    public TabLayoutUIOperator(final HashMap<Integer, BaseViewMiscKeeper> hashMap, final TabLayout tabLayout) {
        mHashMap = hashMap;
        mTabLayout = tabLayout;
    }

    public void adjustTabFromKeeper(final BaseViewMiscKeeper baseViewMiscKeeper) {
        final TabLayout.Tab tab = mTabLayout.newTab();
        mTabLayout.addTab(tab);
        setTabFromMiscKeeper(tab, baseViewMiscKeeper);
    }

    private void setTabFromMiscKeeper(final TabLayout.Tab tab, final BaseViewMiscKeeper baseViewMiscKeeper) {
        TabLayoutUIOperator.CustomizeTabUtil.addCustomizeTab(mTabLayout.getContext(), mTabLayout);
        TabLayoutUIOperator.CustomizeTabUtil.setIcon(tab, baseViewMiscKeeper.getBackgroundResIdUnSelected());
    }

    public void init() {
        mTabLayout.setSelectedTabIndicatorColor(Color.TRANSPARENT);
        mTabLayout.setBackgroundColor(Color.WHITE);
    }

    public TabLayout.Tab getTab(final int index) {
        return mTabLayout.getTabAt(index);
    }

    public void setOnSelectTabLayoutIconIfNeed(BaseViewMiscKeeper currentParamsKeeper) {
        TabLayout.Tab tab = mTabLayout.getTabAt(currentParamsKeeper.getPosition());
        final int iconId = currentParamsKeeper.getBackgroundResIdSelected();
        if (iconId != 0) {
            TabLayoutUIOperator.CustomizeTabUtil.setIcon(tab, iconId);
            TabLayoutUIOperator.CustomizeTabUtil.setBubbleVisibility(tab, View.VISIBLE);
        } else {
            Log.v(TAG, "setOnSelectTabLayoutIconIfNeed: resId = 0, at" + currentParamsKeeper.getTagName());
        }
    }

    private void setOnUnSelectTabLayoutIconIfNeed(BaseViewMiscKeeper oldParamsKeeper) {
        TabLayout.Tab tab = mTabLayout.getTabAt(oldParamsKeeper.getPosition());
        final int iconId = oldParamsKeeper.getBackgroundResIdUnSelected();
        if (iconId != 0) {
            //            tab.setIcon(iconId);
            TabLayoutUIOperator.CustomizeTabUtil.setIcon(tab, iconId);
            TabLayoutUIOperator.CustomizeTabUtil.setBubbleVisibility(tab, View.INVISIBLE);
        } else {
            Log.v(TAG, "setOnUnSelectTabLayoutIconIfNeed: iconId = 0, at " + oldParamsKeeper.getTagName());
        }
    }

    @Override
    public void setKeeper(BaseViewMiscKeeper currentParamsKeeper) {
        setOnSelectTabLayoutIconIfNeed(currentParamsKeeper);
    }

    @Override
    public void unSetKeeper(BaseViewMiscKeeper oldParamsKeeper) {
        setOnUnSelectTabLayoutIconIfNeed(oldParamsKeeper);
    }

    static class CustomizeTabUtil {

        public static void addCustomizeTab(final Context context, final TabLayout tabLayout) {
            //add customize View
            View v = View.inflate(context, R.layout.custom_tab, null);
            tabLayout.getTabAt(tabLayout.getTabCount() - 1).setCustomView(v);
        }

        public static void setIcon(final TabLayout.Tab tab, final int resId) {
            View customView = tab.getCustomView();
            ImageView iconView = (ImageView) customView.findViewById(R.id.icon);
            iconView.setBackgroundResource(resId);
        }

        public static void setBubbleVisibility(final TabLayout.Tab tab, final int visibility) {
            View customView = tab.getCustomView();
            View iconView = (View) customView.findViewById(R.id.bubble);
            iconView.setVisibility(visibility);
        }
    }

}

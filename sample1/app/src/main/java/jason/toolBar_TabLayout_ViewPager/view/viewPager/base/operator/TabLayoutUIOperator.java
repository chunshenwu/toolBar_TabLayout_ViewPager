package jason.toolBar_TabLayout_ViewPager.view.viewPager.base.operator;

import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.util.HashMap;

import jason.toolBar_TabLayout_ViewPager.R;
import jason.toolBar_TabLayout_ViewPager.view.viewPager.base.paramsWork.BaseParamsWorker;

/**
 * Created by jason on 2015/12/1.
 */
public class TabLayoutUIOperator extends BaseUIOperator {

    private String TAG = TabLayoutUIOperator.class.getName();

    private final HashMap<Integer, BaseParamsWorker> mHashMap;
    private final TabLayout mTabLayout;

    public TabLayoutUIOperator(final HashMap<Integer, BaseParamsWorker> hashMap, final TabLayout tabLayout) {
        mHashMap = hashMap;
        mTabLayout = tabLayout;
    }

    public void addPageWithTab(final BaseParamsWorker ParamsWorker) {
        final TabLayout.Tab tab = mTabLayout.newTab();
        mTabLayout.addTab(tab);
        TabLayoutUIOperator.CustomizeTabUtil.addCustomizeTab(mTabLayout.getContext(), mTabLayout);
        TabLayoutUIOperator.CustomizeTabUtil.setIcon(tab, ParamsWorker.getOnUnSelectResId());
    }

    public void init() {
        mTabLayout.setSelectedTabIndicatorColor(Color.TRANSPARENT);
        mTabLayout.setBackgroundColor(Color.WHITE);
    }

    public TabLayout.Tab getTab(final int index) {
        return mTabLayout.getTabAt(index);
    }

    public void setOnSelectTabLayoutIconIfNeed(BaseParamsWorker currentParamsWorker) {
        TabLayout.Tab tab = mTabLayout.getTabAt(currentParamsWorker.getPosition());
        final int iconId = currentParamsWorker.getOnSelectResId();
        if (iconId != 0) {
            TabLayoutUIOperator.CustomizeTabUtil.setIcon(tab, iconId);
            TabLayoutUIOperator.CustomizeTabUtil.setBubbleVisibility(tab, View.VISIBLE);
        } else {
            Log.v(TAG, "setOnSelectTabLayoutIconIfNeed: resId = 0, at" + currentParamsWorker.getTagName());
        }
    }

    private void setOnUnSelectTabLayoutIconIfNeed(BaseParamsWorker oldParamsWorker) {
        TabLayout.Tab tab = mTabLayout.getTabAt(oldParamsWorker.getPosition());
        final int iconId = oldParamsWorker.getOnUnSelectResId();
        if (iconId != 0) {
            //            tab.setIcon(iconId);
            TabLayoutUIOperator.CustomizeTabUtil.setIcon(tab, iconId);
            TabLayoutUIOperator.CustomizeTabUtil.setBubbleVisibility(tab, View.INVISIBLE);
        } else {
            Log.v(TAG, "setOnUnSelectTabLayoutIconIfNeed: iconId = 0, at " + oldParamsWorker.getTagName());
        }
    }

    @Override
    public void setWorker(BaseParamsWorker currentParamsWorker) {
        setOnSelectTabLayoutIconIfNeed(currentParamsWorker);
    }

    @Override
    public void cleanWorker(BaseParamsWorker oldParamsWorker) {
        setOnUnSelectTabLayoutIconIfNeed(oldParamsWorker);
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

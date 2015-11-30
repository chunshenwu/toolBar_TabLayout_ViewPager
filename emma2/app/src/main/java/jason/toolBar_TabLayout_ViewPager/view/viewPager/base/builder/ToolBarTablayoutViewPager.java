package jason.toolBar_TabLayout_ViewPager.view.viewPager.base.builder;

import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import java.util.HashMap;

import jason.toolBar_TabLayout_ViewPager.R;
import jason.toolBar_TabLayout_ViewPager.view.viewPager.base.paramsProvide.ITabLayoutParamsWork;
import jason.toolBar_TabLayout_ViewPager.view.viewPager.base.paramsProvide.IToolBarParamsWork;
import jason.toolBar_TabLayout_ViewPager.view.viewPager.base.paramsProvide.ParamsWorker;
import jason.toolBar_TabLayout_ViewPager.view.viewPager.emma2.MainActivity;

public class ToolBarTablayoutViewPager {

//private String TAG = ToolBarTablayoutViewPager.class.getName();

    private final HashMap<String, ParamsWorker> mHashMap;
    private final ToolBarOperator mToolBarOperator;
    private final TabLayoutOperator  mTabLayoutOperator;
    private final ViewPagerOperator mViewPagerOperator;


    public static class Builder {
        private final ToolBarTablayoutViewPager mToolBarTablayoutViewPager;
        public Builder(MainActivity appCompatActivity, Toolbar toolbar, TabLayout tabLayout, ViewPager viewPager) {
            mToolBarTablayoutViewPager = new ToolBarTablayoutViewPager(appCompatActivity, toolbar, tabLayout, viewPager);
        }

        public Builder addProviderWithTab(final ParamsWorker paramsWorker) {
            mToolBarTablayoutViewPager.addProviderWithTab(paramsWorker);
            return this;
        }

        public void build() {
            mToolBarTablayoutViewPager.init();
        }
    }

    ToolBarTablayoutViewPager(AppCompatActivity appCompatActivity, Toolbar toolbar, TabLayout tabLayout, ViewPager viewPager) {
        mHashMap = new HashMap<>();

        mToolBarOperator = new ToolBarOperator(appCompatActivity, toolbar);
        mTabLayoutOperator = new TabLayoutOperator(mHashMap, toolbar, tabLayout, viewPager);
        mViewPagerOperator = new ViewPagerOperator(mHashMap, tabLayout, viewPager, appCompatActivity.getSupportFragmentManager());
    }

    public void addProviderWithTab(final ParamsWorker paramsWorker) {
        mTabLayoutOperator.addProviderWithTab(paramsWorker);
    }

    public void init() {
        mToolBarOperator.init();
        mTabLayoutOperator.init();
        mViewPagerOperator.init();

        //FIXME: workaround
//        mViewPagerOperator.refresh();
    }
}

class ToolBarOperator {

    private final AppCompatActivity mAppCompatActivity;
    private final Toolbar mToolbar;
    public ToolBarOperator(final AppCompatActivity appCompatActivity, final Toolbar toolbar){
        mAppCompatActivity = appCompatActivity;
        mToolbar = toolbar;
    }

    public void init() {
        mAppCompatActivity.setSupportActionBar(mToolbar);
    }
}

class TabLayoutOperator {

    private final HashMap<String, ParamsWorker> mHashMap;
    private final Toolbar mToolbar;
    private final TabLayout mTabLayout;
    private final ViewPager mViewPager;

    public TabLayoutOperator(final HashMap<String, ParamsWorker> hashMap, final Toolbar toolbar, final TabLayout tabLayout, final ViewPager viewPager){
        mHashMap = hashMap;
        mToolbar = toolbar;
        mTabLayout = tabLayout;
        mViewPager = viewPager;
    }

    public void addProviderWithTab(final ParamsWorker ParamsWorker) {
        final String alias = String.valueOf(mHashMap.size());
        mHashMap.put(alias, ParamsWorker);

        final TabLayout.Tab tab = mTabLayout.newTab();
        tab.setTag(alias);
        mTabLayout.addTab(tab);
        CustomizeTabUtil.addCustomizeTab(mTabLayout.getContext(), mTabLayout);
    }

    public void init() {
        initTabSelectedListener();
        processHardCodeOperator();
    }

    private void processHardCodeOperator() {
        mTabLayout.setSelectedTabIndicatorColor(Color.WHITE);
        mTabLayout.setBackgroundColor(Color.WHITE);
    }

    private void initTabSelectedListener() {
        mTabLayout.setOnTabSelectedListener(new OnTabSelectedListenerImpl(mToolbar, mViewPager, mHashMap));
    }

    class OnTabSelectedListenerImpl implements TabLayout.OnTabSelectedListener {
        private String TAG = OnTabSelectedListenerImpl.class.getName();

        private final Toolbar mToolbar;
        private final ViewPager mViewPager;
        private final HashMap<String, ParamsWorker> mHashMap;
        OnTabSelectedListenerImpl(Toolbar toolbar, final ViewPager viewPager, HashMap<String, ParamsWorker> hashMap) {
            mToolbar = toolbar;
            mViewPager = viewPager;
            mHashMap = hashMap;
        }

        private void setOnSelectToolBarTitle(IToolBarParamsWork provide){
            mToolbar.setTitle("");
            TextView toolbarTitle = (TextView) mToolbar.findViewById(R.id.toolbar_title);
            Log.i(provide.getTAGName(), "setOnSelectToolBarTitle " + toolbarTitle);
            toolbarTitle.setTypeface(toolbarTitle.getTypeface(), provide.getTitleTypeface());
            toolbarTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, provide.getTitleTextSize());
            toolbarTitle.setTextColor(provide.getTitleTextColor());

            mToolbar.setBackgroundColor(provide.getBackgroundColor());
            toolbarTitle.setText(provide.getOnSelectTittle());
        }

        private void setOnSelectToolBarNavigationIconIfNeed(IToolBarParamsWork provide){
            final int iconId = provide.getOnSelectNavigationIconResId();
            if (iconId != 0) {
                mToolbar.setNavigationIcon(iconId);
            } else {
                Log.v(TAG, "setOnSelectToolBarNavigationIconIfNeed: resId = 0, at" + provide.getTAGName());
            }
        }

        private void setOnSelectToolBarNavigationIconListenerIfNeed(IToolBarParamsWork provide){
            final View.OnClickListener listener = provide.getNavigationIconOnClickListener();
            if (listener != null) {
                mToolbar.setNavigationOnClickListener(listener);
            } else {
                Log.v(TAG, "setOnSelectToolBarNavigationIconListenerIfNeed: listener = null, at" + provide.getTAGName());
            }
        }

        private void setOnSelectToolBarMenuIfNeed(IToolBarParamsWork provide){
            final int menuId = provide.getOnSelectMenuResId();
            if (menuId != 0) {
                mToolbar.inflateMenu(menuId);
            } else {
                Log.v(TAG, "setOnSelectToolBarMenuIfNeed: resId = 0, at" + provide.getTAGName());
            }
        }

        private void setOnSelectToolBarMenuOnMenuItemClickListenerIfNeed(IToolBarParamsWork provide){
            final Toolbar.OnMenuItemClickListener listener = provide.getMenuOnMenuItemClickListener();
            if (listener != null) {
                mToolbar.setOnMenuItemClickListener(listener);
            } else {
                Log.v(TAG, "setOnSelectToolBarNavigationIconListenerIfNeed: listener = null, at" + provide.getTAGName());
            }
        }

        private void setOnSelectTabLayoutIconIfNeed(TabLayout.Tab tab, ITabLayoutParamsWork provide){
            final int iconId = provide.getOnSelectResId();
            if (iconId != 0) {
                CustomizeTabUtil.setIcon(tab, iconId);
                CustomizeTabUtil.setBubbleVisibility(tab, View.VISIBLE);
            } else {
                Log.v(TAG, "setOnSelectTabLayoutIconIfNeed: resId = 0, at" + provide.getTAGName());
            }
        }

        private void setOnUnSelectToolBarTitle(){
            mToolbar.setTitle("");
            TextView toolbarTitle = (TextView) mToolbar.findViewById(R.id.toolbar_title);
            toolbarTitle.setText("");
        }

        private void setOnUnSelectMenuIfNeed(){
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

        private void setOnUnSelectTabLayoutIconIfNeed(TabLayout.Tab tab, ITabLayoutParamsWork provide){
            final int iconId = provide.getOnUnSelectResId();
            if (iconId != 0) {
    //            tab.setIcon(iconId);
                CustomizeTabUtil.setIcon(tab, iconId);
                CustomizeTabUtil.setBubbleVisibility(tab, View.INVISIBLE);
            } else {
                Log.v(TAG, "setOnUnSelectTabLayoutIconIfNeed: iconId = 0, at " + provide.getTAGName());
            }
        }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        ParamsWorker paramsWorker = mHashMap.get(tab.getTag());
        Log.i(TAG, "onTabSelected = " + tab.getPosition() + " " + paramsWorker.getTAGName());

        //ToolBar
        setOnSelectToolBarTitle(paramsWorker);

        setOnSelectToolBarNavigationIconIfNeed(paramsWorker);
        setOnSelectToolBarNavigationIconListenerIfNeed(paramsWorker);

        setOnSelectToolBarMenuIfNeed(paramsWorker);
        setOnSelectToolBarMenuOnMenuItemClickListenerIfNeed(paramsWorker);
        //TabLayout
        setOnSelectTabLayoutIconIfNeed(tab, paramsWorker);
        //ViewPager
        mViewPager.setCurrentItem(tab.getPosition());

        //Fragment;
        paramsWorker.getFragment().onTabChangedResume();
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
        ParamsWorker paramsWorker = mHashMap.get(tab.getTag());
        //ToolBar
        setOnUnSelectToolBarTitle();
        setOnUnSelectMenuIfNeed();
        setOnUnSelectNavigationIcon();
        //Tab
        setOnUnSelectTabLayoutIconIfNeed(tab, paramsWorker);
        //ViewPager

        //Fragment;
        paramsWorker.getFragment().onTabChangedPause();
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {
    }
}

    static class CustomizeTabUtil {

        public static void addCustomizeTab(final Context context, final TabLayout tabLayout) {
            //add customize View
            View v = View.inflate(context, R.layout.custom_tab, null);
            tabLayout.getTabAt(tabLayout.getTabCount() - 1).setCustomView(v);
        }

        public static void setIcon(final TabLayout.Tab tab, final int resId) {
            View customView = tab.getCustomView();
            View iconView = (View) customView.findViewById(R.id.icon);
            iconView.setBackgroundResource(resId);
        }

        public static void setBubbleVisibility(final TabLayout.Tab tab, final int visibility) {
            View customView = tab.getCustomView();
            View iconView = (View) customView.findViewById(R.id.bubble);
            iconView.setVisibility(visibility);
        }
    }

}

class ViewPagerOperator {

    private final HashMap<String, ParamsWorker> mHashMap;
    private final TabLayout mTabLayout;
    private final ViewPager mViewPager;
    private final FragmentManager mFragmentManager;
    public ViewPagerOperator(final HashMap<String, ParamsWorker> hashMap, final TabLayout tabLayout, final ViewPager viewPager, final FragmentManager fragmentManager){
        mHashMap = hashMap;
        mTabLayout = tabLayout;
        mViewPager = viewPager;
        mFragmentManager = fragmentManager;
    }

    public void init() {
        mViewPager.setAdapter(new ViewPagerAdapter(mHashMap, mFragmentManager));
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
    }

    public void refresh() {
        int count = mHashMap.size();
        for (int i = --count; i >= 0 ; i--) {
            mViewPager.setCurrentItem(i);
        }
    }

    class ViewPagerAdapter extends FragmentStatePagerAdapter {
        private final String TAG = ViewPagerAdapter.class.getSimpleName();

        private HashMap<String, ParamsWorker> mHashMap;
        public ViewPagerAdapter(final HashMap<String, ParamsWorker> hashMap, FragmentManager fm) {
            super(fm);
            mHashMap = hashMap;
        }

        @Override
        public Fragment getItem(int position) {
            ParamsWorker paramsWorker;
            if (position > mHashMap.size()) {
                Log.e(TAG, "getItem: Error");
                return null;
            }
            paramsWorker = mHashMap.get(String.valueOf(position));
            return paramsWorker.getFragment();
        }

        @Override
        public int getCount() {
            return mHashMap.size();
        }
    }
}
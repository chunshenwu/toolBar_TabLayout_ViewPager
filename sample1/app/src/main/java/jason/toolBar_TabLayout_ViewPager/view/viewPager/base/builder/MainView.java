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
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;

import jason.toolBar_TabLayout_ViewPager.R;
import jason.toolBar_TabLayout_ViewPager.view.viewPager.base.paramsWork.ITabLayoutParamsWork;
import jason.toolBar_TabLayout_ViewPager.view.viewPager.base.paramsWork.IToolBarParamsWork;
import jason.toolBar_TabLayout_ViewPager.view.viewPager.base.paramsWork.ParamsWorker;
import jason.toolBar_TabLayout_ViewPager.view.viewPager.sample1.MainTab;

public class MainView {

    private final HashMap<Integer, ParamsWorker> mHashMap;
    private final ToolBarOperator mToolBarOperator;
    private final TabLayoutOperator mTabLayoutOperator;
    private final ViewPagerOperator mViewPagerOperator;

    public static class Builder {
        private final MainView mMainView;

        public Builder(MainTab appCompatActivity, Toolbar toolbar, TabLayout tabLayout, ViewPager viewPager) {
            mMainView = new MainView(appCompatActivity, toolbar, tabLayout, viewPager);
        }

        public Builder addPageWithTab(final ParamsWorker paramsWorker) {
            mMainView.addPageWithTab(paramsWorker);
            return this;
        }

        public MainView build() {
            mMainView.init();
            mMainView.setDefault();
            return mMainView;
        }
    }

    MainView(AppCompatActivity appCompatActivity, Toolbar toolbar, TabLayout tabLayout, ViewPager viewPager) {
        mHashMap = new HashMap<>();

        mToolBarOperator = new ToolBarOperator(mHashMap, appCompatActivity, toolbar);
        mViewPagerOperator = new ViewPagerOperator(mHashMap, tabLayout, viewPager, appCompatActivity.getSupportFragmentManager());
        mTabLayoutOperator = new TabLayoutOperator(mHashMap, mToolBarOperator, tabLayout, mViewPagerOperator);
    }

    public void addPageWithTab(final ParamsWorker paramsWorker) {
        mTabLayoutOperator.addPageWithTab(paramsWorker);
    }

    public void init() {
        mToolBarOperator.init();
        mTabLayoutOperator.init();
        mViewPagerOperator.init();
    }

    public void setDefault() {
        final int DEFAULT_INDEX = 1;
        final ParamsWorker paramsWorker = mHashMap.get(DEFAULT_INDEX);
        mToolBarOperator.setWorker(paramsWorker);
        TabLayout.Tab tab = mTabLayoutOperator.getTab(DEFAULT_INDEX);
        mTabLayoutOperator.setWorker(tab, paramsWorker);
        mViewPagerOperator.setWorker(tab, paramsWorker);
    }
}

class ToolBarOperator {

    private final String TAG = ToolBarOperator.class.getSimpleName();

    private final HashMap<Integer, ParamsWorker> mHashMap;
    private final AppCompatActivity mAppCompatActivity;
    private final Toolbar mToolbar;

    public ToolBarOperator(final HashMap<Integer, ParamsWorker> hashMap, final AppCompatActivity appCompatActivity, final Toolbar toolbar) {
        mHashMap = hashMap;
        mAppCompatActivity = appCompatActivity;
        mToolbar = toolbar;
    }

    public void init() {
        mAppCompatActivity.setSupportActionBar(mToolbar);
    }

    public void setWorker(final ParamsWorker paramsWorker) {
        setOnSelectToolBarTitle(paramsWorker);

        setOnSelectToolBarNavigationIconIfNeed(paramsWorker);
        setOnSelectToolBarNavigationIconListenerIfNeed(paramsWorker);

        setOnSelectToolBarMenuIfNeed(paramsWorker);
        setOnSelectToolBarMenuOnMenuItemClickListenerIfNeed(paramsWorker);
    }

    public void cleanWorker() {
        setOnUnSelectToolBarTitle();
        setOnUnSelectMenuIfNeed();
        setOnUnSelectNavigationIcon();
    }

    private void setOnSelectToolBarTitle(IToolBarParamsWork worker) {
        mToolbar.setTitle("");
        TextView toolbarTitle = (TextView) mToolbar.findViewById(R.id.toolbar_title);
        Log.i(worker.getTAGName(), "setOnSelectToolBarTitle " + toolbarTitle);
        toolbarTitle.setTypeface(toolbarTitle.getTypeface(), worker.getTitleTypeface());
        toolbarTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, worker.getTitleTextSize());
        toolbarTitle.setTextColor(worker.getTitleTextColor());

        mToolbar.setBackgroundColor(worker.getBackgroundColor());
        toolbarTitle.setText(worker.getOnSelectTittle());
    }

    private void setOnSelectToolBarNavigationIconIfNeed(IToolBarParamsWork worker) {
        final int iconId = worker.getOnSelectNavigationIconResId();
        if (iconId != 0) {
            mToolbar.setNavigationIcon(iconId);
        } else {
            Log.v(TAG, "setOnSelectToolBarNavigationIconIfNeed: resId = 0, at" + worker.getTAGName());
        }
    }

    private void setOnSelectToolBarNavigationIconListenerIfNeed(IToolBarParamsWork worker) {
        final View.OnClickListener listener = worker.getNavigationIconOnClickListener();
        if (listener != null) {
            mToolbar.setNavigationOnClickListener(listener);
        } else {
            Log.v(TAG, "setOnSelectToolBarNavigationIconListenerIfNeed: listener = null, at" + worker.getTAGName());
        }
    }

    private void setOnSelectToolBarMenuIfNeed(IToolBarParamsWork worker) {
        final int menuId = worker.getOnSelectMenuResId();
        if (menuId != 0) {
            mToolbar.inflateMenu(menuId);
        } else {
            Log.v(TAG, "setOnSelectToolBarMenuIfNeed: resId = 0, at" + worker.getTAGName());
        }
    }

    private void setOnSelectToolBarMenuOnMenuItemClickListenerIfNeed(IToolBarParamsWork worker) {
        final Toolbar.OnMenuItemClickListener listener = worker.getMenuOnMenuItemClickListener();
        if (listener != null) {
            mToolbar.setOnMenuItemClickListener(listener);
        } else {
            Log.v(TAG, "setOnSelectToolBarNavigationIconListenerIfNeed: listener = null, at" + worker.getTAGName());
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

class TabLayoutOperator {

    private String TAG = TabLayoutOperator.class.getName();

    private final HashMap<Integer, ParamsWorker> mHashMap;
    private final ToolBarOperator mToolBarOperator;
    private final TabLayout mTabLayout;
    private final ViewPagerOperator mVviewPagerOperator;

    public TabLayoutOperator(final HashMap<Integer, ParamsWorker> hashMap, final ToolBarOperator toolBarOperator, final TabLayout tabLayout, final ViewPagerOperator viewPagerOperator) {
        mHashMap = hashMap;
        mToolBarOperator = toolBarOperator;
        mTabLayout = tabLayout;
        mVviewPagerOperator = viewPagerOperator;
    }

    public void addPageWithTab(final ParamsWorker ParamsWorker) {
        final int position = mHashMap.size();
        mHashMap.put(position, ParamsWorker);

        final TabLayout.Tab tab = mTabLayout.newTab();
        mTabLayout.addTab(tab);
        CustomizeTabUtil.addCustomizeTab(mTabLayout.getContext(), mTabLayout);
        CustomizeTabUtil.setIcon(tab, ParamsWorker.getOnUnSelectResId());
    }

    public void init() {
        initTabSelectedListener();
        processHardCodeOperator();
    }

    private void processHardCodeOperator() {
        mTabLayout.setSelectedTabIndicatorColor(Color.TRANSPARENT);
        mTabLayout.setBackgroundColor(Color.WHITE);
    }

    private void initTabSelectedListener() {
        mTabLayout.setOnTabSelectedListener(new OnTabSelectedListenerImpl(mHashMap));
    }

    public void setWorker(TabLayout.Tab tab, final ParamsWorker paramsWorker) {
        setOnSelectTabLayoutIconIfNeed(tab, paramsWorker);
    }

    public void cleanWorker(TabLayout.Tab tab, final ParamsWorker paramsWorker) {
        setOnUnSelectTabLayoutIconIfNeed(tab, paramsWorker);
    }

    public TabLayout.Tab getTab(final int index) {
        return mTabLayout.getTabAt(index);
    }

    public void setOnSelectTabLayoutIconIfNeed(TabLayout.Tab tab, ITabLayoutParamsWork worker) {
        final int iconId = worker.getOnSelectResId();
        if (iconId != 0) {
            CustomizeTabUtil.setIcon(tab, iconId);
            CustomizeTabUtil.setBubbleVisibility(tab, View.VISIBLE);
        } else {
            Log.v(TAG, "setOnSelectTabLayoutIconIfNeed: resId = 0, at" + worker.getTAGName());
        }
    }

    private void setOnUnSelectTabLayoutIconIfNeed(TabLayout.Tab tab, ITabLayoutParamsWork worker) {
        final int iconId = worker.getOnUnSelectResId();
        if (iconId != 0) {
            //            tab.setIcon(iconId);
            CustomizeTabUtil.setIcon(tab, iconId);
            CustomizeTabUtil.setBubbleVisibility(tab, View.INVISIBLE);
        } else {
            Log.v(TAG, "setOnUnSelectTabLayoutIconIfNeed: iconId = 0, at " + worker.getTAGName());
        }
    }

    class OnTabSelectedListenerImpl implements TabLayout.OnTabSelectedListener {

        private final HashMap<Integer, ParamsWorker> mHashMap;

        OnTabSelectedListenerImpl(HashMap<Integer, ParamsWorker> hashMap) {
            mHashMap = hashMap;
        }

        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            ParamsWorker paramsWorker = mHashMap.get(tab.getPosition());
            Log.i(TAG, "onTabSelected = " + tab.getPosition() + " " + paramsWorker.getTAGName());
            //ToolBar
            mToolBarOperator.setWorker(paramsWorker);
            //TabLayout
            setWorker(tab, paramsWorker);
            //ViewPager
            mVviewPagerOperator.setWorker(tab, paramsWorker);
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {
            ParamsWorker paramsWorker = mHashMap.get(tab.getPosition());
            //ToolBar
            mToolBarOperator.cleanWorker();
            //TabLayout
            cleanWorker(tab, paramsWorker);
            //ViewPager
            mVviewPagerOperator.cleanWorker(paramsWorker);
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

class ViewPagerOperator {

    private final HashMap<Integer, ParamsWorker> mHashMap;
    private final TabLayout mTabLayout;
    private final ViewPager mViewPager;
    private final FragmentManager mFragmentManager;

    public ViewPagerOperator(final HashMap<Integer, ParamsWorker> hashMap, final TabLayout tabLayout, final ViewPager viewPager, final FragmentManager fragmentManager) {
        mHashMap = hashMap;
        mTabLayout = tabLayout;
        mViewPager = viewPager;
        mFragmentManager = fragmentManager;
    }

    public void init() {
        mViewPager.setAdapter(new ViewPagerAdapter(mHashMap, mFragmentManager));
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
    }

    class ViewPagerAdapter extends FragmentStatePagerAdapter {
        private final String TAG = ViewPagerAdapter.class.getSimpleName();

        private HashMap<Integer, ParamsWorker> mHashMap;

        public ViewPagerAdapter(final HashMap<Integer, ParamsWorker> hashMap, FragmentManager fm) {
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
            paramsWorker = mHashMap.get(position);
            return paramsWorker.reAssigntFragment();
        }

        @Override
        public int getCount() {
            return mHashMap.size();
        }
    }

    public void setWorker(TabLayout.Tab tab, final ParamsWorker paramsWorker) {
        //ViewPager
        mViewPager.setCurrentItem(tab.getPosition());
        //Fragment
        paramsWorker.getFragment().performTabPause();
    }

    public void cleanWorker(final ParamsWorker paramsWorker) {
        //ViewPager

        //Fragment;
        paramsWorker.getFragment().performTabPause();
    }
}
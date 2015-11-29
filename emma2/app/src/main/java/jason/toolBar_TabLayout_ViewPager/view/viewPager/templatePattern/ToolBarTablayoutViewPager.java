package jason.toolBar_TabLayout_ViewPager.view.viewPager.templatePattern;

import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.HashMap;

import jason.toolBar_TabLayout_ViewPager.R;
import jason.toolBar_TabLayout_ViewPager.view.viewPager.sample.MainActivity;

public class ToolBarTablayoutViewPager {

//private String TAG = ToolBarTablayoutViewPager.class.getName();

    private final HashMap<String, ParamsProvider> mHashMap;
    private final ToolBarOperator mToolBarOperator;
    private final TabLayoutOperator  mTabLayoutOperator;
    private final ViewPagerOperator mViewPagerOperator;


    public static class Builder {
        private final ToolBarTablayoutViewPager mToolBarTablayoutViewPager;
        public Builder(MainActivity appCompatActivity, Toolbar toolbar, TabLayout tabLayout, ViewPager viewPager) {
            mToolBarTablayoutViewPager = new ToolBarTablayoutViewPager(appCompatActivity, toolbar, tabLayout, viewPager);
        }

        public Builder addProviderWithTab(final ParamsProvider paramsProvider) {
            mToolBarTablayoutViewPager.addProviderWithTab(paramsProvider);
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
        mViewPagerOperator = new ViewPagerOperator(mHashMap, tabLayout, viewPager);
    }

    public void addProviderWithTab(final ParamsProvider paramsProvider) {
        mTabLayoutOperator.addProviderWithTab(paramsProvider);
    }

    public void init() {
        mToolBarOperator.init();
        mTabLayoutOperator.init();
        mViewPagerOperator.init();
        //FIXME: workaround
        mViewPagerOperator.refresh();
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

    private final HashMap<String, ParamsProvider> mHashMap;
    private final Toolbar mToolbar;
    private final TabLayout mTabLayout;
    private final ViewPager mViewPager;

    public TabLayoutOperator(final HashMap<String, ParamsProvider> hashMap, final Toolbar toolbar, final TabLayout tabLayout, final ViewPager viewPager){
        mHashMap = hashMap;
        mToolbar = toolbar;
        mTabLayout = tabLayout;
        mViewPager = viewPager;
    }

    public void addProviderWithTab(final ParamsProvider ParamsProvider) {
        final String alias = String.valueOf(mHashMap.size());
        mHashMap.put(alias, ParamsProvider);

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
        private final HashMap<String, ParamsProvider> mHashMap;
        OnTabSelectedListenerImpl(Toolbar toolbar, final ViewPager viewPager, HashMap<String, ParamsProvider> hashMap) {
            mToolbar = toolbar;
            mViewPager = viewPager;
            mHashMap = hashMap;
        }

        private void setOnSelectToolBarTitle(IToolBarParamsProvide provide){
            mToolbar.setTitle("");
            TextView toolbarTitle = (TextView) mToolbar.findViewById(R.id.toolbar_title);
            Log.i(provide.getTAGName(), "setOnSelectToolBarTitle " + toolbarTitle);
            toolbarTitle.setTypeface(toolbarTitle.getTypeface(), provide.getTitleTypeface());
            toolbarTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, provide.getTitleTextSize());
            toolbarTitle.setTextColor(provide.getTitleTextColor());

            mToolbar.setBackgroundColor(provide.getBackgroundColor());
            toolbarTitle.setText(provide.getOnSelectTittle());
        }

        private void setOnSelectToolBarNavigationIconIfNeed(IToolBarParamsProvide provide){
            final int iconId = provide.getOnSelectNavigationIconResId();
            if (iconId != 0) {
                mToolbar.setNavigationIcon(iconId);
            } else {
                Log.v(TAG, "setOnSelectToolBarNavigationIconIfNeed: resId = 0, at" + provide.getTAGName());
            }
        }

        private void setOnSelectToolBarNavigationIconListenerIfNeed(IToolBarParamsProvide provide){
            final View.OnClickListener listener = provide.getNavigationIconOnClickListener();
            if (listener != null) {
                mToolbar.setNavigationOnClickListener(listener);
            } else {
                Log.v(TAG, "setOnSelectToolBarNavigationIconListenerIfNeed: listener = null, at" + provide.getTAGName());
            }
        }

        private void setOnSelectToolBarMenuIfNeed(IToolBarParamsProvide provide){
            final int menuId = provide.getOnSelectMenuResId();
            if (menuId != 0) {
                mToolbar.inflateMenu(menuId);
            } else {
                Log.v(TAG, "setOnSelectToolBarMenuIfNeed: resId = 0, at" + provide.getTAGName());
            }
        }

        private void setOnSelectToolBarMenuOnMenuItemClickListenerIfNeed(IToolBarParamsProvide provide){
            final Toolbar.OnMenuItemClickListener listener = provide.getMenuOnMenuItemClickListener();
            if (listener != null) {
                mToolbar.setOnMenuItemClickListener(listener);
            } else {
                Log.v(TAG, "setOnSelectToolBarNavigationIconListenerIfNeed: listener = null, at" + provide.getTAGName());
            }
        }

        private void setOnSelectTabLayoutIconIfNeed(TabLayout.Tab tab, ITabLayoutParamsProvide provide){
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

        private void setOnUnSelectTabLayoutIconIfNeed(TabLayout.Tab tab, ITabLayoutParamsProvide provide){
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
        Log.i(TAG, "onTabSelected = " + tab.getPosition());
        ParamsProvider paramsProvider = mHashMap.get(tab.getTag());
        //ToolBar
        setOnSelectToolBarTitle(paramsProvider);

        setOnSelectToolBarNavigationIconIfNeed(paramsProvider);
        setOnSelectToolBarNavigationIconListenerIfNeed(paramsProvider);

        setOnSelectToolBarMenuIfNeed(paramsProvider);
        setOnSelectToolBarMenuOnMenuItemClickListenerIfNeed(paramsProvider);
        //TabLayout
        setOnSelectTabLayoutIconIfNeed(tab, paramsProvider);
        //ViewPager
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
        ParamsProvider paramsProvider = mHashMap.get(tab.getTag());
        //ToolBar
        setOnUnSelectToolBarTitle();
        setOnUnSelectMenuIfNeed();
        setOnUnSelectNavigationIcon();
        //Tab
        setOnUnSelectTabLayoutIconIfNeed(tab, paramsProvider);
        //ViewPager

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

    private final HashMap<String, ParamsProvider> mHashMap;
    private final TabLayout mTabLayout;
    private final ViewPager mViewPager;
    public ViewPagerOperator(final HashMap<String, ParamsProvider> hashMap , final TabLayout tabLayout, final ViewPager viewPager){
        mHashMap = hashMap;
        mTabLayout = tabLayout;
        mViewPager = viewPager;
    }

    public void init() {
        mViewPager.setAdapter(new PagerAdapter(mViewPager.getContext(), mHashMap));
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
    }

    public void refresh() {
        int count = mHashMap.size();
        for (int i = --count; i >= 0 ; i--) {
            mViewPager.setCurrentItem(i);
        }
    }

    class PagerAdapter extends android.support.v4.view.PagerAdapter {

        private String TAG = PagerAdapter.class.getName();

        private final Context mContext;
        private final HashMap<String, ParamsProvider> mHashMap;
        PagerAdapter(final Context context, HashMap<String, ParamsProvider> hashMap) {
            mContext = context;
            mHashMap = hashMap;
        }

        @Override
        public int getCount() {
            return mHashMap.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object o) {
            return o == view;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "Item " + (position + 1);
        }


        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
            ParamsProvider ParamsProvider = mHashMap.get(String.valueOf(position));

            //TODO:

            Log.i(TAG, "postion" + position);

//        ToolBarTablayoutViewPagerImpl toolBarTablayoutViewPagerImpl = mHashMap.get(tab.getTag());

            View view = inflater.inflate(R.layout.pager_item, container, false);
            container.addView(view);
            TextView title = (TextView) view.findViewById(R.id.item_title);
            title.setText(String.valueOf(position + 1));

            return view;
        }
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
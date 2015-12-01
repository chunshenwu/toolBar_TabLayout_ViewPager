package jason.toolBar_TabLayout_ViewPager.view.viewPager.base.builder;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import java.util.HashMap;

import jason.toolBar_TabLayout_ViewPager.view.viewPager.base.operator.BaseUIOperator;
import jason.toolBar_TabLayout_ViewPager.view.viewPager.base.operator.TabLayoutUIOperator;
import jason.toolBar_TabLayout_ViewPager.view.viewPager.base.operator.ToolBarUIOperator;
import jason.toolBar_TabLayout_ViewPager.view.viewPager.base.operator.ViewPagerUIOperator;
import jason.toolBar_TabLayout_ViewPager.view.viewPager.base.paramsWork.BaseParamsWorker;
import jason.toolBar_TabLayout_ViewPager.view.viewPager.sample1.MainTab;

public class MainView {

    private final HashMap<Integer, BaseParamsWorker> mHashMap;

    private final InitialManager mInitialManager;
    private final ToolBarUIOperator mToolBarOperator;
    private final TabLayoutUIOperator mTabLayoutOperator;
    private final ViewPagerUIOperator mViewPagerOperator;
    private int mPostition = 0;


    public static class Builder {
        private final MainView mMainView;

        public Builder(MainTab appCompatActivity, Toolbar toolbar, TabLayout tabLayout, ViewPager viewPager) {
            mMainView = new MainView(appCompatActivity, toolbar, tabLayout, viewPager);
        }

        public Builder addPageWithTab(final BaseParamsWorker paramsWorker) {
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
        mToolBarOperator = new ToolBarUIOperator(mHashMap, appCompatActivity, toolbar);
        mTabLayoutOperator = new TabLayoutUIOperator(mHashMap, tabLayout);
        mViewPagerOperator = new ViewPagerUIOperator(mHashMap, viewPager);

        mInitialManager = new InitialManager(tabLayout, viewPager,  appCompatActivity.getSupportFragmentManager());
    }

    class InitialManager {

        private final TabLayout mTabLayout;
        private final ViewPager mViewPager;
        private final FragmentManager mFragmentManager;

        InitialManager(final TabLayout tabLayout, final ViewPager viewPager, final FragmentManager fragmentManager) {
            mTabLayout = tabLayout;
            mViewPager = viewPager;
            mFragmentManager = fragmentManager;
        }

        public void init() {
            mTabLayout.setOnTabSelectedListener(new OnTabSelectedListenerImpl(mHashMap, new BaseUIOperator[]{mToolBarOperator, mViewPagerOperator, mTabLayoutOperator}));
            mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
            mViewPager.setAdapter(new ViewPagerAdapter(mHashMap, mFragmentManager));
        }

        class OnTabSelectedListenerImpl implements TabLayout.OnTabSelectedListener {

            private final String TAG = OnTabSelectedListenerImpl.class.getSimpleName();

            private final HashMap<Integer, BaseParamsWorker> mHashMap;
            private final BaseUIOperator[] mBaseUIOperator;

            public OnTabSelectedListenerImpl(HashMap<Integer, BaseParamsWorker> hashMap, BaseUIOperator... baseUIOperator) {
                mHashMap = hashMap;
                mBaseUIOperator = baseUIOperator;
            }

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mPostition = tab.getPosition();
                BaseParamsWorker paramsWorker = mHashMap.get(mPostition);
                Log.i(TAG, "onTabSelected = " + tab.getPosition() + " " + paramsWorker.getTagName());
                for (BaseUIOperator operator : mBaseUIOperator) {
                    operator.setWorker(paramsWorker);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                BaseParamsWorker paramsWorker = mHashMap.get(tab.getPosition());
                Log.i(TAG, "onTabUnselected = " + tab.getPosition() + " " + paramsWorker.getTagName());
                for (BaseUIOperator operator : mBaseUIOperator) {
                    operator.cleanWorker(paramsWorker);
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        }

        class ViewPagerAdapter extends FragmentStatePagerAdapter {
            private final String TAG = ViewPagerAdapter.class.getSimpleName();

            private HashMap<Integer, BaseParamsWorker> mHashMap;

            public ViewPagerAdapter(final HashMap<Integer, BaseParamsWorker> hashMap, FragmentManager fm) {
                super(fm);
                mHashMap = hashMap;
            }

            @Override
            public Fragment getItem(int position) {
                BaseParamsWorker paramsWorker;
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
    }

    public void addPageWithTab(final BaseParamsWorker paramsWorker) {
        final int position = mHashMap.size();
        paramsWorker.setPosition(position);
        mHashMap.put(position, paramsWorker);
        mTabLayoutOperator.addPageWithTab(paramsWorker);
    }

    public void init() {
        mInitialManager.init();
        mToolBarOperator.init();
        mTabLayoutOperator.init();
        mViewPagerOperator.init();
    }

    public void setDefault() {
        final int DEFAULT_INDEX = 3;
        final BaseParamsWorker paramsWorker = mHashMap.get(DEFAULT_INDEX);
        mViewPagerOperator.setWorker(paramsWorker);
    }

    public BaseParamsWorker getCurrentWorker() {
        return mHashMap.get(mPostition);
    }
}


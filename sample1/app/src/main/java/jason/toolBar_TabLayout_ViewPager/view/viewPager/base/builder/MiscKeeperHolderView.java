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

import jason.toolBar_TabLayout_ViewPager.view.viewPager.base.uiOperator.BaseUIOperator;
import jason.toolBar_TabLayout_ViewPager.view.viewPager.base.uiOperator.TabLayoutUIOperator;
import jason.toolBar_TabLayout_ViewPager.view.viewPager.base.uiOperator.ToolBarUIOperator;
import jason.toolBar_TabLayout_ViewPager.view.viewPager.base.uiOperator.ViewPagerUIOperator;
import jason.toolBar_TabLayout_ViewPager.view.viewPager.base.miscKeeper.BaseViewMiscKeeper;
import jason.toolBar_TabLayout_ViewPager.view.viewPager.sample1.MainTab;

public class MiscKeeperHolderView {

    private final HashMap<Integer, BaseViewMiscKeeper> mHashMap;

    private final InitialManager mInitialManager;
    private final ToolBarUIOperator mToolBarOperator;
    private final TabLayoutUIOperator mTabLayoutOperator;
    private final ViewPagerUIOperator mViewPagerOperator;
    private int mPostition = 0;


    public static class Builder {
        private final MiscKeeperHolderView mMiscKeeperHolderView;

        public Builder(MainTab appCompatActivity, Toolbar toolbar, TabLayout tabLayout, ViewPager viewPager) {
            mMiscKeeperHolderView = new MiscKeeperHolderView(appCompatActivity, toolbar, tabLayout, viewPager);
        }

        public Builder addViewMiscKeeper(final BaseViewMiscKeeper baseViewMiscKeeper) {
            mMiscKeeperHolderView.addViewMiscKeeper(baseViewMiscKeeper);
            return this;
        }

        public MiscKeeperHolderView build() {
            mMiscKeeperHolderView.init();
            mMiscKeeperHolderView.setDefault();
            return mMiscKeeperHolderView;
        }
    }

    MiscKeeperHolderView(AppCompatActivity appCompatActivity, Toolbar toolbar, TabLayout tabLayout, ViewPager viewPager) {
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

            private final HashMap<Integer, BaseViewMiscKeeper> mHashMap;
            private final BaseUIOperator[] mBaseUIOperator;

            public OnTabSelectedListenerImpl(HashMap<Integer, BaseViewMiscKeeper> hashMap, BaseUIOperator... baseUIOperator) {
                mHashMap = hashMap;
                mBaseUIOperator = baseUIOperator;
            }

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mPostition = tab.getPosition();
                BaseViewMiscKeeper paramsKeeper = mHashMap.get(mPostition);
                Log.i(TAG, "onTabSelected = " + tab.getPosition() + " " + paramsKeeper.getTagName());
                for (BaseUIOperator operator : mBaseUIOperator) {
                    operator.setKeeper(paramsKeeper);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                BaseViewMiscKeeper paramsKeeper = mHashMap.get(tab.getPosition());
                Log.i(TAG, "onTabUnselected = " + tab.getPosition() + " " + paramsKeeper.getTagName());
                for (BaseUIOperator operator : mBaseUIOperator) {
                    operator.unSetKeeper(paramsKeeper);
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        }

        class ViewPagerAdapter extends FragmentStatePagerAdapter {
            private final String TAG = ViewPagerAdapter.class.getSimpleName();

            private HashMap<Integer, BaseViewMiscKeeper> mHashMap;

            public ViewPagerAdapter(final HashMap<Integer, BaseViewMiscKeeper> hashMap, FragmentManager fm) {
                super(fm);
                mHashMap = hashMap;
            }

            @Override
            public Fragment getItem(int position) {
                BaseViewMiscKeeper paramsKeeper;
                if (position > mHashMap.size()) {
                    Log.e(TAG, "getItem: Error");
                    return null;
                }
                paramsKeeper = mHashMap.get(position);
                return paramsKeeper.reAssigntFragment();
            }

            @Override
            public int getCount() {
                return mHashMap.size();
            }
        }
    }

    public void addViewMiscKeeper(final BaseViewMiscKeeper baseViewMiscKeeper) {
        final int position = mHashMap.size();
        baseViewMiscKeeper.setPosition(position);


        mHashMap.put(position, baseViewMiscKeeper);
        mTabLayoutOperator.adjustTabFromKeeper(baseViewMiscKeeper);
    }

    public void init() {
        mInitialManager.init();
        mToolBarOperator.init();
        mTabLayoutOperator.init();
        mViewPagerOperator.init();
    }

    public void setDefault() {
        final int DEFAULT_INDEX = 1;
        final BaseViewMiscKeeper paramsKeeper = mHashMap.get(DEFAULT_INDEX);
        mViewPagerOperator.setKeeper(paramsKeeper);
    }

    public BaseViewMiscKeeper getCurrentKeeper() {
        return mHashMap.get(mPostition);
    }
}


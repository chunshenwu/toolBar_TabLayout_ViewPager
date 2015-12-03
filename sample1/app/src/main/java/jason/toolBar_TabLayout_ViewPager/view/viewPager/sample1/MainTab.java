package jason.toolBar_TabLayout_ViewPager.view.viewPager.sample1;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;

import jason.toolBar_TabLayout_ViewPager.R;
import jason.toolBar_TabLayout_ViewPager.view.viewPager.base.builder.MiscKeeperHolderView;
import jason.toolBar_TabLayout_ViewPager.view.viewPager.sample1.miscKeepers.BulletinViewMiscKeeper;
import jason.toolBar_TabLayout_ViewPager.view.viewPager.sample1.miscKeepers.ChatsViewMiscKeeper;
import jason.toolBar_TabLayout_ViewPager.view.viewPager.sample1.miscKeepers.ContactsViewMiscKeeper;
import jason.toolBar_TabLayout_ViewPager.view.viewPager.sample1.miscKeepers.MoreViewMiscKeeper;
import jason.toolBar_TabLayout_ViewPager.view.viewPager.sample1.miscKeepers.SocialViewMiscKeeper;

public class MainTab extends AppCompatActivity {

    private MiscKeeperHolderView mMiscKeeperHolderView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        if (mMiscKeeperHolderView == null) {

            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            TabLayout tabLayout = (android.support.design.widget.TabLayout) findViewById(R.id.tabs) ;
            ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);

            mMiscKeeperHolderView = new MiscKeeperHolderView.Builder(this, toolbar, tabLayout, viewPager)
                .addViewMiscKeeper(new ContactsViewMiscKeeper(this))
                .addViewMiscKeeper(new ChatsViewMiscKeeper(this))
                .addViewMiscKeeper(new BulletinViewMiscKeeper(this))
                .addViewMiscKeeper(new SocialViewMiscKeeper(this))
                .addViewMiscKeeper(new MoreViewMiscKeeper(this))
                .build();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.e("MainTab", "onCreateOptionsMenu = " + mMiscKeeperHolderView.getCurrentKeeper().getMenuResId());
        getMenuInflater().inflate(mMiscKeeperHolderView.getCurrentKeeper().getMenuResId(), menu);
        return super.onCreateOptionsMenu(menu);
    }
}

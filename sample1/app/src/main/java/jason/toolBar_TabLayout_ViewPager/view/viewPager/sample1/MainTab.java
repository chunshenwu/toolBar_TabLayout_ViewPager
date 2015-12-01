package jason.toolBar_TabLayout_ViewPager.view.viewPager.sample1;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;

import jason.toolBar_TabLayout_ViewPager.R;
import jason.toolBar_TabLayout_ViewPager.view.viewPager.base.builder.MainView;
import jason.toolBar_TabLayout_ViewPager.view.viewPager.sample1.provider.BulletinParamsWorker;
import jason.toolBar_TabLayout_ViewPager.view.viewPager.sample1.provider.ChatsParamsWorker;
import jason.toolBar_TabLayout_ViewPager.view.viewPager.sample1.provider.ContactsParamsWorker;
import jason.toolBar_TabLayout_ViewPager.view.viewPager.sample1.provider.MoreParamsWorker;
import jason.toolBar_TabLayout_ViewPager.view.viewPager.sample1.provider.SocialParamsWorker;

public class MainTab extends AppCompatActivity {

    private MainView mMainView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        if (mMainView == null) {

            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            TabLayout tabLayout = (android.support.design.widget.TabLayout) findViewById(R.id.tabs) ;
            ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);

            mMainView = new MainView.Builder(this, toolbar, tabLayout, viewPager)
                .addPageWithTab(new ContactsParamsWorker(this))
                .addPageWithTab(new ChatsParamsWorker(this))
                .addPageWithTab(new BulletinParamsWorker(this))
                .addPageWithTab(new SocialParamsWorker(this))
                .addPageWithTab(new MoreParamsWorker(this))
                .build();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.e("MainTab", "onCreateOptionsMenu = " + mMainView.getCurrentWorker().getOnSelectMenuResId());
        this.getMenuInflater().inflate(mMainView.getCurrentWorker().getOnSelectMenuResId(), menu);
        return super.onCreateOptionsMenu(menu);
    }
}

package jason.toolBar_TabLayout_ViewPager.view.viewPager.emma2;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import jason.toolBar_TabLayout_ViewPager.R;
import jason.toolBar_TabLayout_ViewPager.view.viewPager.base.builder.ToolBarTablayoutViewPager;
import jason.toolBar_TabLayout_ViewPager.view.viewPager.emma2.provider.BulletinParamsWorker;
import jason.toolBar_TabLayout_ViewPager.view.viewPager.emma2.provider.ChatsParamsWorker;
import jason.toolBar_TabLayout_ViewPager.view.viewPager.emma2.provider.ContactsParamsWorker;
import jason.toolBar_TabLayout_ViewPager.view.viewPager.emma2.provider.MoreParamsWorker;
import jason.toolBar_TabLayout_ViewPager.view.viewPager.emma2.provider.SocialParamsWorker;

public class MainTab extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
    }


    @Override
    protected void onResume() {
        super.onResume();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TabLayout tabLayout = (android.support.design.widget.TabLayout) findViewById(R.id.tabs) ;
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        new ToolBarTablayoutViewPager.Builder(this, toolbar, tabLayout, viewPager)
            .addPageWithTab(new ContactsParamsWorker(getApplicationContext()))
            .addPageWithTab(new ChatsParamsWorker(getApplicationContext()))
            .addPageWithTab(new BulletinParamsWorker(getApplicationContext()))
            .addPageWithTab(new SocialParamsWorker(getApplicationContext()))
            .addPageWithTab(new MoreParamsWorker(getApplicationContext()))
            .build();
    }
}

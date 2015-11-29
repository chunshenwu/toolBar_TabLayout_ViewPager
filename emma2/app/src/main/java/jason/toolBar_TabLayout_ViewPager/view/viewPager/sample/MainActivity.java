package jason.toolBar_TabLayout_ViewPager.view.viewPager.sample;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import jason.toolBar_TabLayout_ViewPager.R;
import jason.toolBar_TabLayout_ViewPager.view.viewPager.templatePattern.ToolBarTablayoutViewPager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TabLayout tabLayout = (android.support.design.widget.TabLayout) findViewById(R.id.tabs) ;
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);

        new ToolBarTablayoutViewPager.Builder(this, toolbar, tabLayout, viewPager)
        .addProviderWithTab(new ContactsParamsProvider(getApplicationContext()))
        .addProviderWithTab(new ChatsParamsProvider(getApplicationContext()))
        .addProviderWithTab(new BulletinParamsProvider(getApplicationContext()))
        .addProviderWithTab(new SocialParamsProvider(getApplicationContext()))
        .addProviderWithTab(new MoreParamsProvider(getApplicationContext()))
        .build()
        ;
    }
}

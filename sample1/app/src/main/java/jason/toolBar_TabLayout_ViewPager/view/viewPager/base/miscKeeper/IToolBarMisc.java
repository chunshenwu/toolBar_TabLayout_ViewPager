package jason.toolBar_TabLayout_ViewPager.view.viewPager.base.miscKeeper;

import android.view.MenuItem;
import android.view.View;

/**
 * Created by cs on 2015/11/28.
 */


interface IToolBarMisc extends IBaseMisc {

    String getTittleText();

    int getNavigationIconResId();
    void onNavigationOnClick(View v);


    int getMenuResId();
    boolean onMenuItemClick(MenuItem item);
}

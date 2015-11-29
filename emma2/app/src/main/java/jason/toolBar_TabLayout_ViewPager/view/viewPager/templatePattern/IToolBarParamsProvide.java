package jason.toolBar_TabLayout_ViewPager.view.viewPager.templatePattern;

import android.support.v7.widget.Toolbar;
import android.view.View;

/**
 * Created by cs on 2015/11/28.
 */


public interface IToolBarParamsProvide extends IProvide {

    //For Toolbar
    String getOnSelectTittle();

    View.OnClickListener getNavigationIconOnClickListener();
    int getOnSelectNavigationIconResId();

    Toolbar.OnMenuItemClickListener getMenuOnMenuItemClickListener();
    int getOnSelectMenuResId();

    //General
    int getTitleTypeface();
    int getTitleTextSize();
    int getTitleTextColor();
    int getBackgroundColor();


}

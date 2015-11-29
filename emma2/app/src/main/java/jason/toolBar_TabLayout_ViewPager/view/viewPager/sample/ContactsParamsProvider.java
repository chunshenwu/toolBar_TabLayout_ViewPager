package jason.toolBar_TabLayout_ViewPager.view.viewPager.sample;

import android.content.Context;
import android.support.v7.widget.Toolbar;
import android.view.View;

import jason.toolBar_TabLayout_ViewPager.R;
import jason.toolBar_TabLayout_ViewPager.view.viewPager.templatePattern.ParamsProvider;

/**
 * Created by cs on 15/11/28.
 */
public class ContactsParamsProvider extends ParamsProvider {

    public ContactsParamsProvider(Context conext) {
        super(conext);
    }

    @Override
    public String getOnSelectTittle() {
        return "Contacts";
    }

    @Override
    public View.OnClickListener getNavigationIconOnClickListener() {
        return null;
    }

    @Override
    public int getOnSelectNavigationIconResId() {
        return 0;
    }

    @Override
    public Toolbar.OnMenuItemClickListener  getMenuOnMenuItemClickListener() {
        return null;
    }

    @Override
    public int getOnSelectMenuResId() {
        return 0;
    }

    @Override
    public int getOnSelectResId() {
        return R.drawable.in;
    }

    @Override
    public int getOnUnSelectResId() {
        return R.drawable.out;
    }

    @Override
    public View getOnSelectView() {
        return null;
    }

    @Override
    public String getTAGName() {
        return this.getClass().getSimpleName();
    }
}

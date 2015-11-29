package jason.toolBar_TabLayout_ViewPager.view.viewPager.templatePattern;


import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;

/**
 * Created by cs on 2015/11/29.
 */
public abstract class ParamsProvider implements IToolBarParamsProvide, ITabLayoutParamsProvide, IViewPagerParamsProvide {

    private final Context mContext;

    public ParamsProvider(final Context conext) {
        mContext = conext;
    }

    public Context getContext() {
        return mContext;
    }

    @Override
    public int getTitleTypeface() {
        return Typeface.BOLD;
    }

    @Override
    public int getTitleTextSize() {
        return 20;
    }

    @Override
    public int getTitleTextColor() {
        return Color.BLACK;
    }

    @Override
    public int getBackgroundColor() {
        return Color.BLUE;
    }


}

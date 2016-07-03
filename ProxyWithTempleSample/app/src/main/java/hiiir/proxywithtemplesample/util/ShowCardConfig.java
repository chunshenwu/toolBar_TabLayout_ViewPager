package hiiir.proxywithtemplesample.util;

import android.content.Context;
import android.content.SharedPreferences;

import hiiir.proxywithtemplesample.view.base.RecyclerViewAdapter;


/**
 * Created by jason_wu on 5/4/16.
 */
public class ShowCardConfig {

    private static final String PREFS_NAME = "ShowCard_config";
    private static final String STATE_NAME = "State";

    private final SharedPreferences mSharedPreferences;

    public ShowCardConfig(Context context) {
        mSharedPreferences = context.getApplicationContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public boolean isFirstTimeShowInAP(RecyclerViewAdapter.IRecyclerViewAdapterPresenter iRecyclerViewAdapterPresenter) {
        return (mSharedPreferences.getLong(iRecyclerViewAdapterPresenter.getClass().getSimpleName(), -1) == -1);
    }

    public void setFirstTimeShowInAP(RecyclerViewAdapter.IRecyclerViewAdapterPresenter iRecyclerViewAdapterPresenter) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putLong(iRecyclerViewAdapterPresenter.getClass().getSimpleName(), System.currentTimeMillis());
        editor.apply();
    }

    public int getState(RecyclerViewAdapter.IRecyclerViewAdapterPresenter iRecyclerViewAdapterPresenter, int defState) {
        return mSharedPreferences.getInt(iRecyclerViewAdapterPresenter.getClass().getSimpleName()+STATE_NAME, defState);
    }

    public void setState(RecyclerViewAdapter.IRecyclerViewAdapterPresenter iRecyclerViewAdapterPresenter, int state) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putInt(iRecyclerViewAdapterPresenter.getClass().getSimpleName()+STATE_NAME, state);
        editor.apply();
    }
}

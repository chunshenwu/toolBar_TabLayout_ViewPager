package hiiir.proxywithtemplesample.view.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import java.util.ArrayList;

import hiiir.proxywithtemplesample.model.Constant;
import hiiir.proxywithtemplesample.util.CommonLog;
import hiiir.proxywithtemplesample.util.ShowCardConfig;

/**
 * Created by jason_wu on 5/4/16.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements AdapterView.OnItemClickListener{

    private static final String TAG = "RecyclerViewAdapter";
    private static final boolean DEG = true;

    private final LayoutInflater mLayoutInflater;
    private final Context mContext;
    private final ShowCardConfig mShowCardConfig;

    private ArrayList<IRecyclerViewAdapterPresenter> mIRecyclerViewAdapterPresenterList = new ArrayList<>();

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    public interface IRecyclerViewAdapterPresenter extends IViewLifeCycle, IReport {

    }

    private interface IViewLifeCycle {
        int getResourceId();
        //每次新產生
        RecyclerView.ViewHolder onCreateViewHolder(LayoutInflater mLayoutInflater, ViewGroup parent);
        //每次出現於ui
        void onBindViewHolder();
        //每次被點擊
        void onClick();
    }

    private interface IReport {
        void report4firstTimeShowInAP();
        Constant.CardContract.CardId getCardId();
    }

    public void addToPresenterList( final int position, IRecyclerViewAdapterPresenter iRecyclerViewAdapterPresenter) {
        mIRecyclerViewAdapterPresenterList.add(position, iRecyclerViewAdapterPresenter);
    }

    public void addToPresenterList(IRecyclerViewAdapterPresenter iRecyclerViewAdapterPresenter) {
        mIRecyclerViewAdapterPresenterList.add(iRecyclerViewAdapterPresenter);
    }

    public void removeFromPresenterList(IRecyclerViewAdapterPresenter iRecyclerViewAdapterPresenter) {
        mIRecyclerViewAdapterPresenterList.remove(iRecyclerViewAdapterPresenter);
    }

    public ArrayList<IRecyclerViewAdapterPresenter> getFromPresenterList() {
        return mIRecyclerViewAdapterPresenterList;
    }

    public RecyclerViewAdapter(Context context) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
        mShowCardConfig = new ShowCardConfig(mContext);
    }

    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final IRecyclerViewAdapterPresenter presenter = mIRecyclerViewAdapterPresenterList.get(viewType);
        RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder )parent.getTag(presenter.getResourceId());
        if (viewHolder == null) {
            final long startTime = System.currentTimeMillis();
            viewHolder = presenter.onCreateViewHolder(mLayoutInflater, parent);
            final long endTime = System.currentTimeMillis();
            CommonLog.d(DEG, TAG, "onCreateViewHolder: " + presenter.getClass().getSimpleName() + " @" + presenter.hashCode() + ", time is "+ (endTime- startTime));

            setOnClickListener(viewHolder, viewType);
            parent.setTag(presenter.getResourceId(), viewHolder);

            report4firstTimeShowInLaunch(mContext, presenter);
            report4firstTimeShowInAP(presenter);
        }
            return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final IRecyclerViewAdapterPresenter presenter = mIRecyclerViewAdapterPresenterList.get(position);
        final long startTime = System.currentTimeMillis();
        presenter.onBindViewHolder();
        final long endTime = System.currentTimeMillis();

        CommonLog.d(DEG, TAG, "onBindViewHolder: " + presenter.getClass().getSimpleName() + " @" + presenter.hashCode() + ", time is "+ (endTime - startTime));

    }


    @Override
    public int getItemCount() {
        return mIRecyclerViewAdapterPresenterList.size();
    }

    //第幾個就是第幾種Type
    @Override
    public int getItemViewType(int position) {
        return position;
    }

    private void setOnClickListener(final RecyclerView.ViewHolder viewHolder, int viewType) {
        viewHolder.itemView.setTag(viewType);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Object tag = v.getTag();
                if (tag != null) {

                    final IRecyclerViewAdapterPresenter presenter;
                    try {
                        presenter = mIRecyclerViewAdapterPresenterList.get(Integer.parseInt(String.valueOf(tag)));
                        presenter.onClick();
                        if (DEG) {
                            CommonLog.d(TAG, presenter.getClass().getSimpleName() + "_onClick@" + presenter.hashCode());
                        }
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public static void report4firstTimeShowInLaunch(final Context context, final IRecyclerViewAdapterPresenter presenter) {
        CommonLog.d(DEG, TAG, "report4firstTimeShowInLaunch: " + presenter.getClass().getSimpleName() + "@" +presenter.hashCode() +
            ", cmbattery_result_page, card = " + presenter.getCardId().getId());
    }

    private void report4firstTimeShowInAP(final IRecyclerViewAdapterPresenter presenter) {
        //等PM要求再打開
//        if (mShowCardConfig.isFirstTimeShowInAP(presenter)) {
//            mShowCardConfig.setFirstTimeShowInAP(presenter);
//            if (DEG) {
//                CommonLog.d(TAG, presenter.getClass().getSimpleName() + "_report4firstTimeShowInAP@" + presenter.hashCode());
//            }
//            presenter.report4firstTimeShowInAP();
//        }
    }
}
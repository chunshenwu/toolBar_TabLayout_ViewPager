package hiiir.proxywithtemplesample.view.card;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;

import hiiir.proxywithtemplesample.model.Constant;
import hiiir.proxywithtemplesample.util.CommonLog;
import hiiir.proxywithtemplesample.view.base.RecyclerViewAdapter;

/**
 * Created by jason_wu on 5/5/16.
 */
public class CardProxy {

    private static final boolean DEG = true;
    private static final String TAG = "CardProxy";
    private HashMap<Constant.CardContract.CardType, Integer> mTypeCountMap;

    private Context mContext;

    public interface ICardProxyPresenter extends RecyclerViewAdapter.IRecyclerViewAdapterPresenter {
        boolean isNeedShow();
        Constant.CardContract.CardType getCardType();
        void resumeCallBack();
        void pauseCallBack();
        void destroyCallBack();

    }

    private ArrayList<ICardProxyPresenter> mICardProxyPresenterList = new ArrayList<>();

    private final RecyclerViewAdapter mAdapter;
    public CardProxy(final Context context) {
        mContext = context;
        mAdapter = new RecyclerViewAdapter(context);
        mTypeCountMap = new HashMap<>(Constant.CardContract.CardType.values().length);
        initCardList();

        checkCardListToAdd();
    }

    private void initCardList() {
        mICardProxyPresenterList.add(new SampleCard1(mContext));
        mICardProxyPresenterList.add(new SampleCard2(mContext));
        mICardProxyPresenterList.add(new SampleCard3(mContext));
    }

    private void checkCardListToAdd() {
        for (ICardProxyPresenter presenter : mICardProxyPresenterList) {
            checkPresenterToAddCard(presenter);
        }
    }

    public CardProxy reInitAndAddCard(ICardProxyPresenter addPresenter) {
        mICardProxyPresenterList.add(addPresenter);
        checkPresenterToAddCard(addPresenter);
        return this;
    }

    private void checkPresenterToAddCard(ICardProxyPresenter presenter) {
        boolean isNeedShow = presenter.isNeedShow();
        if (DEG) {
            CommonLog.d(TAG, "checkCardListToAdd: " + presenter.getClass().getSimpleName() + " isNeedShow = " + isNeedShow);
        }
        if (isNeedShow) {
            mAdapter.addToPresenterList(presenter);
            addTypeCount(presenter.getCardType());
        }
    }

    private void addTypeCount(final Constant.CardContract.CardType type) {
        int count = getTypeCount(type);
        mTypeCountMap.put(type, ++count);
    }

    public int getTypeCount(final Constant.CardContract.CardType type) {
        if (mTypeCountMap.containsKey(type)) {
            return mTypeCountMap.get(type);
        } else {
            return 0;
        }
    }

    public boolean containCard(final Constant.CardContract.CardId id) {
        final ArrayList<RecyclerViewAdapter.IRecyclerViewAdapterPresenter> presenterList = mAdapter.getFromPresenterList();
        for (RecyclerViewAdapter.IRecyclerViewAdapterPresenter presenter : presenterList) {
            if (presenter.getCardId() == id) {
                return true;
            }
        }
        return false;
    }

    public boolean hasCard() {
        return (mAdapter.getItemCount() > 0);
    }

    public int totalCardNum() {
        return mAdapter.getItemCount();
    }

    public RecyclerView.Adapter getAdapter() {
        return mAdapter;
    }

    //TODO:開api給Adapter add
    public void add(final int position, RecyclerViewAdapter.IRecyclerViewAdapterPresenter addPresenter) {
        mAdapter.addToPresenterList(position, addPresenter);
        mAdapter.notifyItemInserted(position);
    }

    public void resume() {
        for (ICardProxyPresenter presenter : mICardProxyPresenterList) {
            final long startTime = System.currentTimeMillis();
            presenter.resumeCallBack();
             final long endTime = System.currentTimeMillis();
            CommonLog.d(DEG, TAG, "resume: " + presenter.getClass().getSimpleName() + " @" + presenter.hashCode() + ", time is "+ (endTime- startTime));
        }
    }

    public void pause() {
        for (ICardProxyPresenter presenter : mICardProxyPresenterList) {
            final long startTime = System.currentTimeMillis();
            presenter.pauseCallBack();
            final long endTime = System.currentTimeMillis();
            CommonLog.d(DEG, TAG, "pause: " + presenter.getClass().getSimpleName() + " @" + presenter.hashCode() + ", time is "+ (endTime- startTime));
        }
    }


    public void destroy() {
        for (ICardProxyPresenter presenter : mICardProxyPresenterList) {
            final long startTime = System.currentTimeMillis();
            presenter.destroyCallBack();
            final long endTime = System.currentTimeMillis();
            CommonLog.d(DEG, TAG, "destroy: " + presenter.getClass().getSimpleName() + " @" + presenter.hashCode() + ", time is "+ (endTime- startTime));
        }
    }
}

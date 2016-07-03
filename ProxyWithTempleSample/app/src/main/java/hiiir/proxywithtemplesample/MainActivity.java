package hiiir.proxywithtemplesample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;

import hiiir.proxywithtemplesample.view.card.CardProxy;

public class MainActivity extends AppCompatActivity {

    //View
    private RecyclerView mCardRecyclerView;
    private CardProxy mCardProxy;
    private Button mSmallButton;
    private Button mMediumButton;
    private Button mBigButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initCard();
    }

    private void initView() {
        mCardRecyclerView = (RecyclerView) findViewById(R.id.cardRecyclerView);
        mCardRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    }

    private void initCard() {
        mCardProxy = new CardProxy(getApplicationContext());
        mCardRecyclerView.setAdapter(mCardProxy.getAdapter());
    }
}

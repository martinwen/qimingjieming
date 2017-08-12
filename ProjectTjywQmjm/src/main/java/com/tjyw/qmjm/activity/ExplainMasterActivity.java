package com.tjyw.qmjm.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.mikepenz.fastadapter.commons.adapters.FastItemAdapter;
import com.tjyw.atom.network.RxSchedulersHelper;
import com.tjyw.atom.pub.inject.From;
import com.tjyw.qmjm.R;
import com.tjyw.qmjm.adapter.ExplainMasterAdapter;
import com.tjyw.qmjm.fragment.ExplainMasterDestinyFragment;
import com.tjyw.qmjm.fragment.ExplainMasterOverviewFragment;
import com.tjyw.qmjm.fragment.ExplainMasterZodiacFragment;
import com.tjyw.qmjm.item.ExplainHeaderWordItem;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.functions.Action1;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by stephen on 17-8-11.
 */
public class ExplainMasterActivity extends BaseToolbarActivity {

    @From(R.id.explainNameContainer)
    protected RecyclerView explainNameContainer;

    @From(R.id.explainMasterContainer)
    protected ViewPager explainMasterContainer;

    protected ExplainMasterAdapter explainMasterAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.atom_explain_host);
        tSetToolBar(getString(R.string.atom_pub_resStringMasterTabExplain));

        List<ExplainHeaderWordItem> itemList = new ArrayList<ExplainHeaderWordItem>();
        itemList.add(new ExplainHeaderWordItem("郭"));
        itemList.add(new ExplainHeaderWordItem("祎"));
        itemList.add(new ExplainHeaderWordItem("赫"));

        FastItemAdapter<ExplainHeaderWordItem> fastItemAdapter = new FastItemAdapter<>();
        fastItemAdapter.set(itemList);

        explainNameContainer.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
        explainNameContainer.setAdapter(fastItemAdapter);

        Observable.timer(2, TimeUnit.SECONDS)
                .compose(RxSchedulersHelper.<Long>io_main())
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        explainMasterAdapter = new ExplainMasterAdapter(getSupportFragmentManager());
                        explainMasterAdapter.setExplainMasterOverviewFragment(ExplainMasterOverviewFragment.newInstance());
                        explainMasterAdapter.setExplainMasterZodiacFragment(ExplainMasterZodiacFragment.newInstance());
                        explainMasterAdapter.setExplainMasterDestinyFragment(ExplainMasterDestinyFragment.newInstance());

                        explainMasterContainer.setAdapter(explainMasterAdapter);
                    }
                });
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

}

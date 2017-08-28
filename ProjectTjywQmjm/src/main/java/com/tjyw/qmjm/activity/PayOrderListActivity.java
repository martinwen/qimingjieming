package com.tjyw.qmjm.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.mikepenz.fastadapter.commons.adapters.FastItemAdapter;
import com.tjyw.atom.network.presenter.PayPresenter;
import com.tjyw.atom.pub.inject.From;
import com.tjyw.qmjm.R;
import com.tjyw.qmjm.item.PayOrderListItem;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import me.dkzwm.widget.srl.SmoothRefreshLayout;
import nucleus.factory.RequiresPresenter;

/**
 * Created by stephen on 25/08/2017.
 */
@RequiresPresenter(PayPresenter.class)
public class PayOrderListActivity extends BaseToolbarActivity<PayPresenter<PayOrderListActivity>>
        implements SmoothRefreshLayout.OnRefreshListener {

    @From(R.id.payOrderRefreshLayout)
    protected SmoothRefreshLayout payOrderRefreshLayout;

    @From(R.id.payOrderContainer)
    protected RecyclerView payOrderContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.atom_pay_order_list);
        tSetToolBar(getString(R.string.atom_pub_resStringPayList));

        immersionBarWith()
                .fitsSystemWindows(true)
                .statusBarColor(R.color.colorPrimary)
                .statusBarDarkFont(true)
                .init();

        FastItemAdapter<PayOrderListItem> adapter = new FastItemAdapter<PayOrderListItem>();
        payOrderContainer.setAdapter(adapter);
        payOrderContainer.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        payOrderContainer.addItemDecoration(
                new HorizontalDividerItemDecoration.Builder(getApplicationContext())
                        .color(R.color.atom_pub_resColorDivider)
                        .sizeResId(R.dimen.atom_pubResDimenRecyclerViewDividerSize)
                        .marginResId(R.dimen.atom_pubResDimenRecyclerViewDivider16dp, R.dimen.atom_pubResDimenRecyclerViewDivider16dp)
                        .showLastDivider()
                        .build());

        adapter.add(new PayOrderListItem(null));
        adapter.add(new PayOrderListItem(null));
        adapter.add(new PayOrderListItem(null));

        payOrderRefreshLayout.setOnRefreshListener(this);
        onRefreshBegin(true);
    }

    @Override
    public void onRefreshBegin(boolean isRefresh) {
        if (isRefresh) {

        } else {

        }
    }

    @Override
    public void onRefreshComplete(boolean isSuccessful) {

    }
}

package com.tjyw.qmjm.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.mikepenz.fastadapter.FastAdapter;
import com.mikepenz.fastadapter.IAdapter;
import com.mikepenz.fastadapter.commons.adapters.FastItemAdapter;
import com.mikepenz.fastadapter.listeners.ClickEventHook;
import com.tjyw.atom.network.conf.IApiField;
import com.tjyw.atom.network.param.ListRequestParam;
import com.tjyw.atom.network.presenter.PayPresenter;
import com.tjyw.atom.network.presenter.listener.OnApiFavoritePostListener;
import com.tjyw.atom.network.presenter.listener.OnApiPostNamingListener;
import com.tjyw.atom.network.result.RIdentifyResult;
import com.tjyw.atom.network.result.RNameDefinition;
import com.tjyw.atom.pub.inject.From;
import com.tjyw.qmjm.R;
import com.tjyw.qmjm.factory.IClientActivityLaunchFactory;
import com.tjyw.qmjm.item.NamingWordItem;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import nucleus.factory.RequiresPresenter;

/**
 * Created by stephen on 28/08/2017.
 */
@RequiresPresenter(PayPresenter.class)
public class PayOrderNameListActivity extends BaseToolbarActivity<PayPresenter<PayOrderNameListActivity>>
        implements OnApiPostNamingListener, OnApiFavoritePostListener.PostAddListener, OnApiFavoritePostListener.PostRemoveListener {

    @From(R.id.namingListContainer)
    protected RecyclerView namingListContainer;

    protected FastItemAdapter<NamingWordItem> nameDefinitionAdapter;

    protected String orderNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        orderNo = pGetStringExtra(IApiField.O.orderNo, null);
        if (TextUtils.isEmpty(orderNo)) {
            finish();
            return ;
        } else {
            setContentView(R.layout.atom_pay_order_name_list);
            tSetToolBar(getString(R.string.atom_pub_resStringNamingList));

            immersionBarWith()
                    .fitsSystemWindows(true)
                    .statusBarColor(R.color.colorPrimary)
                    .statusBarDarkFont(true)
                    .init();
        }

        nameDefinitionAdapter = new FastItemAdapter<NamingWordItem>();
        nameDefinitionAdapter.withOnClickListener(new FastAdapter.OnClickListener<NamingWordItem>() {
            @Override
            public boolean onClick(View v, IAdapter<NamingWordItem> adapter, NamingWordItem item, int position) {
                IClientActivityLaunchFactory.launchExplainMasterActivity(
                        PayOrderNameListActivity.this, new ListRequestParam(item.src)
                );
                return true;
            }
        }).withItemEvent(new ClickEventHook<NamingWordItem>() {
            @Nullable
            @Override
            public View onBind(@NonNull RecyclerView.ViewHolder viewHolder) {
                if (viewHolder instanceof NamingWordItem.NamingWordHolder) {
                    return ((NamingWordItem.NamingWordHolder) viewHolder).getNameWordCollect();
                } else {
                    return super.onBind(viewHolder);
                }
            }

            @Override
            public void onClick(View v, int position, FastAdapter<NamingWordItem> fastAdapter, NamingWordItem item) {
                maskerShowProgressView(true);
                if (item.src.favorite && item.src.id > 0) {
                    getPresenter().postFavoriteRemove(item.src.id, item);
                } else {
                    getPresenter().postFavoriteAdd(
                            item.src.surname,
                            item.src.getGivenName(),
                            item.src.day,
                            item.src.gender,
                            item
                    );
                }
            }
        });

        namingListContainer.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        namingListContainer.setAdapter(nameDefinitionAdapter);
        namingListContainer.addItemDecoration(
                new HorizontalDividerItemDecoration.Builder(getApplicationContext())
                        .color(R.color.atom_pub_resColorDivider)
                        .sizeResId(R.dimen.atom_pubResDimenRecyclerViewDividerSize)
                        .marginResId(R.dimen.atom_pubResDimenRecyclerViewDivider16dp)
                        .showLastDivider()
                        .build());

        maskerShowProgressView(false);
        getPresenter().postPayOrderNameList(orderNo);
    }

    @Override
    public void maskerOnClick(View view, int clickLabelRes) {
        super.maskerOnClick(view, clickLabelRes);
        maskerShowProgressView(false);
        getPresenter().postPayOrderNameList(orderNo);
    }

    @Override
    public void postOnNamingSuccess(RNameDefinition result) {
        maskerHideProgressView();
        if (null != result) {
            int size = result.size();
            if (size > 0) {
                List<NamingWordItem> itemList = new ArrayList<NamingWordItem>();
                for (int i = 0; i < size; i++) {
                    itemList.add(new NamingWordItem(result.get(i)));
                }

                nameDefinitionAdapter.add(itemList);
            }
        }
    }

    @Override
    public void postOnFavoriteAddSuccess(RIdentifyResult result, Object item) {
        maskerHideProgressView();
        if (item instanceof NamingWordItem) {
            showToast(R.string.atom_pub_resStringFavoriteHintAdd);
            NamingWordItem namingWordItem = (NamingWordItem) item;
            namingWordItem.src.id = result.id;
            namingWordItem.src.favorite = true;
            nameDefinitionAdapter.notifyAdapterDataSetChanged();
        }
    }

    @Override
    public void postOnFavoriteRemoveSuccess(Object item) {
        maskerHideProgressView();
        if (item instanceof NamingWordItem) {
            showToast(R.string.atom_pub_resStringFavoriteHintRemove);
            NamingWordItem namingWordItem = (NamingWordItem) item;
            namingWordItem.src.favorite = false;
            nameDefinitionAdapter.notifyAdapterDataSetChanged();
        }
    }
}

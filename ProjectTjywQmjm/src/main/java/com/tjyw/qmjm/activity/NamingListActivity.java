package com.tjyw.qmjm.activity;

import android.content.Context;
import android.content.Intent;
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
import com.tjyw.atom.network.IllegalRequestException;
import com.tjyw.atom.network.conf.IApiField;
import com.tjyw.atom.network.conf.ICode;
import com.tjyw.atom.network.model.PayService;
import com.tjyw.atom.network.param.ListRequestParam;
import com.tjyw.atom.network.presenter.IPost;
import com.tjyw.atom.network.presenter.NamingPresenter;
import com.tjyw.atom.network.presenter.listener.OnApiFavoritePostListener;
import com.tjyw.atom.network.presenter.listener.OnApiPayPostListener;
import com.tjyw.atom.network.presenter.listener.OnApiPostErrorListener;
import com.tjyw.atom.network.presenter.listener.OnApiPostNamingListener;
import com.tjyw.atom.network.result.RIdentifyResult;
import com.tjyw.atom.network.result.RNameDefinition;
import com.tjyw.atom.pub.inject.From;
import com.tjyw.qmjm.ClientQmjmApplication;
import com.tjyw.qmjm.R;
import com.tjyw.qmjm.dialog.NamingPayWindows;
import com.tjyw.qmjm.factory.IClientActivityLaunchFactory;
import com.tjyw.qmjm.item.NamingWordItem;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import nucleus.factory.RequiresPresenter;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by stephen on 14/08/2017.
 */
@RequiresPresenter(NamingPresenter.class)
public class NamingListActivity extends BaseToolbarActivity<NamingPresenter<NamingListActivity>> implements
        OnApiPostErrorListener,
        OnApiPostNamingListener,
        OnApiFavoritePostListener.PostAddListener, OnApiFavoritePostListener.PostRemoveListener,
        OnApiPayPostListener.PostPayServiceListener {

    @From(R.id.namingListContainer)
    protected RecyclerView namingListContainer;

    protected FastItemAdapter<NamingWordItem> nameDefinitionAdapter;

    protected NamingPayWindows namingPayWindows;

    protected ListRequestParam listRequestParam;

    protected PayService payService;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        listRequestParam = (ListRequestParam) pGetSerializableExtra(IApiField.P.param);
        if (null == listRequestParam) {
            finish();
            return ;
        } else {
            setContentView(R.layout.atom_naming_list);
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
                if (null != listRequestParam) {
                    if (hasOrderNo()) {
                        ListRequestParam param = new ListRequestParam(item.src);
                        IClientActivityLaunchFactory.launchExplainMasterActivity(NamingListActivity.this, param);
                    } else {
                        ListRequestParam param = (ListRequestParam) listRequestParam.clone();
                        param.name = item.src.getGivenName();
                        IClientActivityLaunchFactory.launchExplainMasterActivity(NamingListActivity.this, param);
                    }
                }
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
                if (null != listRequestParam) {
                    maskerShowProgressView(true);
                    if (item.src.favorite && item.src.id > 0) {
                        getPresenter().postFavoriteRemove(item.src.id, item);
                    } else {
                        getPresenter().postFavoriteAdd(
                                listRequestParam.surname,
                                item.src.getGivenName(),
                                listRequestParam.day,
                                listRequestParam.gender,
                                item
                        );
                    }
                }
            }
        });

        namingListContainer.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        namingListContainer.setAdapter(nameDefinitionAdapter);
        namingListContainer.addItemDecoration(
                new HorizontalDividerItemDecoration.Builder(ClientQmjmApplication.getContext())
                        .color(R.color.atom_pub_resColorDivider)
                        .sizeResId(R.dimen.atom_pubResDimenRecyclerViewDividerSize)
                        .marginResId(R.dimen.atom_pubResDimenRecyclerViewDivider16dp)
                        .showLastDivider()
                        .build());

        namingListContainer.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                switch (newState) {
                    case RecyclerView.SCROLL_STATE_IDLE:
                        if (! recyclerView.canScrollVertically(1) && ! hasOrderNo()) {
                            if (null == payService) {
                                maskerShowProgressView(true);
                                getPresenter().postPayService(listRequestParam.surname, listRequestParam.day);
                            } else if (null == namingPayWindows) {
                                namingPayWindows = NamingPayWindows.newInstance(getSupportFragmentManager(), listRequestParam, payService);
                            } else if (!namingPayWindows.isVisible()) {
                                namingPayWindows.show(getSupportFragmentManager(), NamingPayWindows.class.getName());
                            }
                        }
                }
            }
        });

        requestListData();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case ICode.SECTION.PAY:
                switch (resultCode) {
                    case ICode.PAY.ALIPAY_SUCCESS:
                    case ICode.PAY.WX_SUCCESS:
                        if (null != data) {
                            listRequestParam.orderNo = data.getStringExtra(IApiField.O.orderNo);
                            if (hasOrderNo()) {
                                requestListData();
                            }
                        }
                }
        }
    }

    @Override
    public void maskerOnClick(View view, int clickLabelRes) {
        requestListData();
    }

    @Override
    public void postOnExplainError(int postId, Throwable throwable) {
        throwable.printStackTrace();
        switch (postId) {
            case IPost.Naming:
                if (throwable instanceof IllegalRequestException) {
                    maskerShowMaskerLayout(throwable.getMessage(), R.string.atom_pub_resStringRetry);
                } else {
                    maskerShowMaskerLayout(getString(R.string.atom_pub_resStringNetworkBroken), R.string.atom_pub_resStringRetry);
                }
                break ;
            default:
                maskerHideProgressView();
        }
    }

    @Override
    public void postOnNamingSuccess(RNameDefinition result) {
        maskerHideProgressView();
        if (null == result || result.size() == 0) {
            maskerShowMaskerLayout(getString(R.string.atom_pub_resStringNetworkBroken), 0);
            return ;
        } else if (hasOrderNo() && null != result.param) {
            listRequestParam = result.param;
        }

        List<NamingWordItem> itemList = new ArrayList<NamingWordItem>();
        int size = result.size();
        for (int i = 0; i < size; i ++) {
            itemList.add(new NamingWordItem(result.get(i)));
        }

        nameDefinitionAdapter.set(itemList);
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

    @Override
    public void postOnPayServiceSuccess(PayService payService) {
        maskerHideProgressView();
        this.payService = payService;
        if (null == namingPayWindows) {
            namingPayWindows = NamingPayWindows.newInstance(getSupportFragmentManager(), listRequestParam, payService);
        } else {
            namingPayWindows.show(getSupportFragmentManager(), NamingPayWindows.class.getName());
        }
    }

    protected void requestListData() {
        if (null != listRequestParam) {
            maskerShowProgressView(false);
            if (hasOrderNo()) {
                getPresenter().postPayOrderNameList(listRequestParam.orderNo);
            } else {
                getPresenter().postNameDefinition(
                        listRequestParam.surname,
                        listRequestParam.day,
                        listRequestParam.gender,
                        listRequestParam.nameNumber
                );
            }
        }
    }

    protected boolean hasOrderNo() {
        return null != listRequestParam && ! TextUtils.isEmpty(listRequestParam.orderNo);
    }
}

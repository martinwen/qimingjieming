package com.tjyw.qmjm.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mikepenz.fastadapter.adapters.FooterAdapter;
import com.mikepenz.fastadapter.adapters.HeaderAdapter;
import com.mikepenz.fastadapter.commons.adapters.FastItemAdapter;
import com.tjyw.atom.network.RxSchedulersHelper;
import com.tjyw.atom.network.conf.IApiField;
import com.tjyw.atom.network.conf.ICode;
import com.tjyw.atom.network.conf.ISection;
import com.tjyw.atom.network.model.NameDefinition;
import com.tjyw.atom.network.model.PayService;
import com.tjyw.atom.network.param.ListRequestParam;
import com.tjyw.atom.network.presenter.NamingPresenter;
import com.tjyw.atom.network.presenter.listener.OnApiFavoritePostListener;
import com.tjyw.atom.network.presenter.listener.OnApiPayPostListener;
import com.tjyw.atom.network.presenter.listener.OnApiPostErrorListener;
import com.tjyw.atom.network.presenter.listener.OnApiPostNamingListener;
import com.tjyw.atom.network.result.RIdentifyResult;
import com.tjyw.atom.network.result.RNameDefinition;
import com.tjyw.atom.network.utils.ArrayUtil;
import com.tjyw.atom.network.utils.JsonUtil;
import com.tjyw.atom.pub.fragment.AtomPubBaseFragment;
import com.tjyw.atom.pub.inject.From;
import com.tjyw.atom.pub.item.AtomPubFastAdapterAbstractItem;
import com.tjyw.qmjm.ClientQmjmApplication;
import com.tjyw.qmjm.R;
import com.tjyw.qmjm.dialog.NamingPayWindows;
import com.tjyw.qmjm.item.ExplainSanCaiItem;
import com.tjyw.qmjm.item.NameDefinitionFooterItem;
import com.tjyw.qmjm.item.NamingWordItem;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import nucleus.factory.RequiresPresenter;
import rx.Observable;
import rx.functions.Action1;
import timber.log.Timber;

/**
 * Created by stephen on 17-8-11.
 */
@RequiresPresenter(NamingPresenter.class)
public class NameMasterRecommendFragment extends BaseFragment<NamingPresenter<NameMasterRecommendFragment>> implements
        OnApiPostErrorListener,
        OnApiPostNamingListener,
        OnApiFavoritePostListener.PostAddListener, OnApiFavoritePostListener.PostRemoveListener,
        OnApiPayPostListener.PostPayServiceListener {

    public static NameMasterRecommendFragment newInstance(RNameDefinition definition) {
        Bundle bundle = new Bundle();
        bundle.putString(IApiField.D.data, JsonUtil.getInstance().toJsonString(definition.list));
        bundle.putSerializable(IApiField.P.param, definition.param);

        NameMasterRecommendFragment fragment = new NameMasterRecommendFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @From(R.id.nameRecommendContainer)
    protected RecyclerView nameRecommendContainer;

    protected FastItemAdapter<AtomPubFastAdapterAbstractItem> nameDefinitionAdapter;

    protected FooterAdapter<NameDefinitionFooterItem> nameDefinitionFooterAdapter;

    protected NamingPayWindows namingPayWindows;

    protected ListRequestParam listRequestParam;

    protected PayService payService;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.atom_name_master_recommend, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        listRequestParam = (ListRequestParam) pGetSerializableExtra(IApiField.P.param);
        if (null == listRequestParam) {
            return ;
        }

        List<NameDefinition> list = JsonUtil.getInstance().parseJavaArray(pGetStringExtra(IApiField.D.data, ISection.JSON.ARRAY), NameDefinition.class);
        if (ArrayUtil.isEmpty(list)) {
            return ;
        }

        nameDefinitionFooterAdapter = new FooterAdapter<NameDefinitionFooterItem>();
        nameDefinitionAdapter = new FastItemAdapter<AtomPubFastAdapterAbstractItem>();

        nameRecommendContainer.setLayoutManager(new LinearLayoutManager(ClientQmjmApplication.getContext(), LinearLayoutManager.VERTICAL, false));
        nameRecommendContainer.setAdapter(nameDefinitionFooterAdapter.wrap(nameDefinitionAdapter));
        nameRecommendContainer.addItemDecoration(
                new HorizontalDividerItemDecoration.Builder(ClientQmjmApplication.getContext())
                        .color(R.color.atom_pub_resColorDivider)
                        .sizeResId(R.dimen.atom_pubResDimenRecyclerViewDividerSize)
                        .marginResId(R.dimen.atom_pubResDimenRecyclerViewDivider8dp)
                        .showLastDivider()
                        .build());

        nameRecommendContainer.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                switch (newState) {
                    case RecyclerView.SCROLL_STATE_IDLE:
                        if (! recyclerView.canScrollVertically(1) && ! hasOrderNo()) {
                            if (null == payService) {
                                maskerShowProgressView(true);
                                getPresenter().postPayService(listRequestParam.surname, listRequestParam.day);
                            } else if (null == namingPayWindows) {
                                namingPayWindows = NamingPayWindows.newInstance(getFragmentManager(), listRequestParam, payService);
                            } else if (!namingPayWindows.isVisible()) {
                                namingPayWindows.show(getFragmentManager(), NamingPayWindows.class.getName());
                            }
                        }
                }
            }
        });

        RNameDefinition definition = new RNameDefinition();
        definition.list = list;
        postOnNamingSuccess(definition);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case ICode.SECTION.PAY:
                switch (resultCode) {
                    case ICode.PAY.ALIPAY_SUCCESS:
                    case ICode.PAY.WX_SUCCESS:
                        if (null != data) {
                            listRequestParam.orderNo = data.getStringExtra(IApiField.O.orderNo);
                            if (hasOrderNo()) {
                                maskerShowProgressView(false);
                                Observable.timer(2, TimeUnit.SECONDS)
                                        .compose(RxSchedulersHelper.<Long>io_main())
                                        .subscribe(new Action1<Long>() {
                                            @Override
                                            public void call(Long aLong) {
                                                nameDefinitionAdapter.clear();
                                                requestListData();
                                            }
                                        }, new Action1<Throwable>() {
                                            @Override
                                            public void call(Throwable throwable) {
                                                nameDefinitionAdapter.clear();
                                                requestListData();
                                            }
                                        });
                            }
                        }
                }
        }
    }

    @Override
    public void postOnExplainError(int postId, Throwable throwable) {

    }

    @Override
    public void postOnNamingSuccess(RNameDefinition result) {
        maskerHideProgressView();
//        if (null == result) {
//            maskerShowMaskerLayout(getString(R.string.atom_pub_resStringNetworkBroken), 0);
//            return ;
//        } else if (result.size() == 0) {
//            maskerShowMaskerLayout(getString(R.string.atom_pub_resStringNamingListLack), R.string.atom_pub_resStringRetry);
//            return ;
//        } else if (hasOrderNo() && null != result.param) {
//            listRequestParam = result.param;
//        }

        List<AtomPubFastAdapterAbstractItem> itemList = new ArrayList<AtomPubFastAdapterAbstractItem>();
        int size = result.size();
        for (int i = 0; i < size; i ++) {
            itemList.add(new NamingWordItem(result.get(i)));
        }

        nameDefinitionAdapter.add(itemList);
        if (hasOrderNo()) {
            nameDefinitionFooterAdapter.add(new NameDefinitionFooterItem(null));
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

    @Override
    public void postOnPayServiceSuccess(PayService payService) {
        maskerHideProgressView();
        this.payService = payService;
        if (null == namingPayWindows) {
            namingPayWindows = NamingPayWindows.newInstance(getFragmentManager(), listRequestParam, payService);
        } else {
            namingPayWindows.show(getFragmentManager(), NamingPayWindows.class.getName());
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

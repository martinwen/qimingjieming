package com.tjyw.qmjm.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mikepenz.fastadapter.FastAdapter;
import com.mikepenz.fastadapter.IAdapter;
import com.mikepenz.fastadapter.adapters.FooterAdapter;
import com.mikepenz.fastadapter.commons.adapters.FastItemAdapter;
import com.mikepenz.fastadapter.listeners.ClickEventHook;
import com.tjyw.atom.network.IllegalRequestException;
import com.tjyw.atom.network.RxSchedulersHelper;
import com.tjyw.atom.network.conf.IApiField;
import com.tjyw.atom.network.conf.ICode;
import com.tjyw.atom.network.conf.ISection;
import com.tjyw.atom.network.model.NameDefinition;
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
import com.tjyw.atom.network.utils.ArrayUtil;
import com.tjyw.atom.network.utils.JsonUtil;
import com.tjyw.atom.pub.inject.From;
import com.tjyw.atom.pub.item.AtomPubFastAdapterAbstractItem;
import com.tjyw.qmjm.ClientQmjmApplication;
import com.tjyw.qmjm.R;
import com.tjyw.qmjm.factory.IClientActivityLaunchFactory;
import com.tjyw.qmjm.item.NameDefinitionFooterItem;
import com.tjyw.qmjm.item.NamingWordItem;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import nucleus.factory.RequiresPresenter;
import rx.Observable;
import rx.functions.Action1;

/**
 * Created by stephen on 17-8-11.
 */
@RequiresPresenter(NamingPresenter.class)
public class NameMasterRecommendFragment extends BaseFragment<NamingPresenter<NameMasterRecommendFragment>> implements
        OnApiPostErrorListener,
        OnApiPostNamingListener,
        OnApiFavoritePostListener.PostAddListener, OnApiFavoritePostListener.PostRemoveListener,
        OnApiPayPostListener.PostPayListVipListener {

    public static NameMasterRecommendFragment newInstance(RNameDefinition definition) {
        Bundle bundle = new Bundle();
        bundle.putString(IApiField.D.data, JsonUtil.getInstance().toJsonString(definition.list));
        bundle.putSerializable(IApiField.P.param, definition.param.clone());

        NameMasterRecommendFragment fragment = new NameMasterRecommendFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @From(R.id.nameListContainer)
    protected RecyclerView nameListContainer;

    protected FastItemAdapter<AtomPubFastAdapterAbstractItem> nameDefinitionAdapter;

    protected FooterAdapter<NameDefinitionFooterItem> nameDefinitionFooterAdapter;

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

        initNameListContainer();

        List<NameDefinition> list = JsonUtil.getInstance().parseJavaArray(pGetStringExtra(IApiField.D.data, ISection.JSON.ARRAY), NameDefinition.class);
        if (! ArrayUtil.isEmpty(list)) {
            RNameDefinition definition = new RNameDefinition();
            definition.list = list;
            postOnNamingSuccess(definition);
        }
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
                                Observable.timer(3, TimeUnit.SECONDS)
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
        if (null == result) {
            maskerShowMaskerLayout(getString(R.string.atom_pub_resStringNetworkBroken), 0);
            return ;
        } else if (result.size() == 0) {
            maskerShowMaskerLayout(getString(R.string.atom_pub_resStringNamingListLack), R.string.atom_pub_resStringRetry);
            return ;
        } else if (hasOrderNo() && null != result.param) {
            listRequestParam = result.param;
        }

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
    public void postOnPayListVipSuccess(int type, PayService payService) {
        maskerHideProgressView();
        this.payService = payService;
        Fragment fragment = getFragmentManager().findFragmentById(R.id.payServiceFragment);
        if (fragment instanceof PayServiceFragment) {
            PayServiceFragment payServiceFragment = (PayServiceFragment) fragment;
            payServiceFragment.setListRequestParam(listRequestParam);
            payServiceFragment.setPayService(payService);
            pShowFragment(R.anim.abc_fade_in, R.anim.abc_fade_out, payServiceFragment);
        }
    }

    protected void initNameListContainer() {
        nameDefinitionFooterAdapter = new FooterAdapter<NameDefinitionFooterItem>();
        nameDefinitionAdapter = new FastItemAdapter<AtomPubFastAdapterAbstractItem>();

        nameListContainer.setLayoutManager(new LinearLayoutManager(ClientQmjmApplication.getContext(), LinearLayoutManager.VERTICAL, false));
        nameListContainer.setAdapter(nameDefinitionFooterAdapter.wrap(nameDefinitionAdapter));
        nameListContainer.addItemDecoration(
                new HorizontalDividerItemDecoration.Builder(ClientQmjmApplication.getContext())
                        .color(R.color.atom_pub_resColorDivider)
                        .sizeResId(R.dimen.atom_pubResDimenRecyclerViewDividerSize)
                        .marginResId(R.dimen.atom_pubResDimenRecyclerViewDivider8dp)
                        .showLastDivider()
                        .build());

        nameListContainer.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                switch (newState) {
                    case RecyclerView.SCROLL_STATE_IDLE:
                        if (canShowPayInterceptWindow()) {
                            if (null == payService) {
                                maskerShowProgressView(true);
                                getPresenter().postPayListVip(
                                        1,
                                        listRequestParam.surname,
                                        listRequestParam.day
                                );
                            } else {
                                postOnPayListVipSuccess(1, payService);
                            }
                        }
                }
            }
        });

        nameDefinitionAdapter.withItemEvent(new ClickEventHook<AtomPubFastAdapterAbstractItem>() {
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
            public void onClick(View v, int position, FastAdapter<AtomPubFastAdapterAbstractItem> fastAdapter, AtomPubFastAdapterAbstractItem item) {
                maskerShowProgressView(true);
                if (item instanceof NamingWordItem) {
                    NamingWordItem namingWordItem = (NamingWordItem) item;
                    if (namingWordItem.src.favorite && namingWordItem.src.id > 0) {
                        getPresenter().postFavoriteRemove(namingWordItem.src.id, item);
                    } else if (hasOrderNo()) {
                        getPresenter().postFavoriteAdd(
                                namingWordItem.src.surname,
                                namingWordItem.src.getGivenName(),
                                namingWordItem.src.day,
                                namingWordItem.src.gender,
                                item
                        );
                    } else if (null != listRequestParam) {
                        getPresenter().postFavoriteAdd(
                                listRequestParam.surname,
                                namingWordItem.src.getGivenName(),
                                listRequestParam.day,
                                listRequestParam.gender,
                                item
                        );
                    }
                }
            }
        }).withOnClickListener(new FastAdapter.OnClickListener<AtomPubFastAdapterAbstractItem>() {
            @Override
            public boolean onClick(View v, IAdapter<AtomPubFastAdapterAbstractItem> adapter, AtomPubFastAdapterAbstractItem item, int position) {
                if (item instanceof NamingWordItem) {
                    if (hasOrderNo()) {
                        ListRequestParam param = new ListRequestParam(((NamingWordItem) item).src);
                        IClientActivityLaunchFactory.launchExplainMasterActivity(NameMasterRecommendFragment.this, param, 100);
                    } else {
                        ListRequestParam param = listRequestParam.clone();
                        param.name = ((NamingWordItem) item).src.getGivenName();
                        IClientActivityLaunchFactory.launchExplainMasterActivity(NameMasterRecommendFragment.this, param, 100);
                    }
                }

                return true;
            }
        });
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

    protected boolean canShowPayInterceptWindow() {
        return ! nameListContainer.canScrollVertically(1) && ! hasOrderNo();
    }

    protected boolean hasOrderNo() {
        return null != listRequestParam && ! TextUtils.isEmpty(listRequestParam.orderNo);
    }
}

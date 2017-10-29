package com.tjyw.bbqm.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mikepenz.fastadapter.FastAdapter;
import com.mikepenz.fastadapter.IAdapter;
import com.mikepenz.fastadapter.commons.adapters.FastItemAdapter;
import com.mikepenz.fastadapter.listeners.ClickEventHook;
import com.tjyw.atom.network.IllegalRequestException;
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
import atom.pub.inject.From;
import com.tjyw.bbqm.ClientQmjmApplication;
import com.tjyw.bbqm.R;
import com.tjyw.bbqm.activity.BaseActivity;
import com.tjyw.bbqm.factory.IClientActivityLaunchFactory;
import com.tjyw.bbqm.item.NamingWordItem;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import nucleus.factory.RequiresPresenter;

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

    protected FastItemAdapter<NamingWordItem> nameDefinitionAdapter;

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
        if (null != listRequestParam) {
            initNameListContainer();

            List<NameDefinition> list = JsonUtil.getInstance().parseJavaArray(pGetStringExtra(IApiField.D.data, ISection.JSON.ARRAY), NameDefinition.class);
            if (! ArrayUtil.isEmpty(list)) {
                RNameDefinition definition = new RNameDefinition();
                definition.list = list;
                postOnNamingSuccess(definition);
            }
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
                            IClientActivityLaunchFactory.launchNamingListActivity((BaseActivity) getActivity(), listRequestParam);
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
        }

        List<NamingWordItem> itemList = new ArrayList<NamingWordItem>();
        int size = result.size();
        for (int i = 0; i < size; i ++) {
            itemList.add(new NamingWordItem(result.get(i)));
        }

        nameDefinitionAdapter.add(itemList);
    }

    @Override
    public void postOnFavoriteAddSuccess(RIdentifyResult result, Object item) {
        showToast(R.string.atom_pub_resStringFavoriteHintAdd);
        NamingWordItem namingWordItem = (NamingWordItem) item;
        namingWordItem.src.id = result.id;
        namingWordItem.src.favorite = true;
        nameDefinitionAdapter.notifyAdapterDataSetChanged();
    }

    @Override
    public void postOnFavoriteRemoveSuccess(Object item) {
        showToast(R.string.atom_pub_resStringFavoriteHintRemove);
        NamingWordItem namingWordItem = (NamingWordItem) item;
        namingWordItem.src.favorite = false;
        nameDefinitionAdapter.notifyAdapterDataSetChanged();
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
            payServiceFragment.setOnPayServiceClickListener(new PayServiceFragment.OnPayServiceClickListener() {
                @Override
                public void payOnServicePayClick(PayServiceFragment fragment, ListRequestParam listRequestParam, PayService payService) {
                    if (null != listRequestParam && null != payService) {
                        IClientActivityLaunchFactory.launchPayOrderActivity(NameMasterRecommendFragment.this, listRequestParam, payService);
                    }
                }
            });
            pShowFragment(R.anim.abc_fade_in, R.anim.abc_fade_out, payServiceFragment);
        }
    }

    protected void initNameListContainer() {
        nameListContainer.setLayoutManager(new LinearLayoutManager(ClientQmjmApplication.getContext(), LinearLayoutManager.VERTICAL, false));
        nameListContainer.setAdapter(nameDefinitionAdapter = new FastItemAdapter<NamingWordItem>());
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
        });

        nameDefinitionAdapter.withItemEvent(new ClickEventHook<NamingWordItem>() {
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
        }).withOnClickListener(new FastAdapter.OnClickListener<NamingWordItem>() {
            @Override
            public boolean onClick(View v, IAdapter<NamingWordItem> adapter, NamingWordItem item, int position) {
                ListRequestParam param = listRequestParam.clone();
                param.name = item.src.getGivenName();
                IClientActivityLaunchFactory.launchExplainMasterActivity(NameMasterRecommendFragment.this, param, 100);
                return true;
            }
        });
    }
}

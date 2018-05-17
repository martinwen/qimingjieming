package com.tjyw.bbqmqd.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import com.tjyw.atom.network.param.ListRequestParam;
import com.tjyw.atom.network.presenter.IPost;
import com.tjyw.atom.network.presenter.NamingPresenter;
import com.tjyw.atom.network.presenter.listener.OnApiFavoritePostListener;
import com.tjyw.atom.network.presenter.listener.OnApiPostErrorListener;
import com.tjyw.atom.network.presenter.listener.OnApiPostNamingListener;
import com.tjyw.atom.network.result.RIdentifyResult;
import com.tjyw.atom.network.result.RNameDefinition;
import com.tjyw.bbqmqd.ClientQmjmApplication;
import com.tjyw.bbqmqd.R;
import com.tjyw.bbqmqd.activity.PayPackageActivity;
import com.tjyw.bbqmqd.factory.IClientActivityLaunchFactory;
import com.tjyw.bbqmqd.item.NamingWordItem;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import atom.pub.inject.From;
import nucleus.factory.RequiresPresenter;

@RequiresPresenter(NamingPresenter.class)
public class PayPackageFragment extends BaseFragment<NamingPresenter<NameMasterRecommendFragment>> implements
        OnApiPostErrorListener,
        OnApiPostNamingListener,
        OnApiFavoritePostListener.PostAddListener, OnApiFavoritePostListener.PostRemoveListener {

    public static PayPackageFragment newInstance(ListRequestParam listRequestParam, int listType) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(IApiField.P.param, listRequestParam);
        bundle.putInt(IApiField.L.listType, listType);

        PayPackageFragment fragment = new PayPackageFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @From(R.id.payPackageListContainer)
    protected RecyclerView payPackageListContainer;

    protected FastItemAdapter<NamingWordItem> nameDefinitionAdapter;

    protected ListRequestParam listRequestParam;

    protected int listType;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.atom_pay_package_fragment, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        payPackageListContainer.setLayoutManager(new LinearLayoutManager(ClientQmjmApplication.getContext(), LinearLayoutManager.VERTICAL, false));
        payPackageListContainer.setAdapter(nameDefinitionAdapter = new FastItemAdapter<NamingWordItem>());
        payPackageListContainer.addItemDecoration(
                new HorizontalDividerItemDecoration.Builder(ClientQmjmApplication.getContext())
                        .color(R.color.atom_pub_resColorDivider)
                        .sizeResId(R.dimen.atom_pubResDimenRecyclerViewDividerSize)
                        .marginResId(R.dimen.atom_pubResDimenRecyclerViewDivider8dp)
                        .showLastDivider()
                        .build());

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
                            item.src.surname,
                            item.src.getGivenName(),
                            item.src.day,
                            item.src.gender,
                            item
                    );
                }
            }
        }).withOnClickListener(new FastAdapter.OnClickListener<NamingWordItem>() {
            @Override
            public boolean onClick(View v, IAdapter<NamingWordItem> adapter, NamingWordItem item, int position) {
                ListRequestParam param = new ListRequestParam(item.src);
                IClientActivityLaunchFactory.launchExplainMasterActivity(PayPackageFragment.this, param, 100);
                return true;
            }
        });

        listRequestParam = (ListRequestParam) pGetSerializableExtra(IApiField.P.param);
        if (null != listRequestParam) {
            listType = pGetIntExtra(IApiField.L.listType, 0);
            maskerShowProgressView(false);
            getPresenter().postPayOrderNameList(listRequestParam.orderNo, listType == 0 ? null : listType);
        }
    }

    @Override
    public void maskerOnClick(View view, int clickLabelRes) {
        super.maskerOnClick(view, clickLabelRes);
        if (null != listRequestParam) {
            maskerShowProgressView(false);
            getPresenter().postPayOrderNameList(listRequestParam.orderNo, listType == 0 ? listType : null);
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
        if (getActivity() instanceof PayPackageActivity) {
            ((PayPackageActivity) getActivity()).setPayOrderRepeatPay(result.statusLabel);
        }
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
}

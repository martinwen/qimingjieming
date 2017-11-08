package com.tjyw.qmjm.fragment;

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
import com.tjyw.atom.network.conf.ISection;
import com.tjyw.atom.network.model.NameDefinition;
import com.tjyw.atom.network.param.ListRequestParam;
import com.tjyw.atom.network.presenter.IPost;
import com.tjyw.atom.network.presenter.NamingPresenter;
import com.tjyw.atom.network.presenter.listener.OnApiFavoritePostListener;
import com.tjyw.atom.network.presenter.listener.OnApiPostErrorListener;
import com.tjyw.atom.network.result.RIdentifyResult;
import com.tjyw.atom.network.utils.ArrayUtil;
import com.tjyw.atom.network.utils.JsonUtil;
import com.tjyw.qmjm.ClientQmjmApplication;
import com.tjyw.qmjm.R;
import com.tjyw.qmjm.factory.IClientActivityLaunchFactory;
import com.tjyw.qmjm.item.NamingWordItem;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import atom.pub.inject.From;
import nucleus.factory.RequiresPresenter;

@RequiresPresenter(NamingPresenter.class)
public class PayPackageFragment extends BaseFragment<NamingPresenter<NameMasterRecommendFragment>> implements
        OnApiPostErrorListener,
        OnApiFavoritePostListener.PostAddListener, OnApiFavoritePostListener.PostRemoveListener {

    public static PayPackageFragment newInstance(List<NameDefinition> list) {
        Bundle bundle = new Bundle();
        bundle.putString(IApiField.D.data, JsonUtil.getInstance().toJsonString(list));

        PayPackageFragment fragment = new PayPackageFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @From(R.id.payPackageListContainer)
    protected RecyclerView payPackageListContainer;

    protected FastItemAdapter<NamingWordItem> nameDefinitionAdapter;

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

        List<NameDefinition> list = JsonUtil.getInstance().parseJavaArray(pGetStringExtra(IApiField.D.data, ISection.JSON.ARRAY), NameDefinition.class);
        if (! ArrayUtil.isEmpty(list)) {
            List<NamingWordItem> itemList = new ArrayList<NamingWordItem>();
            int size = list.size();
            for (int i = 0; i < size; i ++) {
                itemList.add(new NamingWordItem(list.get(i)));
            }

            nameDefinitionAdapter.add(itemList);
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

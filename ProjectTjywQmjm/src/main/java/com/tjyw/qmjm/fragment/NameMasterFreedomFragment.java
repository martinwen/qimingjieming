package com.tjyw.qmjm.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mikepenz.fastadapter.FastAdapter;
import com.mikepenz.fastadapter.IAdapter;
import com.mikepenz.fastadapter.commons.adapters.FastItemAdapter;
import com.tjyw.atom.network.conf.IApiField;
import com.tjyw.atom.network.model.NameCharacter;
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
import com.tjyw.atom.pub.inject.From;
import com.tjyw.qmjm.ClientQmjmApplication;
import com.tjyw.qmjm.R;
import com.tjyw.qmjm.factory.IClientActivityLaunchFactory;
import com.tjyw.qmjm.holder.HeaderWordHolder;
import com.tjyw.qmjm.item.NameFreedomItem;

import java.util.ArrayList;
import java.util.List;

import nucleus.factory.RequiresPresenter;

/**
 * Created by stephen on 17-10-13.
 */
@RequiresPresenter(NamingPresenter.class)
public class NameMasterFreedomFragment extends BaseFragment<NamingPresenter<NameMasterFreedomFragment>> implements
        OnApiPostErrorListener,
        OnApiPostNamingListener,
        OnApiPayPostListener.PostPayListVipListener,
        OnApiFavoritePostListener.PostAddListener,
        OnApiFavoritePostListener.PostRemoveListener {

    public static NameMasterFreedomFragment newInstance(RNameDefinition definition) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(IApiField.P.param, definition.param.clone());

        NameMasterFreedomFragment fragment = new NameMasterFreedomFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @From(R.id.nameFreedomWordContainer)
    protected ViewGroup nameFreedomWordContainer;

    @From(R.id.nameWordContainer)
    protected ViewGroup nameWordContainer;

    @From(R.id.nameWordCollect)
    protected TextView nameWordCollect;

    @From(R.id.nameFreedomDjm)
    protected TextView nameFreedomDjm;

    @From(R.id.nameFreedomXjm)
    protected TextView nameFreedomXjm;

    @From(R.id.nameFreedomYbm)
    protected TextView nameFreedomYbm;

    @From(R.id.nameFreedomContainer)
    protected RecyclerView nameFreedomContainer;

    protected FastItemAdapter<NameFreedomItem> nameFreedomAdapter;

    protected NameDefinition nameDefinition;

    protected ListRequestParam listRequestParam;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.atom_name_master_freedom, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        listRequestParam = (ListRequestParam) pGetSerializableExtra(IApiField.P.param);
        if (null == listRequestParam) {
            return ;
        }

        nameFreedomDjm.setOnClickListener(this);
        nameFreedomXjm.setOnClickListener(this);

        nameFreedomContainer.setAdapter(nameFreedomAdapter = new FastItemAdapter<NameFreedomItem>());
        nameFreedomContainer.setLayoutManager(new LinearLayoutManager(ClientQmjmApplication.getContext()));

        nameFreedomAdapter.withOnClickListener(new FastAdapter.OnClickListener<NameFreedomItem>() {
            @Override
            public boolean onClick(View v, IAdapter<NameFreedomItem> adapter, NameFreedomItem item, int position) {
                resetName(nameDefinition = item.src);
                return true;
            }
        });

        getPresenter().postNameDefinitionDataNormal(
                listRequestParam.surname,
                listRequestParam.day,
                listRequestParam.gender,
                listRequestParam.nameNumber
        );
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.nameFreedomDjm:
                maskerShowProgressView(true);
                getPresenter().postPayListVip(
                        3,
                        listRequestParam.surname,
                        listRequestParam.day
                );
                break ;
            case R.id.nameFreedomXjm:
                maskerShowProgressView(true);
                getPresenter().postPayListVip(
                        4,
                        listRequestParam.surname,
                        listRequestParam.day
                );
                break ;
            case R.id.nameWordCollect:
                maskerShowProgressView(true);
                if (nameDefinition.favorite && nameDefinition.id > 0) {
                    getPresenter().postFavoriteRemove(nameDefinition.id, null);
                } else {
                    getPresenter().postFavoriteAdd(
                            listRequestParam.surname,
                            nameDefinition.getGivenName(),
                            listRequestParam.day,
                            listRequestParam.gender,
                            null
                    );
                }
                break ;
            case R.id.nameFreedomWordContainer:
                ListRequestParam param = listRequestParam.clone();
                param.name = nameDefinition.getGivenName();
                IClientActivityLaunchFactory.launchExplainMasterActivity(this, param);
                break ;
            default:
                super.onClick(v);
        }
    }

    @Override
    public void postOnNamingSuccess(RNameDefinition result) {
        int size = null == result ? 0 : result.size();
        if (size > 0) {
            List<NameFreedomItem> itemList = new ArrayList<NameFreedomItem>();
            for (int i = 0; i < size; i ++) {
                NameDefinition definition = result.get(i);
                if (null != definition) {
                    itemList.add(new NameFreedomItem(definition));
                    if (i == 0) {
                        resetName(this.nameDefinition = definition);
                    }
                }
            }

            nameFreedomAdapter.add(itemList);
        }
    }

    @Override
    public void postOnExplainError(int postId, Throwable throwable) {

    }

    @Override
    public void postOnPayListVipSuccess(int type, PayService payService) {
        maskerHideProgressView();
        Fragment fragment = getFragmentManager().findFragmentById(R.id.payServiceFragment);
        if (fragment instanceof PayServiceFragment) {
            PayServiceFragment payServiceFragment = (PayServiceFragment) fragment;
            payServiceFragment.setListRequestParam(listRequestParam);
            payServiceFragment.setPayService(payService);
            pShowFragment(R.anim.abc_fade_in, R.anim.abc_fade_out, payServiceFragment);
        }
    }

    @Override
    public void postOnFavoriteAddSuccess(RIdentifyResult result, Object item) {
        maskerHideProgressView();
        if (null != nameDefinition) {
            nameDefinition.id = result.id;
            nameDefinition.favorite = true;
            resetName(nameDefinition);
        }
    }

    @Override
    public void postOnFavoriteRemoveSuccess(Object item) {
        maskerHideProgressView();
        if (null != nameDefinition) {
            nameDefinition.id = 0;
            nameDefinition.favorite = false;
            resetName(nameDefinition);
        }
    }

    protected void resetName(NameDefinition nameDefinition) {
        nameWordContainer.removeAllViews();
        List<NameCharacter> wordsList = nameDefinition.wordsList;
        if (! ArrayUtil.isEmpty(wordsList)) {
            for (int i = 0; i < wordsList.size(); i ++) {
                NameCharacter character = wordsList.get(i);
                if (null != character) {
                    nameWordContainer.addView(
                            HeaderWordHolder.newInstance(ClientQmjmApplication.getContext(), character), nameWordContainer.getChildCount()
                    );
                }
            }
        }

        nameFreedomWordContainer.setOnClickListener(this);
        nameWordCollect.setOnClickListener(this);

        nameWordCollect.setSelected(nameDefinition.favorite);
        nameWordCollect.setText(nameDefinition.favorite ? R.string.atom_pub_resStringNamingFavorited :  R.string.atom_pub_resStringNamingFavorite);
    }
}

package com.tjyw.qmjm.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.tjyw.atom.network.conf.ISection;
import com.tjyw.atom.network.model.NameCharacter;
import com.tjyw.atom.network.model.NameDefinition;
import com.tjyw.atom.network.param.ListRequestParam;
import com.tjyw.atom.network.presenter.NamingPresenter;
import com.tjyw.atom.network.result.RNameDefinition;
import com.tjyw.atom.network.utils.ArrayUtil;
import com.tjyw.atom.network.utils.JsonUtil;
import com.tjyw.atom.pub.inject.From;
import com.tjyw.qmjm.ClientQmjmApplication;
import com.tjyw.qmjm.R;
import com.tjyw.qmjm.factory.IClientActivityLaunchFactory;
import com.tjyw.qmjm.holder.HeaderWordHolder;
import com.tjyw.qmjm.item.NameFreedomItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by stephen on 17-10-13.
 */
public class NameMasterFreedomFragment extends BaseFragment<NamingPresenter<NameMasterFreedomFragment>> {

    public static NameMasterFreedomFragment newInstance(RNameDefinition definition) {
        Bundle bundle = new Bundle();
        bundle.putString(IApiField.D.data, JsonUtil.getInstance().toJsonString(definition.list));
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
        nameFreedomYbm.setOnClickListener(this);

        nameFreedomContainer.setAdapter(nameFreedomAdapter = new FastItemAdapter<NameFreedomItem>());
        nameFreedomContainer.setLayoutManager(new LinearLayoutManager(ClientQmjmApplication.getContext()));

        nameFreedomAdapter.withOnClickListener(new FastAdapter.OnClickListener<NameFreedomItem>() {
            @Override
            public boolean onClick(View v, IAdapter<NameFreedomItem> adapter, NameFreedomItem item, int position) {
                resetName(nameDefinition = item.src);
                return true;
            }
        });

        List<NameDefinition> list = JsonUtil.getInstance().parseJavaArray(pGetStringExtra(IApiField.D.data, ISection.JSON.ARRAY), NameDefinition.class);
        if (! ArrayUtil.isEmpty(list)) {
            RNameDefinition definition = new RNameDefinition();
            definition.list = list;
            postOnNamingSuccess(definition);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.nameFreedomDjm:

                break ;
            case R.id.nameFreedomXjm:

                break ;
            case R.id.nameFreedomYbm:

                break ;
            case R.id.nameWordCollect:

                break ;
            case R.id.nameFreedomWordContainer:
                ListRequestParam param = new ListRequestParam(nameDefinition);
                param.day = listRequestParam.day;
                IClientActivityLaunchFactory.launchExplainMasterActivity(this, param);
                break ;
            default:
                super.onClick(v);
        }
    }

    public void postOnNamingSuccess(RNameDefinition result) {
        maskerHideProgressView();
        if (null == result || result.size() == 0) {
            return ;
        }

        List<NameFreedomItem> itemList = new ArrayList<NameFreedomItem>();
        int size = result.size();
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

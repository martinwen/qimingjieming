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
import com.mikepenz.fastadapter.commons.adapters.FastItemAdapter;
import com.mikepenz.fastadapter.listeners.ClickEventHook;
import com.tjyw.atom.network.conf.IApiField;
import com.tjyw.atom.network.model.NameData;
import atom.pub.fragment.AtomPubBaseFragment;
import atom.pub.inject.From;
import com.tjyw.qmjm.ClientQmjmApplication;
import com.tjyw.qmjm.R;
import com.tjyw.qmjm.activity.NameMasterActivity;
import com.tjyw.qmjm.item.NameMasterAnalyzeItem;

/**
 * Created by stephen on 17-8-11.
 */
public class NameMasterAnalyzeFragment extends AtomPubBaseFragment {

    public static NameMasterAnalyzeFragment newInstance(NameData data) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(IApiField.D.data, data);

        NameMasterAnalyzeFragment fragment = new NameMasterAnalyzeFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @From(R.id.nameAnalyzeContainer)
    protected RecyclerView nameAnalyzeContainer;

    protected FastItemAdapter<NameMasterAnalyzeItem> nameAnalyzeAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.atom_name_master_analyze, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        NameData data = (NameData) pGetSerializableExtra(IApiField.D.data);
        if (null != data) {
            nameAnalyzeAdapter = new FastItemAdapter<NameMasterAnalyzeItem>();
            nameAnalyzeAdapter.add(new NameMasterAnalyzeItem(data));
            nameAnalyzeAdapter.withItemEvent(new ClickEventHook<NameMasterAnalyzeItem>() {
                @Nullable
                @Override
                public View onBind(@NonNull RecyclerView.ViewHolder viewHolder) {
                    return viewHolder.itemView.findViewById(R.id.nameMakeAGoodName);
                }

                @Override
                public void onClick(View v, int position, FastAdapter<NameMasterAnalyzeItem> fastAdapter, NameMasterAnalyzeItem item) {
                    if (getActivity() instanceof NameMasterActivity) {
                        ((NameMasterActivity) getActivity()).showFreedomFragment();
                    }
                }
            });

            nameAnalyzeContainer.setLayoutManager(new LinearLayoutManager(ClientQmjmApplication.getContext()));
            nameAnalyzeContainer.setAdapter(nameAnalyzeAdapter);
        }
    }
}

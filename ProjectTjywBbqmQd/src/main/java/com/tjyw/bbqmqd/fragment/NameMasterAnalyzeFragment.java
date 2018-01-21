package com.tjyw.bbqmqd.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tjyw.atom.network.conf.IApiField;
import com.tjyw.atom.network.model.NameData;
import com.tjyw.bbqmqd.R;
import com.tjyw.bbqmqd.activity.NameMasterActivity;
import com.tjyw.bbqmqd.adapter.NameMasterAdapter;
import com.tjyw.bbqmqd.holder.BaZiSheetHolder;
import com.tjyw.bbqmqd.holder.NameBaseInfoHolder;

import atom.pub.fragment.AtomPubBaseFragment;
import atom.pub.inject.From;

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

    @From(R.id.bodyAnalyzeContent)
    protected TextView bodyAnalyzeContent;

    @From(R.id.bodyAnalyzeTip)
    protected TextView bodyAnalyzeTip;

    @From(R.id.nameMakeAGoodName)
    protected TextView nameMakeAGoodName;

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
            BaZiSheetHolder baZiSheetHolder = new BaZiSheetHolder(getView());
            baZiSheetHolder.sheet(data);

            NameBaseInfoHolder nameBaseInfoHolder = new NameBaseInfoHolder(getView());
            nameBaseInfoHolder.baseInfo(data);

            bodyAnalyzeContent.setText(data.fenxi);
            bodyAnalyzeTip.setText(data.tixing);

            nameMakeAGoodName.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.nameMakeAGoodName:
                if (getActivity() instanceof NameMasterActivity) {
                    ((NameMasterActivity) getActivity()).showContainerFragment(NameMasterAdapter.POSITION.FREEDOM, false);
                }
                break ;
            default:
                super.onClick(v);
        }
    }
}

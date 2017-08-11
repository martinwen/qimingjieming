package com.tjyw.qmjm.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tjyw.atom.pub.fragment.AtomPubBaseFragment;
import com.tjyw.qmjm.R;

/**
 * Created by stephen on 17-8-11.
 */
public class ExplainMasterOverviewFragment extends AtomPubBaseFragment {

    public static ExplainMasterOverviewFragment newInstance() {
        Bundle bundle = new Bundle();

        ExplainMasterOverviewFragment fragment = new ExplainMasterOverviewFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.atom_explain_overview, null);
    }

}

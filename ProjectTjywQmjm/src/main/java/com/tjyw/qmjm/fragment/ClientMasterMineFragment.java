package com.tjyw.qmjm.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tjyw.atom.pub.fragment.AtomPubBaseFragment;
import com.tjyw.qmjm.R;

/**
 * Created by stephen on 07/08/2017.
 */
public class ClientMasterMineFragment extends AtomPubBaseFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.atom_master_mine, null);
    }
}

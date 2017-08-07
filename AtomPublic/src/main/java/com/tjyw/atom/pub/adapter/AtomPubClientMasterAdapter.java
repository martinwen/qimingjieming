package com.tjyw.atom.pub.adapter;

import android.support.v4.util.Pair;

import com.tjyw.atom.pub.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by stephen on 07/08/2017.
 */
public class AtomPubClientMasterAdapter {

    public static List<Pair<Integer, Integer>> MASTER_TAB_RESOURCE = new ArrayList<Pair<Integer, Integer>>();
    static {
        MASTER_TAB_RESOURCE.add(
                new Pair<Integer, Integer>(
                        R.string.atom_pub_resStringMasterTabNaming,
                        R.drawable.atom_pub_selector_matser_tab_naming
                )
        );

        MASTER_TAB_RESOURCE.add(
                new Pair<Integer, Integer>(
                        R.string.atom_pub_resStringMasterTabExplain,
                        R.drawable.atom_pub_selector_matser_tab_explain
                )
        );

        MASTER_TAB_RESOURCE.add(
                new Pair<Integer, Integer>(
                        R.string.atom_pub_resStringMasterTabMine,
                        R.drawable.atom_pub_selector_matser_tab_mine
                )
        );
    }

    public static Pair<Integer, Integer> getMasterTabResource(int position) {
        return AtomPubClientMasterAdapter.MASTER_TAB_RESOURCE.get(position);
    }
}

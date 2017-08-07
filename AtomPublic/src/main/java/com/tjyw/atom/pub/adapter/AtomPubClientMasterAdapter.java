package com.tjyw.atom.pub.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.util.Pair;

import com.aspsine.fragmentnavigator.FragmentNavigatorAdapter;
import com.tjyw.atom.pub.R;
import com.tjyw.atom.pub.activity.AtomPubBaseActivity;
import com.tjyw.atom.pub.fragment.AtomPubMasterExplainFragment;
import com.tjyw.atom.pub.fragment.AtomPubMasterMineFragment;
import com.tjyw.atom.pub.fragment.AtomPubMasterNamingFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by stephen on 07/08/2017.
 */
public class AtomPubClientMasterAdapter implements FragmentNavigatorAdapter {

    public static AtomPubClientMasterAdapter newInstance(AtomPubBaseActivity context) {
        return new AtomPubClientMasterAdapter(context);
    }

    public interface POSITION {

        int NAMING = 0;

        int EXPLAIN = 1;

        int MINE = 2;

        int ALL = 3;
    }

    public static List<Pair<Integer, Integer>> MASTER_TAB_RESOURCE = new ArrayList<Pair<Integer, Integer>>();
    static {
        MASTER_TAB_RESOURCE.add(POSITION.NAMING,
                new Pair<Integer, Integer>(
                        R.string.atom_pub_resStringMasterTabNaming,
                        R.drawable.atom_pub_selector_master_tab_naming
                )
        );

        MASTER_TAB_RESOURCE.add(POSITION.EXPLAIN,
                new Pair<Integer, Integer>(
                        R.string.atom_pub_resStringMasterTabExplain,
                        R.drawable.atom_pub_selector_master_tab_explain
                )
        );

        MASTER_TAB_RESOURCE.add(POSITION.MINE,
                new Pair<Integer, Integer>(
                        R.string.atom_pub_resStringMasterTabMine,
                        R.drawable.atom_pub_selector_master_tab_mine
                )
        );
    }

    public static Pair<Integer, Integer> getMasterTabResource(int position) {
        return AtomPubClientMasterAdapter.MASTER_TAB_RESOURCE.get(position);
    }

    protected AtomPubBaseActivity context;

    public AtomPubClientMasterAdapter(AtomPubBaseActivity context) {
        this.context = context;
    }

    @Override
    public Fragment onCreateFragment(int position) {
        FragmentManager fragmentManager = context.getSupportFragmentManager();
        if (null != fragmentManager) {
            switch (position) {
                case POSITION.NAMING:
                    return fragmentManager.findFragmentById(R.id.masterNamingFragment);
                case POSITION.EXPLAIN:
                    return fragmentManager.findFragmentById(R.id.masterExplainFragment);
                case POSITION.MINE:
                    return fragmentManager.findFragmentById(R.id.masterMineFragment);
                default:
                    return null;
            }
        } else {
            return null;
        }
    }

    @Override
    public String getTag(int position) {
        switch (position) {
            case POSITION.NAMING:
                return AtomPubMasterNamingFragment.class.getName();
            case POSITION.EXPLAIN:
                return AtomPubMasterExplainFragment.class.getName();
            case POSITION.MINE:
                return AtomPubMasterMineFragment.class.getName();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return POSITION.ALL;
    }
}

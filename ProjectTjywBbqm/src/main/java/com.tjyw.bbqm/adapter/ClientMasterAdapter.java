package com.tjyw.bbqm.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.util.Pair;

import com.aspsine.fragmentnavigator.FragmentNavigatorAdapter;
import com.tjyw.bbqm.R;
import com.tjyw.bbqm.fragment.ClientMasterCeSuanFragment;
import com.tjyw.bbqm.fragment.ClientMasterExplainFragment;
import com.tjyw.bbqm.fragment.ClientMasterMineFragment;
import com.tjyw.bbqm.fragment.ClientMasterNamingFragment;

import java.util.ArrayList;
import java.util.List;

import atom.pub.activity.AtomPubBaseActivity;

/**
 * Created by stephen on 07/08/2017.
 */
public class ClientMasterAdapter implements FragmentNavigatorAdapter {

    public static ClientMasterAdapter newInstance(AtomPubBaseActivity context) {
        return new ClientMasterAdapter(context);
    }

    public interface POSITION {

        int NAMING = 0;

        int EXPLAIN = 1;

        int CE_SUAN = 2;

        int MINE = 3;

        int ALL = 4;
    }

    public static List<Pair<Integer, Integer>> MASTER_TAB_RESOURCE = new ArrayList<Pair<Integer, Integer>>();
    static {
        MASTER_TAB_RESOURCE.add(POSITION.NAMING,
                new Pair<Integer, Integer>(
                        R.string.atom_pub_resStringMasterTabNaming,
                        R.drawable.atom_selector_master_tab_naming
                )
        );

        MASTER_TAB_RESOURCE.add(POSITION.EXPLAIN,
                new Pair<Integer, Integer>(
                        R.string.atom_pub_resStringMasterTabExplain,
                        R.drawable.atom_selector_master_tab_explain
                )
        );

        MASTER_TAB_RESOURCE.add(POSITION.CE_SUAN,
                new Pair<Integer, Integer>(
                        R.string.atom_pub_resStringMasterTabCeSuan,
                        R.drawable.atom_selector_master_tab_cesuan
                )
        );

        MASTER_TAB_RESOURCE.add(POSITION.MINE,
                new Pair<Integer, Integer>(
                        R.string.atom_pub_resStringMasterTabMine,
                        R.drawable.atom_selector_master_tab_mine
                )
        );
    }

    public static Pair<Integer, Integer> getMasterTabResource(int position) {
        return ClientMasterAdapter.MASTER_TAB_RESOURCE.get(position);
    }

    protected AtomPubBaseActivity context;

    public ClientMasterAdapter(AtomPubBaseActivity context) {
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
                case POSITION.CE_SUAN:
                    return fragmentManager.findFragmentById(R.id.masterCeSuanFragment);
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
                return ClientMasterNamingFragment.class.getName();
            case POSITION.EXPLAIN:
                return ClientMasterExplainFragment.class.getName();
            case POSITION.CE_SUAN:
                return ClientMasterCeSuanFragment.class.getName();
            case POSITION.MINE:
                return ClientMasterMineFragment.class.getName();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return POSITION.ALL;
    }
}

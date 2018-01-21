package com.tjyw.bbqmqd.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.tjyw.atom.network.model.Explain;
import com.tjyw.bbqmqd.ClientQmjmApplication;
import com.tjyw.bbqmqd.R;
import com.tjyw.bbqmqd.fragment.ExplainMasterDestinyFragment;
import com.tjyw.bbqmqd.fragment.ExplainMasterOverviewFragment;
import com.tjyw.bbqmqd.fragment.ExplainMasterSanCaiFragment;
import com.tjyw.bbqmqd.fragment.ExplainMasterZodiacFragment;

/**
 * Created by stephen on 17-8-11.
 */
public class ExplainMasterAdapter extends FragmentPagerAdapter {

    public interface POSITION {

        int OVERVIEW = 0;

        int ZODIAC = 1;

        int DESTINY = 2;

        int SANCAI = 3;

        int ALL = 4;
    }

    public static ExplainMasterAdapter newInstance(FragmentManager fragmentManager, Explain explain) {
        return new ExplainMasterAdapter(fragmentManager)
                    .setOverviewFragment(ExplainMasterOverviewFragment.newInstance(explain))
                    .setZodiacFragment(ExplainMasterZodiacFragment.newInstance(explain))
                    .setDestinyFragment(ExplainMasterDestinyFragment.newInstance(explain))
                    .setSanCaiFragment(ExplainMasterSanCaiFragment.newInstance(explain));
    }

    protected ExplainMasterOverviewFragment overviewFragment;

    protected ExplainMasterZodiacFragment zodiacFragment;

    protected ExplainMasterDestinyFragment destinyFragment;

    protected ExplainMasterSanCaiFragment sanCaiFragment;

    public ExplainMasterAdapter(FragmentManager fm) {
        super(fm);
    }

    public ExplainMasterAdapter setOverviewFragment(ExplainMasterOverviewFragment overviewFragment) {
        this.overviewFragment = overviewFragment;
        return this;
    }

    public ExplainMasterAdapter setZodiacFragment(ExplainMasterZodiacFragment zodiacFragment) {
        this.zodiacFragment = zodiacFragment;
        return this;
    }

    public ExplainMasterAdapter setDestinyFragment(ExplainMasterDestinyFragment destinyFragment) {
        this.destinyFragment = destinyFragment;
        return this;
    }

    public ExplainMasterAdapter setSanCaiFragment(ExplainMasterSanCaiFragment sanCaiFragment) {
        this.sanCaiFragment = sanCaiFragment;
        return this;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case POSITION.OVERVIEW:
                return overviewFragment;
            case POSITION.ZODIAC:
                return zodiacFragment;
            case POSITION.DESTINY:
                return destinyFragment;
            case POSITION.SANCAI:
            default:
                return sanCaiFragment;
        }
    }

    @Override
    public int getCount() {
        return POSITION.ALL;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case POSITION.OVERVIEW:
                return ClientQmjmApplication.pGetString(R.string.atom_pub_resStringExplainOverview);
            case POSITION.ZODIAC:
                return ClientQmjmApplication.pGetString(R.string.atom_pub_resStringExplainZodiac);
            case POSITION.DESTINY:
                return ClientQmjmApplication.pGetString(R.string.atom_pub_resStringExplainDestiny);
            case POSITION.SANCAI:
            default:
                return ClientQmjmApplication.pGetString(R.string.atom_pub_resStringExplainSanCaiWuGe);
        }
    }
}

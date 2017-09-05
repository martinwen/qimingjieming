package com.tjyw.qmjm.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.tjyw.atom.network.model.Explain;
import com.tjyw.qmjm.fragment.ExplainMasterDestinyFragment;
import com.tjyw.qmjm.fragment.ExplainMasterOverviewFragment;
import com.tjyw.qmjm.fragment.ExplainMasterZodiacFragment;

/**
 * Created by stephen on 17-8-11.
 */
public class ExplainMasterAdapter extends FragmentPagerAdapter {

    public interface POSITION {

        int OVERVIEW = 0;

        int ZODIAC = 1;

        int DESTINY = 2;

        int ALL = 3;
    }

    public static ExplainMasterAdapter newInstance(FragmentManager fragmentManager, Explain explain) {
        return new ExplainMasterAdapter(fragmentManager)
                    .setOverviewFragment(ExplainMasterOverviewFragment.newInstance(explain))
                    .setZodiacFragment(ExplainMasterZodiacFragment.newInstance(explain))
                    .setDestinyFragment(ExplainMasterDestinyFragment.newInstance(explain));
    }

    protected ExplainMasterOverviewFragment overviewFragment;

    protected ExplainMasterZodiacFragment zodiacFragment;

    protected ExplainMasterDestinyFragment destinyFragment;

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

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case POSITION.OVERVIEW:
                return overviewFragment;
            case POSITION.ZODIAC:
                return zodiacFragment;
            case POSITION.DESTINY:
            default:
                return destinyFragment;
        }
    }

    @Override
    public int getCount() {
        return POSITION.ALL;
    }

    public void showOverviewFragment(ViewPager container) {
        if (null != container) {
            container.setCurrentItem(0);
        }
    }

    public void showZodiacFragment(ViewPager container) {
        if (null != container) {
            container.setCurrentItem(1);
        }
    }

    public void showDestinyFragment(ViewPager container) {
        if (null != container) {
            container.setCurrentItem(2);
        }
    }
}

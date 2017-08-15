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
public abstract class ExplainMasterAdapter extends FragmentPagerAdapter {

    public static ExplainMasterAdapter newInstance(FragmentManager fragmentManager, Explain explain) {
        if (false) {
            return new ExplainDoubleAdapter(fragmentManager)
                    .setOverviewFragment(ExplainMasterOverviewFragment.newInstance(explain))
                    .setDestinyFragment(ExplainMasterDestinyFragment.newInstance(explain));
        } else {
            return new ExplainPerfectAdapter(fragmentManager)
                    .setOverviewFragment(ExplainMasterOverviewFragment.newInstance(explain))
                    .setZodiacFragment(ExplainMasterZodiacFragment.newInstance(explain))
                    .setDestinyFragment(ExplainMasterDestinyFragment.newInstance(explain));
        }
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
            case 0:
                return overviewFragment;
            case 1:
                return zodiacFragment;
            case 2:
            default:
                return destinyFragment;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    public abstract void showOverviewFragment(ViewPager container);

    public abstract void showZodiacFragment(ViewPager container);

    public abstract void showDestinyFragment(ViewPager container);

    static class ExplainPerfectAdapter extends ExplainMasterAdapter {

        public ExplainPerfectAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public void showOverviewFragment(ViewPager container) {
            if (null != container) {
                container.setCurrentItem(0);
            }
        }

        @Override
        public void showZodiacFragment(ViewPager container) {
            if (null != container) {
                container.setCurrentItem(1);
            }
        }

        @Override
        public void showDestinyFragment(ViewPager container) {
            if (null != container) {
                container.setCurrentItem(2);
            }
        }
    }

    static class ExplainDoubleAdapter extends ExplainPerfectAdapter {

        public ExplainDoubleAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public void showZodiacFragment(ViewPager container) {

        }

        @Override
        public void showDestinyFragment(ViewPager container) {
            if (null != container) {
                container.setCurrentItem(1);
            }
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return overviewFragment;
                case 1:
                default:
                    return destinyFragment;
            }
        }

        @Override
        public int getCount() {
            return 2;
        }
    }
}

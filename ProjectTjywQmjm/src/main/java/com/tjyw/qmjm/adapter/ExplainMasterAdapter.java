package com.tjyw.qmjm.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.tjyw.qmjm.fragment.ExplainMasterDestinyFragment;
import com.tjyw.qmjm.fragment.ExplainMasterOverviewFragment;
import com.tjyw.qmjm.fragment.ExplainMasterZodiacFragment;

/**
 * Created by stephen on 17-8-11.
 */
public class ExplainMasterAdapter extends FragmentPagerAdapter {

    protected ExplainMasterOverviewFragment explainMasterOverviewFragment;

    protected ExplainMasterZodiacFragment explainMasterZodiacFragment;

    protected ExplainMasterDestinyFragment explainMasterDestinyFragment;

    public ExplainMasterAdapter(FragmentManager fm) {
        super(fm);
    }

    public void setExplainMasterOverviewFragment(ExplainMasterOverviewFragment explainMasterOverviewFragment) {
        this.explainMasterOverviewFragment = explainMasterOverviewFragment;
    }

    public void setExplainMasterZodiacFragment(ExplainMasterZodiacFragment explainMasterZodiacFragment) {
        this.explainMasterZodiacFragment = explainMasterZodiacFragment;
    }

    public void setExplainMasterDestinyFragment(ExplainMasterDestinyFragment explainMasterDestinyFragment) {
        this.explainMasterDestinyFragment = explainMasterDestinyFragment;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return explainMasterOverviewFragment;
            case 1:
                return explainMasterZodiacFragment;
            case 2:
            default:
                return explainMasterDestinyFragment;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}

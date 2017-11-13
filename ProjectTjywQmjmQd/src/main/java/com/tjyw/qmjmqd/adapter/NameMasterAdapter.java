package com.tjyw.qmjmqd.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.tjyw.atom.network.result.RNameDefinition;
import com.tjyw.qmjmqd.fragment.NameMasterAnalyzeFragment;
import com.tjyw.qmjmqd.fragment.NameMasterFreedomFragment;
import com.tjyw.qmjmqd.fragment.NameMasterLuckyFragment;
import com.tjyw.qmjmqd.fragment.NameMasterRecommendFragment;

/**
 * Created by stephen on 17-8-11.
 */
public class NameMasterAdapter extends FragmentPagerAdapter {

    public interface POSITION {

        int ANALYZE = 0;

        int FREEDOM = 1;

        int RECOMMEND = 2;

        int LUCKY = 3;

        int ALL = 4;
    }

    public static NameMasterAdapter newInstance(FragmentManager fragmentManager, RNameDefinition definition) {
        return new NameMasterAdapter(fragmentManager)
                    .setAnalyzeFragment(NameMasterAnalyzeFragment.newInstance(definition.data))
                    .setMasterFreedomFragment(NameMasterFreedomFragment.newInstance(definition))
                    .setRecommendFragment(NameMasterRecommendFragment.newInstance(definition))
                    .setLuckyFragment(NameMasterLuckyFragment.newInstance(definition.param));
    }

    protected NameMasterAnalyzeFragment masterAnalyzeFragment;

    protected NameMasterFreedomFragment masterFreedomFragment;

    protected NameMasterRecommendFragment masterRecommendFragment;

    protected NameMasterLuckyFragment masterLuckyFragment;

    public NameMasterAdapter(FragmentManager fm) {
        super(fm);
    }

    public NameMasterAdapter setAnalyzeFragment(NameMasterAnalyzeFragment masterAnalyzeFragment) {
        this.masterAnalyzeFragment = masterAnalyzeFragment;
        return this;
    }

    public NameMasterAdapter setMasterFreedomFragment(NameMasterFreedomFragment masterFreedomFragment) {
        this.masterFreedomFragment = masterFreedomFragment;
        return this;
    }

    public NameMasterAdapter setRecommendFragment(NameMasterRecommendFragment masterRecommendFragment) {
        this.masterRecommendFragment = masterRecommendFragment;
        return this;
    }

    public NameMasterAdapter setLuckyFragment(NameMasterLuckyFragment masterLuckyFragment) {
        this.masterLuckyFragment = masterLuckyFragment;
        return this;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case POSITION.ANALYZE:
                return masterAnalyzeFragment;
            case POSITION.FREEDOM:
                return masterFreedomFragment;
            case POSITION.RECOMMEND:
                return masterRecommendFragment;
            case POSITION.LUCKY:
            default:
                return masterLuckyFragment;
        }
    }

    @Override
    public int getCount() {
        return POSITION.ALL;
    }
}

package com.tjyw.qmjm.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.tjyw.atom.network.model.Explain;
import com.tjyw.atom.network.result.RNameDefinition;
import com.tjyw.qmjm.fragment.NameMasterAnalyzeFragment;
import com.tjyw.qmjm.fragment.NameMasterLuckyFragment;
import com.tjyw.qmjm.fragment.NameMasterRecommendFragment;

/**
 * Created by stephen on 17-8-11.
 */
public class NameMasterAdapter extends FragmentPagerAdapter {

    public interface POSITION {

        int ANALYZE = 0;

        int RECOMMEND = 1;

        int LUCKY = 2;

        int ALL = 3;
    }

    public static NameMasterAdapter newInstance(FragmentManager fragmentManager, RNameDefinition definition) {
        return new NameMasterAdapter(fragmentManager)
                    .setAnalyzeFragment(NameMasterAnalyzeFragment.newInstance(definition.data))
                    .setRecommendFragment(NameMasterRecommendFragment.newInstance(definition))
                    .setLuckyFragment(NameMasterLuckyFragment.newInstance(null));
    }

    protected NameMasterAnalyzeFragment masterAnalyzeFragment;

    protected NameMasterRecommendFragment masterRecommendFragment;

    protected NameMasterLuckyFragment masterLuckyFragment;

    public NameMasterAdapter(FragmentManager fm) {
        super(fm);
    }

    public NameMasterAdapter setAnalyzeFragment(NameMasterAnalyzeFragment masterAnalyzeFragment) {
        this.masterAnalyzeFragment = masterAnalyzeFragment;
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

    public void showAnalyzeFragment(ViewPager container) {
        if (null != container) {
            container.setCurrentItem(POSITION.ANALYZE);
        }
    }

    public void showRecommendFragment(ViewPager container) {
        if (null != container) {
            container.setCurrentItem(POSITION.RECOMMEND);
        }
    }

    public void showLuckyFragment(ViewPager container) {
        if (null != container) {
            container.setCurrentItem(POSITION.LUCKY);
        }
    }
}

package com.tjyw.bbbzqm.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.tjyw.atom.network.param.ListRequestParam;
import com.tjyw.bbbzqm.fragment.PayPackageFragment;

/**
 * Created by stephen on 17-8-11.
 */
public class PayPackageAdapter extends FragmentPagerAdapter {

    public interface POSITION {

        int NORMAL = 0;

        int HIGH = 1;

        int LUCKY = 2;

        int ALL = 3;
    }

    public static PayPackageAdapter newInstance(FragmentManager fragmentManager, ListRequestParam listRequestParam) {
        return new PayPackageAdapter(fragmentManager)
                .setPayPackageNormalFragment(PayPackageFragment.newInstance(listRequestParam, 3)) // 大吉名
                .setPayPackageHighFragment(PayPackageFragment.newInstance(listRequestParam, 1)) // 高分吉名
                .setPayPackageLuckyFragment(PayPackageFragment.newInstance(listRequestParam, 2)); // 天降吉名
    }

    protected PayPackageFragment payPackageNormalFragment;

    protected PayPackageFragment payPackageHighFragment;

    protected PayPackageFragment payPackageLuckyFragment;

    public PayPackageAdapter(FragmentManager fm) {
        super(fm);
    }

    public PayPackageAdapter setPayPackageNormalFragment(PayPackageFragment payPackageNormalFragment) {
        this.payPackageNormalFragment = payPackageNormalFragment;
        return this;
    }

    public PayPackageAdapter setPayPackageHighFragment(PayPackageFragment payPackageHighFragment) {
        this.payPackageHighFragment = payPackageHighFragment;
        return this;
    }

    public PayPackageAdapter setPayPackageLuckyFragment(PayPackageFragment payPackageLuckyFragment) {
        this.payPackageLuckyFragment = payPackageLuckyFragment;
        return this;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case POSITION.NORMAL:
                return payPackageNormalFragment;
            case POSITION.HIGH:
                return payPackageHighFragment;
            case POSITION.LUCKY:
            default:
                return payPackageLuckyFragment;
        }
    }

    @Override
    public int getCount() {
        return POSITION.ALL;
    }

    public void showAnalyzeFragment(ViewPager container) {
        if (null != container) {
            container.setCurrentItem(POSITION.NORMAL);
        }
    }

    public void showFreedomFragment(ViewPager container) {
        if (null != container) {
            container.setCurrentItem(POSITION.HIGH);
        }
    }

    public void showLuckyFragment(ViewPager container) {
        if (null != container) {
            container.setCurrentItem(POSITION.LUCKY);
        }
    }
}

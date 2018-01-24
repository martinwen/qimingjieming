package com.tjyw.bbqmqd.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;

import com.tjyw.atom.network.model.Banner;

import java.util.List;

/**
 * Created by stephen on 24/01/2018.
 */
public class ClientBannerAdapter extends PagerAdapter {

    protected List<Banner> bannerLis;

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return false;
    }
}

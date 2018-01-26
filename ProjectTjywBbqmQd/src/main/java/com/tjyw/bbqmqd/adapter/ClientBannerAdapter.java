package com.tjyw.bbqmqd.adapter;

import android.support.annotation.DrawableRes;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;
import com.tjyw.atom.network.model.Banner;
import com.tjyw.bbqmqd.ClientQmjmApplication;
import com.tjyw.bbqmqd.R;

import java.util.ArrayList;
import java.util.List;

import atom.pub.fresco.ImageFacade;

/**
 * Created by stephen on 24/01/2018.
 */
public class ClientBannerAdapter extends PagerAdapter {

    protected List<Banner> bannerList;

    public ClientBannerAdapter() {
        bannerList = new ArrayList<Banner>();
    }

    public void addBanner(@DrawableRes int drawableRes) {
        Banner banner = new Banner();
        banner.drawableRes = drawableRes;
        bannerList.add(banner);
    }

    protected Banner get(int position) {
        return null == bannerList ? null : bannerList.get(position);
    }

    @Override
    public int getCount() {
        return null == bannerList ? 0 : bannerList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        SimpleDraweeView draweeView = (SimpleDraweeView) LayoutInflater.from(ClientQmjmApplication.getContext()).inflate(R.layout.atom_client_banner_body, null);

        Banner banner = get(position);
        if (null != banner) {
            ImageFacade.loadImage(banner.drawableRes, draweeView);
        }

        container.addView(draweeView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        return draweeView;
    }
}

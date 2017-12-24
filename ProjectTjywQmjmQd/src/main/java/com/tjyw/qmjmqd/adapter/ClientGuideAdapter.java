package com.tjyw.qmjmqd.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.tjyw.qmjmqd.ClientQmjmApplication;
import com.tjyw.qmjmqd.R;

import atom.pub.fresco.ImageFacade;

/**
 * Created by stephen on 24/12/2017.
 */
public class ClientGuideAdapter extends PagerAdapter implements View.OnClickListener {

    static final int[] GUIDE_RESOURCE = {
            R.drawable.atom_png_guide_first,
            R.drawable.atom_png_guide_second,
            R.drawable.atom_png_guide_third
    };

    public interface OnClientGuideClickListener {

        void guideOnLaunchClick(View view);
    }

    protected OnClientGuideClickListener onClientGuideClickListener;

    public ClientGuideAdapter(OnClientGuideClickListener onClientGuideClickListener) {
        this.onClientGuideClickListener = onClientGuideClickListener;
    }

    @Override
    public void onClick(View v) {
        if (null != onClientGuideClickListener) {
            onClientGuideClickListener.guideOnLaunchClick(v);
        }
    }

    @Override
    public int getCount() {
        return GUIDE_RESOURCE.length;
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
        View convertView = LayoutInflater.from(ClientQmjmApplication.getContext()).inflate(R.layout.atom_master_guide_body, null, false);
        convertView.setOnClickListener(this);
        container.addView(convertView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        SimpleDraweeView bodyImage = (SimpleDraweeView) convertView.findViewById(R.id.bodyImage);
        ImageFacade.loadImage(GUIDE_RESOURCE[position], bodyImage);

        TextView bodyStart = (TextView) convertView.findViewById(R.id.bodyStart);
//        bodyStart.setVisibility(position == 2 ? View.VISIBLE : View.GONE);

        return convertView;
    }
}
